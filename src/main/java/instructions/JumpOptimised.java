package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.CoreException;
import exception.LanguageException;
import exception.NotWellFormedException;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class JumpOptimised implements Executable
{
    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (executionContext.getValue() != 0)
            return;
        executionContext.bound(executionContext.getJumpLink());
    }
}