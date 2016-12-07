package core;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


/**
 * Created on 07/12/2016.
 * @author TANG Yi
 */
public class CoreTest {
    Core core0;
    Core core1;
    @Before
    public void setUp() throws Exception {
        int CAPACITY = 30000;
        byte[] memory = new byte[CAPACITY];
        core0 = new Core(" ",  null, null, 0,  memory, 0);
        memory[1] = 30;
        core1 = new Core(" ",  null, null, 0,  memory, 1);

    }

    @Test
    public void printValue() throws Exception {
        assertEquals(0, core0.printValue());
        assertEquals(30,core1.printValue());
    }

    @Ignore
    public void run() throws Exception {
    }

    @Test
    public void getMemorySnapshot() throws Exception {
        assertEquals("C1:  30   ",core1.getMemorySnapshot());
        assertEquals(String.format("C%d:%4d   ", 1, 30),core1.getMemorySnapshot());

    }
}
