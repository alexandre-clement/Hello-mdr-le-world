package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;
import exception.OutOfMemoryException;

/**
 * L'instruction Right.
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 25/11/2016.
 */
public class Right implements Executable
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.RIGHT;
    }

    /**
     * Incremente de 1 le pointeur memoire.
     *
     * @param executionContext le contexte
     * @throws ExitException si le pointeur memoire depasse la capacite maximale de la memoire
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (!executionContext.hasNextCell())
            throw new OutOfMemoryException(this.getClass().getSimpleName(), "#execute", executionContext.getInstruction(), executionContext.getPointer());
        executionContext.nextCell();
    }
}
