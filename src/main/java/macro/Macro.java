package macro;

import com.udojava.evalex.Expression;
import core.Instructions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Definition des macros.
 * <p>
 * <p>Une macro doit etre indentee.
 * Une macro doit etre suivis d'un retour a la ligne.
 * Une macro peut appeler d'autres macro avec des valeurs constantes en parametres (non variables, non evaluables).
 * Une macro possede un operateur "APPLY x ON" en remplacant x par une variable, une expression, ou une valeurs fixes.
 * le corps sur lequel s'applique l'operateur APPLY ON doit etre indentee.
 * </p>
 * les operateurs sont insensibles a la casse
 * <p>
 * Exemple:
 * <pre>
 * MACRO MULTI_DECR nombre_de_decrement
 *      APPLY nombre_de_decrement ON
 *          DECR
 *
 * // Les 3 definitions de TO_DIGIT suivantes sont equivalentes
 *
 * MACRO TO_DIGIT
 *      MULTI_DECR 48
 *
 * MACRO TO_DIGIT2
 *      APPLY 48 ON
 *          DECR
 *
 * MACRO TO_DIGIT3
 *      APPLY 48/2 ON
 *          MULTI_DECR 1
 *          -
 *
 * // Incremente 2^x cellules memoire a la valeur y
 *
 * MACRO Incr_2_pow_x_cell_to_y x y
 *      APPLY 2^x ON
 *          APPLY y ON
 *              INCR
 *          RIGHT
 * </pre>
 *
 * @author Alexandre Clement
 * @since 24/12/2016.
 */
public class Macro
{
    /**
     * Capture tous les caractere jusqu'a la fin de la chaine.
     */
    static final String MATCH_ALL = "(.*?)(?=(?=" + Instructions.COMMENT + ")|(?=$))";
    /**
     * Le pattern identifiant la macro.
     */
    private final Pattern pattern;
    /**
     * Le nom de la macro.
     */
    private String name;
    /**
     * La sequence associe a la macro.
     */
    private Sequence sequence;
    /**
     * Les parametres de la macro.
     */
    private String[] parameters;

    /**
     * Construit une macro.
     *
     * @param name       le nom de la macro
     * @param parameters les parametres de la macro
     * @param sequence   la sequence de la macro
     */
    Macro(String name, String parameters, String sequence)
    {
        this.pattern = Pattern.compile("(?m)^[ \\t]*" + name + MATCH_ALL);
        this.name = name;
        this.parameters = findParameters(parameters);
        this.sequence = buildSequences(sequence);
    }

    /**
     * Construit un pattern indentee.
     *
     * @param indentation le nombre d'indentation
     * @param pattern     le pattern
     * @return un pattern indentee
     */
    private Pattern indentedPattern(int indentation, String pattern)
    {
        return Pattern.compile("(?i)^( {" + indentation * MacroBuilder.INDENTATION + "}|\t{" + indentation + "})" + pattern);
    }

    /**
     * Trouve les parametres contenues dans une chaine de caractere.
     *
     * @param parameters la chaine de caractere
     * @return le tableau contenant les parametres
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
     * Cherche si la macro est dans la ligne, et remplace son nom par sa sequence.
     *
     * @param string la ligne a match
     * @return la ligne avec remplacement de la macro par sa sequence si la macro est dans la ligne
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
     * Genere la liste de sequence contenue dans la macro.
     *
     * @param string le corps de la macro
     * @return la sequence correspondante
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
            apply = indentedPattern(indentation, "(?i)APPLY[ \t]+([^\\s]+)[ \t]+ON[ \t]*(?:" + Instructions.COMMENT + ".*)?$");
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
     * Interface definissant les sequences composant le corps d'une macro.
     *
     * @see StringSequence
     * @see Sequence
     */
    @FunctionalInterface
    private interface Matchable
    {
        /**
         * Renvoie la valeurs de la sequence en fonction des valeurs des parametres donnees a la macro.
         *
         * @param values les valeurs des parametres de la macro
         * @return la valeur de la sequence
         */
        String match(String[] values);
    }

    /**
     * Une sequence ne contenant qu'une chaine de caractere.
     */
    private class StringSequence implements Matchable
    {
        private String sequence;

        private StringSequence(String sequence)
        {
            this.sequence = sequence;
        }

        /**
         * Renvoie la sequence associer a l'instance.
         *
         * @param values les valeurs des parametres de la macro
         * @return la chaine de caractere
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
     * Une sequence contenant d'autres sequence.
     * <p>
     * Represente un APPLY ON
     * On peut ajouter des sequences a une sequence
     * au même titre que l'on peut inserer plusieurs operateur APPLY ON dans un APPLY ON
     */
    private class Sequence implements Matchable
    {
        private String parameter;
        private List<Matchable> sequences;

        private Sequence()
        {
            sequences = new ArrayList<>();
        }

        /**
         * Creer une sequence a parametre iterant son contenue en fonction des valeurs de ces parametres.
         *
         * @param parameter les parametres de la sequence
         */
        private Sequence(String parameter)
        {
            this();
            this.parameter = parameter;
        }

        /**
         * Ajoute une sequence.
         *
         * @param matchable la sequence a ajoutee
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