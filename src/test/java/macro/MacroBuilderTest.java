package macro;

import main.MainTest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
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
        File file = new File(MainTest.FILENAME);
        FileWriter write = new FileWriter(file);
        write.write("macro MULTI_INCR nb_INCR # definition de la macro MULTI_DECR\n" +
                "    apply nb_INCR on\n" +
                "        INCR\n" +
                "\n" +
                "MULTI_INCR 3\n" +
                "# CO: 3\n" +
                "RIGHT\n" +
                "++++ # C1: 4\n" +
                "<[->[->+>+<<]>[-<+>]<<]>[-] # C3: C0 * C1 = 12");
        write.close();
        macroBuilder = new MacroBuilder(MainTest.FILENAME);
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