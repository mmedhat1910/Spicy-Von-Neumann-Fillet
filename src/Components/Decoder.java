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
        // todo: sign extend



        r1 = (instruction >> 23) & 0b000011111;
//        if((instruction << 4) < 0){
//            r1 = r1 | 0b111100000;
//        }

        r2 = (instruction >> 18) & 0b00000000011111;
//        if((instruction << 9)<0){
//            r2 = r2 | 0b11111111100000;
//        }
//        r1 = instruction  & 0b00001111100000000000000000000000;
//        r2 =  instruction  & 0b00000000011111000000000000000000;
        r3 = (instruction >> 13) & 0b0000000000000011111;
//        if((instruction << 14) < 0){
//            r3 = r3 | 0b1111111111111100000;
//        }

//        r3 =instruction  & 0b00000000000000111110000000000000;
        shamt = instruction &  0b00000000000000000001111111111111;
//        imm = instruction & 0b00000000000000111111111111111111;

        int immediate = instruction << 14;
        imm = instruction & 0b00000000000000111111111111111111;
        if (immediate < 0){
            imm = imm  | 0b11111111111111110000000000000000;
        }

        address = instruction & 0b00001111111111111111111111111111;

    }

    public boolean isImmediate(int instruction){
        int op = instruction >> 28;
        return op == 2 || op==3 || op==4 || op==5 || op==6 || op==7 || op==-6 || op==-5; //1010 -> 0101 +1 = 0110 -- 1011 -> 0100 +1 = 0101
    }
    public boolean isAddress(int instruction) {return (instruction >> 28) == 7;};


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

    public static void main(String[] args) {
        int instruction = 0b110001000000000000000000000100;
        System.out.println(instruction);


        int opcode = (instruction >> 28);
        int r1 = instruction  & 0b00001111100000000000000000000000;
        int r2 = instruction  & 0b00000000011111000000000000000000;
        int r3 = instruction  & 0b00000000000000111110000000000000;
        int shamt =  0b00000000000000000001111111111111;
        int imm = instruction & 0b00000000000000111111111111111111;
        int address = instruction & 0b00001111111111111111111111111111;


        System.out.println(opcode);
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(shamt);
        System.out.println(imm);
        System.out.println(address);



    }
}

