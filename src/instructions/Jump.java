package instructions;

import core.Core;
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
    public void execute(Core core) throws CoreException, LanguageException
    {
        if (core.memory[core.pointer] != 0)
            return;
        int close = 1;
        int brace = core.instruction;
        while (close != 0)
        {
            core.instruction += 1;
            if (core.instruction >= core.program.length)
                throw new NotWellFormedException(brace);
            if (core.program[core.instruction] == Instructions.BACK)
                close -= 1;
            if (core.program[core.instruction] == Instructions.JUMP)
                close += 1;
        }
    }
}