package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;
import exception.OutOfMemoryException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 *         <p>
 *         L'instruction Right
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
     * Incrémente de 1 le pointeur mémoire
     *
     * @param executionContext le contexte
     * @throws ExitException si le pointeur mémoire dépasse la capacité maximale de la mémoire
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (!executionContext.hasNextCell())
            throw new OutOfMemoryException(this.getClass().getSimpleName(), "#execute", executionContext.getInstruction(), executionContext.getPointer());
        executionContext.nextCell();
    }
}
