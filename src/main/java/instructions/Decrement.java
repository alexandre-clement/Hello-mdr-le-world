package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;
import exception.OverflowException;

/**
 * Instruction Decrement.
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 23/11/2016.
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
     * Decremente la cellule memoire pointee de 1.
     *
     * @param executionContext le contexte
     * @throws ExitException si la cellule memoire est a la valeur minimale
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (executionContext.getValue() == ExecutionContext.MIN)
            throw new OverflowException(this.getClass().getSimpleName(), "#execute", executionContext.getInstruction(), executionContext.getPointer());
        executionContext.decrement();
    }
}
