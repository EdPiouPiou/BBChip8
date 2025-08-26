package org.pioupiou;

import org.pioupiou.model.Chip8CPUImpl;
import javafx.application.Application;

import java.io.File;

public class Main {

    private static Chip8CPUImpl chip8;

    public static void main(String[] args) {
        Application.launch(BB8App.class);
    }
}