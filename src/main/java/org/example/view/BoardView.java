package org.example.view;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import org.example.entities.board.Board;
import org.example.entities.board.Position;
import org.example.entities.board.Tile;

import java.util.Map;
import java.util.Set;

public class BoardView {
    private final Board board;
    private final int tileSize;
    private final double offsetX;
    private final double offsetY;
    private final TileDrawer[][] tileDrawers;

    public BoardView(Board board, int tileSize, double offsetX, double offsetY) {
        this.board = board;
        this.tileSize = tileSize;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.tileDrawers = new TileDrawer[board.getRows()][board.getCols()];
        initializeTileDrawers();
    }

    private void initializeTileDrawers() {
        board.forEachTile((row, col, tile) -> {
            double x = offsetX + col * tileSize;
            double y = offsetY + row * tileSize;
            Color color = (row + col) % 2 == 0 ? Color.WHITE : Color.BLACK;
            tileDrawers[row][col] = new TileDrawer(new Coordinate2D(x, y), color, tileSize);
        });
    }

    public void render(Map<Tile, Color> highlightedTiles) {
        // Clear all previous highlights
        board.forEachTile((row, col, tile) -> tileDrawers[row][col].clearGameHighlight());

        // Apply new highlights
        if (highlightedTiles != null) {
            for (Map.Entry<Tile, Color> entry : highlightedTiles.entrySet()) {
                Tile tile = entry.getKey();
                Color color = entry.getValue();
                tileDrawers[tile.getRow()][tile.getCol()].setGameHighlight(color);
            }
        }
    }

    public TileDrawer[][] getTileDrawers() {
        return tileDrawers;
    }


    public void renderWithFogOfWar(Set<Position> visiblePositions, Map<Tile, Color> highlightedTiles) {
        // First, apply highlights (if any)
        render(highlightedTiles);

        // Then apply fog-of-war overlay
        board.forEachTile((row, col, tile) -> {
            Position pos = new Position(row, col);
            if (visiblePositions.contains(pos)) {
                tileDrawers[row][col].clearFog();
            } else {
                tileDrawers[row][col].enableFog();
            }
        });
    }
}
