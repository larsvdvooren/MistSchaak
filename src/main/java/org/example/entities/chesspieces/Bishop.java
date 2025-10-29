package org.example.entities.chesspieces;

import org.example.entities.board.Board;
import org.example.entities.board.Position;
import org.example.entities.board.Tile;

import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(pieceColor color) {
        super(color);
    }

    @Override
    public List<Position> getValidMoves(Position currentPosition, Board board) {
        Tile currentTile = board.getTile(currentPosition.getRow(), currentPosition.getCol());
        return getDiagonalMoves(currentTile, board);
    }
}
