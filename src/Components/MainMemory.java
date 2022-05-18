package Components;


public class MainMemory{
    private int[] memory= new int[2048];
    private boolean MemRead; //Boolean flag coming from Control Unit
    private boolean MemWrite; //Boolean flag coming from Control Unit


    
    public int getInstruction(int address){
            if (address <= 1023)
                return memory[address];
            else{
                System.out.println("Wrong instruction address assigned");
                return -1;
            }
    }

    public int readData(int address){
            if (MemRead && address>1023)
                return memory[address];
            else {
                System.out.println("Wrong address assigned");
                return -1;
            }
    }

    public void writeData(int address,int inputValue){

            if (MemWrite && address>1023) {
                this.memory[address] = inputValue;
                System.out.println("Input Value added to the memory Successfully");
            }
            else
                System.out.println("Wrong address assigned");

    }
}
