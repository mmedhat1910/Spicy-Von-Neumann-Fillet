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

    Queue<Integer> fetchQueue = new LinkedList<Integer>();
    Queue<Integer> decodeQueue = new LinkedList<Integer>();
    Queue<Integer> executeQueue = new LinkedList<Integer>();
    Queue<Integer> memoryQueue = new LinkedList<Integer>();
    Queue<Integer> wbQueue = new LinkedList<Integer>();

    public CPU() throws IOException {
        this.memory = new MainMemory();
        this.registerFile = new RegisterFile();
        this.decoder = new Decoder();
        this.controlUnit = new ControlUnit();

        this.alu = new ALU();
    }

    public void run(String filename) throws IOException {


        this.parser = new Parser(filename);
        ArrayList<Integer> instructions = parser.getInstructions();
        this.pc = 0;
        int index = 0;

        // saving instructions to instruction memory
        while (index < instructions.size()) {
            int instruction = instructions.get(index);
            memory.writeData(index, instruction);
        }
        int cycle = 1;
        int decodeCycles = 1;
        while (true) {
            if(cycle % 2 == 1){
                fetch();
            }
            decode(decodeCycles);

        }
    }

    public void fetch() {
        int instruction = memory.getInstruction(pc);
        pc++;
        fetchQueue.add(instruction);
    }

    public void decode(int cycles){
        if(cycles %2 == 1)
            return;
        if(decodeQueue.size() > 0){
            // decode prev instruction
            int instruction = decodeQueue.poll();
            decoder.decode(instruction);
            registerFile.setReadReg1(decoder.getR2());
            registerFile.setReadReg2(decoder.getR3());
            registerFile.setWriteReg(decoder.getR1());
            registerFile.setRegWrite(controlUnit.isRegWrite());
        }
        if(fetchQueue.size() > 0){
            int instruction = fetchQueue.poll();
            decodeQueue.add(instruction);
        }
    }

    public void execute(int cycles){
        if(cycles %2 == 1)
            return;
        if(executeQueue.size() > 0){
            // decode prev instruction
            int instruction = executeQueue.poll();

        }
        if(fetchQueue.size() > 0){
            int instruction = fetchQueue.poll();
            decodeQueue.add(instruction);
        }
    }
}
