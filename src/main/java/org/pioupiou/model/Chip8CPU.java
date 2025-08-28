package org.pioupiou.model;

import java.io.File;

public interface Chip8CPU {

    //general methods that drive the emulation
    void initialize();

    void emulate();

    boolean isDrawFlag();

    void setDrawFlag(boolean drawFlag);

    //connect to viewModel and input by user
    void getKeyPressed(String keyPressed);

    void getKeyReleased(String keyReleased);

    void loadGame(File chip8ROM);

    Sprite getSprite();
}
