package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;
import exception.OutOfMemoryException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 *         <p>
 *         Instruction Left
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
     * Décrémente le pointeur mémoire de 1
     *
     * @param executionContext le contexte
     * @throws ExitException si le pointeur mémoire dépasse la capacité minimale de la mémoire
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (!executionContext.hasPreviousCell())
            throw new OutOfMemoryException(this.getClass().getSimpleName(), "#execute", executionContext.getInstruction(), executionContext.getPointer());
        executionContext.previousCell();
    }
}
