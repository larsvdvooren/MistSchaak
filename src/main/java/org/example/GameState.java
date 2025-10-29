package org.example;

import org.example.entities.board.Board;
import org.example.entities.chesspieces.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private Board board;
    private boolean whiteTurn;
    private List<ChessPiece> capturedWhitePieces; // holds the captured pieces that can be displayed, could
    private List<ChessPiece> capturedBlackPieces; // dito
    private boolean gameOver;
    private ChessPiece.pieceColor winner;

    public GameState(Board board) {
        this.board = board;
        this.whiteTurn = true;
        this.capturedWhitePieces = new ArrayList<>();
        this.capturedBlackPieces = new ArrayList<>();
        this.gameOver = false;
        this.winner = null;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void switchTurn() {
        whiteTurn = !whiteTurn;
    } // change to enum + switch case to handle player swap screen //


    // TODO \/ render next to the board, top down, maybe only show captured pieces of the other side // Could

    public void addPieceToCapturedPieces(ChessPiece capturedPiece) {
        if(capturedPiece.getColor() == ChessPiece.pieceColor.WHITE) {
            capturedWhitePieces.add(capturedPiece);
        } else {
            capturedBlackPieces.add(capturedPiece);
        }
    } // needed in future to render captured pieces // could

    public List<ChessPiece> getCapturedWhitePieces() {

        return capturedWhitePieces;
    }

    public List<ChessPiece> getCapturedBlackPieces() {

        return capturedBlackPieces;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public ChessPiece.pieceColor getWinner() {
        return winner;
    }

    public void setWinner(ChessPiece.pieceColor winner) {
        this.winner = winner;
    }
}
