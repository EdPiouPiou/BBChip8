package org.pioupiou.model;

import org.pioupiou.presenter.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Opcodes {
    private boolean[][] display;
    private int[] memory; //binary
    private int adressI;
    private int programCounter;
    private int delayTimer;
    private int soundTimer;
    private int key;
    private List<Integer> routineStack = new ArrayList<>();
    private int stackPointer; //maybe put it in Chip8 later
    private int x;
    private int y;
    private int opcodeInt;
    private int[] registersV = new int[16];
    private boolean paused;
    private Keyboard keyboard = new Keyboard();

    public Opcodes(boolean[][] display, int[] memory) {
        this.display = display;
        this.memory = memory;
    }

    public void executeOpcode(String opcode){
        programCounter += 2; //because each opcode is 2 bits long
        opcodeInt = Integer.parseInt(opcode, 16);
        //0x0F00 and 0x00F0 allow us to get 2nd and 3rd nibbles respectively
        x = (opcodeInt & 0x0F00) >> 8; //shift 2nd nibble right by 8 bits to get rid of 3rd and 4th nibble
        y = (opcodeInt & 0x00F0) >> 4; //shift 3rd nibble right by 4 bits to get rid of 4th nibble

        switch(opcode) {
            case String s when s.matches("^0([A-F0-9]{3})") -> System.out.println("NOT IMPLEMENTED : USUALLY IGNORED");
            case String s when s.matches("00EO") -> this.clearDisplay();
            case String s when s.matches("00EE") -> {
                //store last element of the routine stack (topmost) in the program counter
                programCounter = routineStack.getLast();
                //reduce stackPointer by one to go to the next subroutine
                stackPointer--;
            }
            case String s when s.matches("^1([A-F0-9]{3})") -> programCounter = opcodeInt & 0xFFF; //FFF grabs the value of NNN
            case String s when s.matches("^2([A-F0-9]{3})") -> {
                ++stackPointer;
                routineStack.add(programCounter);
                programCounter = opcodeInt & 0xFFF;
            }
            case String s when s.matches("^3([A-F0-9]{3})") -> {
                int nn = Integer.parseInt(opcode.substring(2),16);
                if (registersV[x] == nn) {
                    programCounter += 2;
                }
            }
            case String s when s.matches("^4([A-F0-9]{3})") -> {
                int nn = Integer.parseInt(opcode.substring(2),16);
                if (registersV[x] != nn) {
                    programCounter += 2;
                }
            }

            case String s when s.matches("^5([A-F0-9]{2}0)") -> {
                if (registersV[x] == registersV [y]) {
                    programCounter += 2;
                }
            }
            case String s when s.matches("^6([A-F0-9]{3})") -> {
                int nn = Integer.parseInt(opcode.substring(2),16);
                registersV[x] = nn;
            }
            case String s when s.matches("^7([A-F0-9]{3})") -> {
                int nn = Integer.parseInt(opcode.substring(2),16);
                if (registersV[x] == registersV[0xF]) {
                    registersV[x] += nn;
                }
            }
            case String s when s.matches("^8([A-F0-9]{2})0") -> {
                registersV[x] = registersV[y];
            }
            case String s when s.matches("^8([A-F0-9]{2})1") -> {
                registersV[x] = registersV[x] | registersV[y];
            }
            case String s when s.matches("^8([A-F0-9]{2})2") -> {
                registersV[x] = registersV[x] & registersV[y];
            }
            case String s when s.matches("^8([A-F0-9]{2})3") -> {
                registersV[x] = registersV[x] ^ registersV[y];
            }
            case String s when s.matches("^8([A-F0-9]{2})4") -> {
                if (registersV[x] + registersV[y] > 255) { //check for overflow
                    registersV[0xF] = 1;
                    registersV[x] = (registersV[x] + registersV[y]) << 8; //shift left by 8 to keep lowest 8 bits ? //TODO
                } else {
                    registersV[0xF] = 0;
                    registersV[x] = registersV[x] + registersV[y];
                }
            }
            case String s when s.matches("^8([A-F0-9]{2})5") -> {
                if (registersV[x] <= registersV[y]) { //check for underflow
                    registersV[0xF] = 1;
                } else {
                    registersV[0xF] = 0;
                    registersV[x] = registersV[x] - registersV[y];
                }

            }
            case String s when s.matches("^8([A-F0-9]{2})6") -> {
                //store least significant bit of VX before shift to VF
                registersV[0xF] = x & 0x1;
                //right shift Vx by one
                x >>= 1;

            }
            case String s when s.matches("^8([A-F0-9]{2})7") -> {
                if (registersV[y] <= registersV[x]) { //check for underflow
                    registersV[0xF] = 1;
                }
                else {
                    registersV[x] = registersV[y] - registersV[x];
                    registersV[0xF] = 0;
                }
            }
            case String s when s.matches("^8([A-F0-9]{2})E") -> {
                //store least significant bit of VX before shift to VF
                registersV[0xF] = x & 0x1;
                //left shift Vx by one
                x <<= 1;

            }
            case String s when s.matches("^9([A-F0-9]{2})0") -> {
                if (registersV[x] == registersV[y]) {
                    programCounter += 2;
                }
            }
            case String s when s.matches("^A([A-F0-9]{3})") -> adressI = Integer.parseInt(opcode.substring(1),16);
            case String s when s.matches("^B([A-F0-9]{3})") -> {
                programCounter = registersV[0x0] + Integer.parseInt(opcode.substring(1), 16);
            }
            case String s when s.matches("^C([A-F0-9]{3})") -> {
                int random = (int) (Math.random() * (255 - 0) + 0);
                registersV[x] = random & Integer.parseInt(opcode.substring(2), 16);
            }
            case String s when s.matches("^D([A-F0-9]{3})") -> {
                //init VF to 0
                registersV[0xF] = 0;
                //coordinates X stocked in Vx
                int coordX = registersV[x];
                //coordinates Y stocked in Vy
                int coordY = registersV[y];
                int nibble = Integer.parseInt(opcode.substring(5)); //DXYN got as 0xDXYN so N is last nibble, it represents the height of the sprite
                for(int heightIndex = 0; heightIndex < nibble; heightIndex++){
                    //read sprite from memory
                    int sprite = memory[adressI + heightIndex];

                    //draw sprite, each sprite is 8 pixels wide
                    for(int column = 0; column < 8; column++){
                        if((sprite & 0x80) > 0 ) { //if bit/sprite not 0, render or erase the pixel at given column
                            if(this.renderSprite( registersV[x] + column, registersV[y] + heightIndex) == 1){ //set Vf to 1 if pixel is erased
                                registersV[0xF] = 1;
                            }
                        }
                        sprite <<= 1; //left shift sprite by 1 to move next column/bit of the sprite
                    }
                }

                //compare values between already present pixel and new one with a XOR op

            }
            case String s when s.matches("^E[A-F0-9]9E") -> {
                if(this.getKey() == x){
                    programCounter += 2;
                }
            }
            case String s when s.matches("^E[A-F0-9]A1") -> {
                if(this.getKey() != x){
                    programCounter += 2;
                }
            }
            case String s when s.matches("^F[A-F0-9]07") -> registersV[x] = getDelay();
            case String s when s.matches("^F[A-F0-9]0A") -> {
                paused = true;
                if(keyboard.onNextKeyPress(key) != -1){
                    registersV[x] = getKey();
                    paused = false;
                }

            }
            case String s when s.matches("^F[A-F0-9]15") -> delayTimer = registersV[x];
            case String s when s.matches("^F[A-F0-9]18") -> soundTimer = registersV[x];
            case String s when s.matches("^F[A-F0-9]1E") -> {
                if(registersV[x] != registersV[0xF]){
                    adressI += registersV[x];
                }
            }
            case String s when s.matches("^F[A-F0-9]29") -> {
                adressI = x*5; //multiplied by 5 because each sprite is 4*5 bit long
            }
            case String s when s.matches("^F[A-F0-9]33") -> {
                String decimal = "01010110"; //V = 56 = 0101 0110 en binaire
                decimal += decimal + Integer.toBinaryString(Integer.parseInt(String.valueOf(opcode.charAt(2)), 16));
                //manage if VX is in tens or units, it will get shifted (4, 8 or 12 digits in the Vx decimal)
                //not sure it can happen but better safe than sorry
                if(Integer.toBinaryString(registersV[x]).length() == 4){ //TODO
                    //only units
                    memory[adressI + 2] = Integer.parseInt(decimal,16);
                }
                else if(Integer.toBinaryString(registersV[x]).length() == 8){
                    //tens and units
                    memory[adressI + 1] = Integer.parseInt(decimal.substring(0,4),16);
                    memory[adressI + 2] = Integer.parseInt(decimal.substring(4),16);
                }
                else if(Integer.toBinaryString(registersV[x]).length() == 12){
                    //hundreds tens and units
                    memory[adressI] = Integer.parseInt(decimal.substring(0,4),16);
                    memory[adressI + 1] = Integer.parseInt(decimal.substring(4,8),16);
                    memory[adressI + 2] = Integer.parseInt(decimal.substring(8),16);
                }
            }
            case String s when s.matches("^F[A-F0-9]55") -> {
                for(int registerIndex = 0; registerIndex <= x; registerIndex++){
                    memory[adressI + registerIndex] = registersV[registerIndex];
                }
            }
            case String s when s.matches("^F[A-F0-9]65") -> {
                // read values from memory, starting from I, and dump them in the corresponding registers
                for(int registerIndex = 0; registerIndex <= x; registerIndex++){
                   registersV[registerIndex] = memory[adressI + registerIndex];
                }
            }
            default -> System.out.println("ERROR : OPCODE UNKNOWN");
        }
    }

    private void clearDisplay(){
        display = new boolean[32][64];
    }

    private void skip(){

    }

    private int getDelay(){
        return 0;
    }

    private int getKey(){
        return 0;
    }

    private int renderSprite(int coordX, int coordY){
        return 0;
    }
}
