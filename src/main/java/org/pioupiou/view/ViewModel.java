package org.pioupiou.view;

import org.pioupiou.model.Chip8CPU;
import org.pioupiou.model.Sprite;

import java.io.File;

public class ViewModel {

    Chip8CPU chip8CPU;

    public ViewModel(Chip8CPU chip8CPU) {
        this.chip8CPU = chip8CPU;
    }

    public void sendKeyPressedToCPU(String keyPressed){
        System.out.println(keyPressed);
    }

    public void sendKeyReleasedToCPU(String keyReleased){
        System.out.println(keyReleased);
    }

    public void sendFileToCPU(File chip8ROM){
        chip8CPU.loadGame(chip8ROM);
    }

    public Sprite getSpriteToDraw() {
        return chip8CPU.getSprite();
    }
}
