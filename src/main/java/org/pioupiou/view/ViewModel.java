package org.pioupiou.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.pioupiou.model.Chip8CPU;

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

    //TODO, will get image to draw from CPU Impl
    public void getPixelToDraw() {
    }
}
