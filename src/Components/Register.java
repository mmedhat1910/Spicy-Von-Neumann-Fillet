package Components;

public class Register {
    int size = 32;
    int data = 0;
    String name;
    static int count = 1;
    public Register(){
        if (count < 32) {
            this.name = "R" + "" + count;
            count++;
        }

        else {
            System.out.println("You cannot initiate any more registers");
        }

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