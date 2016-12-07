package instructions;

import core.Core;
import core.ExecutionContext;
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
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (executionContext.memory[executionContext.pointer] == Core.MIN)
            throw new OverflowException(executionContext.instruction, executionContext.pointer);
        executionContext.memory[executionContext.pointer] -= 1;
    }
}
