package instructions;

import core.Core;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created on 07/12/2016.
 * @author TANG Yi
 */
public class DecrementTest {
    Core core;

    @Before
    public void init() {
        int CAPACITY = 30000;
        core = new Core(" ",  null, null, 0,  new byte[CAPACITY], 3);
    }

    @Test
    public void executeTest() throws Exception {
        core.memory[3]=22;
        new Decrement().execute(this.core);
        assertEquals(21,core.memory[3]);
    }

}