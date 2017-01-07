package macro;

import com.udojava.evalex.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Une sequence contenant d'autres sequences.
 * <p>
 * Represente un APPLY ON
 * On peut ajouter des sequences a une sequence
 * au même titre que l'on peut inserer plusieurs operateur APPLY ON dans un APPLY ON
 *
 * @author Alexandre Clement
 * @since 05/01/2017.
 */
class Sequence implements Matchable
{
    private String parameter;
    private List<Matchable> sequences;

    Sequence()
    {
        sequences = new ArrayList<>();
    }

    /**
     * Creer une sequence a parametre iterant son contenue en fonction des valeurs de ces parametres.
     *
     * @param parameter les parametres de la sequence
     */
    Sequence(String parameter)
    {
        this();
        this.parameter = parameter;
    }

    /**
     * Ajoute une sequence.
     *
     * @param matchable la sequence a ajoutee
     */
    void add(Matchable matchable)
    {
        sequences.add(matchable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String match(String[] parameters, String[] values)
    {
        String seq = sequences.stream().map(matchable -> matchable.match(parameters, values)).collect(Collectors.joining("\n"));
        if (parameter == null)
            return seq;
        Expression expression = new Expression(parameter);
        for (int i = 0; i < parameters.length; i++)
        {
            expression.with(parameters[i], values[i]);
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < expression.eval().intValue(); i++)
        {
            stringBuilder.append(seq).append("\n");
        }
        if (stringBuilder.length() > 0)
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @Override
    public String toString()
    {
        return sequences == null ? null : "[" + sequences.stream().map(Object::toString).collect(Collectors.joining("\t")) + "]";
    }
}
