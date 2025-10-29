package org.example.entities.buttons;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.example.MistSchaak;

// serves as a big, screencovering button that allows any click in the window to transition to a specified scene

public class ClickableOverlay extends RectangleEntity implements MouseButtonPressedListener {

    private final MistSchaak game;
    private final int targetScene;

    public ClickableOverlay(Coordinate2D location, MistSchaak game, int targetScene) {
        super(location);
        this.game = game;
        this.targetScene = targetScene;

        setWidth(MistSchaak.WINDOW_WIDTH);
        setHeight(MistSchaak.WINDOW_HEIGHT);
        setFill(Color.TRANSPARENT);
        setStrokeWidth(0);
    }

    @Override
    public void onMouseButtonPressed(MouseButton mouseButton, Coordinate2D coordinate2D) {
        game.setActiveScene(targetScene);
    }
}


