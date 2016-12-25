package macro;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class MacroBuilderTest
{
    private MacroBuilder macroBuilder;

    @Before
    public void setUp() throws Exception
    {
        macroBuilder = new MacroBuilder("src/test/test.bf");
    }

    @Test
    public void build() throws Exception
    {
        File file = macroBuilder.build();
        Scanner scanner = new Scanner(file);
        assertEquals("INCR", scanner.nextLine());
        assertEquals("INCR", scanner.nextLine());
        assertEquals("INCR", scanner.nextLine());
        assertNotEquals("INCR", scanner.nextLine());
    }
}