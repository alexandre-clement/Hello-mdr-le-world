package language;

import exception.ExitException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class BitmapImageTest
{
    private BitmapImage bitmapImage;

    @Before
    public void setUp() throws Exception
    {
        bitmapImage = new BitmapImage("src/test/test.bmp");
    }

    @After
    public void after() throws Exception
    {
        try
        {
            bitmapImage.close();
        }
        catch (ExitException exception)
        {
            // Already closed
        }
    }

    @Test
    public void createImage() throws Exception
    {

    }

    @Test
    public void next() throws Exception
    {
        assertEquals("1", bitmapImage.next()); // INCR
        assertEquals("1", bitmapImage.next()); // INCR
        assertEquals("1", bitmapImage.next()); // INCR
        assertEquals("16776961", bitmapImage.next()); // RIGHT
        assertEquals("1", bitmapImage.next()); // INCR
        assertEquals("1", bitmapImage.next()); // INCR
        assertEquals("1", bitmapImage.next()); // INCR
        assertEquals("1", bitmapImage.next()); // INCR
        assertEquals("7077677", bitmapImage.next()); // LEFT
        assertEquals("33024", bitmapImage.next()); // JUMP
        assertEquals("11861886", bitmapImage.next()); // DECR
        assertEquals("16776961", bitmapImage.next()); // RIGHT

    }

    @Test(expected = IllegalStateException.class)
    public void close() throws Exception
    {
        bitmapImage.close();
        bitmapImage.next();
    }

}