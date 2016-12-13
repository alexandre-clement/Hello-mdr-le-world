package macro;

import exception.LanguageException;
import language.ReadFile;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 11/12/2016.
 */
public class MacroBuilder
{
    private final Pattern pattern;
    private final ReadFile file;

    public MacroBuilder(ReadFile file)
    {
        this(file, Pattern.compile("^\\s*#\\s*define\\s+(\\w*)\\s+(.*)$"));
    }

    private MacroBuilder(ReadFile file, Pattern compile)
    {
        this.file = file;
        pattern = compile;
    }

    public Deque<Macro> findMacro() throws LanguageException
    {
        Matcher matcher;
        Deque<Macro> macros = new ArrayDeque<>();
        try
        {
            // pour chaque ligne du fichier
            for (String line = file.next(); line != null; line = file.next())
            {
                // on applique le paterne d'une macro i.e #define name(parameters) sequence
                matcher = pattern.matcher(line);
                // tant que l'on a une macro contenue dans la ligne
                if (matcher.matches())
                {
                    String sequence = matcher.group(2);
                    for (Macro macro : macros)
                    {
                        sequence = macro.match(sequence);
                    }
                    macros.add(new Macro(matcher.group(1), sequence));
                    macros.peekLast().applyOn(macros);
                }
            }
            file.reset();
        }
        catch (IOException exception)
        {
            throw new LanguageException(127, "File not found");
        }
        return macros;
    }


}