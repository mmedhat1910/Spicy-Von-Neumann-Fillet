package Components;

public class RegisterFile {
    private int[] registers = new int[32];
    private boolean regWrite;
    private int readReg1;
    private int readReg2;

    public void setWriteReg(int writeReg) {
        this.writeReg = writeReg;
    }

    private int writeReg;

    public void setRegWrite(boolean regWrite) {
        this.regWrite = regWrite;
    }

    public int getData1(){
        return readReg1 == 0 ? 0 :  registers[readReg1];
    }

    public int getData2(){
        return readReg2 == 0 ? 0 :  registers[readReg2];
    }

    public void writeData(int data) {
        if (regWrite) {
            this.registers[writeReg] = data;
        } else {
            System.out.println("Error writing in register");
        }
    }





    public void setReadReg1(int readReg1) {
        this.readReg1 = readReg1;
    }



    public void setReadReg2(int readReg2) {
        this.readReg2 = readReg2;
    }
}
