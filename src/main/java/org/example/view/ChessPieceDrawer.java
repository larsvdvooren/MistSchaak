package org.example.view;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;


public class ChessPieceDrawer extends SpriteEntity implements Collider /*, MouseEnterListener, MouseExitListener */ {

    protected ChessPieceDrawer(String resource, Coordinate2D initialLocation, int tileSize) {
        super(resource, initialLocation, new Size(tileSize, tileSize));
    }
}

