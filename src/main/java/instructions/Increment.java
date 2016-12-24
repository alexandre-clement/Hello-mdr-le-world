package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;
import exception.OverflowException;

/**
 * Instruction Increment
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 23/11/2016.
 */
public class Increment implements Executable
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.INCREMENT;
    }

    /**
     * Incrémente la cellule mémoire pointée de 1
     *
     * @param executionContext le contexte
     * @throws ExitException si la cellule est à la valeur maximale
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        if (executionContext.getValue() == ExecutionContext.MAX)
            throw new OverflowException(this.getClass().getSimpleName(), "#execute", executionContext.getInstruction(), executionContext.getPointer());
        executionContext.increment();
    }
}
