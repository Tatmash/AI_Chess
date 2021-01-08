package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.MenuSelectionManager.defaultManager;

/**
 * This class provides a graphical user interface.
 *
 * @author Janos Nagy
 */
public class Table extends Component {
    /**
     * The frame of our chessboard
     */
    private final JFrame gameFrame;
    /**
     * The chessboard that contains the fields/tiles
     */
    private final BoardPanel boardPanel;
    /**
     * true if the player's alliance is black, false otherwise
     */
    private boolean isPlayerAllianceBlack;

    /**
     * dimensions of the frame
     */
    private static final Dimension FRAME_DIMENSION = new Dimension(600, 600);
    /**
     * dimensions of the board panel
     */
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(600, 600);
    /**
     * dimensions of the field panel
     */
    private static final Dimension FIELD_PANEL_DIMENSION = new Dimension(10, 10);

    /**
     * constructs a new table
     */
    public Table() {
        this.gameFrame = new JFrame("Chess AI");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar myMenuBar = new JMenuBar();
        createMenuBar(myMenuBar);
        this.gameFrame.setJMenuBar(myMenuBar);
        this.gameFrame.setSize(FRAME_DIMENSION);
        this.gameFrame.setResizable(false);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.isPlayerAllianceBlack = false;

    }

    /**
     * getter method for the isPlayerAllianceBlack variable
     *
     * @return the isPlayerAllianceBlack variable
     */
    public boolean getIsPlayerAllianceBlack() {
        return isPlayerAllianceBlack;
    }

    public JFrame getGameFrame() {
        return this.gameFrame;
    }

    /**
     * Creates the menubar in the GUI.
     *
     * @param myMenuBar the menubar element
     */
    private void createMenuBar(final JMenuBar myMenuBar) {
        myMenuBar.add(createFileMenu());
        myMenuBar.add(createAllianceMenu());
    }

