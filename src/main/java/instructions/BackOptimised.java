package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.CoreException;
import exception.LanguageException;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class BackOptimised implements Executable, Loop
{

    @Override
    public Instructions getInstructions()
    {
        return Instructions.BACK;
    }

    @Override
    public Instructions getLinkedInstructions() {
        return Instructions.JUMP;
    }

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (executionContext.getValue() == 0)
            return;
        executionContext.bound(executionContext.getJumpLink());
    }
}
