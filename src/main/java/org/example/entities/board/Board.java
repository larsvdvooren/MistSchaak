package org.example.entities.board;

import org.example.GameState;
import org.example.entities.chesspieces.ChessPiece;

import java.util.List;

public class Board {
    private final Tile[][] tiles;
    private final Position[][] positions;

    public Board(int boardSize) {
        tiles = new Tile[boardSize][boardSize]; // in case 'Big Chess' changes the rules of chess to sell more chessboards
        this.positions = new Position[boardSize][boardSize];
        initializeTiles();
    }

    private void initializeTiles() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                tiles[row][col] = new Tile(row, col);
                positions[row][col] = new Position(row, col);
            }
        }
    }

    public Position getPosition(int row, int col) {
        return positions[row][col];
    }

    public int getRows() {
        return tiles.length;
    }

    public int getCols() {
        return tiles[0].length;
    }

    public Tile getTile(int row, int col) {
        if (row < 0 || row >= tiles.length || col < 0 || col >= tiles[row].length) {
            throw new IllegalArgumentException("invalid row or col: " + row + ", " + col);
        }
        return tiles[row][col];
    }

    public void placeChessPiece(int row, int col, ChessPiece chessPiece) {
        Tile tile = getTile(row, col);
        if (!tile.isEmpty()) {
            throw new IllegalStateException("Tile is already occupied: " + row + ", " + col);
        }
        tile.setChessPiece(chessPiece);
    }

    public void moveChessPiece(ChessPiece chessPiece, Tile from, Tile to) {
        to.setChessPiece(chessPiece);
        from.setChessPiece(null);
    }

    public void takeChessPiece(int row, int col) {
        getTile(row, col).setChessPiece(null);
    }

    public boolean moveChessPieceIfValid(int startRow, int startCol, int destinationRow, int destinationCol, GameState gameState) {
        Tile fromTile = getTile(startRow, startCol);
        Tile toTile = getTile(destinationRow, destinationCol);
        ChessPiece chessPiece = fromTile.getChessPiece();

        if (chessPiece == null) {
            return false; // no chesspiece here
        }

        List<Position> validMoves = chessPiece.getValidMoves(new Position(startRow, startCol), this);
        boolean moveIsValid = validMoves.stream().anyMatch(position -> position.getRow() == destinationRow && position.getCol() == destinationCol);

        if (!moveIsValid) {
            return false;
        }

        // Check if capturing a piece (including king)
        if(!toTile.isEmpty()) {
            ChessPiece capturedPiece = toTile.getChessPiece();
            gameState.addPieceToCapturedPieces(capturedPiece);

            // Check if captured piece is a king - game over!
            if (capturedPiece instanceof org.example.entities.chesspieces.King) {
                gameState.setGameOver(true);
                gameState.setWinner(chessPiece.getColor()); // Current player wins
            }
        }

        moveChessPiece(chessPiece, fromTile, toTile);
        return true;
    }


    public void forEachTile(TileConsumer consumer) {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                consumer.accept(row, col, tiles[row][col]);
            }
        }
    }

    @FunctionalInterface
    public interface TileConsumer {
        void accept(int row, int col, Tile tile);
    }

}