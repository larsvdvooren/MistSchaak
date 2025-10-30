package org.example.util;

import javafx.scene.paint.Color;

public class GameConstants {

    public static final int BOARD_SIZE = 8;
    public static final int BOARD_OFFSET_X = 100;
    public static final int BOARD_OFFSET_Y = 100;

    public static final Color FOG_COLOR = Color.rgb(20, 20, 140);
    public static final Color SELECTED_TILE_COLOR = Color.GREEN;
    public static final Color VALID_MOVE_TILE_COLOR = Color.YELLOW;
    public static final Color ATTACK_TILE_COLOR = Color.RED;
    public static final Color HOVER_TILE_COLOR = Color.BLUE;

    public static final int DEFAULT_FONT_SIZE = 50;
    public static final int VICTORY_TEXT_SIZE = 60;

    private GameConstants() {
        throw new AssertionError("GameConstants should not be instantiated");
    }
}

