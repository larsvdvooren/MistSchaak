package org.example.entities.board;

import org.example.entities.chesspieces.ChessPiece;

public class Tile {
    private final int row;
    private final int col;
    private ChessPiece chessPiece; // null if no piece is present on the tile

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.chessPiece = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isEmpty() {
        return chessPiece == null;
    }

    public boolean isOccupied() {
        return !isEmpty();
    }


    // fix these two idiots
    public boolean hasFriendlyChessPiece(ChessPiece currentPlayerChessPiece) {
        return chessPiece != null && chessPiece.getColor() == currentPlayerChessPiece.getColor();
    }

    public boolean hasEnemyChessPiece(ChessPiece currentPlayerChessPiece) {
        return chessPiece != null && chessPiece.getColor() != currentPlayerChessPiece.getColor();
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }
}