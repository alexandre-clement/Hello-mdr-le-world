package macro;

import com.udojava.evalex.Expression;
import core.Instructions;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
class Macro
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
        this.pattern = Pattern.compile("(?m)^[ \\t]*" + name + "(?:\\s|$)" + MATCH_ALL);
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
        Pattern p = Pattern.compile("([^\\s]+)");
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
        String temp = string;
        Matcher matcher = pattern.matcher(temp);

        while (matcher.find())
        {
            String[] values = findParameters(matcher.group(1));
            values = Arrays.stream(values).map(value -> new Expression(value).eval().toBigInteger().toString()).toArray(String[]::new);
            if (values.length != parameters.length)
                break;
            temp = temp.replace(matcher.group(), sequence.match(parameters, values));
            matcher = pattern.matcher(temp);
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
            apply = indentedPattern(indentation, "(?i)APPLY[ \t]+(.+?)[ \t]+ON[ \t]*(?:" + Instructions.COMMENT + ".*)?$");
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

}