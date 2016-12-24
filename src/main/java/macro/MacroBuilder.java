package macro;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 24/12/2016.
 */
public class MacroBuilder
{
    static final int INDENTATION = 4;
    private final Pattern definition;
    private final Pattern sequence;
    private RandomAccessFile file;
    private PrintWriter writer;
    private File tmp;

    public MacroBuilder(String filename) throws IOException
    {
        tmp = File.createTempFile(filename, ".tmp");
        writer = new PrintWriter(new BufferedWriter(new FileWriter(tmp)));
        file = new RandomAccessFile(filename, "r");
        definition = Pattern.compile("(?i)^[ \\t]*MACRO[ \\t]+(\\w+)([\\w \\t]*)$");
        sequence = Pattern.compile("^(?:\\t| {" + INDENTATION + "})+(.*)$");
    }

    public File build() throws IOException
    {
        Deque<Macro> macros = new ArrayDeque<>();
        Matcher defMatcher;
        Matcher seqMatcher;
        StringBuilder body;
        String temp;

        for (String line = file.readLine(); line != null; line = file.readLine())
        {
            defMatcher = definition.matcher(line);
            if (defMatcher.matches())
            {
                body = new StringBuilder();
                for (seqMatcher = sequence.matcher(file.readLine()); seqMatcher.matches(); seqMatcher = sequence.matcher(file.readLine()))
                {
                    body.append(seqMatcher.group()).append('\n');
                }
                macros.push(new Macro(defMatcher.group(1), defMatcher.group(2), body + "\n "));
            }
            else
            {
                temp = line;
                for (Macro macro : macros)
                {
                    temp = macro.match(temp);
                }
                writer.write(temp + '\n');
            }
        }
        writer.flush();
        writer.close();
        return tmp;
    }
}