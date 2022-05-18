package Components;

public class ALU {
    private int control;
    private int op1;
    private int op2;
    private int result;
    private boolean zero;


    public int getResult () {
        return result;
    }
    public boolean getZero () {
        return zero;
    }

    public void setOp1(int op1){
        this.op1 = op1;
    }
    public void setOp2(int op2){
        this.op2 = op2;
    }
    public void setControl(int control){
        this.control = control;
        switch (control) {
            case 0 -> result = op1 + op2;
            case 1 -> result = op1 - op2;
            case 2 -> result = op1 * op2;
            case 3 -> result = op1 & op2;
            case 4 -> result = op1 | op2;
            case 5 -> result = op1 << op2;
            case 6 -> result = op1 >> op2;
        }
        zero = result == 0;
    }
}

}
