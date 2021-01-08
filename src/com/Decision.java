package com;
/**
 * This class provides functionality for choosing moves that the machine responds with to user moves.
 * Tis functionality is provided by implementing the Minimax and Alpha - Beta Pruning algorithms and their
 * static evaluator function(s).
 *
 * @author Janos Nagy
 */

import static com.Board.mirrorBoardHorizontally;

public class Decision {
    /**
     * This variable determines how many times alphaBeta() calls itself.
     * It represents the amount of steps that aplphaBeta() thinks ahead.
     */
    static int globalDepth = 4;

    /**
     * Evaluates the board by adding up the pre-defined values of all pieces that belong to one alliance
     * and compares it to the sum of pre-defined values of the other alliance's pieces.
     *
     * @param depth This parameter indicates the level of the decision tree that this function was called on.
     * @return An integer value that represents the difference between the sum of all values of the pieces of the two alliances.
     */
    public static int getMaterialEvaluation(int depth) {

        int result = 0;
        result += getMaterialValue();
        mirrorBoardHorizontally();
        result -= getMaterialValue();
        mirrorBoardHorizontally();
        return -(result + depth * 50);
    }

    /**
     * In the alphaBeta() function, player variable can have two values. These two values indicate in which alliance's
     * perspective the board is being evaluated. togglePlayer() is used in alphaBeta() for changing the value of the player
     * variable in an alternating fashion.
     *
     * @param player Holds an integer value that indicates in which alliance's perspective the board is being evaluated.
     * @return An integer value zero if the taken parameter's value (player) was one.
     * An integer value one if the taken parameter's value (player) was zero.
     */
    private static int togglePlayer(int player) {
        if (player == Constans.AI) {
            return Constans.HUMAN;
        }
        return Constans.AI;
    }

    /**
     * In the alphaBeta() function, the sum of the values of all pieces of one alliance is multiplied by 1 or -1
     * depending on which alliance's perspective is being evaluated. getMultiplier() returns the multiplier corresponding
     * to the integer value taken as parameter.
     *
     * @param player Holds an integer value that indicates in which alliance's perspective the board is being evaluated.
     * @return An integer value either 1 or -1.
     */
    private static int getMultiplier(int player) {
        if (player == Constans.AI) {
            return Constans.MULTIPLIIER_AI;
        }
        return Constans.MULTIPLIIER_USER;
    }

    /**
     * This method returns the appropriate move according to the depth of the decision tree that it was called on.
     *
     * @param result alphaBeta() returns a String that contains a move (the first 7 characters) and a value that
     *               the move was associated with (the characters after the seventh).
     *               The result parameter is a String value that represents a move that was extracted from the return
     *               value of the alphaBeta() function.
     * @param move   is a String value that represents a move that is a response to another move at a previous layer of
     *               the decision tree. Initially, at level 4 in the decision tree, this value is an empty string: "".
     * @param depth  an int value that represents the level of the decision tree that this function was called on.
     * @return returns the String value held in result parameter if the depth of the decision tree equals to the
     * global depth (4) or
     * returns the String value held in the move parameter otherwise.
     */
    private static String getMoveOnDepth(String result, String move, int depth) {
        if (depth == globalDepth) {
            return result;
        }
        return move;
    }

    /**
     * This function ensures that the minimax algorithm's functionality in our alpha-beta pruning algorithm operates
     * correctly. Meaning that in the minimizing layers of the decision tree this function returns a move that is
     * associated with an integer value that is lower than or equals to the integer value held in the beta parameter.
     * While in maximizing layers it returns a moe that is  associated with an integer value that is larger than the
     * integer value held in the alpha parameter.
     *
     * @param alpha  the current int value of alpha in the alphaBeta() function.
     * @param beta   the current int value of beta in the alphaBeta() function.
     * @param depth  an int value that represents the level of the decision tree that this function was called on.
     * @param result the String value that the previous alphaBeta() call returned. Contains a move(char 0-6) and a value
     *               assigned to it (char 7-*).
     * @param move   is a String value that represents a move that is a response to another move at a previous layer of
     *               the decision tree. Initially, at level 4 in the decision tree, this value is an empty string: "".
     *               In getMinMaxMove this variable will be assigned a new value so that it holds the chosen move
     *               in the current layer of the decision tree.
     * @param player is an int value either 0 or 1. In this context it indicates if he current layer is a minimizing or
     *               a maximizing layer.
     * @return a String value that holds the chosen move.
     */
    private static String getMinMaxMove(int alpha, int beta, int depth, String result, String move, int player) {
        if (player == 0) {
            if (getMoveValue(result) <= beta) {
                move = getMoveOnDepth(result.substring(0, 7), move, depth);
            }
        } else {
            if (getMoveValue(result) > alpha) {
                move = getMoveOnDepth(result.substring(0, 7), move, depth);
            }
        }
        return move;
    }

