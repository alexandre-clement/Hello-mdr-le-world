package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 *         <p>
 *         Instruction Back Optimisée
 */
public class BackOptimised implements Executable, Loop
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.OPTIMISED_BACK;
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
     * Saute jusqu'à l'instruction JumpOptimised associé
     *
     * @param executionContext le contexte
     * @throws ExitException l'instruction JumpOptimised associé n'existe pas
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (executionContext.getValue() == 0)
            return;
        executionContext.bound(executionContext.getJumpLink());
    }
}
