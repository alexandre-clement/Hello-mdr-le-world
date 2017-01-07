package probe;

import core.ExecutionContext;

/**
 * Interface definissant une metrique.
 *
 * @author Alexandre Clement
 * @see Metrics
 * @see Time
 * @see Trace
 * @since 08/12/2016.
 */
interface Meter
{
    /**
     * Initialise la metrique.
     */
    void initialize();

    /**
     * Recupere le resultat de la metrique apres execution du programme.
     */
    void getResult();

    /**
     * Recupere une image du contexte lors de l'execution du programme.
     *
     * @param executionContext the current execution context of the program
     */
    void acknowledge(ExecutionContext executionContext);
}
