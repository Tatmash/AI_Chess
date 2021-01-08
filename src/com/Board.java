package com;

import java.util.Arrays;
import java.util.Collections;

/**
 * This class is responsible for the logical representation of the chessBoard, provides functionality that maintains the consistency of
 * the board according to the current state of the game.
 *
 * @author Janos Nagy
 */
public class Board {
    static String chessBoard[][] = {

            {Constans.AI_ROOK, Constans.AI_KNIGHT, Constans.AI_BISHOP, Constans.AI_QUEEN, Constans.AI_KING, Constans.AI_BISHOP, Constans.AI_KNIGHT, Constans.AI_ROOK},
            {Constans.AI_PAWN, Constans.AI_PAWN, Constans.AI_PAWN, Constans.AI_PAWN, Constans.AI_PAWN, Constans.AI_PAWN, Constans.AI_PAWN, Constans.AI_PAWN},
            {Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD},
            {Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD},
            {Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD},
            {Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD, Constans.EMPTY_FIELD},
            {Constans.USER_PAWN, Constans.USER_PAWN, Constans.USER_PAWN, Constans.USER_PAWN, Constans.USER_PAWN, Constans.USER_PAWN, Constans.USER_PAWN, Constans.USER_PAWN},
            {Constans.USER_ROOK, Constans.USER_KNIGHT, Constans.USER_BISHOP, Constans.USER_QUEEN, Constans.USER_KING, Constans.USER_BISHOP, Constans.USER_KNIGHT, Constans.USER_ROOK}
    };

    /**
     * Reverses the order of the elements in the chessBoard[][] array.
     * Changes lower case letters to upper case and upper case letters to lower case.
     * This results in the logical representation of the user pieces from the perspective of the AI.
     * <p>
     * For example, let us assume that the user plays with white pieces and he AI plays with black pieces.
     * Calling this function logically makes the same effect as if they turned the board around 180 degrees during
     * the game and carried on playing but now player as black and the AI as white.
     * <p>
     * The AI uses this function to "think" 4 moves ahead.
     */
    public static void mirrorBoardHorizontally() {
        Collections.reverse(Arrays.asList(chessBoard));

        for (int i = 0; i < 64; i++) {

            int row = i / 8;
            int column = i % 8;

            if (Character.isUpperCase(chessBoard[row][column].charAt(0))) {
                Character temp = chessBoard[row][column].charAt(0);
                Character lowerCase = Character.toLowerCase(temp);
                chessBoard[row][column] = String.valueOf(lowerCase);
            } else if (Character.isLowerCase(chessBoard[row][column].charAt(0))) {
                Character temp = chessBoard[row][column].charAt(0);
                Character upperCase = Character.toUpperCase(temp);
                chessBoard[row][column] = String.valueOf(upperCase);
            }
        }
    }

    /**
     * Takes a move variable as a parameter and executes the move by replacing the values held in the chessBoard[][] array
     * according to the values held in the move variable.
     *
     * @param move A String type variable represents a move in the chess game.
     *             It consists seven characters that can be letters, numbers or spaces.
     *             Character 0: A number that represents the ID of the array held in the first dimension of chessBoard[][]
     *             Character 1: A number that represents the ID of the element held in the second dimension of chessBoard[][]
     *             Character 0 and 1: Together they serve as coordinates that are pointing to the element in chessBoard[][] that
     *             represents the piece that is being moved.
     *             Character 2: A number that represents the ID of the array held in the first dimension of chessBoard[][]
     *             Character 3: A number that represents the ID of the element held in the second dimension of chessBoard[][]
     *             Character 2 and 3: Together they serve as coordinates that are pointing to the element in chessBoard[][] that
     *             represents the piece that is being captured and replaced by the moved piece.
     *             Character 4: A letter representing the captured piece, or a space if there is not capture to execute in this move.
     *             Character 5: A letter "Q"that represents the piece that the pawn is being promoted to, or a space if there is no pawn
     *             promotion to execute.
     *             Character 6: A letter "P" that indicates that this move includes the execution of pawn promotion, or a space
     *             if there is no pawn promotion to execute in this move.
     */
    public static void executeMove(String move) {
        char capturedPiece = move.charAt(4);
        int originRow = Character.getNumericValue(move.charAt(0));
        int originColumn = Character.getNumericValue(move.charAt(1));
        int destinationRow = Character.getNumericValue(move.charAt(2));
        int destinationColumn = Character.getNumericValue(move.charAt(3));

        int promotionStartColumn = Character.getNumericValue(move.charAt(0));
        int promotionDestinationColumn = Character.getNumericValue(move.charAt(1));
        String newPiece = String.valueOf(move.charAt(3));

        if (capturedPiece != 'P') {                                          // captured pieces can be only lower case chars. Capital Constans.WHITE_PAWN means pawnPromotion
            if (move.charAt(6) != ' ') {
                chessBoard[destinationRow][destinationColumn] = chessBoard[originRow][originColumn];
                chessBoard[originRow][originColumn] = Constans.EMPTY_FIELD;

                int enPassanPawnRow = Character.getNumericValue(move.charAt(5));
                int enPassanPawnColumn = Character.getNumericValue(move.charAt(6));

                chessBoard[enPassanPawnRow][enPassanPawnColumn] = Constans.EMPTY_FIELD;
            } else {
                chessBoard[destinationRow][destinationColumn] = chessBoard[originRow][originColumn];
                chessBoard[originRow][originColumn] = Constans.EMPTY_FIELD;

                if (Constans.USER_KING.equals(chessBoard[destinationRow][destinationColumn])) {
                    King.userKingPosition = destinationColumn * 8 + destinationRow;     // produces fieldID
                }
            }
        } else {
            // pawnPromotion
            // startColumn, destinationColumn, capturedPiece, newPiece, P
            chessBoard[1][promotionStartColumn] = Constans.EMPTY_FIELD;
            chessBoard[0][promotionDestinationColumn] = newPiece;
        }
    }

