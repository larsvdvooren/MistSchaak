package org.example.mouseInteraction;

 // interface that controls collision with the mouse pointer
public interface TileCollider {

    // mouse enters collision area
    void onMouseCursorEnter();

    // mouse exits collision area
    void onMouseCursorExit();
}