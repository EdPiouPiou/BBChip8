package org.pioupiou;

public class Chip8CPU {
    private int[] memory = new int[4096];
    private boolean[][] display = new boolean[32][64];//binary
    private String Vx = "V0";
    private String Vy = "V0";
    private boolean drawFlag;
    Opcodes opcodes = new Opcodes(display, memory, Vx, Vy);

    public void initialize(){

    }

    public void loadGame(String gameName){

    }

    public void emulate(){
        //fetch opcode
        int programCounter = 0; //will be dependent of input
            //data at a memory location is one byte, but an opcode is two, so we use pc and pc+1
        int locn = memory[programCounter]; //binary
        int locn1 = memory[programCounter+1]; //binary
            //merge both with a bitwise OR by shifting locn 8 bits, then merging
        int opcode = locn << 8 | locn1;
        opcodes.executeOpcode(Integer.toHexString(opcode));

        //decode opcode
        //execute opcode
    }

    public boolean getDrawFlag() {
        return drawFlag;
    }

    public void setDrawFlag(boolean drawFlag) {
        this.drawFlag = drawFlag;
    }
}
