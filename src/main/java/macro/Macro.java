package macro;

import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 10/12/2016.
 */
public class Macro
{
    private final Pattern pattern;
    private String sequence;

    public Macro(String name, String sequence)
    {
        this.pattern = Pattern.compile("^\\s*" + name + "\\s*(\\d+)?\\s*$");
        this.sequence = sequence;
    }

    public void applyOn(Deque<Macro> macros)
    {
        Matcher matcher;
        for (Macro macro : macros)
        {
            macro.sequence = match(macro.sequence);
        }
    }

    public String match(String string)
    {
        Matcher matcher = pattern.matcher(string);

        if (!matcher.find())
            return string;
        if (matcher.group(1) == null)
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
        return "Macro { " + pattern + ": " + sequence + " }";
    }
}
