package com;

/**
 * This class provides the off-set coordinates of a king's potential legal moves.
 * This class is also responsible for providing a list of moves that are already validated.
 *
 * @author Janos Nagy
 */
public class King {

    /**
     * The current position af the user's king.
     */
    protected static int userKingPosition;

    /**
     * The off-set coordinates  of a king's potential legal moves.
     */
    public static int[][][] legalMoveIndex = {
            {
                    {-1, -1}
            },
            {
                    {-1, 0}
            },
            {
                    {-1, 1}
            },
            {
                    {0, 1}
            },
            {
                    {1, 1}
            },
            {
                    {1, 0}
            },
            {
                    {1, -1}
            },
            {
                    {0, -1}
            },
    };

    /**
     * This function creates a list of possible king - moves that are valid according to the rules of the game and to the current state
     * of the chessboard.
     *
     * @param currentPosition The current position of the king. This position here is represented by a single int which
     *                        refers to the ID of the field (0-63) on the board.
     * @return A single String value that holds all possible legal moves of the king.
     */
    public static String legalMoves(int currentPosition) {
        return Move.legalMoves(currentPosition, legalMoveIndex);
    }
}
