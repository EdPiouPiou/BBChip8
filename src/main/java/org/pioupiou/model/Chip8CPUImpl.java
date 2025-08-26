package org.pioupiou.model;

import java.io.*;

public class Chip8CPUImpl implements Chip8CPU{
    private int[] memory;
    private boolean[][] display;
    private boolean drawFlag;
    private Opcodes opcodes;

    public Chip8CPUImpl(){
        this.memory = new int[4096];
        this.display = new boolean[32][64];
        this.drawFlag = false;
        this.opcodes = new Opcodes(display, memory, 0x200);
    }
    @Override
    public void initialize(){

    }

    @Override
    public void loadGame(File chip8ROM) {
        try (FileInputStream fileInputStream = new FileInputStream(chip8ROM);) {
            byte[] hexByte = new byte[fileInputStream.available()];
            fileInputStream.read(hexByte);
            int pc = opcodes.getProgramCounter();
            //NB : 1 op in ch8 file = 16 bits, 8 bits = 1 byte, 1 byte per memory slot, hence for one opcode we use memory[n] and memory [n+1]
            // for every opcode = 2 bytes, pc += 2
            for(int index = 0; index < hexByte.length - 1; index+=2){
                if (pc == 0x200) {
                    memory[0x200] = hexByte[index] & 0xFF; //use 0xFF to go from signed byte to unsigned int
                    memory[0x200+1] = hexByte[index+1] & 0xFF;
                } else {
                    memory[0x200 + pc] = hexByte[index] & 0xFF;
                    memory[0x200 + pc + 1] = hexByte[index+1] & 0xFF;
                }
                pc+=2; //up local pc to two while loading game but do not change opcode program counter
            }
            //once game is loaded in memory, start emulation loop
            for(;;) {
                this.emulate();
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
        return opcodes.getSpriteToBeRendered();
    }

    @Override
    public void emulate(){
        //FETCH OPCODE
        int programCounter = opcodes.getProgramCounter();
        //data at a memory location is one byte, but an opcode is two, so we use pc and pc+1 to read 1 opcode
        int locn = memory[programCounter]; //binary
        int locn1 = memory[programCounter+1]; //binary
            //merge both with a bitwise OR by shifting locn 8 bits, then merging
        int opcode = locn << 8 | locn1;

        //DECODE AND EXEC OPCODE
        opcodes.executeOpcode(Integer.toHexString(opcode));

        //UPDATE TIMERS
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

    public void setOpcodes(Opcodes opcodes) {
        this.opcodes = opcodes;
    }

}
