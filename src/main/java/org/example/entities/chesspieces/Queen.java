package org.example.entities.chesspieces;

import org.example.entities.board.Board;
import org.example.entities.board.Position;
import org.example.entities.board.Tile;

import java.util.List;

public class Queen extends ChessPiece {

    public Queen(pieceColor color) {
        super(color);
    }

    @Override
    public List<Position> getValidMoves(Position currentPosition, Board board) {
        Tile currentTile = board.getTile(currentPosition.getRow(), currentPosition.getCol());

        List<Position> validMoves = getStraightLineMoves(currentTile, board);
        validMoves.addAll(getDiagonalMoves(currentTile, board));
        return validMoves;
    }
}
