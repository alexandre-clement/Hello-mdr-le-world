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
 * @author Alexandre Clement
 *         Created the 24/12/2016.
 */
public class Macro
{
    private final Pattern pattern;
    private String name;
    private Sequence sequence;
    private String[] parameters;

    Macro(String name, String parameters, String sequence)
    {
        this.pattern = Pattern.compile("(?m)^[ \\t]*" + name + "(.*)$");
        this.name = name;
        this.parameters = findParameters(parameters);
        this.sequence = buildSequences(sequence);
    }

    private Pattern indentedPattern(int indentation, String pattern)
    {
        return Pattern.compile("(?i)^( {" + indentation * MacroBuilder.INDENTATION + "}|\t{" + indentation + "})" + pattern);
    }

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
            body = indentedPattern(indentation, "(.*)$");
            matcher = body.matcher(line);
            while (!matcher.matches())
            {
                Sequence node = sequences.pollLast();
                if (sequences.isEmpty())
                    return node;
                body = indentedPattern(--indentation, "(.*)$");
                matcher = body.matcher(line);
            }
            int start = matcher.group(1).length();
            apply = indentedPattern(indentation, "(?i)APPLY[ \t]+([^\\s]+)[ \t]+ON[ \t]*$");
            matcher = apply.matcher(line);

            if (matcher.matches())
            {
                indentation += 1;
                Sequence newSequence = new Sequence(matcher.group(2));
                sequences.peek().add(newSequence);
                sequences.add(newSequence);
            }
            else
            {
                sequences.peekLast().add(new StringSequence(line.substring(start)));
            }
        }
        return sequences.pop();
    }

    @FunctionalInterface
    private interface Matchable
    {
        String match(String[] values);
    }

    private class StringSequence implements Matchable
    {
        private String sequence;

        StringSequence(String sequence)
        {
            this.sequence = sequence;
        }

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

        private void add(Matchable matchable)
        {
            sequences.add(matchable);
        }

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
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            return stringBuilder.toString();
        }

        @Override
        public String toString()
        {
            return sequences == null ? null: "[" +sequences.stream().map(Object::toString).collect(Collectors.joining("\t"))+"]";
        }
    }
}