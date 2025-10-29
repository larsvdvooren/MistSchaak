package org.example.entities.chesspieces;

import org.example.entities.board.Board;
import org.example.entities.board.Position;
import org.example.entities.board.Tile;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(pieceColor color) {
        super(color);
    }

    @Override
    public List<Position> getValidMoves(Position currentPosition, Board board) {
        List<Position> validMoves = new ArrayList<>();
        int row = currentPosition.getRow();
        int col = currentPosition.getCol();

        int direction = (getColor() == pieceColor.WHITE) ? -1 : 1;
        int startRow = (getColor() == pieceColor.WHITE) ? 6 : 1;

        // Normal single step
        int oneStepRow = row + direction;
        if (isValid(oneStepRow, col, board) && !board.getTile(oneStepRow, col).isOccupied()) {
            validMoves.add(new Position(oneStepRow, col));

            // double step first move
            if (row == startRow) {
                int twoStepsRow = row + 2 * direction;
                if (isValid(twoStepsRow, col, board) && !board.getTile(twoStepsRow, col).isOccupied()) {
                    validMoves.add(new Position(twoStepsRow, col));
                }
            }
        }

        // Attack move
        int[] captureCols = {col - 1, col + 1};
        for (int captureCol : captureCols) {
            int captureRow = row + direction;
            if (isValid(captureRow, captureCol, board)) {
                Tile destinationTile = board.getTile(captureRow, captureCol);
                if (destinationTile.isOccupied() && destinationTile.getChessPiece().getColor() != getColor()) {
                    validMoves.add(new Position(captureRow, captureCol));
                }
            }
        }

        return validMoves;
    }

    private boolean isValid(int row, int col, Board board) {
        return row >= 0 && row < board.getRows() && col >= 0 && col < board.getCols();
    }

    public List<Position> getAttackPositions(Position currentPosition, Board board) {
        List<Position> attackPositions = new ArrayList<>();
        int row = currentPosition.getRow();
        int col = currentPosition.getCol();
        int direction = (getColor() == pieceColor.WHITE) ? -1 : 1;

        // Diagonal attack squares (left and right)
        int[] captureCols = {col - 1, col + 1};
        for (int captureCol : captureCols) {
            int captureRow = row + direction;
            if (isValid(captureRow, captureCol, board)) {
                attackPositions.add(new Position(captureRow, captureCol));
            }
        }

        return attackPositions;
    }
}
