package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;

/**
 * Instruction Back Optimisee.
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 07/12/2016.
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
     * L'instruction ferme une boucle.
     *
     * @return false
     */
    @Override
    public boolean open()
    {
        return false;
    }

    /**
     * Saute jusqu'a l'instruction JumpOptimised associe.
     *
     * @param executionContext le contexte
     * @throws ExitException l'instruction JumpOptimised associe n'existe pas
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (executionContext.getValue() == 0)
            return;
        executionContext.bound(executionContext.getJumpLink());
    }
}
