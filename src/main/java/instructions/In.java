package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;

import java.io.IOException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 *         <p>
 *         Instruction In
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
     * Remplace la valeur de la cellule mémoire pointée par la valeur du caractère ascii présent dans le flux d'entrée
     *
     * @param executionContext le contexte
     * @throws ExitException si le flux d'entrée n'existe pas
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