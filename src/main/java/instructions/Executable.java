package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.ExitException;

/**
 * Interface pour les Instructions.
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 25/11/2016.
 */
public interface Executable
{
    /**
     * Recupere les proprietes de l'instruction.
     *
     * @return les proprietes de l'instruction
     */
    Instructions getInstructions();

    /**
     * Execute l'instruction sur le contexte.
     *
     * @param executionContext le contexte
     * @throws ExitException si l'instruction engendre une erreur
     */
    void execute(ExecutionContext executionContext) throws ExitException;
}
