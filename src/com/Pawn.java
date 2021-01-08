package com;

/**
 * This class provides the functionality of calculating legal moves of a pawn.
 * This class is also responsible for providing a list of potential moves of the pawn that are already validated.
 *
 * @author Janos Nagy
 */
public class Pawn {

    /**
     * This method creates regular moves (one step ahead) and jump moves(two steps ahead)
     *
     * @param currentPosition The current position of the pawn. This position here is represented by a single int which
     *                        refers to the ID of the field (0-63) on the board.
     * @return a String value that may contain more than one moves. The move(s) can be either regular move or
     * regular and jump move.
     */
    public static String pawnJumpOrMoveException(int currentPosition) {
        String result = "";

        int row = currentPosition / 8;
        int column = currentPosition % 8;

        if (row != 1) {
            if (Move.stillOnBoard(row - 1, column) && Move.fieldEmpty(row - 1, column)) {
                result = result + Move.createMoveIfKingSafe(row, column, row - 1, column);
            }

            if (row == 6) {
                if (Move.stillOnBoard(row - 2, column) && Move.fieldEmpty(row - 1, column) && Move.fieldEmpty(row - 2, column)) {
                    result = result + Move.createMoveIfKingSafe(row, column, row - 2, column);
                }
            }
        }

        return result;
    }

    /**
     * This method calculates potential capture moves of a pawn.
     *
     * @param currentPosition The current position of the pawn. This position here is represented by a single int which
     *                        refers to the ID of the field (0-63) on the board.
     * @return A string value that may hold more than one potential capture moves of a pawn.
     */
    public static String pawnCaptureException(int currentPosition) {
        String result = "";
        String capturedPiece = Constans.EMPTY_FIELD;

        int row = currentPosition / 8;
        int column = currentPosition % 8;
        if (row != 1) {
            if (Move.stillOnBoard(row - 1, column - 1) && Move.enemyOnField(row - 1, column - 1)) {
                result = result + Move.createMoveIfKingSafe(row, column, row - 1, column - 1);
            }
            if (Move.stillOnBoard(row - 1, column + 1) && Move.enemyOnField(row - 1, column + 1)) {
                result = result + Move.createMoveIfKingSafe(row, column, row - 1, column + 1);
            }
        }
        return result;
    }

    /**
     * This methods calculates all potential en passant moves of a pawn.
     *
     * @param currentPosition The current position of the pawn. This position here is represented by a single int which
     *                        refers to the ID of the field (0-63) on the board.
     * @return A string value that may hold more than one potential capture moves of a pawn.
     */
    public static String pawnEnPassantException(int currentPosition) {
        String result = "";
        String capturedPiece = Constans.EMPTY_FIELD;

        int row = currentPosition / 8;
        int column = currentPosition % 8;

        if (row == 3) {
            if (Move.stillOnBoard(row - 1, column - 1) &&
                    Move.enemyOnField(row, column - 1) &&
                    Move.fieldEmpty(row - 1, column - 1)
            ) {
                capturedPiece = Board.chessBoard[row][column - 1];

                if (Constans.AI_PAWN.equals(capturedPiece)) {
                    if (Board.kingSafe()) {
                        result = result + "" + row + "" + column + "" + (row - 1) + "" + (column - 1) + "" + capturedPiece + "" + row + "" + (column - 1);
                    }
                }
            }

            if (Move.stillOnBoard(row - 1, column + 1) &&
                    Move.enemyOnField(row, column + 1) &&
                    Move.fieldEmpty(row - 1, column + 1)) {
                capturedPiece = Board.chessBoard[row][column + 1];

                if (Constans.AI_PAWN.equals(capturedPiece)) {
                    if (Board.kingSafe()) {
                        result = result + "" + row + "" + column + "" + (row - 1) + "" + (column + 1) + "" + capturedPiece + "" + row + "" + (column + 1);
                    }
                }
            }
        }

        return result;
    }

