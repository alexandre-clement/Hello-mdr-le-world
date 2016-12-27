package main;

import org.junit.Test;

import java.io.File;

/**
 * @author Alexandre Clement
 * @since 27/12/2016.
 */
public class MainTest
{
    public static final String PATH = "target/test";
    public static final String FILENAME = "target/test.bf";
    public static final String BITMAP = "target/test_out.bmp";
    public static final String INPUT = "target/input";
    public static final String OUTPUT = "target/output";

    @Test
    public void fileTest()
    {
        File file;
        file = new File(FILENAME);
        file = new File(BITMAP);
        file = new File(INPUT);
        file = new File(OUTPUT);
    }
}