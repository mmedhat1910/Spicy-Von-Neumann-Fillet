package Components;

public class RegisterFile {
    private int[] registers = new int[32];
    private boolean regWrite;


    public void setRegWrite(boolean regWrite) {
        this.regWrite = regWrite;
    }

    public int getData1(int address){
        return registers[address];
    }

    public int getData2(int address){
        return registers[address];
    }

    public void writeData(int address , int data) {
        if (regWrite) {
            this.registers[address] = data;
        } else {
            System.out.println("Error");
        }
    }

}
