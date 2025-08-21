package org.pioupiou.model;

import java.io.*;

public class Chip8CPUImpl implements Chip8CPU{
    private int[] memory = new int[4096];
    private boolean[][] display = new boolean[32][64];//binary
    private boolean drawFlag;
    Opcodes opcodes = new Opcodes(display, memory);

    @Override
    public void initialize(){

    }

    @Override
    public void loadGame(File chip8ROM) {
        try (FileInputStream fileInputStream = new FileInputStream(chip8ROM);) {
            byte[] hexByte = new byte[fileInputStream.available()];
            fileInputStream.read(hexByte);
            int pc = 0;
            //NB : 1 op in ch8 file = 16 bits, 8 bits = 1 byte, 1 byte per memory slot, hence for one opcode we use memory[n] and memory [n+1]
            // for every opcode = 2 bytes, pc += 2
            for (byte b : hexByte) {
                if (pc == 0) {
                    memory[0x200] = b;
                } else {
                    memory[0x200 + pc] = b;
                }
                pc++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
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
        opcodes.executeOpcode(Integer.toHexString(opcode));
    }

    @Override
    public boolean getDrawFlag() {
        return drawFlag;
    }

    @Override
    public void setDrawFlag(boolean drawFlag) {
        this.drawFlag = drawFlag;
    }

    @Override
    public void getKeyPressed(String keyPressed) {

    }

    @Override
    public void getKeyReleased(String keyReleased) {

    }
}
