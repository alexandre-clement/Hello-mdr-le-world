package instructions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class JumpTest
{
    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void open() throws Exception
    {
        assertTrue(new Jump().open());
    }

    @Test
    public void execute() throws Exception
    {

    }

}