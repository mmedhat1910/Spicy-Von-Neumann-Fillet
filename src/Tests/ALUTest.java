package Tests;

import Components.ALU;
import Controllers.ControlUnit;
import org.junit.Test;

import static org.junit.Assert.*;

public class ALUTest {

    @Test
    public void testALU() {
        ALU alu = new ALU();
        alu.setOp1(1);
        alu.setOp2(2);
        alu.setControl(0);
        assertEquals("Add ",3, alu.getResult());
        assertFalse(alu.getZero());

        alu.setOp1(3);
        alu.setOp2(2);
        alu.setControl(1);
        assertEquals("Sub", 1, alu.getResult());
        assertFalse(alu.getZero());

        alu.setOp1(5);
        alu.setOp2(2);
        alu.setControl(2);
        assertEquals("Multiply" ,10, alu.getResult());
        assertFalse(alu.getZero());

        alu.setOp1(1);
        alu.setOp2(2);
        alu.setControl(3);
        assertEquals("Bitwise AND",0, alu.getResult());
        assertTrue(alu.getZero());

        alu.setOp1(1);
        alu.setOp2(2);
        alu.setControl(4);
        assertEquals("Bitwise OR",3, alu.getResult());
        assertFalse(alu.getZero());

        alu.setOp1(1);
        alu.setOp2(2);
        alu.setControl(5);
        assertEquals("Shift left",4, alu.getResult());
        assertFalse(alu.getZero());

        alu.setOp1(8);
        alu.setOp2(2);
        alu.setControl(6);
        assertEquals(2, alu.getResult());
        assertFalse(alu.getZero());


    }

    @Test
    public void testZeroFlag(){
        ControlUnit control = new ControlUnit();
        control.run(0b0100);
        ALU alu = new ALU();
        alu.setOp1(4);
        alu.setOp2(5);
        alu.setControl(control.getALUOp());
//        alu.getResult();
        assertFalse(alu.getZero());

    }
}