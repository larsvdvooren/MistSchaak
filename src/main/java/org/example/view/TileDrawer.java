package org.example.view;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import com.github.hanyaeger.api.userinput.MouseEnterListener;
import com.github.hanyaeger.api.userinput.MouseExitListener;
import javafx.scene.paint.Color;
import org.example.util.GameConstants;

public class TileDrawer extends RectangleEntity {

    private final Color defaultColor;
    private Color gameHighlightColor;
    private Color currentDisplayColor;
    private boolean isFogged;

    public final Color selectedTileColor = GameConstants.SELECTED_TILE_COLOR;
    public final Color validMovesTileColor = GameConstants.VALID_MOVE_TILE_COLOR;
    public final Color attackableTileColor = GameConstants.ATTACK_TILE_COLOR;
    private final Color fogColor = GameConstants.FOG_COLOR;

    private final Coordinate2D location;

    public TileDrawer(Coordinate2D location, Color color, double size) {
        super(location);
        this.location = location;
        this.defaultColor = color;
        this.gameHighlightColor = defaultColor; // Initially no game highlight
        this.currentDisplayColor = defaultColor;
        this.isFogged = false; // Initially no fog

        setWidth(size);
        setHeight(size);
        setFill(defaultColor);
    }

    private Color averageColor(Color c1, Color c2) {
        return new Color(
            (c1.getRed() + c2.getRed()) / 2,
            (c1.getGreen() + c2.getGreen()) / 2,
            (c1.getBlue() + c2.getBlue()) / 2,
            (c1.getOpacity() + c2.getOpacity()) / 2
        );
    }

    public void setGameHighlight(Color color) {
        this.gameHighlightColor = averageColor(this.defaultColor, color);
        this.currentDisplayColor = this.gameHighlightColor;
        setFill(this.currentDisplayColor);
    }

    public void clearGameHighlight() {
        this.gameHighlightColor = defaultColor;
        this.currentDisplayColor = defaultColor;
        setFill(defaultColor);
    }

    public void enableFog() {
        isFogged = true;
        if (this.defaultColor == Color.WHITE) {
            setFill((fogColor).brighter());
        } else {
            setFill((fogColor).darker());
        }
    }

    public void clearFog() {
        isFogged = false;
        setFill(currentDisplayColor);
    }
}