    /**
     * This function determines if the given origin and destination coordinates are referring to an en passant move or not.
     *
     * @param originRow         the x coordinate of the origin position.
     * @param originColumn      the y coordinate of the origin position.
     * @param destinationRow    the x coordinate of the destination position.
     * @param destinationColumn the y coordinate of the destination position.
     * @return true if the if the given origin and destination coordinates are referring to an en passant move.
     * false otherwise
     */
    public static boolean isEnPassantMove(int originRow, int originColumn, int destinationRow, int destinationColumn) {
        if (Constans.USER_PAWN.equals(Board.chessBoard[originRow][originColumn]) &&
                Constans.AI_PAWN.equals(Board.chessBoard[originRow][destinationColumn]) &&
                Constans.EMPTY_FIELD.equals(Board.chessBoard[destinationRow][destinationColumn]) &&
                destinationRow == 2) {
            return true;
        }
        return false;
    }

    /**
     * Ths function calculates and returns all potential pawn promotion moves of a pawn from a given position.
     *
     * @param currentPosition The current position of the pawn. This position here is represented by a single int which
     *                        refers to the ID of the field (0-63) on the board.
     * @return a String value that holds all possible pawn promotion moves of a pawn.
     */
    public static String pawnPromotionException(int currentPosition) {
        String result = "";

        int row = currentPosition / 8;
        int column = currentPosition % 8;

        if (row == 1) {
            if (Move.stillOnBoard(row - 1, column) && Move.fieldEmpty(row - 1, column)) {
                result = createMoveIfKingSafe(row, column, row - 1, column);
            }
            if (Move.stillOnBoard(row - 1, column - 1) && Move.enemyOnField(row - 1, column - 1)) {
                result = createMoveIfKingSafe(row, column, row - 1, column - 1);
            }
            if (Move.stillOnBoard(row - 1, column + 1) && Move.enemyOnField(row - 1, column + 1)) {
                result = createMoveIfKingSafe(row, column, row - 1, column + 1);
            }
        }

        return result;
    }

    /**
     * This function checks if the given origin and destination coordinates are referring to a move that does
     * not leave the user's king in check.
     *
     * @param originRow         the x coordinate of the origin position.
     * @param originColumn      the y coordinate of the origin position.
     * @param destinationRow    the x coordinate of the destination position.
     * @param destinationColumn the y coordinate of the destination position.
     * @return A String value that holds a move that does not leave the user's king in check.
     * An empty String ("") if the given coordinates are referring to a move that does leave the
     * user's king in check
     */
    public static String createMoveIfKingSafe(int originRow, int originColumn, int destinationRow, int destinationColumn) {
        String move = "";
        String capturedPiece = Board.chessBoard[destinationRow][destinationColumn];

        Board.chessBoard[originRow][originColumn] = Constans.EMPTY_FIELD;
        Board.chessBoard[destinationRow][destinationColumn] = Constans.USER_PAWN;
        if (Board.kingSafe()) {
            move = originColumn + "" + destinationColumn + "" + capturedPiece + "" + Constans.USER_QUEEN + "P" + Constans.DOUBLE_SPACE;
        }
        Board.chessBoard[originRow][originColumn] = Constans.USER_PAWN;
        Board.chessBoard[destinationRow][destinationColumn] = capturedPiece;

        return move;
    }

    /**
     * This function collects and returns all potential legal and valid moves of a pawn from a given position.
     *
     * @param currentPosition The current position of the pawn. This position here is represented by a single int which
     *                        refers to the ID of the field (0-63) on the board.
     * @return A String value that holds all potential legal and valid moves of a pawn from a given position.
     */
    public static String legalMoves(int currentPosition) {
        String collectedLegalMoves = pawnJumpOrMoveException(currentPosition);
        collectedLegalMoves = collectedLegalMoves + pawnCaptureException(currentPosition);
        collectedLegalMoves = collectedLegalMoves + pawnEnPassantException(currentPosition);
        collectedLegalMoves = collectedLegalMoves + pawnPromotionException(currentPosition);
        return collectedLegalMoves;
    }
}