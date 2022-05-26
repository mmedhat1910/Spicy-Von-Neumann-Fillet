package Controllers;

public class ControlUnit {
    private boolean RegDst = false;
    private boolean Branch = false;
    private boolean MemRead = false;
    private boolean MemWrite = false;
    private boolean MemtoReg = false;
    private boolean ALUSrc = false;
    private boolean RegWrite = false;


    private int ALUOp;


    public void run(int opcode){
        RegDst = opcode == 0 || opcode == 1;
        Branch = opcode == 4 || opcode == 7;
        MemRead = opcode == 10 || opcode == -6;
        MemtoReg = opcode == 10 || opcode == -6;
        MemWrite = opcode == 11 || opcode == -5;
        ALUSrc = opcode == 2 || opcode == 3 || opcode == 5 || opcode == 6  ||  opcode == 8 || opcode == 9 || opcode == 10 || opcode == 11 || opcode == -8 || opcode ==-7 || opcode == -6 || opcode == -5;
        RegWrite = opcode == 0 || opcode == 1 || opcode == 2 || opcode == 3  || opcode == 5 || opcode == 6  || opcode == -8 || opcode == -7 ||  opcode == 10 || opcode == -6;
//        PassR1 =  opcode == 10 || opcode == -6;;
//        if(opcode == 0 || opcode == 3 || opcode == 7 || opcode == 10 || opcode == 11){
//            ALUOp = 0b000;
//        }else
        if(opcode == 1 || opcode == 4){
            // Sub => SUB & BNE
            ALUOp = 0b001;
        }else if(opcode == 2 ){
            // multiply => MULI
            ALUOp = 0b010;
        }else if(opcode == 5 ){
            // AND => ANDI
            ALUOp = 0b011;
        }else if(opcode == 6 ){
            // OR => ORI
            ALUOp = 0b100;
        }else if(opcode == 8 || opcode == -8){
            // Shift left => SLL
            ALUOp = 0b101;
        }else if(opcode == 9 || opcode == -7){
            // Shift right => SRL
            ALUOp = 0b110;
        }else {
            // Add
            ALUOp = 0b000;
        }
    }

    public boolean isRegDst() {
        return RegDst;
    }

    public boolean isBranch() {
        return Branch;
    }

    public boolean isMemRead() {
        return MemRead;
    }

    public boolean isMemWrite() {
        return MemWrite;
    }

    public boolean isMemtoReg() {
        return MemtoReg;
    }

    public boolean isALUSrc() {
        return ALUSrc;
    }

    public boolean isRegWrite() {
        return RegWrite;
    }

    public int getALUOp() {
        return ALUOp;
    }
}
