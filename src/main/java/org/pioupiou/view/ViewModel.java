package org.pioupiou.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.pioupiou.model.Chip8CPU;

public class ViewModel {

    Chip8CPU chip8CPU;

    public ViewModel(Chip8CPU chip8CPU) {
    }

    public void sendKeyPressedToCPU(String keyPressed){
        System.out.println(keyPressed);
    }

    public void sendKeyReleasedToCPU(String keyReleased){
        System.out.println(keyReleased);
    }
}
