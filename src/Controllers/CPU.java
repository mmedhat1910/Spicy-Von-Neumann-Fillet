package Controllers;

import Components.ALU;
import Components.Decoder;
import Components.MainMemory;
import Components.RegisterFile;
import Utils.Parser;

import java.io.IOException;
import java.util.ArrayList;

public class CPU {
    int pc;
    MainMemory memory;
    RegisterFile registerFile;
    Decoder decoder;
    ControlUnit controlUnit;
    Parser parser;
    ALU alu;

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
        while (true) {

        }
    }

    public void fetch() {
        int instruction = memory.readData(pc);
        pc++;
        decoder.decode(instruction);
    }
}
