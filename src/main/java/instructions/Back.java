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
public class Back implements Executable
{

    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (executionContext.getValue() == 0)
            return;
        int close = -1;
        int brace = executionContext.getInstruction();
        while (close != 0)
        {
            executionContext.previousInstruction();
            if (!executionContext.hasPreviousInstruction())
                throw new NotWellFormedException(brace);
            if (executionContext.getCurrentInstruction() == Instructions.BACK)
                close -= 1;
            if (executionContext.getCurrentInstruction() == Instructions.JUMP)
                close += 1;
        }
    }
}