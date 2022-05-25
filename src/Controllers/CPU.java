package Controllers;

import Components.ALU;
import Components.Decoder;
import Components.MainMemory;
import Components.RegisterFile;
import Utils.Parser;
import Utils.StageBuffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class CPU {
    int pc;
    MainMemory memory;
    RegisterFile registerFile;
    Decoder decoder;
    ControlUnit controlUnit;
    Parser parser;
    ALU alu;
    int instructionCount = 0;
    int sp;

    StageBuffer<Object> fetchDecode1 = new StageBuffer<>();
    StageBuffer<HashMap<String, Integer>> decode1Decode2 = new StageBuffer<>();
    StageBuffer<HashMap<String, Integer>> decode2Execute1 = new StageBuffer<>();




    boolean done = true;

    public CPU() throws IOException {
        this.memory = new MainMemory();
        this.registerFile = new RegisterFile();
        this.decoder = new Decoder();
        this.controlUnit = new ControlUnit();
        this.alu = new ALU();
        this.pc = 1;
        this.sp=1024;

    }

    public void run(String filename) throws IOException {

        this.parser = new Parser(filename);
        ArrayList<Integer> instructions = parser.getInstructions();
        this.pc = 0;
//        int index = 0;

        // saving instructions to instruction memory
        for (int index = 0; index < instructions.size();index++) {
            Object instruction = instructions.get(index);
            memory.setWriteAddress(index);
//            memory.writeData(instruction);
            instructionCount++;
        }
//        int cycle = 1;

        int cycle = 1;
        while (true) {
            System.out.println("Cycle: "+cycle);
            System.out.println("--------------------------------------");

            if(cycle % 2 == 1){
                fetch();
            }
            decode1();
            decode2();
            execute1();
            execute2();
            if(cycle % 2 == 0){
                memoryOp();
            }
            writeBack();


            System.out.println("--------------------------------------");
            cycle++;


        }
    }



    public void fetch() {
        memory.setReadAddress(pc);
        int instruction = memory.getInstruction();
        System.out.println("fetch: "+ instruction);
        pc++;
        fetchDecode1.setNewBlock(instruction);
        if(instruction == -1){
            // TODO when instruction end
        }
    }

    public void decode1(){
            Object inst = fetchDecode1.getOldBlock();
            if (inst != null) {
                int instruction = (int) inst;
                System.out.println("decoding 1: " + instruction);
                decoder.decode(instruction);
                HashMap<String, Integer> map = new HashMap<>();

                map.put("r1", decoder.getR1());
                map.put("r2", decoder.getR2());
                map.put("r3", decoder.getR3());
                map.put("imm", decoder.getImm());
                map.put("shamt", decoder.getShamt());
                map.put("address", decoder.getAddress());
                map.put("instruction", instruction);
                map.put("pc", pc);
                decode1Decode2.setNewBlock(map);

            }
        }

    public void decode2(){
        HashMap<String, Integer> map = decode1Decode2.getOldBlock();
        if (map != null) {
            System.out.println("decoding 2: " + map.get("instruction"));
            registerFile.setReadReg1(map.get("r2"));
            registerFile.setReadReg2(map.get("r3"));
            registerFile.setWriteReg(map.get("r1"));
            registerFile.setRegWrite(controlUnit.isRegWrite());

            HashMap<String, Integer> map2 = new HashMap<>();
            map2.put("imm", map.get("imm"));
            map2.put("shamt", map.get("shamt"));
            map2.put("address", map.get("address"));
            map2.put("instruction", map.get("instruction"));
            map2.put("readData1", registerFile.getData1());
            map2.put("readData2", registerFile.getData2());
            map2.put("pc", pc);
            decode2Execute1.setNewBlock(map2);

        }
    }



    public void execute1(){
        HashMap<String, Integer> map = decode2Execute1.getOldBlock();
        if (map != null) {

            int instruction = map.get("instruction");
            System.out.println("execute: "+ instruction);

            alu.setControl(controlUnit.getALUOp());
            alu.setOp1(map.get("readData1"));
            if(controlUnit.isALUSrc()){
                int op = instruction >> 28;

                if(op == 0b0010 || op==0b0011 || op==0b0100|| op==0b0101 || op == 0b0110  || op == -6 || op== -5){
                    alu.setOp2(map.get("imm"));
                }else if(op == 7){
                    alu.setOp2(map.get("address"));
                }else if(op == -8 || op==-7){
                    alu.setOp2(map.get("shamt"));
                }
                // TODO sign extended immediate or shamt
                // TODO sign extender ask about it
            }else{
                alu.setOp2(map.get("readData2"));
            }
        }
    }

    public void execute2() {
        //TODO FOR JUMP AND BRANCH
    }

    public void memoryOp(){}

    public void writeBack(){}

    public static void main(String[] args) {
        try {
            CPU cpu = new CPU();
            cpu.run("code.txt");
        } catch (IOException e) {
            System.out.println("Here");
            throw new RuntimeException(e);
        }
    }
}
