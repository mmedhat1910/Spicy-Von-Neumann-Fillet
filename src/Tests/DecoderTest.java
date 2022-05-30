package Tests;

import Components.Decoder;
import org.junit.Test;

import static org.junit.Assert.*;

public class DecoderTest {

    @Test
    public void testDecode() {
        Decoder d = new Decoder();
        d.decode(0b01110000000000000000000000000111);
        assertEquals("Test OPCODE", 0b0111 , d.getOpcode());
//        assertEquals("Test R1", 0b00001 , d.getR1());
//        assertEquals("Test R2", 0b00010 , d.getR2());
//        assertEquals("Test R3", 0b00011 , d.getR3());
//        assertEquals("Test SHAMT", 0b0000000000011 , d.getShamt());
//        assertEquals("Test IMM", 0b000000000000000110 , d.getImm());
        assertEquals("Test ADDRESS", 7 , d.getAddress());
    }
}