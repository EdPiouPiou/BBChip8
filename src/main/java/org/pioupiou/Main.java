package org.pioupiou;

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

    };
    private static void setupGraphics(){

    };
    private static void setupInput(){

    };
}