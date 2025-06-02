package org.pioupiou;

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

    public Opcodes(boolean[][] display, int[] memory, String Vx, String Vy) {
        this.display = display;
    }

    public void executeOpcode(String opcode){
        switch(opcode){
            case "ONNN":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "00EO":
                this.clearDisplay();
                break;
            case "00EE":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "1NNN":
                currentMemoryAddress = "0x" + opcode.substring(1);
                break;
            case "2NNN":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "3XNN":
                Vx = "V" + opcode.charAt(1);
                if(Vx == opcode.substring(2)){
                    skip();
                };
                break;
            case "4XNN":
                Vx = "V" + opcode.charAt(1);
                if(Vx != opcode.substring(2)){
                    skip();
                };
                break;
            case "5XY0":
                Vx = "V" + opcode.charAt(1);
                Vy = "V" + opcode.charAt(2);
                if(Vx == Vy){
                    skip();
                };
                break;
            case "6XNN":
                Vx = opcode.substring(2);
                break;
            case "7XNN":
                Vx = "V" + opcode.charAt(1);
                if(Vx != "VF"){
                    Vx = Integer.toHexString(Integer.parseInt(Vx, 16) + Integer.parseInt(opcode.substring(2),16));
                }
                break;
            case "8XY0":
                Vx = "V" + opcode.charAt(2);
                break;
            case "8XY1":
                Vx = "V" + opcode.charAt(1);
                Vx = Integer.toHexString(Integer.parseInt(Vx, 16) | Integer.parseInt(opcode.substring(2),16));
                break;
            case "8XY2":
                Vx = "V" + opcode.charAt(1);
                Vx = Integer.toHexString(Integer.parseInt(Vx, 16) & Integer.parseInt(opcode.substring(2),16));
                break;
            case "8XY3":
                Vx = "V" + opcode.charAt(1);
                Vx = Integer.toHexString(Integer.parseInt(Vx, 16) ^ Integer.parseInt(opcode.substring(2),16));
                break;
            case "8XY4":
                Vx = "V" + opcode.charAt(1);
                Vy = "V" + opcode.charAt(2);
                int tempResultOverflow = Integer.parseInt(Vx, 16) + Integer.parseInt(Vy,16); //used to check for overflow
                if(tempResultOverflow < 0 ){ //check for overflow
                    Vf = 1;
                }
                else{
                    Vf = 0;
                }
                break;
            case "8XY5":
                Vx = "V" + opcode.charAt(1);
                Vy = "V" + opcode.charAt(2);
                if(Integer.parseInt(Vx, 16) >= Integer.parseInt(Vy, 16)){ //check for underflow
                    Vf = 1;
                }
                else{
                    Vf = 0;
                }
                break;
            case "8XY6":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "8XY7":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "8XYE":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "9XY0":
                Vx = "V" + opcode.charAt(1);
                Vy = "V" + opcode.charAt(2);
                if(Vx != Vy){
                    skip();
                };
                break;
            case "ANNN":
                adressI = opcode.substring(1);
                break;
            case "BNNN":
                programCounter = Integer.parseInt("V0", 16) + Integer.parseInt(opcode.substring(1),16);
                break;
            case "CXNN":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "DXYN":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "EX9E":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "EXA1":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "FX07":
                Vx = Integer.toHexString(getDelay());
                break;
            case "FX0A":
                Vx = Integer.toHexString(getKey());
                break;
            case "FX15":
                delayTimer = Integer.parseInt(Vx, 16);
                break;
            case "FX18":
                soundTimer = Integer.parseInt(Vx, 16);
                break;
            case "FX1E":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "FX29":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "FX33":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "FX55":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
            case "FX65":
                System.out.println("ERROR : NOT IMPLEMENTED");
                break;
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
