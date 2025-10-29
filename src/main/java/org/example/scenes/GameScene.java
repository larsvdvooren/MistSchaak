package org.example.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.scenes.DynamicScene;
import javafx.scene.paint.Color;
import org.example.GameState;
import org.example.MistSchaak;
import org.example.entities.board.Board;
import org.example.entities.board.InitialChessPieces;
import org.example.entities.board.Position;
import org.example.entities.board.Tile;
import org.example.mouseInteraction.EntitySelector;
import org.example.mouseInteraction.MouseCursor;
import org.example.util.GameConstants;
import org.example.view.BoardView;
import org.example.view.ChessPieceDrawer;
import org.example.view.ChessPieceView;
import org.example.view.TileDrawer;

import java.util.*;

// The main game display

import static org.example.MistSchaak.WINDOW_HEIGHT;

public class GameScene extends DynamicScene {

    public int boardSize = GameConstants.BOARD_SIZE;
    public int tileSize = WINDOW_HEIGHT / (boardSize + 2);
    public double offsetX = GameConstants.BOARD_OFFSET_X;
    public double offsetY = GameConstants.BOARD_OFFSET_Y;

    private Board board;
    private GameState gameState;
    private BoardView boardView;
    private ChessPieceView chessPieceView;
    private EntitySelector entitySelector;
    private TileDrawer[][] tileDrawers;
    private final MistSchaak game;

    // --- Fog-of-War cache ---
    private Set<Position> cachedVisiblePositions;
    private boolean cachedWhiteTurn;
    private boolean fogNeedsUpdate = true;

    private List<ChessPieceDrawer> currentPieceDrawers = new ArrayList<>();

    public GameScene(MistSchaak game) {
        this.game = game;
    }

    @Override
    public void setupScene() {
        setBackgroundColor(Color.SLATEGREY);
    }

    @Override
    public void setupEntities() {
        if (game.getBoard() == null) {
            board = new Board(boardSize);
            InitialChessPieces.setup(board);
            gameState = new GameState(board);
            game.setBoard(board);
            game.setGameState(gameState);
        } else {
            board = game.getBoard();
            gameState = game.getGameState();
        }

        boardView = new BoardView(board, tileSize, offsetX, offsetY);
        tileDrawers = boardView.getTileDrawers();

        board.forEachTile((row, col, tile) -> addEntity(tileDrawers[row][col]));

        chessPieceView = new ChessPieceView(board, tileSize, offsetX, offsetY);
        redrawChessPieces();

        entitySelector = new EntitySelector(new Coordinate2D(offsetX, offsetY), board, tileDrawers, tileSize, gameState, this, game);
        addEntity(entitySelector);

        MouseCursor mouseCursor = new MouseCursor(new Coordinate2D(0, 0));
        addEntity(mouseCursor);
    }

    // --- cache management  ---
    private Set<Position> getCachedVisiblePositions() {
        boolean isWhiteTurn = gameState.isWhiteTurn();
        if (fogNeedsUpdate || cachedVisiblePositions == null || cachedWhiteTurn != isWhiteTurn) {
            cachedVisiblePositions = chessPieceView.getVisiblePositions(isWhiteTurn);
            cachedWhiteTurn = isWhiteTurn;
            fogNeedsUpdate = false;
        }
        return cachedVisiblePositions;
    }

    public void redrawChessPieces() {
        // Remove old pieces
        for (var oldChessPiece : currentPieceDrawers) {
            oldChessPiece.remove();
        }
        currentPieceDrawers.clear();

        // Render pieces with fog-of-war
        List<ChessPieceDrawer> newPieceDrawers =
                chessPieceView.renderWithFogOfWar(gameState.isWhiteTurn());
        for (var newChessPiece : newPieceDrawers) {
            addEntity(newChessPiece);
            currentPieceDrawers.add(newChessPiece);
        }

        // Apply fog-of-war to tiles
        Set<Position> visiblePositions = getCachedVisiblePositions();
        boardView.renderWithFogOfWar(visiblePositions, null);
    }

    public void redrawTiles(Map<Tile, Color> highlightedTiles) {
        Set<Position> visiblePositions = getCachedVisiblePositions();
        boardView.renderWithFogOfWar(visiblePositions, highlightedTiles);
    }
}
