package Components;

public class ALU {
    private int control;
    private int op1;
    private int op2;
    private int result;
    private boolean zero;

    public void run(int control){
//        switch (control) {
//            case 0 : ADD(); break;
//            case 1 : SUB(); break;
//            case 2 : MULI(); break;
//            case 3 : ADDI(); break;
//            case 4 : BNE(); break;
//            case 5 : ANDI(); break;
//            case 6 : ORI(); break;
//            case 7 : Jump(); break;
//            case 8 : SLL(); break;
//            case 9 : SRL(); break;
//            case 10: LW(); break;
//            case 11: SW(); break;
//        }
        zero = result == 0;
    }



//    private void SUB() {
//        result = op1 - op2;
//    }
//
//    private void ADD() {
//        result = op1 + op2;
//    }
//    private void MULI(){
//        result = op1 * op2;
//    }

}
