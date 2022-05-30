package Tests;

import Utils.PipelineRegister;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class PipelineRegisterTest {

    @Test
    public void testPropagate() {
        PipelineRegister<HashMap<String, Integer>> pipelineRegister = new PipelineRegister<>();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        pipelineRegister.setNewBlock(map);
        assertNull(pipelineRegister.getOldBlock());
        assertNotNull(pipelineRegister.getNewBlock());

        pipelineRegister.propagate();
        assertNull(pipelineRegister.getNewBlock());
        assertNotNull(pipelineRegister.getOldBlock());
    }

    @Test
    public void testFlush(){
        PipelineRegister<HashMap<String, Integer>> pipelineRegister = new PipelineRegister<>();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        pipelineRegister.setNewBlock(map);
        assertNull(pipelineRegister.getOldBlock());
        assertNotNull(pipelineRegister.getNewBlock());

        pipelineRegister.propagate();
        pipelineRegister.flush();
        assertNull(pipelineRegister.getNewBlock());
        assertNull(pipelineRegister.getOldBlock());
    }
}