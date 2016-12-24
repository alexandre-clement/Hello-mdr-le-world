package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 *         <p>
 *         Interface pour les Instructions
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
