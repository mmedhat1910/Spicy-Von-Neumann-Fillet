package Components;


public class MainMemory{
    private int[] memory= new int[2048];
    private boolean MemRead; //Boolean flag coming from Control Unit
    private boolean MemWrite; //Boolean flag coming from Control Unit
    private int address;


    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getInstruction(){
            if (address <= 1023)
                return memory[address];
            else{
                System.out.println("Wrong instruction address assigned");
                return -1;
            }
    }

    public int getReadData(){
            if (MemRead && address >1023)
                return memory[address];
            else {
                System.out.println("Wrong address assigned");
                return -1;
            }
    }

    public void writeData(int inputValue) {
        if(MemWrite && address > 1023) {
            memory[address] = inputValue;
            System.out.println("Writing to data memory");
        }else if (MemWrite && address <= 1023) {
            memory[address] = inputValue;
            System.out.println("Writing to instruction memory");
        }else {
            System.out.println("Cannot write to memory");
        }

    }
    public void setMemRead(boolean memRead) {
        MemRead = memRead;
    }

    public void setMemWrite(boolean memWrite) {
        MemWrite = memWrite;
    }

    public void display(int address){
        System.out.println("Memory at address "+address+" is "+memory[address]);

    }
    public void display(int address1, int address2){
        System.out.print("Memory: [");
        for(int i=address1;i<=address2;i++){
            System.out.print(memory[i]+", ");
        }
        System.out.println("]");
    }
}
