package Tests;

import Components.MainMemory;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainMemoryTest {

    @Test
    public void testGetInstruction() {
        MainMemory memory = new MainMemory();
        memory.setAddress(0);
        memory.writeData(0);
        assertEquals(0, memory.getInstruction());

        memory.setAddress(500);
        memory.writeData(5210);
        assertEquals(5210, memory.getInstruction());
    }

    @Test
    public void getReadWriteData() {
        MainMemory memory = new MainMemory();
        memory.setMemWrite(true);
        memory.setAddress(2000);
        memory.writeData(65489);
        memory.setMemWrite(false);

        memory.setMemRead(true);
        memory.setAddress(2000);

         assertEquals(65489, memory.getReadData());
    }
}