package instructions;

import core.Core;
import exception.CoreException;
import exception.LanguageException;
import exception.OutOfMemoryException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 */
public class Right implements Executable
{

    @Override
    public void execute(Core core) throws CoreException, LanguageException
    {
        if (core.pointer == core.memory.length)
            throw new OutOfMemoryException(core.instruction, core.pointer);
        core.pointer += 1;
    }
}
