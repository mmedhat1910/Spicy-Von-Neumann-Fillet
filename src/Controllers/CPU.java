package Controllers;

import Components.ALU;
import Components.Decoder;
import Components.MainMemory;
import Components.RegisterFile;
import Utils.Parser;

import java.io.IOException;
import java.util.ArrayList;
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

    Queue<Integer> fetchQueue = new LinkedList<Integer>();
    Queue<Integer> decodeQueue = new LinkedList<Integer>();
    Queue<Integer> executeQueue = new LinkedList<Integer>();
    Queue<Integer> memoryQueue = new LinkedList<Integer>();
    Queue<Integer> wbQueue = new LinkedList<Integer>();

    int fetched = -1;
    int decoded = -1;
    int executed = -1;
    int memoryOperated = -1;
    int writtenBack = -1;

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
            int instruction = instructions.get(index);
            memory.setWriteAddress(index);
            memory.writeData(instruction);
            instructionCount++;
        }
//        int cycle = 1;
        int decodeCycles = 1;
        int executeCycles = 1;
        int cycle = 1;
        while (true) {
            System.out.println("Cycle: "+cycle);
            System.out.println("--------------------------------------");

            if(cycle % 2 == 1){
                fetch();
            }
            decode(decodeCycles);
            execute(executeCycles);
            if(cycle % 2 == 0){
                memoryOp();
            }
            writeBack();
            decodeCycles++;
            executeCycles++;

            System.out.println("--------------------------------------");
            cycle++;

            if(fetched == -1 && decoded == -1 && executed == -1 && writtenBack == -1 && memoryOperated == -1) break;
        }
    }

    public void fetch() {
        memory.setReadAddress(pc);
        int instruction = memory.getInstruction();
        fetched = instruction;
        System.out.println("fetch: "+ instruction);
        pc++;
        fetchQueue.add(instruction);
        if(pc <= instructionCount){
            done = false;
        }
    }

    public void decode(int cycles){
        if(cycles %2 == 0)
            decoded = -1;
        if(decodeQueue.size() > 0){
            // decode prev instruction
            int instruction = decodeQueue.poll();
            decoded = instruction;
            System.out.println("decode: "+ instruction);

            decoder.decode(instruction);
            registerFile.setReadReg1(decoder.getR2());
            registerFile.setReadReg2(decoder.getR3());
            registerFile.setWriteReg(decoder.getR1());
            registerFile.setRegWrite(controlUnit.isRegWrite());

        }
        if(fetchQueue.size() > 0){
            int instruction = fetchQueue.peek();

                decodeQueue.add(fetchQueue.poll());
                decoded = instruction;

        }
    }

    public void execute(int cycles){
//        if(cycles %2 == 0)
//            return;
        if(executeQueue.size() > 0){
            // decode prev instruction
            int instruction = executeQueue.poll();
            executed = instruction;
            System.out.println("execute: "+ instruction);

            alu.setControl(controlUnit.getALUOp());
            alu.setOp1(registerFile.getData1());
            if(controlUnit.isALUSrc()){
                int op = instruction >> 28;
                if(op == 2 || op==3 || op==4 || op==5 || op==6  || op==10 || op==11){
                    alu.setOp2(decoder.getImm());
                }else if(op == 7){
                    alu.setOp2(decoder.getAddress());
                }else if(op == 8 || op==9){
                    alu.setOp2(decoder.getShamt());
                }
                // TODO sign extended immediate or shamt
            }else{
                alu.setOp2(registerFile.getData2());
            }
        }
        if(decodeQueue.size() > 0){
            int instruction = decodeQueue.peek();
            if(instruction != decoded){
                executeQueue.add(decodeQueue.poll());
                executed = instruction;
            }else{
                decoded = -1;
            }
        }
    }

    public void memoryOp(){

        if(memoryQueue.size() > 0){
            int instruction = memoryQueue.poll();
            memoryOperated = instruction;
            System.out.println("memory: "+ instruction);

            memory.setMemRead(controlUnit.isMemRead());
            memory.setMemWrite(controlUnit.isMemWrite());
            memory.setReadAddress(alu.getResult());
            memory.writeData(registerFile.getData2());

        }
        if(executeQueue.size() > 0){
            int instruction = executeQueue.peek();
            if(instruction != executed){
                memoryQueue.add(executeQueue.poll());
                memoryOperated = instruction;
            }else{
                executed = -1;
            }
        }
    }

    public void writeBack(){

        if(wbQueue.size()>0){
            int instruction = wbQueue.poll();
            writtenBack = instruction;
            System.out.println("write back: "+ instruction);

            if(controlUnit.isMemtoReg()){
                registerFile.writeData(memory.readData());
            }else{
                registerFile.writeData(alu.getResult());
            }
        }
        if(memoryQueue.size() > 0){
            int instruction = memoryQueue.peek();
            if(instruction == memoryOperated){
                wbQueue.add(memoryQueue.poll());
                writtenBack = instruction;
            }else{
                memoryOperated = -1;
            }
        }
    }

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
