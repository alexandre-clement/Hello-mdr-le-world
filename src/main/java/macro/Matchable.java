package macro;

/**
 * Interface definissant les sequences composant le corps d'une macro.
 *
 * @author Alexandre Clement
 * @see StringSequence
 * @see Sequence
 */
@FunctionalInterface
interface Matchable
{
    /**
     * Renvoie la valeurs de la sequence en fonction des valeurs des parametres donnees a la macro.
     *
     * @param parameters les paramètres de la macro
     * @param values     les valeurs des parametres de la macro
     * @return la valeur de la sequence
     */
    String match(String[] parameters, String[] values);
}
