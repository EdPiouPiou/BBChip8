package org.pioupiou;

public class Opcodes {
    public static void executeOpcode(String opcode){
        switch(opcode){
            case "ONNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "00EO" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "00EE" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "1NNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "2NNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "3XNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "4XNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "5XY0" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "6XNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "7XNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "8XY0" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "8XY1" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "8XY2" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "8XY3" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "8XY4" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "8XY5" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "8XY6" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "8XY7" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "8XYE" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "9XY0" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "ANNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "BNNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "CXNN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "DXYN" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "EX9E" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "EXA1" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "FX07" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "FX0A" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "FX15" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "FX18" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "FX1E" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "FX29" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "FX33" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "FX55" -> System.out.println("ERROR : NOT IMPLEMENTED");
            case "FX65" -> System.out.println("ERROR : NOT IMPLEMENTED");
            default -> System.out.println("ERROR : UNKNOWN OPCODE");
        }
    }
}
