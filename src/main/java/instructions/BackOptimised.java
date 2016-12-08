package instructions;

import core.ExecutionContext;
import exception.CoreException;
import exception.LanguageException;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class BackOptimised implements Executable
{
    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (executionContext.getValue() == 0)
            return;
        executionContext.bound(executionContext.getJumpLink());
    }
}