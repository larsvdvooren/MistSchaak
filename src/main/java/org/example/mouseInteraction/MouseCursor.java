package org.example.mouseInteraction;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.CircleEntity;
import com.github.hanyaeger.api.userinput.MouseMovedListener;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Invisible mouse cursor entity that follows the mouse and detects collisions with tiles and chess pieces.
 * This enables collision-based hover detection.
 */
public class MouseCursor extends CircleEntity implements Collided, MouseMovedListener {

    private final Set<TileCollider> previouslyCollidingTiles = new HashSet<>();

    public MouseCursor(Coordinate2D initialLocation) {
        super(initialLocation);
        setRadius(1); // Very small radius for precise collision detection
        setFill(Color.TRANSPARENT);
        setOpacity(0); // Completely invisible
    }

    @Override
    public void onMouseMoved(Coordinate2D coordinate2D) {
        // Update the cursor position to follow the mouse
        setAnchorLocation(coordinate2D);
    }

    @Override
    public void onCollision(List<Collider> collidingObjects) {
        // Track current collisions
        Set<TileCollider> currentlyCollidingTiles = new HashSet<>();

        // Process all current collisions
        for (Collider collider : collidingObjects) {
            if (collider instanceof TileCollider) {
                TileCollider tileCollider = (TileCollider) collider;
                currentlyCollidingTiles.add(tileCollider);

                // Only call onMouseCursorEnter for newly colliding tiles
                if (!previouslyCollidingTiles.contains(tileCollider)) {
                    tileCollider.onMouseCursorEnter();
                }
            }
        }

        // Find tiles that are no longer colliding (mouse exited)
        for (TileCollider previousTile : previouslyCollidingTiles) {
            if (!currentlyCollidingTiles.contains(previousTile)) {
                previousTile.onMouseCursorExit();
            }
        }

        // Update the set of previously colliding tiles
        previouslyCollidingTiles.clear();
        previouslyCollidingTiles.addAll(currentlyCollidingTiles);
    }
}

