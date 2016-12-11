package macro;

import instructions.Executable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 10/12/2016.
 */
public class Macro
{
    private final Pattern pattern;
    private final String sequence;
    private final boolean hasParameters;

    public Macro(String name, String sequence, String parameters)
    {
        hasParameters = parameters != null;
        if (!hasParameters)
            pattern = Pattern.compile(name);
        else
            pattern = Pattern.compile(name + "\\((\\d*)\\)");
        this.sequence = sequence;
    }

    public String match(String string)
    {
        Matcher matcher = pattern.matcher(string);
        if (!matcher.find())
            return string;
        if (!hasParameters)
            return matcher.replaceAll(sequence);
        String seq = "";
        for (Integer i = 0; i < Integer.valueOf(matcher.group(1)); i++)
        {
            seq = seq.concat(sequence);
        }
        return matcher.replaceAll(seq);
    }

    @Override
    public String toString()
    {
        return "Macro{" + "pattern=" + pattern + ", sequence='" + sequence + '\'' + '}';
    }
}
