package org.example.entities.chesspieces;

import org.example.entities.board.Board;
import org.example.entities.board.Position;
import org.example.entities.board.Tile;

import java.util.List;

public class Rook extends ChessPiece {

    public Rook(pieceColor color) {
        super(color);
    }

    @Override
    public List<Position> getValidMoves(Position currentPosition, Board board) {
        Tile currentTile = board.getTile(currentPosition.getRow(), currentPosition.getCol());
        return getStraightLineMoves(currentTile, board);
    }
}