    /**
     * Takes a move variable as a parameter and undoes the move by replacing the values held in the chessBoard[][] array
     * according to the values held in the move variable.
     *
     * @param move A String type variable represents a move in the chess game.
     *             It consists seven characters that can be letters, numbers or spaces.
     *             Character 0: A number that represents the ID of the array held in the first dimension of chessBoard[][]
     *             Character 1: A number that represents the ID of the element held in the second dimension of chessBoard[][]
     *             Character 0 and 1: Together they serve as coordinates that are pointing to the location where the moved piece is being moved back.
     *             Character 2: A number that represents the ID of the array held in the first dimension of chessBoard[][]
     *             Character 3: A number that represents the ID of the element held in the second dimension of chessBoard[][]
     *             Character 2 and 3: Together they serve as coordinates that are pointing to the element in chessBoard[][] that
     *             represents the piece that is being moved back to its original location.
     *             Character 4: A letter representing the captured piece being replaced to it's original location, or a space
     *             if there is no capture to undo in this move.
     *             Character 5: A letter "Q" that represents the piece that the pawn was promoted to, or a space if there is no pawn
     *             promotion to execute.
     *             Character 6: A letter "P" that indicates that this move includes the execution of pawn promotion, or a space
     *             if there is no pawn promotion to execute in this move.
     */
    public static void undoMove(String move) {
        if (move.charAt(4) != 'P') {
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
            if (move.charAt(6) != ' ') {
                chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = Constans.EMPTY_FIELD;
                chessBoard[Character.getNumericValue(move.charAt(5))][Character.getNumericValue(move.charAt(6))] = String.valueOf(move.charAt(4));
            } else {
                chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = String.valueOf(move.charAt(4));
            }
        } else {
            //if pawn promotion
            chessBoard[1][Character.getNumericValue(move.charAt(0))] = Constans.USER_PAWN;
            chessBoard[0][Character.getNumericValue(move.charAt(1))] = String.valueOf(move.charAt(2));
        }
    }

    /**
     * Produces a String variable that contains all legal moves of all pieces that belong to the user.
     *
     * @return A String value that contains all legal moves of all pieces that belong to the user.
     */
    public static String allLegalMoves() {
        String allLegalMoves = "";

        for (int i = 0; i < 64; i++) {
            switch (chessBoard[i / 8][i % 8]) {
                case Constans.USER_PAWN:
                    allLegalMoves += Pawn.legalMoves(i);
                    break;
                case Constans.USER_ROOK:
                    allLegalMoves += Rook.legalMoves(i);
                    break;
                case Constans.USER_KNIGHT:
                    allLegalMoves += Knight.legalMoves(i);
                    break;
                case Constans.USER_BISHOP:
                    allLegalMoves += Bishop.legalMoves(i);
                    break;
                case Constans.USER_QUEEN:
                    allLegalMoves += Queen.legalMoves(i);
                    break;
                case Constans.USER_KING:
                    allLegalMoves += King.legalMoves(i);
                    break;
            }
        }
        return allLegalMoves;//x1,y1,x2,y2,captured piece
    }

