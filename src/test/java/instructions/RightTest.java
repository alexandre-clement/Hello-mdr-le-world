package instructions;

import static org.junit.Assert.*;
import core.Core;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author TANG Yi
 * Created on 07/12/2016.
 */
public class RightTest {

    Core core;

    @Before
    public void init() {
        int CAPACITY = 30000;
        core = new Core(" ",  null, null, 0,  new byte[CAPACITY], 3);
    }
    @Test
    public void executeTest() throws Exception {
        new Right().execute(this.core);
        assertEquals(4,core.pointer);
    }

}