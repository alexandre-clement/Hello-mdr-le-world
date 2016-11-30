package instructions;

import core.Core;
import exception.CoreException;
import exception.LanguageException;
import exception.OutOfMemoryException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 */
public class Left implements Executable
{

    @Override
    public void execute(Core core) throws CoreException, LanguageException
    {
        if (core.pointer == 0)
            throw new OutOfMemoryException(core.instruction, core.pointer);
        core.pointer -= 1;
    }
}
