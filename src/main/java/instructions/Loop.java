package instructions;


/**
 * @author Alexandre Clement
 *         Created the 09/12/2016.
 *         <p>
 *         Interface pour les boucles
 */
@FunctionalInterface
public interface Loop
{
    /**
     * @return true si l'instruction ouvre une boucle, false sinon
     */
    boolean open();
}
