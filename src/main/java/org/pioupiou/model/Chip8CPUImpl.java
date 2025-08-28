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
                    this.fillMemory(0x200, index, hexByte);
                    this.fillMemory(0x200+1, index+1, hexByte);

                } else {
                    this.fillMemory(pc, index, hexByte);
                    this.fillMemory(pc + 1, index + 1, hexByte);
                }
                pc+=2; //up local pc to two while loading game but do not change opcode program counter
            }
            //once game is loaded in memory, start emulation loop
            while(opcodes.getProgramCounter() < 4096) {
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
    public boolean isDrawFlag() {
        return opcodes.isDrawFlag();
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

    private void fillMemory(int memoryIndex, int hexByteIndex, byte[] hexByte){
        if(hexByte[hexByteIndex] < 0){
            memory[memoryIndex] = hexByte[hexByteIndex] & 0xFF; //use 0xFF to go from signed byte to unsigned int
        }
        else memory[memoryIndex] = hexByte[hexByteIndex];
    }

    public void setOpcodes(Opcodes opcodes) {
        this.opcodes = opcodes;
    }

}
