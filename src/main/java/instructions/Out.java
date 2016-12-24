package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;

/**
 * Instruction Out
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 25/11/2016.
 */
public class Out implements Executable
{
    /**
     * {@inheritDoc}
     */
    @Override
    public Instructions getInstructions()
    {
        return Instructions.OUT;
    }

    /**
     * Affiche sur le flux de sortie la valeur ascii de la cellule mémoire pointée
     *
     * @param executionContext le contexte
     * @throws ExitException si le flux de sortie n'existe pas
     */
    @Override
    public void execute(ExecutionContext executionContext) throws ExitException
    {
        executionContext.out((char) executionContext.printValue());
    }
}
