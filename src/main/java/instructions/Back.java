package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;
import exception.NotWellFormedException;

/**
 * Instruction Back Itératif
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 26/11/2016.
 */
public class Back implements Executable, Loop
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.ITERATIVE_BACK;
    }

    /**
     * @return false
     */
    @Override
    public boolean open()
    {
        return false;
    }

    /**
     * Retourne à l'instruction Jump associé
     *
     * @param executionContext le contexte
     * @throws ExitException si l'instruction Jump n'est pas trouvé
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (executionContext.getValue() == 0)
            return;
        int close = -1;
        int brace = executionContext.getInstruction();
        while (close != 0)
        {
            executionContext.previousInstruction();
            if (!executionContext.hasPreviousInstruction())
                throw new NotWellFormedException(this.getClass().getSimpleName(), "#execute", brace);
            Instructions current = executionContext.getCurrentInstruction();
            if (current.getLoopType() == getInstructions().getLoopType() && ((Loop) executionContext.getCurrentExecutable()).open() == open())
                close -= 1;
            if (current.getLoopType() == getInstructions().getLoopType() && ((Loop) executionContext.getCurrentExecutable()).open() != open())
                close += 1;
        }
    }
}