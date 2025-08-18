package org.pioupiou.core;

import org.pioupiou.model.Chip8CPU;
import org.pioupiou.model.Chip8CPUImpl;

public class ModelFactory {
    private Chip8CPU chip8CPU;

    public Chip8CPU getChip8CPU(){
        if(chip8CPU == null) chip8CPU = new Chip8CPUImpl();
        return chip8CPU;
    }
}
