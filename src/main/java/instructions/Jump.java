package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;
import exception.NotWellFormedException;

/**
 * @author Alexandre Clement
 *         Created the 26/11/2016.
 *         <p>
 *         Instruction Jump
 */
public class Jump implements Executable, Loop
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.ITERATIVE_JUMP;
    }

    /**
     * @return true
     */
    @Override
    public boolean open()
    {
        return true;
    }

    /**
     * Avance jusqu'à l'instruction Back associé
     *
     * @param executionContext le contexte
     * @throws ExitException si l'instruction Back n'est pas trouvé
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (executionContext.getValue() != 0)
            return;
        int close = 1;
        int brace = executionContext.getInstruction();
        while (close != 0)
        {
            executionContext.nextInstruction();
            if (!executionContext.hasNextInstruction())
                throw new NotWellFormedException(this.getClass().getSimpleName(), "#execute", brace);
            Instructions current = executionContext.getCurrentInstruction();
            if (current.getLoopType() == getInstructions().getLoopType() && ((Loop) executionContext.getCurrentExecutable()).open() != open())
                close -= 1;
            if (current.getLoopType() == getInstructions().getLoopType() && ((Loop) executionContext.getCurrentExecutable()).open() == open())
                close += 1;
        }
    }
}