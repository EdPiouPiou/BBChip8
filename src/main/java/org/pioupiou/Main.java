package org.pioupiou;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.pioupiou.model.Chip8CPU;
import javafx.application.Application;

public class Main {

    private static Chip8CPU chip8;

    public static void main(String[] args) {
        //set up display and prep for user input
        setupGraphics();
        setupInput();

        //init chip8 and load game
        chip8.initialize();
        chip8.loadGame("pong");

        for(;;){
            chip8.emulate();
            //draw
            if(chip8.getDrawFlag()){
                drawGraphics();
            }
            //set pressed keys (method defined in Chip8 class)
        }
    }

    // methods
    private static void drawGraphics(){

    }
    private static void setupGraphics(){

    }
    private static void setupInput(){

    }
}