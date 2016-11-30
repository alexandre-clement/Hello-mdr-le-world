package instructions;

import core.Core;
import exception.CoreException;
import exception.LanguageException;
import exception.OverflowException;

/**
 * @author Alexandre Clement
 *         Created the 23/11/2016.
 */
public class Decrement implements Executable
{

    @Override
    public void execute(Core core) throws CoreException, LanguageException
    {
        if (core.memory[core.pointer] == Core.MIN)
            throw new OverflowException(core.instruction, core.pointer);
        core.memory[core.pointer] -= 1;
    }
}
