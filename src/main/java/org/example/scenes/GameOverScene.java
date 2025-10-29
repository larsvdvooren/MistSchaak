package org.example.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.MistSchaak;
import org.example.entities.buttons.ClickableOverlay;
import org.example.entities.chesspieces.ChessPiece;
import org.example.util.GameConstants;

// Game over scene, tells the players who won the game

public class GameOverScene extends StaticScene {

    private final MistSchaak game;

    public GameOverScene(MistSchaak game) {
        this.game = game;
    }

    @Override
    public void setupScene() {
        // BAckground color depends on who won
        ChessPiece.pieceColor winner = game.getGameState().getWinner();
        if (winner == ChessPiece.pieceColor.WHITE) {
            setBackgroundColor(Color.WHITE);
        } else {
            setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    public void setupEntities() {
        ChessPiece.pieceColor winner = game.getGameState().getWinner();
        String winnerText = (winner == ChessPiece.pieceColor.WHITE) ? "White" : "Black";
        Color textColor = (winner == ChessPiece.pieceColor.WHITE) ? Color.BLACK : Color.WHITE;

        // Game over message
        String gameOverTextString = winnerText + " won!";
        TextEntity gameOverText = new TextEntity(
            new Coordinate2D(MistSchaak.WINDOW_WIDTH / 2.0 - (gameOverTextString.length() * (GameConstants.VICTORY_TEXT_SIZE / 4.0)), MistSchaak.WINDOW_HEIGHT / 2.0 - 100),
            gameOverTextString
        );
        gameOverText.setFont(Font.font("Arial", GameConstants.VICTORY_TEXT_SIZE));
        gameOverText.setFill(textColor);
        addEntity(gameOverText);

        // Instruction text
        String instructionTextString = "Click to return to the main menu";
        TextEntity instructionText = new TextEntity(
            new Coordinate2D(MistSchaak.WINDOW_WIDTH / 2.0 - (instructionTextString.length() * (GameConstants.DEFAULT_FONT_SIZE / 4.0)), MistSchaak.WINDOW_HEIGHT / 1.5),
            instructionTextString
        );
        instructionText.setFont(Font.font("Arial", GameConstants.DEFAULT_FONT_SIZE));
        instructionText.setFill(textColor);
        addEntity(instructionText);

        // Add invisible clickable overlay to return to main menu (scene 0)
        ClickableOverlay overlay = new ClickableOverlay(new Coordinate2D(0, 0), game, 0);
        addEntity(overlay);
    }
}

