package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;

import java.io.IOException;

/**
 * Instruction In.
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 25/11/2016.
 */
public class In implements Executable
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.IN;
    }

    /**
     * Remplace la valeur de la cellule memoire pointee par la valeur du caractere ascii present dans le flux d'entree.
     *
     * @param executionContext le contexte
     * @throws ExitException si le flux d'entree n'existe pas
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        try
        {
            executionContext.in(executionContext.readNextValue());
        }
        catch (IOException exception)
        {
            throw new ExitException(3, this.getClass().getSimpleName(), "#execute", exception);
        }
    }
}