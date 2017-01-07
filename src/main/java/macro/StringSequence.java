package macro;

/**
 * Une sequence ne contenant qu'une chaine de caractere.
 *
 * @author Alexandre Clement
 */
class StringSequence implements Matchable
{
    private String sequence;

    StringSequence(String sequence)
    {
        this.sequence = sequence;
    }

    /**
     * Renvoie la sequence associer a l'instance.
     *
     * @param parameters les paramètres de la macro
     * @param values     les valeurs des parametres de la macro
     * @return la chaine de caractere
     */
    @Override
    public String match(String[] parameters, String[] values)
    {
        String temp = sequence;
        for (int i = 0; i < parameters.length; i++)
        {
            temp = temp.replaceAll("(?<![\\w])(" + parameters[i] + ")(?![\\w])", values[i]);
        }
        return temp;
    }

    @Override
    public String toString()
    {
        return sequence;
    }
}
