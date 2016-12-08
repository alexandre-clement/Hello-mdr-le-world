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
public class Increment implements Executable
{

    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (executionContext.getValue() == ExecutionContext.MAX)
            throw new OverflowException(executionContext.getInstruction(), executionContext.getPointer());
        executionContext.increment();
    }
}
