package instructions;

import core.ExecutionContext;
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
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (executionContext.pointer == executionContext.memory.length)
            throw new OutOfMemoryException(executionContext.instruction, executionContext.pointer);
        executionContext.pointer += 1;
    }
}
