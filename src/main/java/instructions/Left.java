package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;
import exception.OutOfMemoryException;

/**
 * Instruction Left.
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 25/11/2016.
 */
public class Left implements Executable
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.LEFT;
    }

    /**
     * Decremente le pointeur memoire de 1.
     *
     * @param executionContext le contexte
     * @throws ExitException si le pointeur memoire depasse la capacite minimale de la memoire
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (!executionContext.hasPreviousCell())
            throw new OutOfMemoryException(this.getClass().getSimpleName(), "#execute", executionContext.getInstruction(), executionContext.getPointer());
        executionContext.previousCell();
    }
}
