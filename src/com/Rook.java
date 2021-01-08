package com;

/**
 * This class provides the off-set coordinates of a rook's potential legal moves.
 * This class is also responsible for providing a list of moves that are already validated.
 *
 * @author Janos Nagy
 */
public class Rook {

    /**
     * The off-set coordinates of a rooks's potential legal moves.
     */
    public static int[][][] legalMoveIndex = {
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
     * This function creates a list of possible rook - moves that are valid according to the rules of the game and to the current state
     * of the chessboard.
     *
     * @param currentPosition The current position of the rook. This position here is represented by a single int which
     *                        refers to the ID of the field (0-63) on the board.
     * @return A single String value that holds all possible legal moves of the rook from the given position.
     */
    public static String legalMoves(int currentPosition) {
        return Move.legalMoves(currentPosition, legalMoveIndex);
    }
}
