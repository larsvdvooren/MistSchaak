package org.example.entities.chesspieces;

import org.example.entities.board.Board;
import org.example.entities.board.Position;
import org.example.entities.board.Tile;

import java.util.ArrayList;
import java.util.List;

public abstract class ChessPiece {
    public enum pieceColor {
        BLACK,
        WHITE
    }

    protected final pieceColor color;

    public ChessPiece(pieceColor color) {
        this.color = color;
    }

    public String getColorName() {
        return color == pieceColor.WHITE ? "White" : "Black";
    }

    public String getColorNameLowerCase() {
        return getColorName().toLowerCase();
    }

    public pieceColor getColor() {
        return color;
    }

    public abstract List<Position> getValidMoves(Position currentPosition, Board board);

    protected List<Position> getStraightLineMoves(Tile currentTile, Board board) {
        List<Position> validMoves = new ArrayList<>();
        int row = currentTile.getRow();
        int col = currentTile.getCol();

        int maxCols = board.getCols();
        int maxRows = board.getRows();

        ChessPiece currentPiece = currentTile.getChessPiece();

        // Get potential moves left
        for (int c = col -1; c >= 0; c--) {
            Tile tile = board.getTile(row, c);
            if (!tileIsValid(validMoves, tile, currentPiece, board)) break;
        }

        // Get potential moves right
        for (int c = col +1; c < maxCols; c++) {
            Tile tile = board.getTile(row, c);
            if (!tileIsValid(validMoves, tile, currentPiece, board)) break;
        }

        // Get potential moves up
        for (int r = row -1; r >= 0; r--) {
            Tile tile = board.getTile(r, col);
            if (!tileIsValid(validMoves, tile, currentPiece, board)) break;
        }

        // Get potential moves down
        for  (int r = row +1; r < maxRows; r++) {
            Tile tile = board.getTile(r, col);
            if (!tileIsValid(validMoves, tile, currentPiece, board)) break;
        }
        return validMoves;
    }

    protected List<Position> getDiagonalMoves(Tile currentTile, Board board) {
        List<Position> validMoves = new ArrayList<>();
        int row = currentTile.getRow();
        int col = currentTile.getCol();

        int maxCols = board.getCols();
        int maxRows = board.getRows();

        ChessPiece currentPiece = currentTile.getChessPiece();

        // Get potential moves up left
        for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) {
            Tile tile = board.getTile(r, c);
            if (!tileIsValid(validMoves, tile, currentPiece, board)) break;
        }

        // Get potential moves up right
        for (int r = row - 1, c = col + 1; r >= 0 && c < maxCols; r--, c++) {
            Tile tile = board.getTile(r, c);
            if (!tileIsValid(validMoves, tile, currentPiece, board)) break;
        }

        // Get potential moves down left
        for (int r = row + 1, c = col - 1; r < maxRows && c >= 0; r++, c--) {
            Tile tile = board.getTile(r, c);
            if (!tileIsValid(validMoves, tile, currentPiece, board)) break;
        }

        // Get potential moves down right
        for (int r = row + 1, c = col + 1; r < maxRows && c < maxCols; r++, c++) {
            Tile tile = board.getTile(r, c);
            if (!tileIsValid(validMoves, tile, currentPiece, board)) break;
        }
        return validMoves;
    }

    public boolean tileIsValid(List<Position> validMoves, Tile tile, ChessPiece currentPiece, Board board) {
        if (tile.isEmpty()) {
            validMoves.add(board.getPosition(tile.getRow(),  tile.getCol()));
            return true;
        } else if (tile.hasEnemyChessPiece(currentPiece)) {
            validMoves.add(board.getPosition(tile.getRow(),  tile.getCol()));
            return false; // can't go further, can capture
        } else {
            return false;
        }
    }
}
