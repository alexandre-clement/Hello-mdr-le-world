package interpreter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 *         Created the 08/11/2016.
 */
public class FilenamesTest {

    @Test
    public void setNameTest() {
        Filenames.source.setName("source");
        assertEquals(Filenames.source.getName(), "source");
    }
}
