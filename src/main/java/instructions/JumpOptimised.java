package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;

/**
 * Instruction Jump Optimisée
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 07/12/2016.
 */
public class JumpOptimised implements Executable, Loop
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.OPTIMISED_JUMP;
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
     * Saute jusqu'à l'instruction BackOptimised associé
     *
     * @param executionContext le contexte
     * @throws ExitException l'instruction BackOptimised associé n'existe pas
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (executionContext.getValue() != 0)
            return;
        executionContext.bound(executionContext.getJumpLink());
    }
}