    /**
     * Determines if any pieces are able to leave the user's king in check.
     *
     * @return A boolean value that is true if at least one piece on the board is able to leave the user's king in check and
     * false if there is no piece on the board that is able to leave the user's king in check.
     */
    public static boolean kingSafe() {
        int[][][] cordinateModifier = {
                {
                        {-1, -1}, {-2, -2}, {-3, -3}, {-4, -4}, {-5, -5}, {-6, -6}, {-7, -7}, {-2, -1}
                },
                {
                        {1, -1}, {2, -2}, {3, -3}, {4, -4}, {5, -5}, {6, -6}, {7, -7}, {-1, -2}
                },
                {
                        {-1, 1}, {-2, 2}, {-3, 3}, {-4, 4}, {-5, 5}, {-6, 6}, {-7, 7}, {1, -2},
                },
                {
                        {1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}, {2, -1}
                },
                {
                        {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}, {-2, 1}
                },
                {
                        {0, -1}, {0, -2}, {0, -3}, {0, -4}, {0, -5}, {0, -6}, {0, -7}, {-1, 2}
                },
                {
                        {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}, {1, 2}
                },
                {
                        {-1, 0}, {-2, 0}, {-3, 0}, {-4, 0}, {-5, 0}, {-6, 0}, {-7, 0}, {2, 1}
                }
        };

        int kingPosition = Move.whereIsMyKing(Constans.USER_KING);

        int row = kingPosition / 8;
        int column = kingPosition % 8;

        boolean kingIsInTrouble = false;

        for (int direction = 0; direction < cordinateModifier.length; direction++) {
            for (int i = 0; i < cordinateModifier[direction].length; i++) {
                int destinationRow = row + cordinateModifier[direction][i][0];
                int destinationColumn = column + cordinateModifier[direction][i][1];

                if (Move.stillOnBoard(destinationRow, destinationColumn)) {
                    if (Move.enemyOnField(destinationRow, destinationColumn)) {
                        switch (chessBoard[destinationRow][destinationColumn]) {
                            case Constans.AI_KING:
                                if (
                                        (Math.abs(cordinateModifier[direction][i][0]) == 1 || Math.abs(cordinateModifier[direction][i][0]) == 0) &&
                                                (Math.abs(cordinateModifier[direction][i][1]) == 1 || Math.abs(cordinateModifier[direction][i][1]) == 0)
                                ) {
                                    kingIsInTrouble = true;
                                }
                                break;
                            case Constans.AI_PAWN:
                                if (
                                        Math.abs(cordinateModifier[direction][i][0]) == Math.abs(cordinateModifier[direction][i][1]) &&
                                                column != destinationColumn &&
                                                row > destinationRow &&
                                                Math.abs(cordinateModifier[direction][i][0]) == 1
                                ) {
                                    kingIsInTrouble = true;
                                }
                                break;
                            case Constans.AI_BISHOP:
                                if (
                                        Math.abs(cordinateModifier[direction][i][0]) == Math.abs(cordinateModifier[direction][i][1])
                                ) {
                                    kingIsInTrouble = true;
                                }
                                break;
                            case Constans.AI_QUEEN:
                                if (
                                        Math.abs(cordinateModifier[direction][i][0]) == Math.abs(cordinateModifier[direction][i][1]) || (
                                                Math.abs(cordinateModifier[direction][i][0]) != Math.abs(cordinateModifier[direction][i][1]) &&
                                                        (
                                                                row == destinationRow || column == destinationColumn
                                                        ))
                                ) {
                                    kingIsInTrouble = true;
                                }
                                break;
                            case Constans.AI_ROOK:
                                if (
                                        Math.abs(cordinateModifier[direction][i][0]) != Math.abs(cordinateModifier[direction][i][1]) &&
                                                (
                                                        row == destinationRow || column == destinationColumn
                                                )
                                ) {
                                    kingIsInTrouble = true;
                                }
                                break;
                            case Constans.AI_KNIGHT:
                                if (
                                        Math.abs(cordinateModifier[direction][i][0]) != Math.abs(cordinateModifier[direction][i][1]) &&
                                                (
                                                        row != destinationRow && column != destinationColumn
                                                )
                                ) {
                                    kingIsInTrouble = true;
                                }
                                break;
                            default:
                                break;
                        }
                    }

                    if (!Move.fieldEmpty(destinationRow, destinationColumn)) {
                        i = cordinateModifier[direction].length;
                    }
                    if (kingIsInTrouble) {
                        return false;
                    }
                }
            }
        }

        if (kingIsInTrouble) {
            return false;
        }
        return true;
    }
}