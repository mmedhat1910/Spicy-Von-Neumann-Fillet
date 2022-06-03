package Utils;

import Components.Decoder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    final ArrayList<String> codeText;

    public Parser(String file) throws IOException {
        this.codeText = loadCodeText(file);
    }

    public int parseInstruction(String codeline){
        String[] instruction = codeline.split(" ");
        int instructionValue = 0;
        if(instruction[0].equals("J")){
            instructionValue = 0b01110000000000000000000000000000;
            return instructionValue | Integer.parseInt(instruction[1]);
        }else if(instruction[0].equals("NOP")){
            instructionValue = 0b11000000000000000000000000000000;
            return instructionValue;
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
        int imm = Integer.parseInt(instruction[3]) & 0b00000000000000111111111111111111;
        return instructionValue | imm;

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



    public String reverseParse(int instruction) {
        Decoder decoder = new Decoder();
        decoder.decode(instruction);
        int opcode = decoder.getOpcode();

        String op = switch (opcode) {
            case 0b0000 -> "ADD";
            case 0b0001 -> "SUB";
            case 0b0010 -> "MULI";
            case 0b0011 -> "ADDI";
            case 0b0100 -> "BNE";
            case 0b0101 -> "ANDI";
            case 0b0110 -> "ORI";
            case 0b0111 -> "J";
            case -8 -> "SLL";
            case -7 -> "SRL";
            case -6 -> "LW";
            case -5 -> "SW";
            default -> "NOP";
        };

        String r1 = "R" + decoder.getR1();
        String r2 = "R" + decoder.getR2();
        String r3 = "R" + decoder.getR3();
        String imm = decoder.getImm() + "";
        String shamt = decoder.getShamt() + "";
        String address = decoder.getAddress() + "";

//        int[] R_inst_arr = new int[]{0,1-8,-7};
//        ArrayList<Integer> R_inst = Arrays.asList(R_inst_arr);
        if(opcode  == 0|| opcode == 1 || opcode == -8 || opcode == -7)
            return op + " " + r1 + " " + r2 + " " + r3;
        else if (opcode == 7)
            return op + " " + address;
        else if(op.equals("NOP")) return "NOP";
        else
            return op + " " + r1 + " " + r2 + " " + imm;

    }
    public static void main(String[] args) throws IOException {
        Parser p = new Parser("code.txt");
        System.out.println(p.getCodeText());
        p.parseInstruction(p.getCodeText().get(0));
        for (String inst : p.getCodeText()){
            System.out.println( Integer.toBinaryString(p.parseInstruction(inst)));
        }
        //TODO test all instructions parsing
        System.out.println(p.parseInstruction("ADDI R1 R2 -5"));
        System.out.println(p.reverseParse(p.parseInstruction("ADD R1 R2 R3")));

    }

}
