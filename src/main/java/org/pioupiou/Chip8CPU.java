package org.pioupiou;

public class Chip8CPU {

    private boolean drawFlag;

    public boolean getDrawFlag() {
        return drawFlag;
    }

    public void setDrawFlag(boolean drawFlag) {
        this.drawFlag = drawFlag;
    }

    public void initialize(){

    }

    public void loadGame(String gameName){

    }

    public void emulate(){
        //fetch opcode
        int programCounter = 0; //will be dependent of input
        int[] memory = new int[4096]; //binary
            //data at a memory location is one byte, but an opcode is two, so we use pc and pc+1
        int locn = memory[programCounter]; //binary
        int locn1 = memory[programCounter+1]; //binary
            //merge both with a bitwise OR by shifting locn 8 bits, then merging
        int opcode = locn << 8 | locn1;
        Opcodes.executeOpcode(Integer.toHexString(opcode));

        //decode opcode
        //execute opcode
    }
}