    /**
     * Creates the alliance menu in the menubar with two radio buttons (black and white) and an OK button.
     *
     * @return the alliance menu as a Jmenu type.
     */
    private JMenu createAllianceMenu() {
        final JMenu allianceMenu = new JMenu("Alliance");
        final JRadioButton blackAlliance = new JRadioButton("The Messengers of Death        (Black)");
        final JRadioButton whiteAlliance = new JRadioButton("The Sacred Warriors of Truth   (White)");
        final ButtonGroup allianceGroup = new ButtonGroup();
        allianceGroup.add(blackAlliance);
        allianceGroup.add(whiteAlliance);
        allianceMenu.add(blackAlliance);
        allianceMenu.add(whiteAlliance);
        whiteAlliance.setSelected(true);
        whiteAlliance.setActionCommand("white");
        blackAlliance.setActionCommand("black");

        JButton okButton = new JButton("OK");
        allianceMenu.add(okButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (allianceGroup.getSelection().getActionCommand() == "black") {

                    Table.this.isPlayerAllianceBlack = true;
                    System.out.println(allianceGroup.getSelection().getActionCommand());
                    boardPanel.drawBoard();
                    defaultManager().clearSelectedPath();
                    //TilePanel.drawTile();


                } else if (allianceGroup.getSelection().getActionCommand() == "white") {

                    Table.this.isPlayerAllianceBlack = false;
                    System.out.println(allianceGroup.getSelection().getActionCommand());
                    boardPanel.drawBoard();
                    defaultManager().clearSelectedPath();
                    //drawTile();
                }
            }
        });


        return allianceMenu;
    }

    /**
     * Creates a file menu in the manubar with an exit and a new game option.
     *
     * @return
     */
    private JMenu createFileMenu() {

        final JMenu fileMenu = new JMenu("File");
        final JMenuItem newGame = new JMenuItem("New Game");
        final JMenuItem exitMenuItem = new JMenuItem("Exit");

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);


        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int confirm = JOptionPane.showConfirmDialog(Table.this,
                        "Staring a new game. Your current game will be lost. \n \nDo you wish to continue?\n ",
                        "New Game",
                        JOptionPane.OK_CANCEL_OPTION);

                if (confirm == JOptionPane.OK_OPTION) {
                    startNewGame();
                }
            }
        });
        fileMenu.add(newGame);

        return fileMenu;
    }

    /**
     * resets the board, stars a new game.
     */
    private void startNewGame() {
        Board.chessBoard = new String[][]{
                {"r", "k", "b", "q", "a", "b", "k", "r"},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {"R", "K", "B", "Q", "A", "B", "K", "R"}
        };

        Table.this.gameFrame.dispose();
        Table newTable = new Table();
    }


    public class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        /**
         * Constructs a board panel.
         */
        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();                                    // list of tiles on the board, should it be an array[64]??
            for (int i = 0; i < 64; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);      // orders this tile to it's tileID
                this.boardTiles.add(tilePanel);                                     // adds the tile to the list
                add(tilePanel);                                                     // adds the tile to this board
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        /**
         * executes of the graphical representation of the chessboard.
         */
        public void drawBoard() {
            removeAll();
            for (final TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile();
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    /**
     * the y coordinate of the origin in a move
     */
    public int originColumn;
    /**
     * the x coordinate of the origin in a move
     */
    public int originRow;
    /**
     * the y coordinate of the destination in a move
     */
    public int destinationColumn;
    /**
     * the x coordinate of the destination in a move
     */
    public int destinationRow;

    /**
     * Determines if the user is able to execute a valid move from the chosen position. If yes, it changes the background colour
     * of the given tile panel to dark green.
     *
     * @param tile         a TilePanel object where the move starts from
     * @param originRow    the x coordinate of the origin coordinate in a move
     * @param originColumn the y coordinate of the origin coordinate in a move
     * @return true if the user is able to execute a valid move from the chosen position.
     * false otherwise.
     */
    private boolean firstClickHandler(TilePanel tile, int originRow, int originColumn) {
        if (!Move.canIMoveFromHere(originRow, originColumn)) {
            Move.clearUserMove();
            return false;
        }

        tile.setBackground(new Color(0x0F5716));
        Move.appendUserMove(String.valueOf(originRow));
        Move.appendUserMove(String.valueOf(originColumn));
        return true;
    }

    /**
     * Creates a move when the second click happens if it's a valid move.
     *
     * @param originRow         the x coordinate of the origin in a move
     * @param originColumn      the y coordinate of the origin in a move
     * @param destinationRow    the x coordinate of the destination in a move
     * @param destinationColumn the y coordinate of the destination in a move
     */
    private void createMovesOnSecondClick(int originRow, int originColumn, int destinationRow, int destinationColumn) {
        if (destinationRow == 0 && originRow == 1 && Constans.USER_PAWN.equals(Board.chessBoard[originRow][originColumn])) {
            //pawn promotion
            Move.createPawnPromotionMove(String.valueOf(originColumn),
                    String.valueOf(destinationColumn),
                    Board.chessBoard[destinationRow][destinationColumn],
                    "QP");
        } else {
            //regular move
            Move.appendUserMove(String.valueOf(destinationRow));
            Move.appendUserMove(String.valueOf(destinationColumn));

            if (Pawn.isEnPassantMove(originRow, originColumn, destinationRow, destinationColumn)) {
                Move.appendUserMove(Board.chessBoard[originRow][destinationColumn]);
                Move.appendUserMove(String.valueOf(originRow));
                Move.appendUserMove(String.valueOf(destinationColumn));
            } else {
                Move.appendUserMove(Board.chessBoard[destinationRow][destinationColumn]);
                Move.appendUserMove(Constans.DOUBLE_SPACE);
            }
        }

        String move = Move.getUserMove();
        if (!Move.canIMoveFromHere(originRow, originColumn) || !Move.canIMoveHere(destinationRow, destinationColumn)) {
            Move.clearUserMove();
            Move.setUserLastDestination("");
        }
    }

    /**
     * creates a pop up window when the user is in checkmate. offers a new game or exit.
     *
     * @param message the message written in the pop up window.
     * @param title   the title of the pop up window.
     */
    private void checkmatePopUp(String message, String title) {
        Object[] options = {"New Game", "Exit"};
        int response = JOptionPane.showOptionDialog(gameFrame,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (response == 0) {
            startNewGame();
        } else {
            System.exit(0);
        }
    }

    /**
     * Checks is a gien move is on the list of all potential valid moves.
     *
     * @param possibleMoves the list of all potential valid moves on the board.
     * @param move          the move to check
     * @return true if the list contains the given move
     * false otherwise
     */
    private boolean moveValidator(String possibleMoves, String move) {
        if (possibleMoves.replaceAll(move, "").length() < possibleMoves.length()) {
            return true;
        }
        return false;
    }

    /**
     * creates and validates the AI's next move
     */
    private void aiMove() {
        Board.mirrorBoardHorizontally();
        String move = Decision.alphaBeta(Decision.globalDepth, 1000000, -1000000, "", Constans.AI);
        String aiMovePossibilities = Board.allLegalMoves();

        System.out.println("AI possible moves: ");
        System.out.println(aiMovePossibilities);
        System.out.println(move + " possible length: " + aiMovePossibilities.length());

        if (aiMovePossibilities.length() == 0) {
            checkmatePopUp("Congratulations! Your opponent is in Checkmate.", "You won.");
        } else {
            if (moveValidator(aiMovePossibilities, move.substring(0, 7))) {
                Board.executeMove(move);
                Move.setUserLastDestination("");
            } else {
                System.out.println("the AI is trying to cheat!");
            }
        }

        Board.mirrorBoardHorizontally();
    }

    /**
     * This class provides the graphical representation of each field on the chessboard.
     */
    private class TilePanel extends JPanel {
        /**
         * an int value that identifies each field (0-63)
         */
        private final int tileID;

        /**
         * Constructor for the TilePanel class
         *
         * @param boardPanel the boardPanel that the fields are on
         * @param tileID     an int value that identifies each field (0-63
         */
        TilePanel(final BoardPanel boardPanel,
                  final int tileID) {

            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(FIELD_PANEL_DIMENSION);

            assignTileColor();

            if (getIsPlayerAllianceBlack()) {
                playerAsBlack();
            }
            if (!getIsPlayerAllianceBlack()) {
                playerAsWhite();
            }


            addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(final MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1 && Move.getUserMove() == "") {
                        Move.setUserLastDestination("");
                        boardPanel.drawBoard();

                        System.out.println(" FIRST CLICK ");
                        originRow = tileID / 8;
                        originColumn = tileID % 8;

                        TilePanel tile = (TilePanel) e.getSource();
                        firstClickHandler(tile, originRow, originColumn);

                        System.out.println("origin : " + Move.getUserMove());
                    } else if (e.getButton() == MouseEvent.BUTTON1 && Move.getUserMove().length() == 2) {
                        System.out.println(" SECOND CLICK ");
                        destinationRow = tileID / 8;
                        destinationColumn = tileID % 8;


                        createMovesOnSecondClick(originRow, originColumn, destinationRow, destinationColumn);

                        System.out.println("destination : " + destinationRow + " " + destinationColumn);
                        System.out.println("dragMove : " + Move.getUserMove());
                        String userLegalsMoves = Board.allLegalMoves();

                        if (userLegalsMoves.length() == 0) {
                            checkmatePopUp("Checkmate. Would you like to play another game?", "Checkmate.");
                        }

                        System.out.println(userLegalsMoves);

                        if (moveValidator(userLegalsMoves, Move.getUserMove())) {
                            Board.executeMove(Move.getUserMove());
                            boardPanel.drawBoard();
                            aiMove();
                        }

                        if (Move.getUserMove().length() >= 4) {
                            Move.setUserLastDestination(Move.getUserMove());
                            Move.clearUserMove();
                        }

                        boardPanel.drawBoard();
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(final MouseEvent mouseEvent) {

                }
            });

            validate();
        }

        private int getTileID() {
            return this.tileID;
        }

        /**
         * assings the alternating light-gray and dark-gray colours of the fields of the chessboard
         */
        private void assignTileColor() {
            if (((this.tileID >= 0 && this.tileID <= 7 ||
                    this.tileID >= 16 && this.tileID <= 23 ||
                    this.tileID >= 32 && this.tileID <= 39 ||
                    this.tileID >= 48 && this.tileID <= 55) &&
                    this.tileID % 2 == 0)
                    ||
                    (this.tileID >= 8 && this.tileID <= 15 ||
                            this.tileID >= 24 && this.tileID <= 31 ||
                            this.tileID >= 40 && this.tileID <= 47 ||
                            this.tileID >= 56 && this.tileID <= 63) &&
                            this.tileID % 2 == 1) {

                setBackground(new Color(0xA5A3A5));
            } else {
                setBackground(new Color(0x4F4E4E));
            }
            String move = Move.getUserLastDestination();

            if (!Board.kingSafe() && Constans.USER_KING.equals(Board.chessBoard[tileID / 8][tileID % 8])) {
                setBackground(new Color(0x5E0C0B));
                Move.clearUserMove();
            }

            if (move.length() > 4) {
                int destinationRow = Character.getNumericValue(move.charAt(2));
                int destinationColumn = Character.getNumericValue(move.charAt(3));

                if (tileID == destinationRow * 8 + destinationColumn) {
                    if (Move.canIMoveFromHere(destinationRow, destinationColumn)) {
                        setBackground(new Color(0x6E6C6E));
                    }
                }
            }
        }

        /**
         * the graphical representation of each field on the chessboard
         */
        public void drawTile() {
            assignTileColor();

            if (getIsPlayerAllianceBlack()) {
                playerAsBlack();

            } else if (!getIsPlayerAllianceBlack()) {
                playerAsWhite();
            }
            //highlightLegals(board);
            validate();
            repaint();
        }

        /**
         * assigns white pieces to the user and black to the AI.
         */
        private void playerAsWhite() {
            this.removeAll();
            for (int i = 0; i <= 63; i++) {
                //int j=-1,k=-1;
                switch (Board.chessBoard[i / 8][i % 8]) {
                    case "P":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WP.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "p":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        ImageIO.read(new File("images/BP.png"));
                                /*this.*/
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "R":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        ImageIO.read(new File("images/WR.png"));
                                /*this.*/
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "r":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BR.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "K":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WN.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "k":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BN.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "B":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WB.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "b":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BB.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "Q":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WQ.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "q":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BQ.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "A":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WK.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "a":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BK.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        }

        /**
         * assigns white pieces to the AI and black to the user.
         */
        private void playerAsBlack() {
            this.removeAll();
            for (int i = 0; i <= 63; i++) {
                //int j=-1,k=-1;
                switch (Board.chessBoard[i / 8][i % 8]) {
                    case "p":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WP.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "P":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        ImageIO.read(new File("images/BP.png"));
                                /*this.*/
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "r":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        ImageIO.read(new File("images/WR.png"));
                                /*this.*/
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case "R":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BR.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "k":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WN.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "K":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BN.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "b":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WB.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "B":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BB.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "q":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WQ.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "Q":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BQ.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "a":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/WK.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "A":
                        if (i == this.tileID) {
                            try {
                                final BufferedImage image =
                                        /*this.*/ImageIO.read(new File("images/BK.png"));
                                add(new JLabel(new ImageIcon(image)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        }
    }
}
