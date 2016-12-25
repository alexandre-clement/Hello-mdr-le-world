package macro;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class MacroTest
{
    private Macro toDigit;
    private Macro multiDecr;
    private Macro set;
    private Macro toDigitWithoutMultiDecr;

    @Before
    public void setUp() throws Exception
    {
        toDigit = new Macro("TO_DIGIT", "", "    MULTI_DECR 48\n");
        toDigitWithoutMultiDecr = new Macro("2DIGIT", "", "    APPLY 48 ON\n        DECR\n");
        multiDecr = new Macro("MULTI_DECR", "x", "    APPLY x ON\n        DECR\n");
        set = new Macro("Incr_2_pow_x_cell_to_y", "x y", "    APPLY 2^x ON\n        APPLY y ON\n            INCR\n        RIGHT\n");
    }

    @Test
    public void match() throws Exception
    {
        assertEquals("MULTI_DECR 48", toDigit.match("TO_DIGIT"));
        assertEquals("DECR\nDECR\nDECR", multiDecr.match("MULTI_DECR 3"));
        assertEquals(multiDecr.match("MULTI_DECR 48"), toDigitWithoutMultiDecr.match("2DIGIT"));
        assertEquals("INCR\nINCR\nRIGHT\nINCR\nINCR\nRIGHT\nINCR\nINCR\nRIGHT\nINCR\nINCR\nRIGHT\nINCR\nINCR\nRIGHT\nINCR\nINCR\nRIGHT\nINCR\nINCR\nRIGHT\nINCR\nINCR\nRIGHT", set.match("Incr_2_pow_x_cell_to_y 3 2"));
    }

    @Test
    public void failMatch() throws Exception
    {
        assertEquals("to_digit", toDigit.match("to_digit"));
        assertEquals("MULTI_DECR", multiDecr.match("MULTI_DECR"));
    }
}