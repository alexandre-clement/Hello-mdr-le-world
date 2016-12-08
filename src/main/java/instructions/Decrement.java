package instructions;

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
        if (executionContext.getValue() == ExecutionContext.MIN)
            throw new OverflowException(executionContext.getInstruction(), executionContext.getPointer());
        executionContext.decrement();
    }
}
