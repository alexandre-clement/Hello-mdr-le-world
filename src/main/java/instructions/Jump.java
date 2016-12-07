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
public class Jump implements Executable
{

    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        if (executionContext.memory[executionContext.pointer] != 0)
            return;
        int close = 1;
        int brace = executionContext.instruction;
        while (close != 0)
        {
            executionContext.instruction += 1;
            if (executionContext.instruction >= executionContext.program.length)
                throw new NotWellFormedException(brace);
            if (executionContext.program[executionContext.instruction] == Instructions.BACK)
                close -= 1;
            if (executionContext.program[executionContext.instruction] == Instructions.JUMP)
                close += 1;
        }
    }
}