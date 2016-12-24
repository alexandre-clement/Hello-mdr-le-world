package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;
import exception.OverflowException;

/**
 * @author Alexandre Clement
 *         Created the 23/11/2016.
 *         <p>
 *         Instruction Decrement
 */
public class Decrement implements Executable
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.DECREMENT;
    }

    /**
     * Décrémente la cellule mémoire pointée de 1
     *
     * @param executionContext le contexte
     * @throws ExitException si la cellule mémoire est à la valeur minimale
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (executionContext.getValue() == ExecutionContext.MIN)
            throw new OverflowException(this.getClass().getSimpleName(), "#execute", executionContext.getInstruction(), executionContext.getPointer());
        executionContext.decrement();
    }
}
