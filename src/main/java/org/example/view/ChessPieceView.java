package org.example.view;

import com.github.hanyaeger.api.Coordinate2D;
import org.example.entities.board.Board;
import org.example.entities.board.Position;
import org.example.entities.chesspieces.ChessPiece;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChessPieceView {
    private final Board board;
    private final double offsetX;
    private final double offsetY;
    private final int tileSize;
    private final List<ChessPieceDrawer> chessPieceDrawers;

    public ChessPieceView(Board board, int tileSize, double offsetX, double offsetY) {
        this.board = board;
        this.tileSize = tileSize;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.chessPieceDrawers = new ArrayList<>();
    }

//    // old renderer for all pieces
//    public List<ChessPieceDrawer> render() {
//        chessPieceDrawers.clear();
//        for (int row = 0; row < board.getRows(); row++) {
//            for (int col = 0; col < board.getCols(); col++) {
//                Tile tile = board.getTile(row, col);
//                ChessPiece chessPiece = tile.getChessPiece();
//                if (chessPiece != null) {
//                    double x = offsetX + col * tileSize;
//                    double y = offsetY + row * tileSize;
//                    String resource = getResourceForPiece(chessPiece);
//                    chessPieceDrawers.add(new ChessPieceDrawer(resource, new Coordinate2D(x, y), tileSize));
//                }
//            }
//        }
//        return chessPieceDrawers;
//    }

    private String getResourceForPiece(ChessPiece chessPiece) {
        String color = chessPiece.getColorNameLowerCase();
        String chessPieceType = chessPiece.getClass().getSimpleName().toLowerCase(); //queen, rook
        //return "Images/ChessPieces/" + color + "_" + chessPieceType + ".png"; // refuses to work
        return color + "_" + chessPieceType + ".png";
    }



    // Renders the Fog of War, obscuring enemy chesspieces that you cannot move towards or strike
    public List<ChessPieceDrawer> renderWithFogOfWar(boolean isWhiteTurn) {
        chessPieceDrawers.clear();
        Set<Position> visiblePositions = getVisiblePositions(isWhiteTurn);

        board.forEachTile((row, col, tile) -> {
            ChessPiece chessPiece = tile.getChessPiece();

            // Only render pieces on visible tiles
            if (chessPiece != null && visiblePositions.contains(new Position(row, col))) {
                double x = offsetX + col * tileSize;
                double y = offsetY + row * tileSize;
                String resource = getResourceForPiece(chessPiece);
                chessPieceDrawers.add(new ChessPieceDrawer(resource, new Coordinate2D(x, y), tileSize));
            }
        });
        return chessPieceDrawers;
    }

    public Set<Position> getVisiblePositions(boolean isWhiteTurn) {
        Set<Position> visiblePositions = new HashSet<>();
        ChessPiece.pieceColor currentPlayerColor = isWhiteTurn ? ChessPiece.pieceColor.WHITE : ChessPiece.pieceColor.BLACK;

        board.forEachTile((row, col, tile) -> {
            ChessPiece chessPiece = tile.getChessPiece();

            // If this chesspiece belongs to the player
            if (chessPiece != null && chessPiece.getColor() == currentPlayerColor) {
                Position piecePosition = new Position(row, col);

                // make the piece visible
                visiblePositions.add(piecePosition);

                // add all validMoves to visible tiles
                List<Position> validMoves = chessPiece.getValidMoves(piecePosition, board);
                visiblePositions.addAll(validMoves);

                // Pawns have their potential attack moves added on top of their movement
                if (chessPiece instanceof org.example.entities.chesspieces.Pawn) {
                    org.example.entities.chesspieces.Pawn pawn = (org.example.entities.chesspieces.Pawn) chessPiece;
                    List<Position> attackPositions = pawn.getAttackPositions(piecePosition, board);
                    visiblePositions.addAll(attackPositions);
                }
            }
        });
        return visiblePositions;
    }
}
