package org.example.entities.buttons;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.userinput.MouseButtonPressedListener;
import com.github.hanyaeger.api.userinput.MouseEnterListener;
import com.github.hanyaeger.api.userinput.MouseExitListener;

import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.MistSchaak;
import org.example.util.GameConstants;

public abstract class Button extends TextEntity implements MouseButtonPressedListener, MouseEnterListener, MouseExitListener {

    private static final int defaultFontsize = GameConstants.DEFAULT_FONT_SIZE;
    protected final MistSchaak mistSchaak;

    private final Color BASE_COLOR;
    private final Color LIGHTER_COLOR;
    private final ButtonBackground BACKGROUND;

    protected Button (Coordinate2D position, String buttonName, Color textColor , MistSchaak mistSchaak) {
        super (position, buttonName);
        this.mistSchaak = mistSchaak;

        setAnchorLocation(position);
        setFont(Font.font ("Verdana", defaultFontsize));
        setFill(textColor);

        // sets up the highlight color of a button
        this.BASE_COLOR = textColor;
        this.LIGHTER_COLOR = BASE_COLOR.brighter();

        // sets up Background behind text
        int padding  = defaultFontsize/2;
        Text text = new Text (getText());
        text.setFont(Font.font ("Verdana", defaultFontsize));
        double width = text.getLayoutBounds().getWidth() + padding; // size of text entity + padding

        double height = defaultFontsize * 1.5;
        double borderWidth = defaultFontsize / 10.0;

        double backGroundX = position.getX() - padding / 2; //offsets the background properly
        double backGroundY = position.getY();

        BACKGROUND = new ButtonBackground(new Coordinate2D(backGroundX, backGroundY), width, height, Color.BLACK, LIGHTER_COLOR, borderWidth);
    }


    public ButtonBackground getBackground() {
        return BACKGROUND;
    }

    @Override
    public void onMouseEntered() {
        setFill(LIGHTER_COLOR);
    } // maybe do this with Highlighter class

    @Override
    public void onMouseExited() {
        setFill(BASE_COLOR);
    } // maybe do this with Highlighter class
}
