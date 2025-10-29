package org.example.entities.chesspieces;

import org.example.entities.board.Board;
import org.example.entities.board.Position;
import org.example.entities.board.Tile;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece{
    public  King(pieceColor color) {
        super(color);
    }

    @Override
    public List<Position> getValidMoves(Position currentPosition, Board board) {
        List<Position> validMoves = new ArrayList<>();
        int row = currentPosition.getRow();
        int col = currentPosition.getCol();


        int[][] moves = { // possible directions to move
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (isValid(newRow, newCol, board)) {
                Tile destinationTile = board.getTile(newRow, newCol);
                if (destinationTile.isOccupied()) {
                    if (destinationTile.getChessPiece().getColor() != this.getColor()) {
                        validMoves.add(new Position(newRow, newCol));
                    }
                } else {
                    validMoves.add(new Position(newRow, newCol));
                }
            }
        }

        return validMoves;
    }

    private boolean isValid(int row, int col, Board board) {
        return row >= 0 && row < board.getRows() && col >= 0 && col < board.getCols();
    }
}
