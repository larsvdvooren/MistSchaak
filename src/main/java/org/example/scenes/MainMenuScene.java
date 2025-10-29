package org.example.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.scenes.StaticScene;
import javafx.scene.paint.Color;
import org.example.MistSchaak;
import org.example.entities.buttons.StartButton;

public class MainMenuScene extends StaticScene {
    private MistSchaak mistSchaak;

    public MainMenuScene(MistSchaak mistSchaak) {
        this.mistSchaak = mistSchaak;
    }

    @Override
    public void setupScene() {
        setBackgroundColor(Color.SLATEGREY);
    }

    @Override
    public void setupEntities() {
        var StartButton = new StartButton(new Coordinate2D(getWidth() / 4, getHeight() / 2.5), mistSchaak); // magic adjacent numbers

        addEntity(StartButton.getBackground());
        addEntity(StartButton);
    }
}