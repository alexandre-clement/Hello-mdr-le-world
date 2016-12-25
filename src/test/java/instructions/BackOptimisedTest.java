package instructions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class BackOptimisedTest
{
    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void open() throws Exception
    {
        assertFalse(new BackOptimised().open());
    }

    @Test
    public void execute() throws Exception
    {

    }

}