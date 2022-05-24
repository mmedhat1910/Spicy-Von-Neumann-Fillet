package Components;


public class MainMemory{
    private int[] memory= new int[2048];
    private boolean MemRead; //Boolean flag coming from Control Unit
    private boolean MemWrite; //Boolean flag coming from Control Unit
    private int readAddress;
    private int writeAddress;

    public void setWriteAddress(int writeAddress) {
        this.writeAddress = writeAddress;
    }



    public void setReadAddress(int dataAddress) {
        this.readAddress = dataAddress;
    }


    
    public int getInstruction(){
            if (readAddress <= 1023)
                return memory[readAddress];
            else{
                System.out.println("Wrong instruction address assigned");
                return -1;
            }
    }

    public int readData(){
            if (MemRead && readAddress >1023)
                return memory[readAddress];
            else {
                System.out.println("Wrong address assigned");
                return -1;
            }
    }

    public void writeData(int inputValue){
                this.memory[writeAddress] = inputValue;
    }

    public void setMemRead(boolean memRead) {
        MemRead = memRead;
    }

    public void setMemWrite(boolean memWrite) {
        MemWrite = memWrite;
    }
}
