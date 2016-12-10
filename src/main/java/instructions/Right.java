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
public class Right implements Executable
{

    @Override
    public Instructions getInstructions()
    {
        return Instructions.RIGHT;
    }

    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (!executionContext.hasNextCell())
            throw new OutOfMemoryException(executionContext.getInstruction(), executionContext.getPointer());
        executionContext.nextCell();
    }
}
