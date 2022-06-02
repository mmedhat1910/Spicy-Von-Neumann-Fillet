package Controllers;

import Components.ALU;
import Components.Decoder;
import Components.MainMemory;
import Components.RegisterFile;
import Utils.Parser;
import Utils.PipelineRegister;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CPU {
    int pc;
    MainMemory memory;
    RegisterFile registerFile;
    Decoder decoder;
    ControlUnit controlUnit;
    Parser parser;
    ALU alu;
    int instructionCount = 0;

    PipelineRegister<HashMap<String, Integer>> fetch_decode1 = new PipelineRegister<>();
    PipelineRegister<HashMap<String, Integer>> decode1_decode2 = new PipelineRegister<>();
    PipelineRegister<HashMap<String, Integer>> decode2_execute1 = new PipelineRegister<>();
    PipelineRegister<HashMap<String, Integer>> execute1_execute2 = new PipelineRegister<>();
    PipelineRegister<HashMap<String, Integer>> execute2_memory = new PipelineRegister<>();
    PipelineRegister<HashMap<String, Integer>> memory_writeBack = new PipelineRegister<>();



    boolean done = false;
    boolean isDoneFetch = false;
    boolean isDoneDecode1 = false;
    boolean isDoneDecode2 = false;
    boolean isDoneExec1 = false;
    boolean isDoneExec2 = false;
    boolean isDoneMem = false;


    public CPU() throws IOException {
        this.memory = new MainMemory();
        this.registerFile = new RegisterFile();
        this.decoder = new Decoder();
        this.controlUnit = new ControlUnit();
        this.alu = new ALU();
        this.pc = 1;
    }

    public void run(String filename) throws IOException {

        this.parser = new Parser(filename);
        ArrayList<Integer> instructions = parser.getInstructions();

//        int index = 0;

        // saving instructions to instruction memory
        memory.setMemWrite(true);
        int index;
        for (index = 0; index < instructions.size();index++) {
            int instruction = instructions.get(index);
            memory.setAddress(index);
            memory.writeData(instruction);
            instructionCount++;
        }
//        System.out.println("Index "+ index);
        memory.setAddress(index);
        memory.writeData(-1);
        memory.setMemWrite(false);
//        int cycle = 1;

        int cycle = 1;
        while(!done) { //TODO: change this
            System.out.println("Cycle: "+cycle);
            System.out.println("--------------------------------------");

            if(cycle % 2 == 1){
                //fetch runs in odd cycles
                fetch();
            }
            decode1();
            decode2();
            execute1();
            execute2();
            if(cycle % 2 == 0){
                //memory runs in even cycles
                memoryOp();
            }
            writeBack();


            propagatePipelines();
//            displayPipelines();
            cycle++;
            System.out.println("--------------------------------------");

        }
            System.out.println("Terminating... ");
            System.out.println(registerFile);
            memory.display(0, 20);
//            System.out.println("********** CHANGE WHILE LOOP ********** ");
    }



    public void fetch() {

        if(isDoneFetch) return;

        memory.setAddress(pc-1);
        int instruction = memory.getInstruction();
        //TODO REMOVE

        System.out.println("fetch: "+ instruction);
        HashMap<String, Integer> instructionMap = new HashMap<>();
        instructionMap.put("instruction", instruction);
        instructionMap.put("pc", pc);
        fetch_decode1.setNewBlock(instructionMap);
        if(instruction == -1){
            System.out.println("Fetching HALT");
            isDoneFetch = true;
            return;
        }
        pc++;

    }

    public void decode1(){

        if(isDoneDecode1) return;

        HashMap<String, Integer> fetchMap = fetch_decode1.getOldBlock();

            if (fetchMap != null) {
                int instruction = fetchMap.get("instruction");

                System.out.println("decoding 1: " + instruction);

                HashMap<String, Integer> map = new HashMap<>();

                if(instruction == -1){
                    System.out.println("Decode 1 HALT");
                    isDoneDecode1 = true;
                    map.put("instruction", instruction);
                    map.put("pc", fetchMap.get("pc"));
                    decode1_decode2.setNewBlock(map);
                    return;
                }
                if((instruction >>28) == -4 ){
                    return;
                }

                decoder.decode(instruction);
                // control
                controlUnit.run(decoder.getOpcode());


                map.put("regWrite", controlUnit.isRegWrite() ? 1 : 0);
                map.put("memRead", controlUnit.isMemRead() ? 1 : 0);
                map.put("memWrite", controlUnit.isMemWrite() ? 1 : 0);
                map.put("branch", controlUnit.isBranch() ? 1 : 0);
                map.put("memToReg", controlUnit.isMemtoReg() ? 1 : 0);
                map.put("regDst", controlUnit.isRegDst() ? 1 : 0);
                map.put("ALUSrc", controlUnit.isALUSrc() ? 1 : 0);
                map.put("ALUOp", controlUnit.getALUOp());


                // registers
                map.put("r1", decoder.getR1());
                map.put("r2", decoder.getR2());
                map.put("r3", decoder.getR3());
                map.put("imm", decoder.getImm());
                map.put("shamt", decoder.getShamt());
                map.put("address", decoder.getAddress());
                map.put("instruction", instruction);
                map.put("pc", fetchMap.get("pc"));
                decode1_decode2.setNewBlock(map);

            }
        }

    public void decode2(){

        if(isDoneDecode2) return;


        HashMap<String, Integer> map = decode1_decode2.getOldBlock();
        if (map != null) {
            System.out.println("decoding 2: " + map.get("instruction"));
            int instruction = map.get("instruction");

            HashMap<String, Integer> decode2Map = new HashMap<>();

            if(instruction == -1){
                System.out.println("Decode 2 HALT");
                isDoneDecode2 = true;
                decode2Map.put("instruction", map.get("instruction"));
                decode2Map.put("pc", map.get("pc"));
                decode2_execute1.setNewBlock(decode2Map);
                return;
            }

            if((instruction >>28) == -4 ){
                return;
            }

            registerFile.setReadReg1(map.get("r2"));
            registerFile.setReadReg2(map.get("r3"));
            registerFile.setWriteReg(map.get("r1"));
            registerFile.setRegWrite(map.get("regWrite") == 1);


//            if((map.get("instruction") >> 28) ==  4){
//                System.out.println("Helloo");
//                System.out.println(map.get("r1"));
//                System.out.println(map.get("r2"));
//                registerFile.setReadReg1(map.get("r1"));
//                registerFile.setReadReg2(map.get("r2"));
//
//                System.out.println(registerFile.getReadReg1());
//                System.out.println(registerFile.getReadReg2());
//
//                System.out.println(registerFile.getData1());
//                System.out.println(registerFile.getData2());
//            }




            decode2Map.put("memRead", map.get("memRead"));
            decode2Map.put("memWrite", map.get("memWrite"));
            decode2Map.put("branch", map.get("branch"));
            decode2Map.put("memToReg", map.get("memToReg"));
            decode2Map.put("regDst", map.get("regDst"));
            decode2Map.put("ALUSrc", map.get("ALUSrc"));
            decode2Map.put("ALUOp", map.get("ALUOp"));
            decode2Map.put("r1", map.get("r1"));
            decode2Map.put("regWrite", map.get("regWrite"));
            decode2Map.put("imm", map.get("imm"));
            decode2Map.put("shamt", map.get("shamt"));
            decode2Map.put("address", map.get("address"));
            decode2Map.put("instruction", map.get("instruction"));
            decode2Map.put("readData1", registerFile.getData1());
            decode2Map.put("readData2", registerFile.getData2());


            decode2Map.put("pc", map.get("pc"));
            // pass r1 to memory
            registerFile.setReadReg1(map.get("r1"));
            decode2Map.put("readDataR1", registerFile.getData1());

            decode2_execute1.setNewBlock(decode2Map);

        }
    }

    public void execute1(){

        if(isDoneExec1) return;


        HashMap<String, Integer> map = decode2_execute1.getOldBlock();
        if (map != null) {

            int instruction = map.get("instruction");

            HashMap<String, Integer> execute1Map = new HashMap<>();


            if(instruction == -1){
                System.out.println("Execute 1 HALT");
                isDoneExec1 = true;
                execute1Map.put("instruction", instruction);
                execute1Map.put("pc", map.get("pc"));
                execute1_execute2.setNewBlock(execute1Map);

                return;
            }

            if((instruction >>28) == -4 ){
                return;
            }

            System.out.println("execute: "+ instruction);

            alu.setControl(map.get("ALUOp"));
            alu.setOp1(map.get("readData1"));
            if(map.get("ALUSrc") == 1){
                int op = instruction >> 28;

                if(op == 0b0010 || op==0b0011 || op==0b0101 || op == 0b0110  || op == -6 || op== -5){
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



            execute1Map.put("memRead", map.get("memRead"));
            execute1Map.put("memWrite", map.get("memWrite"));
            execute1Map.put("branch", map.get("branch"));
            execute1Map.put("memToReg", map.get("memToReg"));
            execute1Map.put("readData2", map.get("readData2"));
            execute1Map.put("r1", map.get("r1"));
            execute1Map.put("regWrite", map.get("regWrite"));
            execute1Map.put("aluResult", alu.getResult());
            execute1Map.put("imm", map.get("imm"));
            execute1Map.put("shamt", map.get("shamt"));
            execute1Map.put("address", map.get("address"));
            execute1Map.put("instruction", map.get("instruction"));
            execute1Map.put("pc", map.get("pc"));
            execute1Map.put("not_zero", alu.getZero() ? 0 : 1); // if not zero then 1 else 0
            execute1Map.put("readDataR1", map.get("readDataR1"));
            execute1_execute2.setNewBlock(execute1Map);


        }
    }

    public void execute2() {

        if(isDoneExec2) return;


        HashMap<String, Integer> map = execute1_execute2.getOldBlock();
        if(map != null){
            System.out.println("execute2: "+ map.get("instruction"));
            int instruction = map.get("instruction");
            HashMap<String, Integer> execute2Map = new HashMap<>();

            if(instruction == -1){
                System.out.println("Execute 2 HALT");
                isDoneExec2 = true;
                execute2Map.put("pc", map.get("pc"));
                execute2Map.put("instruction", map.get("instruction"));
                execute2_memory.setNewBlock(execute2Map);
                return;
            }

            if((instruction >>28) == -4 ){ //NOP
                return;
            }
            int opcode = instruction >> 28;
            //branch

            if(map.get("branch") == 1 && map.get("not_zero") == 1){
//            if(map.get("branch") == 1){
                System.out.println("Immediate " + map.get("imm"));
                System.out.println("Pc " + map.get("pc"));
                this.pc = map.get("pc") + 1 + map.get("imm");
                System.out.println("Pc " + this.pc);
                System.out.println("Branching to "+this.pc);
                flushPipelines();
            }else if(opcode == 0b0111){ //jump
                this.pc = ((map.get("pc")) & 0b11110000000000000000000000000000) | map.get("address");
                // TODO: change this to handle pc - 1
                System.out.println("Jumping to "+this.pc + " by  "+ map.get("instruction"));
                flushPipelines();
            }





            execute2Map.put("memRead", map.get("memRead"));
            execute2Map.put("memWrite", map.get("memWrite"));
            execute2Map.put("memToReg", map.get("memToReg"));
            execute2Map.put("readData2", map.get("readData2"));
            execute2Map.put("aluResult", map.get("aluResult"));
            execute2Map.put("r1", map.get("r1"));
            execute2Map.put("regWrite", map.get("regWrite"));
            execute2Map.put("pc", map.get("pc"));
            execute2Map.put("instruction", map.get("instruction"));
            execute2Map.put("readDataR1", map.get("readDataR1"));



            execute2_memory.setNewBlock(execute2Map);



        }


        //TODO FOR JUMP AND BRANCH
    }

    public void memoryOp(){

        if(isDoneMem) return;


        HashMap<String, Integer> map = execute2_memory.getOldBlock();
        if(map != null){
            System.out.println("memory: "+ map.get("instruction"));

            int instruction = map.get("instruction");
            HashMap<String, Integer> memoryMap = new HashMap<>();

            if(instruction == -1){
                System.out.println("Memory HALT");
                isDoneMem = true;
                memoryMap.put("pc", map.get("pc"));
                memoryMap.put("instruction", map.get("instruction"));
                memory_writeBack.setNewBlock(memoryMap);
                return;
            }

            if((instruction >>28) == -4 ){ // NOP
                return;
            }

            memory.setMemWrite(map.get("memWrite") == 1);
            memory.setMemRead(map.get("memRead") == 1);
            memory.setAddress(map.get("aluResult"));
            memory.writeData(map.get("readDataR1")); // SW R1 R2 IMM -> MEM[R2+IMM] = R1


            memoryMap.put("r1", map.get("r1"));
            memoryMap.put("regWrite", map.get("regWrite"));
            memoryMap.put("memoryReadData", memory.getReadData());
            memoryMap.put("memToReg", map.get("memToReg"));
            memoryMap.put("aluResult", map.get("aluResult"));
            memoryMap.put("pc", map.get("pc"));
            memoryMap.put("instruction", map.get("instruction"));
            memory_writeBack.setNewBlock(memoryMap);



        }
    }

    public void writeBack(){
        HashMap<String, Integer> map = memory_writeBack.getOldBlock();
        if(map != null){
            System.out.println("writeBack: "+ map.get("instruction"));

            int instruction = map.get("instruction");

            if(instruction == -1){
                System.out.println("Write Back HALT");
                done = true;
                return;
            }

            if((instruction >>28) == -4 ){ // NOP
                return;
            }

            registerFile.setRegWrite(map.get("regWrite") == 1);
            registerFile.setWriteReg(map.get("r1"));
            if(map.get("memToReg") == 1){
                registerFile.writeData(map.get("memoryReadData"));
            }else{
                registerFile.writeData(map.get("aluResult"));
            }



        }
    }


    public void displayPipelines(){
        System.out.println("IF/ID1");
        System.out.println(fetch_decode1);
        System.out.println("ID1/ID2");
        System.out.println(decode1_decode2);
        System.out.println("ID2/EXE1");
        System.out.println(decode2_execute1);
        System.out.println("EXE1/EXE2");
        System.out.println(execute1_execute2);
        System.out.println("EXE2/MEM");
        System.out.println(execute2_memory);
        System.out.println("MEM/WB");
        System.out.println(memory_writeBack);

    }

    public void propagatePipelines(){
        System.out.println("Propagate Pipelines");
        fetch_decode1.propagate();
        decode1_decode2.propagate();
        decode2_execute1.propagate();
        execute1_execute2.propagate();
        execute2_memory.propagate();
        memory_writeBack.propagate();
    }

    public void flushPipelines(){
        System.out.println("Flushing pipelines");
        this.fetch_decode1.flush();
        this.decode1_decode2.flush();
        this.decode2_execute1.flush();
    }

    public boolean gameOver(){
        return decode1_decode2.getOldBlock() == null && decode2_execute1.getOldBlock() == null && execute1_execute2.getOldBlock() == null && execute2_memory.getOldBlock() == null && memory_writeBack.getOldBlock() == null && fetch_decode1.getOldBlock() == null;
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
