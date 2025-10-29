package org.example.entities.buttons;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.example.MistSchaak;

public class StartButton extends Button {
    public StartButton(Coordinate2D position, MistSchaak mistSchaak) {
        super(position, "Start Multiplayer", Color.GRAY, mistSchaak);
    }

    @Override
    public void onMouseButtonPressed(MouseButton mouseButton, Coordinate2D coordinate2D) {
        mistSchaak.setActiveScene(1); // Go to SetupGameScene
    }
}
