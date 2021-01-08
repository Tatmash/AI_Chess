package com;

/**
 * This class provides functionality that maintains the calculation and validation of moves.
 *
 * @author Janos Nagy
 */
public class Move {
    /**
     * A String type variable that represents the user's move in the chess game.
     * It consists of seven characters that can be letters, numbers or spaces.
     * Character 0: A number that represents the ID of the array held in the first dimension of chessBoard[][]
     * Character 1: A number that represents the ID of the element held in the second dimension of chessBoard[][]
     * Character 0 and 1: Together they serve as coordinates that are pointing to the element in chessBoard[][] that
     * represents the piece that the user moves.
     * Character 2: A number that represents the ID of the array held in the first dimension of chessBoard[][]
     * Character 3: A number that represents the ID of the element held in the second dimension of chessBoard[][]
     * Character 2 and 3: Together they serve as coordinates that are pointing to the element in chessBoard[][] that
     * represents the piece that is being captured and replaced by the moved piece.
     * Character 4: A letter representing the captured piece, or a space if there is not capture to execute in this move.
     * Character 5: A letter "Q" that represents the piece that the pawn is being promoted to, or a space if there is no pawn
     * promotion to execute.
     * Character 6: A letter "P" that indicates that this move includes the execution of pawn promotion, or a space
     * if there is no pawn promotion to execute in this move.
     */
    private static String userMove = "";

    /**
     * A String type variable that represents the coordinates of the destination of the last move made by the user.
     * It consist of the following two characters:
     * <p>
     * Character 0: A number that represents the ID of the array held in the first dimension of chessBoard[][]
     * Character 1: A number that represents the ID of the element held in the second dimension of chessBoard[][]
     */
    private static String userLastDestination = "";

    /**
     * An accessor method that returns the user's last moves destinations coordinates.
     *
     * @return A String variable that represents the coordinates of the destination of the last move made by the user.
     */
    public static String getUserLastDestination() {
        return userLastDestination;
    }

    /**
     * A mutator method that updates the value held in the userLastDestination variable.
     *
     * @param userLastDestination A String type variable that holds the user's last destination's coordinades.
     */
    public static void setUserLastDestination(String userLastDestination) {
        Move.userLastDestination = userLastDestination;
    }

    /**
     * An accessor method that returns the userMove variable
     *
     * @return A string type variable that represents the user's move.
     */
    public static String getUserMove() {
        return userMove;
    }

    /**
     * This method appends the value held in the userMove variable with the value of the variable that it takes as a parameter.
     *
     * @param charToAdd AString type variable that holds the value that the userMove variable will be appended with.
     */
    public static void appendUserMove(String charToAdd) {
        userMove += charToAdd;
    }

    /**
     * This method empties the userMove variable.
     */
    public static void clearUserMove() {
        userMove = "";
    }

    /**
     * Creates a pawn promotion move and stores it in the userMove variable
     *
     * @param originColumn      A String value that represents the column where the pawn moves from.
     * @param destinationColumn A String value that represents the column where the pawn moves to.
     * @param capturedPiece     A String value that represents the piece that is being captured within this move.
     *                          A single letter if a capture is involved or a single space if a capture is not involved in this move.
     * @param promotedTo        A String value "Q" that represents the piece that the pawn is promoted to.
     */
    public static void createPawnPromotionMove(String originColumn, String destinationColumn, String capturedPiece, String promotedTo) {
        userMove = originColumn + destinationColumn + capturedPiece + promotedTo + Constans.DOUBLE_SPACE;
    }

    public static String legalMoves(int currentPosition, int[][][] legalMoveIndex) {                          // try recursion + legalMoves calls validMoves(currentPosition)

        String result = "";

        int originRow = currentPosition / 8;
        int originColumn = currentPosition % 8;


        for (int direction = 0; direction < legalMoveIndex.length; direction++) {
            for (int i = 0; i < legalMoveIndex[direction].length; i++) {
                int destinationRow = originRow + legalMoveIndex[direction][i][0];
                int destinationColumn = originColumn + legalMoveIndex[direction][i][1];

                if (stillOnBoard(destinationRow, destinationColumn)) {
                    if (enemyOrEmpty(destinationRow, destinationColumn)) {
                        result = result + createMoveIfKingSafe(originRow, originColumn, destinationRow, destinationColumn);
                    }

                    if (!fieldEmpty(destinationRow, destinationColumn)) {
                        i = legalMoveIndex[direction].length;
                    }
                }
            }
        }

        return result;
    }

