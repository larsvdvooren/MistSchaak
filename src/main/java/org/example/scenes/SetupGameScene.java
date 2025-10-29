package org.example.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.GameState;
import org.example.MistSchaak;
import org.example.entities.board.Board;
import org.example.entities.board.InitialChessPieces;

// Sets up the chessboard before starting the game

public class SetupGameScene extends StaticScene {

    private final MistSchaak game;
    private int localTextsize = 30;

    public SetupGameScene(MistSchaak game) {
        this.game = game;
    }

    @Override
    public void setupScene() {
        setBackgroundColor(Color.DARKSLATEGRAY);
    }

    @Override
    public void setupEntities() {
        // Initialize board and game state once
        Board board = new Board(8);
        InitialChessPieces.setup(board);
        GameState gameState = new GameState(board);

        game.setBoard(board);
        game.setGameState(gameState);

        // Displays loading message
        String setupText = "Setting up game...";
        TextEntity loadingText = new TextEntity(
            new Coordinate2D(MistSchaak.WINDOW_WIDTH / 2.0 - (setupText.length() * (localTextsize / 4.0)), MistSchaak.WINDOW_HEIGHT / 2.0),
            setupText
        );
        loadingText.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        loadingText.setFill(Color.WHITE);
        addEntity(loadingText);

        // Auto transition to the actual game
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    javafx.application.Platform.runLater(() -> {
                        game.setActiveScene(2); // GameScene
                    });
                }
            },
            500 // 500ms delay
        );
    }
}

