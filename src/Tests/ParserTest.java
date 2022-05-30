package Tests;

import Utils.Parser;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void testParseInstruction_R() throws IOException {
        Parser p = new Parser("code.txt");
        assertEquals("Parser test SUB R1 R2 R3",  0b00010000100010000110000000000000,p.parseInstruction("SUB R1 R2 R3"));

    }



    @Test
    public void testParseInstruction_I() throws IOException {
        Parser p = new Parser("code.txt");
        assertEquals("Parser test ADDI R1 R2 R3 ",  0b00110000100010000000000000000101,p.parseInstruction("ADDI R1 R2 5"));

    }

    @Test
    public void testParseInstruction_J() throws IOException {
        Parser p = new Parser("code.txt");
        assertEquals("Parser test J 3 ",  0b011100000000000000000000000000011,p.parseInstruction("J 3"));
    }

    @Test
    public void testReverseParse() throws IOException {
        Parser p = new Parser("code.txt");
        assertEquals("Parser test J 3 ",  "J 3",p.reverseParse(0b011100000000000000000000000000011));
    }
}