    /**
     * The coordinates in a move may point to a destination that is not represented on the board.
     * This method checks if the destination of a move is a valid destination in terms of locality.
     *
     * @param destinationRow    An integer value that represents the horizontal coordinate of the given position.
     * @param destinationColumn An integer value that represents the vertical coordinate of the given position.
     * @return True if the coordinates are valid in terms of locality
     * False if the coordinates are not valid in terms of locality.
     */
    public static boolean stillOnBoard(int destinationRow, int destinationColumn) {
        if (
                ((destinationRow >= 0) && (destinationRow < 8)) && ((destinationColumn >= 0) && (destinationColumn < 8))
        ) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if a given position on the board is occupied by a piece and if the piece that occupies the
     * the given position belongs to the opponent team's alliance.
     *
     * @param destinationRow    An integer value that represents the horizontal coordinate of the given position.
     * @param destinationColumn An integer value that represents the vertical coordinate of the given position.
     * @return true if the given position is empty or is occupied by an enemy piece.
     * false otherwise
     */
    public static boolean enemyOrEmpty(int destinationRow, int destinationColumn) {
        return fieldEmpty(destinationRow, destinationColumn) || enemyOnField(destinationRow, destinationColumn);
    }

    /**
     * This method checks if a given position is not occupied by any pieces on the board.
     *
     * @param destinationRow    An integer value that represents the horizontal coordinate of the given position.
     * @param destinationColumn An integer value that represents the vertical coordinate of the given position.
     * @return true if the given position is empty
     * false otherwise
     */
    public static boolean fieldEmpty(int destinationRow, int destinationColumn) {
        if (Constans.EMPTY_FIELD.equals(Board.chessBoard[destinationRow][destinationColumn])) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if a given position on the board is occupied by a piece that belongs to the opponent team's alliance.
     *
     * @param destinationRow    An integer value that represents the horizontal coordinate of the given position.
     * @param destinationColumn An integer value that represents the vertical coordinate of the given position.
     * @return true if the given position is occupied by a piece that belongs to the opponent team's alliance.
     */
    public static boolean enemyOnField(int destinationRow, int destinationColumn) {
        if (Character.isLowerCase(Board.chessBoard[destinationRow][destinationColumn].charAt(0))) {
            return true;
        }
        return false;
    }

    /**
     * Creates a move if the move does not leave the user's king in check.
     *
     * @param originRow         An integer value that represents the horizontal coordinate of the position that the piece moves from.
     * @param originColumn      An integer value that represents the vertical coordinate of the given position that the piece moves from.
     * @param destinationRow    An integer value that represents the horizontal coordinate of the given position that the piece moves to.
     * @param destinationColumn An integer value that represents the vertical coordinate of the given position that the piece moves to.
     * @return A String value that represents a move that not leave the user's king in check.
     */
    public static String createMoveIfKingSafe(int originRow, int originColumn, int destinationRow, int destinationColumn) {
        String capturedPiece;
        String piece = Board.chessBoard[originRow][originColumn];
        String move = "";

        capturedPiece = Board.chessBoard[destinationRow][destinationColumn];
        Board.chessBoard[originRow][originColumn] = Constans.EMPTY_FIELD;
        Board.chessBoard[destinationRow][destinationColumn] = piece;

        King.userKingPosition = whereIsMyKing(Constans.USER_KING);
        if (Board.kingSafe()) {
            move = originRow + "" + originColumn + "" + destinationRow + "" + destinationColumn + capturedPiece + Constans.DOUBLE_SPACE;
        }
        Board.chessBoard[originRow][originColumn] = piece;
        Board.chessBoard[destinationRow][destinationColumn] = capturedPiece;

        return move;
    }

    /**
     * Finds the position of a given king.
     *
     * @param myKing A string variable that represents the king piece that's location needs to be identified.
     * @return An int value that represents the ID of the field that the given king occupies.
     */
    public static int whereIsMyKing(String myKing) {
        int kingIndex = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (myKing.equals(Board.chessBoard[i][j])) {
                    kingIndex = i * 8 + j;
                }
            }
        }
        return kingIndex;
    }


    /**
     * Checks if the given origin position matches any current legal moves's origin values.
     *
     * @param originRow    An integer value that represents the horizontal coordinate of the position that the piece moves from.
     * @param originColumn An integer value that represents the vertical coordinate of the position that the piece moves from.
     * @return true if the given origin position matches any current legal move's origin values.
     * false otherwise.
     */
    public static boolean canIMoveFromHere(int originRow, int originColumn) {
        String allLegalMoves = Board.allLegalMoves();
        for (int i = 0; i <= allLegalMoves.length() - 7; i += 7) {
            String moveToCheck = allLegalMoves.substring(i, i + 7);
            int moveToCheckOriginRow = Character.getNumericValue(moveToCheck.charAt(0));
            int moveToCheckOriginColumn = Character.getNumericValue(moveToCheck.charAt(1));
            if (moveToCheckOriginRow == originRow && moveToCheckOriginColumn == originColumn) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given destination position matches any current legal moves's destination values.
     *
     * @param destinationRow    An integer value that represents the horizontal coordinate of the position that the piece moves to.
     * @param destinationColumn An integer value that represents the vertical coordinate of the position that the piece moves to.
     * @return true if the given detination position matches any current legal move's destination values.
     * false otherwise
     */
    public static boolean canIMoveHere(int destinationRow, int destinationColumn) {
        String allLegalMoves = Board.allLegalMoves();
        for (int i = 0; i <= allLegalMoves.length() - 7; i += 7) {
            String moveToCheck = allLegalMoves.substring(i, i + 7);
            int moveToCheckOriginRow = Character.getNumericValue(moveToCheck.charAt(2));
            int moveToCheckOriginColumn = Character.getNumericValue(moveToCheck.charAt(3));
            if (moveToCheckOriginRow == destinationRow && moveToCheckOriginColumn == destinationColumn) {
                return true;
            }
        }
        return false;
    }
}
