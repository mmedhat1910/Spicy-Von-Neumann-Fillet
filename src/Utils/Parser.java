package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Parser {
    final ArrayList<String> codeText;

    public Parser(String file) throws IOException {
        this.codeText = loadCodeText(file);
    }
    public int parseInstruction(String codeline){
        String[] instruction = codeline.split(" ");
        int instructionValue = 0;
        if(instruction[0].equals("J")){
            instructionValue = 0b011100000000000000000000000000000;;
            return instructionValue | Integer.parseInt(instruction[1]);
        }

        instructionValue = switch (instruction[0]) {
            // R types

            case "SUB" -> 0b0001;
            case "SLL" -> 0b1000;
            case "SRL" -> 0b1001;
            // I types
            case "MULI" -> 0b0010;
            case "ADDI" -> 0b0011;
            case "BNE" -> 0b0100;
            case "ANDI" -> 0b0101;
            case "ORI" -> 0b0110;
            case "LW" -> 0b1010;
            case "SW" -> 0b1011;
            default -> instructionValue;
        };
        //shift opcode
        instructionValue = instructionValue << 28;

        //R1
        int tempReg =  Integer.parseInt(instruction[1].substring(1));
        tempReg = tempReg << 23;
        instructionValue = instructionValue | tempReg;
        //R2
        tempReg =  Integer.parseInt(instruction[2].substring(1));
        tempReg = tempReg << 18;
        instructionValue = instructionValue | tempReg;

        if(instruction[0].equals("ADD") || instruction[0].equals("SUB")){
            int r3 =  Integer.parseInt(instruction[3].substring(1));
            r3 = r3 << 13;
            return instructionValue | r3;
        }
        //shamt or imm
        return instructionValue | Integer.parseInt(instruction[3]);

        }

        public ArrayList<Integer> getInstructions(){
            ArrayList<Integer> instructions = new ArrayList<>();
            for (String inst : codeText){
                instructions.add(parseInstruction(inst));
            }
            return instructions;
        }
    public ArrayList<String> loadCodeText(String filename) throws IOException {
        ArrayList<String> code = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while (line != null)
        {
            code.add(line); line = br.readLine();
        }
        br.close();
        return code;
    }


    public ArrayList<String> getCodeText() {
        return codeText;
    }

    public static void main(String[] args) throws IOException {
        Parser p = new Parser("code.txt");
        System.out.println(p.getCodeText());
        p.parseInstruction(p.getCodeText().get(0));
        for (String inst : p.getCodeText()){
            System.out.println( Integer.toBinaryString(p.parseInstruction(inst)));
        }
        //TODO test all instructions parsing


    }
}
