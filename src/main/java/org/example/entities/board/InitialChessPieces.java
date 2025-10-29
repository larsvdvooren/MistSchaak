package org.example.entities.board;

import org.example.entities.chesspieces.ChessPiece;
import org.example.entities.chesspieces.Knight;
import org.example.entities.chesspieces.Pawn;
import org.example.entities.chesspieces.Rook;
import org.example.entities.chesspieces.Bishop;
import org.example.entities.chesspieces.King;
import org.example.entities.chesspieces.Queen;

public class InitialChessPieces {
    // sets up the initial chess pieces

    public static void setup(Board board) {
        int colAmount = board.getCols();
        int rowAmount = board.getRows();

        int whiteBackRow = 0;
        int whiteFrontRow = 1;
        int blackBackRow = rowAmount - 1;
        int blackFrontRow = rowAmount - 2;

        // place frontline pawns
        for (int col = 0; col < colAmount; col++) {
            board.placeChessPiece( whiteFrontRow, col, new Pawn(ChessPiece.pieceColor.BLACK));
            board.placeChessPiece(blackFrontRow, col, new Pawn(ChessPiece.pieceColor.WHITE));
        }

        // place rooks
        board.placeChessPiece(whiteBackRow, 0, new Rook(ChessPiece.pieceColor.BLACK));
        board.placeChessPiece(whiteBackRow, colAmount - 1, new Rook(ChessPiece.pieceColor.BLACK));
        board.placeChessPiece(blackBackRow, 0, new Rook(ChessPiece.pieceColor.WHITE));
        board.placeChessPiece(blackBackRow, colAmount - 1, new Rook(ChessPiece.pieceColor.WHITE));

        // place knights
        board.placeChessPiece(whiteBackRow, 1, new Knight(ChessPiece.pieceColor.BLACK));
        board.placeChessPiece(whiteBackRow, colAmount - 2, new Knight(ChessPiece.pieceColor.BLACK));
        board.placeChessPiece(blackBackRow, 1, new Knight(ChessPiece.pieceColor.WHITE));
        board.placeChessPiece(blackBackRow, colAmount - 2, new Knight(ChessPiece.pieceColor.WHITE));

        // place bishops
        board.placeChessPiece(whiteBackRow, 2, new Bishop(ChessPiece.pieceColor.BLACK));
        board.placeChessPiece(whiteBackRow, colAmount - 3, new Bishop(ChessPiece.pieceColor.BLACK));
        board.placeChessPiece(blackBackRow, 2, new Bishop(ChessPiece.pieceColor.WHITE));
        board.placeChessPiece(blackBackRow, colAmount - 3, new Bishop(ChessPiece.pieceColor.WHITE));

        // place queens
        board.placeChessPiece(whiteBackRow, 3, new Queen(ChessPiece.pieceColor.BLACK));
        board.placeChessPiece(blackBackRow, 4, new Queen(ChessPiece.pieceColor.WHITE));

        // place kings
        board.placeChessPiece(whiteBackRow, 4, new King(ChessPiece.pieceColor.BLACK));
        board.placeChessPiece(blackBackRow, 3, new King(ChessPiece.pieceColor.WHITE));

        // in case the board is wider than 8, this function fills those spots with pawns
        for (int col = 0; col < colAmount; col++) {
            if (board.getTile(whiteBackRow, col) == null) {
                board.placeChessPiece(whiteBackRow, col, new Pawn(ChessPiece.pieceColor.WHITE));
            }
            if (board.getTile(rowAmount - 1, col) == null) {
                board.placeChessPiece(blackBackRow, col, new Pawn(ChessPiece.pieceColor.BLACK));
            }
        }
    }
}

