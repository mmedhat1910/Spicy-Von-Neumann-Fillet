package Components;

public class PCRegister extends Register {

    public PCRegister() {
        super();
        final String name = "R32";
    }

    public void incrementPC(){
        this.data++;
    }

    public int getPC() {
        return this.data;
    }
}
