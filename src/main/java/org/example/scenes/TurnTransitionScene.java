package org.example.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.MistSchaak;
import org.example.entities.buttons.ClickableOverlay;


public class TurnTransitionScene extends StaticScene {

    private final MistSchaak game;
    private final int localTextSize = 40;

    public TurnTransitionScene(MistSchaak game) {
        this.game = game;
    }

    @Override
    public void setupScene() {
        setBackgroundColor(Color.BLACK);
    }

    @Override
    public void setupEntities() {
        // Switch turn FIRST (happens when entering this scene after a move)
        game.getGameState().switchTurn();

        // Now display whose turn it is (after the switch)
        boolean currentIsWhiteTurn = game.getGameState().isWhiteTurn();

        // Tells the player whose turn it is now
        String playerColor = currentIsWhiteTurn ? "White" : "Black";
        String nextPlayerText = "Click to make a move as " + playerColor;
        TextEntity turnText = new TextEntity(
            new Coordinate2D(MistSchaak.WINDOW_WIDTH / 2.0 - (nextPlayerText.length() * (localTextSize / 4.0)), MistSchaak.WINDOW_HEIGHT / 3.0),
            nextPlayerText
        );
        turnText.setFont(Font.font("Arial", localTextSize));
        turnText.setFill(Color.WHITE);
        addEntity(turnText);

        // Add invisible clickable overlay as a megabutton (goes to GameScene)
        ClickableOverlay overlay = new ClickableOverlay(new Coordinate2D(0, 0), game, 2);
        addEntity(overlay);
    }
}

