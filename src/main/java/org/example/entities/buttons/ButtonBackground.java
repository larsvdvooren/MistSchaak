package org.example.entities.buttons;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import javafx.scene.paint.Color;

public class ButtonBackground extends RectangleEntity {

    public ButtonBackground(Coordinate2D position, double width, double height, Color fillColor, Color borderColor, double borderWidth) {
        super(position,new Size(width, height));

        setFill(fillColor);
        setStrokeColor(borderColor);
        setStrokeWidth(borderWidth);
    }
}
