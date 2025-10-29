package org.example;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import org.example.entities.board.Board;
import org.example.scenes.GameOverScene;
import org.example.scenes.GameScene;
import org.example.scenes.MainMenuScene;
import org.example.scenes.SetupGameScene;
import org.example.scenes.TurnTransitionScene;

public class MistSchaak extends YaegerGame {
    // Window size constants
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = WINDOW_HEIGHT; // works best when width is at least as large as height

    // Game state
    private GameState gameState;
    private Board board;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void setupGame() {
        setGameTitle("MistSchaak");
        setSize(new Size(WINDOW_WIDTH, WINDOW_HEIGHT));
    }

    @Override
    public void setupScenes() {
        addScene(0, new MainMenuScene(this));
        addScene(1, new SetupGameScene(this));      // Initialize game state
        addScene(2, new GameScene(this));           // Main gameplay
        addScene(3, new TurnTransitionScene(this)); // Transition scene (reads current state)
        addScene(4, new GameOverScene(this));       // Game over scene (king captured)
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
