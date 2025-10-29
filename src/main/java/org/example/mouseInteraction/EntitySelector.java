package org.example.mouseInteraction;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.example.GameState;
import org.example.MistSchaak;
import org.example.entities.board.Board;
import org.example.entities.board.Position;
import org.example.entities.board.Tile;
import org.example.entities.chesspieces.ChessPiece;
import org.example.scenes.GameScene;
import org.example.view.TileDrawer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntitySelector extends RectangleEntity implements MouseButtonPressedListener {

    private ChessPiece selectedPiece;
    private Tile selectedTile;
    private final Board board;
    private final TileDrawer[][] tileDrawers;
    private final int tileSize;
    private final double offsetX;
    private final double offsetY;
    private final GameState gameState;
    private final GameScene gameScene;
    private final MistSchaak game;

    public EntitySelector(Coordinate2D location, Board board, TileDrawer[][] tileDrawers, int tileSize, GameState gameState, GameScene gameScene, MistSchaak game) {
        super(location);

        this.board = board;
        this.tileDrawers = tileDrawers;
        this.tileSize = tileSize;
        this.offsetX = location.getX();
        this.offsetY = location.getY();
        this.gameState = gameState;
        this.gameScene = gameScene;
        this.game = game;

        setWidth(board.getCols() * tileSize);
        setHeight(board.getRows() * tileSize);

        setFill(Color.TRANSPARENT);
        setStrokeWidth(0);
    }

    @Override
    public void onMouseButtonPressed(MouseButton button, Coordinate2D coordinate) {
        Position clickPos = getClickPosition(coordinate);
        if (!isValidClick(clickPos)) {
            clearSelectionAndHighlights();
            return;
        }

        Tile clickedTile = board.getTile(clickPos.getRow(), clickPos.getCol());

        if (selectedPiece == null) {
            attemptPieceSelection(clickedTile);
        } else {
            attemptMove(clickedTile);
        }
    }

    private Position getClickPosition(Coordinate2D coordinate) {
        int row = (int) ((coordinate.getY() - offsetY) / tileSize);
        int col = (int) ((coordinate.getX() - offsetX) / tileSize);
        return new Position(row, col);
    }

    private boolean isValidClick(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < board.getRows() &&
                pos.getCol() >= 0 && pos.getCol() < board.getCols();
    }

    private void attemptPieceSelection(Tile tile) {
        if (!tile.isEmpty() && isPieceColorMatchingTurn(tile.getChessPiece())) {
            selectedPiece = tile.getChessPiece();
            selectedTile = tile;
            highlightValidMoves(tile);
        }
    }

    private void attemptMove(Tile destinationTile) {
        boolean success = board.moveChessPieceIfValid(
                selectedTile.getRow(), selectedTile.getCol(),
                destinationTile.getRow(), destinationTile.getCol(),
                gameState
        );

        if (success) {
            gameScene.redrawChessPieces();
            clearSelectionAndHighlights();
            transitionToNextScene();
        } else {
            clearSelectionAndHighlights();
        }
    }

    private void transitionToNextScene() {
        if (gameState.isGameOver()) {
            game.setActiveScene(4);
        } else {
            game.setActiveScene(3);
        }
    }

    private boolean isPieceColorMatchingTurn(ChessPiece piece) {
        boolean isWhitePiece = piece.getColor() == ChessPiece.pieceColor.WHITE;
        return isWhitePiece == gameState.isWhiteTurn();
    }

    private void clearSelectionAndHighlights() {
        selectedPiece = null;
        selectedTile = null;
        gameScene.redrawTiles(null);
    }

    private void highlightValidMoves(Tile selectedTile) {
        Map<Tile, Color> tilesToHighlight = new HashMap<>();

        tilesToHighlight.put(selectedTile, tileDrawers[selectedTile.getRow()][selectedTile.getCol()].selectedTileColor);

        List<Position> validMoves = selectedPiece.getValidMoves(board.getPosition(selectedTile.getRow(), selectedTile.getCol()), board);
        for (Position position : validMoves) {
            Tile targetTile = board.getTile(position.getRow(), position.getCol());
            TileDrawer tileDrawer = tileDrawers[position.getRow()][position.getCol()];

            if (targetTile.isEmpty()) {
                tilesToHighlight.put(targetTile, tileDrawer.validMovesTileColor);
            } else if (targetTile.hasEnemyChessPiece(selectedPiece)) {
                tilesToHighlight.put(targetTile, tileDrawer.attackableTileColor);
            }
        }
        gameScene.redrawTiles(tilesToHighlight);
    }
}
