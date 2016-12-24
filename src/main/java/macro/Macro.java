package macro;

import com.udojava.evalex.Expression;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Définition des macros
 * <p>
 * Une macro doit être indentée
 * Une macro doit être suivis d'un retour à la ligne
 * Une macro peut appeler d'autres macro avec des valeurs constantes en paramètres (non variables, non évaluables)
 * Une macro possède un opérateur "APPLY x ON" en remplaçant x par une variable, une expression, ou une valeurs fixes
 * le corps sur lequel s'applique l'opérateur APPLY ON doit être indentée
 * <p>
 * les opérateurs sont insensibles à la casse
 * <p>
 * Exemple:
 * <p>
 * MACRO MULTI_DECR nombre_de_decrement
 *      APPLY nombre_de_decrement ON
 *          DECR
 * <p>
 * // Les 3 définitions de TO_DIGIT suivantes sont équivalentes
 * <p>
 * MACRO TO_DIGIT
 *      MULTI_DECR 48
 * <p>
 * MACRO TO_DIGIT2
 *      APPLY 48 ON
 *          DECR
 * <p>
 * MACRO TO_DIGIT3
 *      APPLY 48/2 ON
 *          MULTI_DECR 1
 * -
 * <p>
 * // Incrémente 2^x cellules mémoire à la valeur y
 * <p>
 * MACRO Incr_2^x_cell_to_y x y
 *      APPLY 2^x ON
 *          APPLY y ON
 *              INCR
 *          RIGHT
 *
 * @author Alexandre Clement
 * @since 24/12/2016.
 */
public class Macro
{
    /**
     * Capture tous les caractère jusqu'à la fin de la chaîne
     */
    static final String MATCH_ALL = "(.*)$";
    /**
     * Le pattern identifiant la macro
     */
    private final Pattern pattern;
    /**
     * Le nom de la macro
     */
    private String name;
    /**
     * La séquence associé à la macro
     */
    private Sequence sequence;
    /**
     * Les paramètres de la macro
     */
    private String[] parameters;

    /**
     * Construit une macro
     *
     * @param name       le nom de la macro
     * @param parameters les paramètres de la macro
     * @param sequence   la séquence de la macro
     */
    Macro(String name, String parameters, String sequence)
    {
        this.pattern = Pattern.compile("(?m)^[ \\t]*" + name + MATCH_ALL);
        this.name = name;
        this.parameters = findParameters(parameters);
        this.sequence = buildSequences(sequence);
    }

    /**
     * Construit un pattern indentée
     *
     * @param indentation le nombre d'indentation
     * @param pattern     le pattern
     * @return un pattern indentée
     */
    private Pattern indentedPattern(int indentation, String pattern)
    {
        return Pattern.compile("(?i)^( {" + indentation * MacroBuilder.INDENTATION + "}|\t{" + indentation + "})" + pattern);
    }

    /**
     * Trouve les paramètres contenues dans une chaîne de caractère
     *
     * @param parameters la chaîne de caractère
     * @return le tableau contenant les paramètres
     */
    private String[] findParameters(String parameters)
    {
        Pattern p = Pattern.compile("(\\w+)");
        Matcher m = p.matcher(parameters);
        List<String> params = new ArrayList<>();
        while (m.find())
        {
            params.add(m.group());
        }
        return params.toArray(new String[params.size()]);
    }

    /**
     * Cherche si la macro est dans la ligne, et remplace son nom par sa séquence
     *
     * @param string la ligne a match
     * @return la ligne avec remplacement de la macro par sa séquence si la macro est dans la ligne
     */
    String match(String string)
    {
        Matcher matcher = pattern.matcher(string);
        String temp = string;
        while (matcher.find())
        {
            String[] values = findParameters(matcher.group(1));
            if (values.length == parameters.length)
                temp = matcher.replaceAll(sequence.match(values));
        }
        return temp;
    }

    @Override
    public String toString()
    {
        return name;
    }

    /**
     * Génère la liste de séquence contenue dans la macro
     *
     * @param string le corps de la macro
     * @return la séquence correspondante
     */
    private Sequence buildSequences(String string)
    {
        int indentation = 1;
        Pattern apply;
        Pattern body;
        Matcher matcher;
        Deque<Sequence> sequences = new ArrayDeque<>();
        sequences.add(new Sequence());
        String[] lines = string.split("\n");
        for (String line : lines)
        {
            body = indentedPattern(indentation, MATCH_ALL);
            matcher = body.matcher(line);
            while (!matcher.matches())
            {
                Sequence node = sequences.pollLast();
                if (sequences.isEmpty())
                    return node;
                body = indentedPattern(--indentation, MATCH_ALL);
                matcher = body.matcher(line);
            }
            int start = matcher.group(1).length();
            apply = indentedPattern(indentation, "(?i)APPLY[ \t]+([^\\s]+)[ \t]+ON[ \t]*$");
            matcher = apply.matcher(line);

            if (matcher.matches())
            {
                indentation += 1;
                Sequence newSequence = new Sequence(matcher.group(2));
                sequences.peekLast().add(newSequence);
                sequences.add(newSequence);
            }
            else
            {
                sequences.peekLast().add(new StringSequence(line.substring(start)));
            }
        }
        return sequences.pop();
    }

    /**
     * Interface définissant une le corps d'une macro
     *
     * @see StringSequence
     * @see Sequence
     */
    @FunctionalInterface
    private interface Matchable
    {
        /**
         * Renvoie la valeurs de la séquence en fonction des valeurs des paramètres données à la macro
         *
         * @param values les valeurs des paramètres de la macro
         * @return la valeur de la séquence
         */
        String match(String[] values);
    }

    /**
     * Une séquence ne contenant qu'une chaîne de caractère
     */
    private class StringSequence implements Matchable
    {
        private String sequence;

        StringSequence(String sequence)
        {
            this.sequence = sequence;
        }

        /**
         * @param values les valeurs des paramètres de la macro
         * @return la chaîne de caractère
         */
        @Override
        public String match(String[] values)
        {
            return sequence;
        }

        @Override
        public String toString()
        {
            return sequence;
        }
    }

    /**
     * Une séquence contenant d'autres séquence
     */
    private class Sequence implements Matchable
    {
        private String parameter;
        private List<Matchable> sequences;

        private Sequence()
        {
            sequences = new ArrayList<>();
        }

        private Sequence(String parameter)
        {
            this();
            this.parameter = parameter;
        }

        /**
         * Ajoute une séquence
         *
         * @param matchable la séquence a ajoutée
         */
        private void add(Matchable matchable)
        {
            sequences.add(matchable);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String match(String[] values)
        {
            String seq = sequences.stream().map(matchable -> matchable.match(values)).collect(Collectors.joining("\n"));
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
}