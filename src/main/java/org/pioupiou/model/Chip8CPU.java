package org.pioupiou.model;

public interface Chip8CPU {

    //general methods that drive the emulation
    public void initialize();

    public void emulate();

    public boolean getDrawFlag();

    public void setDrawFlag(boolean drawFlag);

    //connect to viewModel and input by user
    public void getKeyPressed(String keyPressed);

    public void getKeyReleased(String keyReleased);

    public void loadGame(String gameName);
}
