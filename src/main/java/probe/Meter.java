package probe;

import core.ExecutionContext;

/**
 * Interface définissant une métrique
 *
 * @author Alexandre Clement
 * @see Metrics
 * @see Time
 * @see Trace
 * @since 08/12/2016.
 */
public interface Meter
{
    /**
     * Initialise la métrique
     */
    void initialize();

    /**
     * Récupère le résultat de la métrique après exécution du programme
     */
    void getResult();

    /**
     * Récupère une image du contexte lors de l'exécution du programme
     *
     * @param executionContext the current execution context of the program
     */
    void acknowledge(ExecutionContext executionContext);
}
