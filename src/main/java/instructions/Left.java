package instructions;

import core.ExecutionContext;
import core.Instructions;
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
    public Instructions getInstructions()
    {
        return Instructions.LEFT;
    }

    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (!executionContext.hasPreviousCell())
            throw new OutOfMemoryException(executionContext.getInstruction(), executionContext.getPointer());
        executionContext.previousCell();
    }
}