    /**
     * This method compares the int value held in alpha parameter to the value (held as a String) in result parameter and assigns the value
     * found in result to alpha if the value found in result is larger.
     *
     * @param alpha  the current int value of alpha in the alphaBeta() function.
     * @param result the String value that the previous alphaBeta() call returned. Contains a move(char 0-6) and a value
     *               assigned to it (char 7-*).
     * @return an int that represents the updated value of alpha.
     */
    // alpha
    private static int getMax(int alpha, String result) {
        if (getMoveValue(result) > alpha) {
            alpha = getMoveValue(result);
        }
        return alpha;
    }

    /**
     * This method compares the int value held in beta parameter to the value (held as a String) in result parameter
     * and assigns the value found in result to beta if the value found in result is smaller than or equals to beta.
     *
     * @param beta   the current int value of beta in the alphaBeta() function.
     * @param result the String value that the previous alphaBeta() call returned. Contains a move(char 0-6) and a value
     *               assigned to it (char 7-*).
     * @return an int that represents the updated value of beta.
     */
    // beta
    private static int getMin(int beta, String result) {
        if (getMoveValue(result) <= beta) {
            beta = getMoveValue(result);
        }
        return beta;
    }

    /**
     * This function orders the beta value to the move taken as a parameter if player parameter's current value is 0 and
     * orders the alpha value to the move if player parameter's current value is 1.
     *
     * @param move   The chosen move.
     * @param alpha  the current int value of alpha in the alphaBeta() function.
     * @param beta   the current int value of beta in the alphaBeta() function.
     * @param player is an int value either 0 for AI or 1 for user.
     * @return A String value consisting of 7+ characters. The first 7 characters represent the move and the rest of the
     * characters represent the value assigned to it. The assigned value is alpha if the current player is AI.
     * Tha assigned value is beta if the current player is user.
     */
    private static String moveIfPlayerIsAi(String move, int alpha, int beta, int player) {
        if (player == Constans.AI) {
            move = move + beta;
        } else {
            move = move + alpha;
        }
        return move;
    }

    /**
     * This method extracts the value associated with the move if any, from the move variable taken as a parameter.
     *
     * @param move String that contains a move (the first 7 characters) and a value that the move was associated
     *             with (the characters after the seventh).
     * @return If the taken parameter holds a String value that is longer than 7 characters the function returns an int
     * value that represents the result of the static evaluation of the move.
     * Otherwise it returns the whole move variable as an int.
     */
    private static int getMoveValue(String move) {
        if (move.length() > 7) {
            return Integer.valueOf(move.substring(7));
        }
        return Integer.valueOf(move);
    }

    /**
     * This recursive function is responsible for selecting the most optimal move that can be a
     * response from the AI to the user's move.
     *
     * @param depth  an int value that represents the level of the decision tree that this function was called on.
     *               must be between (0-4)
     * @param beta   holds the int value of the currently most valuable move at minimizing levels. It is set to 1000000 initially.
     * @param alpha  holds the int value of the currently most valuable move at maximizing levels. It is set to -1000000 initially.
     * @param move   a String value that represents a potential legal move that is currently being evaluated. It is set to an empty
     *               String ("") initially.
     * @param player is an int value either 0 for AI or 1 for user. It represents from whom perspective the board is
     *               being evaluated at the most recent call af the alphaBeta() function.
     * @return A String value that represents the chosen move (character 0-6) and value associated with it (char 7-*)
     */
    public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
        String allLegalMoves = Board.allLegalMoves();
        if (depth == 0 || allLegalMoves.length() == 0) {
            int multiplier = getMultiplier(player);
            return move + (Decision.getMaterialEvaluation(depth) * multiplier);
        }
        player = togglePlayer(player);
        for (int i = 0; i < allLegalMoves.length(); i += 7) {
            Board.executeMove(allLegalMoves.substring(i, i + 7));
            mirrorBoardHorizontally();
            String result = alphaBeta(depth - 1, beta, alpha, allLegalMoves.substring(i, i + 7), player);
            mirrorBoardHorizontally();
            Board.undoMove(allLegalMoves.substring(i, i + 7));

            move = getMinMaxMove(alpha, beta, depth, result, move, player);

            if (player == Constans.AI) {
                beta = getMin(beta, result);
            } else {
                alpha = getMax(alpha, result);
            }
            if (alpha >= beta) {
                return moveIfPlayerIsAi(move, alpha, beta, player);
            }
        }
        return moveIfPlayerIsAi(move, alpha, beta, player);
    }

    /**
     * This method calculates the value of all the pieces that currently belongs to the user's alliance.
     *
     * @return An int value that represents the value of all the pieces that currently belongs to the user's alliance.
     */
    public static int getMaterialValue() {
        int result = 0;
        for (int i = 0; i < 64; i++) {
            switch (Board.chessBoard[i / 8][i % 8]) {
                case Constans.USER_PAWN:
                    result += 100;
                    break;
                case Constans.USER_KNIGHT:
                case Constans.USER_BISHOP:
                    result += 300;
                    break;
                case Constans.USER_ROOK:
                    result += 500;
                    break;
                case Constans.USER_QUEEN:
                    result += 1000;
                    break;
                case Constans.USER_KING:
                    result += 10000;
                    break;
            }
        }
        return result;
    }
}
