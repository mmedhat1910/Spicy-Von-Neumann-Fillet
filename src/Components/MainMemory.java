package Components;


public class MainMemory{
    private int[] memory= new int[2048];
    private boolean readDataFlag; //Boolean flag coming from Control Unit
    private boolean writeFlag; //Boolean flag coming from Control Unit

    private int inputValue;  //Input Value coming from Register file

    
    public int getInstruction(int address){
        try {
            if (address <= 1023)
                return memory[address];
            else
                 throw new IndexOutOfBoundsException();
        } catch (IndexOutOfBoundsException e){
            return -1;
        }
    }

    public int readData(int address){
        try {
            if (readDataFlag)
                return memory[address];
            else
                throw new RuntimeException();
        }catch(RuntimeException e){
            return -1;
        }
    }

    public void writeData(int address,int inputValue){
        try {
            if (writeFlag && address>1023) {
                this.memory[address] = inputValue;
                System.out.println("Input Value added to the memory Successfully");
            }
            else
                throw new RuntimeException();
        }catch(RuntimeException e){
            System.out.println("Wrong address assigned");
        }
    }
}
