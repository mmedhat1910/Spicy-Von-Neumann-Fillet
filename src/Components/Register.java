package Components;

public class Register {
    int size = 32;
    int data = 0;
    String name;
    static int count = 1;
    public Register(){

    }

    public Register[] ArrRegisters (){
        Register[] Registers = new Register[33];
        Registers[0] = new ZeroRegister();
        for (int i=1; i<32; i++){
            Registers[i] = new Register();
        }
        Registers[32] = new PCRegister();

        return Registers;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void displayReg(){
        System.out.println("Register name: " + this.name + "\n" + "Register data: " + this.data + "\n");
    }

    public static void decode(int instruction) {

        int opcode = 0;  // bits31:26
        int rs = 0;      // bits25:21
        int rt = 0;      // bit20:16
        int rd = 0;      // bits15:11
        int shamt = 0;   // bits10:6
        int funct = 0;   // bits5:0
        int imm = 0;     // bits15:0
        int address = 0; // bits25:0
        int valueRS = 0;
        int valueRT = 0;

        opcode = 0b11111100000000000000000000000000 & instruction;
        rs = 0b00000011111000000000000000000000 & instruction;
        rt = 0b00000000000111110000000000000000 & instruction;
        rd = 0b00000000000000001111100000000000 & instruction;
        shamt = 0b00000000000000000000011111000000 & instruction;
        funct = 0b00000000000000000000000000111111 & instruction;
        imm = 0b00000000000000001111111111111111 & instruction;
        address = 0b00000000111111111111111111111111 & instruction;

        opcode= opcode>> 26;
        rs= rs>> 21;
        rt= rt>> 16;
        rd= rd>>11;
        shamt=shamt >>6;
       // valueRS=registerFile[rs];
       // valueRT=registerFile[rt];

    }



    public static void main(String[] args) {
        Register x = new Register();
        Register y = new Register();
        Register z = new Register();
        y.setData(751);

        x.displayReg();
        y.displayReg();
        z.displayReg();
    }



}