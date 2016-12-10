package instructions;

import core.Instructions;

/**
 * @author Alexandre Clement
 *         Created the 09/12/2016.
 */
public interface Loop
{
    boolean open();
    Instructions getLinkedInstructions();
}
