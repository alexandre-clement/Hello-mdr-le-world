package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.CoreException;
import exception.LanguageException;
import exception.NotWellFormedException;

/**
 * @author Alexandre Clement
 *         Created the 26/11/2016.
 */
public class Jump implements Executable, Loop
{

    @Override
    public Instructions getInstructions()
    {
        return Instructions.OLD_JUMP;
    }

    @Override
    public boolean open()
    {
        return true;
    }

    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (executionContext.getValue() != 0)
            return;
        int close = 1;
        int brace = executionContext.getInstruction();
        while (close != 0)
        {
            executionContext.nextInstruction();
            if (!executionContext.hasNextInstruction())
                throw new NotWellFormedException(brace);
            Instructions current = executionContext.getCurrentInstruction();
            if (current.getLoopType() == getInstructions().getLoopType() && ((Loop) executionContext.getCurrentExecutable()).open() != open())
                close -= 1;
            if (current.getLoopType() == getInstructions().getLoopType() && ((Loop) executionContext.getCurrentExecutable()).open() == open())
                close += 1;
        }
    }
}