package Tests;

import Controllers.ControlUnit;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControlUnitTest {

    @Test
    public void testControlADDI() {
        int opcode = 0b0011;
        ControlUnit cu = new ControlUnit();
        cu.run(opcode);
        assertFalse(cu.isBranch());
        assertFalse(cu.isMemRead());
        assertFalse(cu.isMemtoReg());
        assertFalse(cu.isMemWrite());
        assertTrue(cu.isALUSrc());
        assertTrue(cu.isRegWrite());
        assertEquals(0b000, cu.getALUOp());
            /*
        private int ALUOp;
        */

    }

    @Test
    public void testControlSW(){
        int opcode = 0b1011;
        ControlUnit cu = new ControlUnit();
        cu.run(opcode);
        assertFalse(cu.isBranch());
        assertFalse(cu.isMemRead());
        assertFalse(cu.isMemtoReg());
        assertTrue(cu.isMemWrite());
        assertTrue(cu.isALUSrc());
        assertFalse(cu.isRegWrite());
        assertEquals(0b000, cu.getALUOp());
    }


    @Test
    public void testControlLW(){
        int opcode = 0b1010;
        ControlUnit cu = new ControlUnit();
        cu.run(opcode);
        assertFalse(cu.isBranch());
        assertTrue(cu.isMemRead());
        assertTrue(cu.isMemtoReg());
        assertFalse(cu.isMemWrite());
        assertTrue(cu.isRegWrite());
        assertEquals(0b000, cu.getALUOp());
    }

    @Test
    public void testControlBNE(){
        int opcode = 0b0100;
        ControlUnit cu = new ControlUnit();
        cu.run(opcode);
        assertTrue(cu.isBranch());
        assertFalse(cu.isMemRead());
        assertFalse(cu.isMemtoReg());
        assertFalse(cu.isMemWrite());
        assertFalse(cu.isALUSrc());
        assertFalse(cu.isRegWrite());
        assertEquals(0b001, cu.getALUOp());
    }


    @Test
    public void testControlJump(){
        int opcode = 0b0111;
        ControlUnit cu = new ControlUnit();
        cu.run(opcode);
        assertTrue(cu.isBranch());
        assertFalse(cu.isMemRead());
        assertFalse(cu.isMemtoReg());
        assertFalse(cu.isMemWrite());
        assertFalse(cu.isALUSrc());
        assertFalse(cu.isRegWrite());
        assertEquals(0b000, cu.getALUOp());
    }

    @Test
    public void testControlANDI(){
        int opcode = 0b0101;
        ControlUnit cu = new ControlUnit();
        cu.run(opcode);
        assertFalse(cu.isBranch());
        assertFalse(cu.isMemRead());
        assertFalse(cu.isMemtoReg());
        assertFalse(cu.isMemWrite());
        assertTrue(cu.isALUSrc());
        assertTrue(cu.isRegWrite());
        assertEquals(0b011, cu.getALUOp());
    }

}