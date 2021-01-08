package com;

/**
 * This class contains all constant values used in the program.
 *
 * @author Janos Nagy
 */
public class Constans {
    /**
     * A constant String variable that represents an empty field on the chessboard.
     */
    public static final String EMPTY_FIELD = " ";

    /**
     * A set of constant String variables that represent the pieces that belong to the AI's alliance.
     */
    public static final String AI_BISHOP = "b";
    public static final String AI_ROOK = "r";
    public static final String AI_QUEEN = "q";
    public static final String AI_PAWN = "p";
    public static final String AI_KNIGHT = "k";
    public static final String AI_KING = "a";

    /**
     * A set of constant String variables that represent the pieces that belong to the user's alliance.
     */
    public static final String USER_BISHOP = "B";
    public static final String USER_ROOK = "R";
    public static final String USER_QUEEN = "Q";
    public static final String USER_PAWN = "P";
    public static final String USER_KNIGHT = "K";
    public static final String USER_KING = "A";

    /**
     * A constant String variable that holds a double space. It is used when creating a move that is not an en passant move.
     */
    public static final String DOUBLE_SPACE = "  ";

    /**
     * Constant integer variables that indicate whether at the given time in the game it it the AI's or user's turn to move.
     * These variables are used by the AI when evaluating the possible outcomes of moves.
     */
    public static final Integer AI = 0;
    public static final Integer HUMAN = 1;

    /**
     * Constant integer variables that hold a value that the AI uses for evaluating the board.
     */
    public static final Integer MULTIPLIIER_AI = -1;
    public static final Integer MULTIPLIIER_USER = 1;
}

