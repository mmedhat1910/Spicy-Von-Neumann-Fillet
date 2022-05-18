package Components;

public class Decoder {
    int opcode = 0;  // bits 31:28
    int r1 = 0;      // bits 27:23
    int r2 = 0;      // bits 22:18
    int r3 = 0;      // bits 17:13
    int shamt = 0;   // bits 12:0
    int imm = 0;     // bits 17:0
    int address = 0; // bits 27:0

    public  void decode(int instruction) {
        // Complete the decode() body...
        opcode = (instruction >> 28);
        r1 = (instruction >> 23) & 0b000011111;
        r2 = (instruction >> 18) & 0b00000000011111;
        r3 = (instruction >> 13) & 0b0000000000000011111;
        shamt =  0b00000000000000000001111111111111;
        imm = instruction & 0b00000000000000111111111111111111;
        address = instruction & 0b00001111111111111111111111111111;
    }

    public int getOpcode() {
        return opcode;
    }

    public int getR1() {
        return r1;
    }

    public int getR2() {
        return r2;
    }

    public int getR3() {
        return r3;
    }

    public int getShamt() {
        return shamt;
    }

    public int getImm() {
        return imm;
    }

    public int getAddress() {
        return address;
    }
}

