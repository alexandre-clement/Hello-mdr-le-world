package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;

/**
 * Interface pour les Instructions
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 25/11/2016.
 */
public interface Executable
{
    /**
     * @return les propriétés de l'instruction
     */
    Instructions getInstructions();

    /**
     * Exécute l'instruction sur le contexte
     *
     * @param executionContext le contexte
     * @throws ExitException si l'instruction engendre une erreur
     */
    void execute(ExecutionContext executionContext) throws ExitException;
}
