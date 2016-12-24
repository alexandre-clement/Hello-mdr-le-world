package instructions;


/**
 * Interface pour les boucles
 *
 * @author Alexandre Clement
 * @see Executable
 * @since 09/12/2016.
 */
@FunctionalInterface
public interface Loop
{
    /**
     * @return true si l'instruction ouvre une boucle, false sinon
     */
    boolean open();
}
