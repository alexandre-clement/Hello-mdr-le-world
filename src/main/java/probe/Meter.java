package probe;

import core.ExecutionContext;

/**
 * @author Alexandre Clement
 *         Created the 08/12/2016.
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
