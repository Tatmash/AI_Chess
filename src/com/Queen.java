package com;

/**
 * This class provides the off-set coordinates of a queen's potential legal moves.
 * This class is also responsible for providing a list of moves that are already validated.
 *
 * @author Janos Nagy
 */
public class Queen {

    /**
     * The off-set coordinates  of a queen's potential legal moves.
     */
    public static int[][][] legalMoveIndex = {
            {
                    {-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}
            },
            {
                    {1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}
            },
            {
                    {-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}
            },
            {
                    {1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}
            },
            {
                    {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}
            },
            {
                    {0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}
            },
            {
                    {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}
            },
            {
                    {-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}
            }
    };

    /**
     * This function creates a list of possible queen - moves that are valid according to the rules of the game and to the current state
     * of the chessboard.
     *
     * @param currentPosition The current position of the queen. This position here is represented by a single int which
     *                        refers to the ID of the field (0-63) on the board.
     * @return A single String value that holds all possible legal moves of the queen.
     */
    public static String legalMoves(int currentPosition) {
        return Move.legalMoves(currentPosition, legalMoveIndex);
    }
}
