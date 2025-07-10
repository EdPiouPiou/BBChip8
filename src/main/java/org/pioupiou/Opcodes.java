package org.pioupiou;

import java.util.ArrayList;
import java.util.List;

public class Opcodes {
    private boolean[][] display;
    private int[] memory; //binary
    private String Vx;
    private String Vy;
    private String currentMemoryAddress;
    private String adressI;
    private int programCounter;
    private int delayTimer;
    private int soundTimer;
    private int key;
    private int Vf;
    private List<Integer> routineStack = new ArrayList<>();
    private int stackPointer; //maybe put it in Chip8 later
    private int x;
    private int y;
    private int opcodeInt;
    private int[] registersV = new int[16];

    public Opcodes(boolean[][] display, int[] memory, String Vx, String Vy) {
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
                Vx = "V" + opcode.charAt(1);
                if (Vx.equals(opcode.substring(2))) {
                    programCounter += 2;
                }
            }
            case String s when s.matches("^4([A-F0-9]{3})") -> {
                Vx = "V" + opcode.charAt(1);
                if (!Vx.equals(opcode.substring(2))) {
                    programCounter += 2;
                }
            }

            case String s when s.matches("^5([A-F0-9]{2}0)") -> {
                Vx = "V" + opcode.charAt(1);
                Vy = "V" + opcode.charAt(2);
                if (Vx.equals(Vy)) {
                    programCounter += 2;
                }
            }
            case String s when s.matches("^6([A-F0-9]{3})") -> Vx = opcode.substring(2);
            case String s when s.matches("^7([A-F0-9]{3})") -> {
                Vx = "V" + opcode.charAt(1);
                if (Vx.equals("VF")) {
                    Vx = Integer.toHexString(Integer.parseInt(Vx, 16) + Integer.parseInt(opcode.substring(2), 16));
                }
            }
            case String s when s.matches("^8([A-F0-9]{2})0") -> Vx = "V" + opcode.charAt(2);
            case String s when s.matches("^8([A-F0-9]{2})1") -> {
                Vx = "V" + opcode.charAt(1);
                Vx = Integer.toHexString(Integer.parseInt(Vx, 16) | Integer.parseInt(opcode.substring(2), 16));
            }
            case String s when s.matches("^8([A-F0-9]{2})2") -> {
                Vx = "V" + opcode.charAt(1);
                Vx = Integer.toHexString(Integer.parseInt(Vx, 16) & Integer.parseInt(opcode.substring(2), 16));
            }
            case String s when s.matches("^8([A-F0-9]{2})3") -> {
                Vx = "V" + opcode.charAt(1);
                Vx = Integer.toHexString(Integer.parseInt(Vx, 16) ^ Integer.parseInt(opcode.substring(2), 16));
            }
            case String s when s.matches("^8([A-F0-9]{2})4") -> {
                Vx = "V" + opcode.charAt(1);
                Vy = "V" + opcode.charAt(2);
                int tempResultOverflow = Integer.parseInt(Vx, 16) + Integer.parseInt(Vy, 16); //used to check for overflow
                if (tempResultOverflow < 0) { //check for overflow
                    Vf = 1;
                } else {
                    Vf = 0;
                }
            }
            case String s when s.matches("^8([A-F0-9]{2})5") -> {
                Vx = "V" + opcode.charAt(1);
                Vy = "V" + opcode.charAt(2);
                if (Integer.parseInt(Vx, 16) >= Integer.parseInt(Vy, 16)) { //check for underflow
                    Vf = 1;
                } else {
                    Vf = 0;
                }
            }
            case String s when s.matches("^8([A-F0-9]{2})6") -> {
                //store least significant bit of VX before shift to VF "ERROR : NOT IMPLEMENTED"
                Vf = x & 0x1;
                //right shift Vx by one
                x >>= 1;

            }
            case String s when s.matches("^8([A-F0-9]{2})7") -> {
                Vx = Integer.toHexString( Integer.parseInt(Vx, 16) - Integer.parseInt(Vy, 16));
                if(Integer.parseInt(Vy, 16) >= Integer.parseInt(Vx, 16)) {
                    Vf = 1;
                }
                else Vf = 0;
            }
            case String s when s.matches("^8([A-F0-9]{2})E") -> {
                //store least significant bit of VX before shift to VF "ERROR : NOT IMPLEMENTED"
                Vf = x & 0x1;
                //left shift Vx by one
                x <<= 1;

            }
            case String s when s.matches("^9([A-F0-9]{2})0") -> {
                Vx = "V" + opcode.charAt(1);
                Vy = "V" + opcode.charAt(2);
                if (Vx.equals(Vy)) {
                    skip();
                }
            }
            case String s when s.matches("^A([A-F0-9]{3})") -> adressI = opcode.substring(1);
            case String s when s.matches("^B([A-F0-9]{3})") -> programCounter = Integer.parseInt("V0", 16) + Integer.parseInt(opcode.substring(1),16);
            case String s when s.matches("^C([A-F0-9]{3})") -> {
                int random = (int) (Math.random() * (255 - 0) + 0);
                Vx = Integer.toHexString(random & Integer.parseInt(opcode.substring(2), 16));
            }
            case String s when s.matches("^D([A-F0-9]{3})") -> {
                //"ERROR : NOT IMPLEMENTED"
                //coordinates X
                //coordinates Y
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
            case String s when s.matches("^F[A-F0-9]07") -> Vx = Integer.toHexString(getDelay());
            case String s when s.matches("^F[A-F0-9]0A") -> Vx = Integer.toHexString(getKey());
            case String s when s.matches("^F[A-F0-9]15") -> delayTimer = Integer.parseInt(Vx, 16);
            case String s when s.matches("^F[A-F0-9]18") -> soundTimer = Integer.parseInt(Vx, 16);
            case String s when s.matches("^F[A-F0-9]1E") -> {
                if(!Vx.equals("VF")){
                    adressI = Integer.toHexString(Integer.parseInt(adressI, 16) + Integer.parseInt(Vx,16));
                }
            }
            case String s when s.matches("^F[A-F0-9]29") -> {
                adressI = Integer.toHexString(x*5); //multiplied by 5 because each sprite is 4*5 bit long
            }
            case String s when s.matches("^F[A-F0-9]33") -> {
                String decimal = "01010110"; //V = 56 = 0101 0110 en binaire
                decimal += decimal + Integer.toBinaryString(Integer.parseInt(String.valueOf(opcode.charAt(2)), 16));
                int adressI1 = Integer.parseInt(adressI, 16) + 1;
                int adressI2 = Integer.parseInt(adressI, 16) + 2;
                //manage if VX is in tens or units, it will get shifted (4, 8 or 12 digits in the Vx decimal)
                //not sure it can happen but better safe than sorry
                if(Vx.length() == 4){
                    //only units
                    memory[adressI2] = Integer.parseInt(decimal,16);
                }
                else if(Vx.length() == 8){
                    //tens and units
                    memory[adressI1] = Integer.parseInt(decimal.substring(0,4),16);
                    memory[adressI2] = Integer.parseInt(decimal.substring(4),16);
                }
                else if(Vx.length() == 12){
                    //hundreds tens and units
                    memory[Integer.parseInt(adressI, 16)] = Integer.parseInt(decimal.substring(0,4),16);
                    memory[adressI1] = Integer.parseInt(decimal.substring(4,8),16);
                    memory[adressI2] = Integer.parseInt(decimal.substring(8),16);
                }
            }
            case String s when s.matches("^F[A-F0-9]55") -> {
                for(int registerIndex = 0; registerIndex <= x; registerIndex++){
                    memory[Integer.parseInt(adressI, 16) + registerIndex] = registersV[registerIndex];
                }
            }
            case String s when s.matches("^F[A-F0-9]65") -> {
                // read values from memory, starting from I, and dump them in the corresponding registers
                for(int registerIndex = 0; registerIndex <= x; registerIndex++){
                   registersV[registerIndex] = memory[Integer.parseInt(adressI, 16) + registerIndex];
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
}
