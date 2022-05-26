package Tests;
import Utils.Parser;
import org.junit.Test;
import Components.RegisterFile;

import static org.junit.Assert.assertEquals;


public class RegisterFileTest {

    @Test
    public void testWriteToReg(){
        RegisterFile regFile = new RegisterFile();
        regFile.setRegWrite(true);
        regFile.setWriteReg(12);
        regFile.writeData(351);
        regFile.setReadReg1(12);
        regFile.setWriteReg(31);
        regFile.writeData(2);
        regFile.setReadReg2(31);
        assertEquals("Test writing into register",  351,regFile.getData1());
        assertEquals("Test writing into register",  2,regFile.getData2());
    }

    @Test
    public void testZeroReg(){
        RegisterFile regFile = new RegisterFile();
        regFile.setRegWrite(true);
        regFile.setWriteReg(0);
        regFile.writeData(351);
        regFile.setReadReg1(0);
        assertEquals("Test writing into register",  0,regFile.getData1());
    }


}