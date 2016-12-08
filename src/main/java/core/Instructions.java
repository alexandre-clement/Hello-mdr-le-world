package core;

import exception.CoreException;
import exception.LanguageException;
import instructions.*;
import language.Language;

import java.awt.*;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 24/11/2016.
 */
public enum Instructions // implements Executable
{
    // Instructions     executable          instructions    shortcut    color                       metrics type                instructions semantics
    INCREMENT(          new Increment(),    "INCR",         '+',        new Color(255, 255, 255),   MetricsType.DATA_WRITE,     "Increment the pointed cell by one"),
    DECREMENT(          new Decrement(),    "DECR",         '-',        new Color(75, 0, 130),      MetricsType.DATA_WRITE,     "Decrement the pointed cell by one"),
    LEFT(               new Left(),         "LEFT",         '<',        new Color(148, 0, 211),     MetricsType.DATA_MOVE,      "Move the memory pointer to the left"),
    RIGHT(              new Right(),        "RIGHT",        '>',        new Color(0, 0, 255),       MetricsType.DATA_MOVE,      "Move the memory pointer to the right"),
    OUT(                new Out(),          "OUT",          '.',        new Color(0, 255, 0),       MetricsType.DATA_READ,      "Print out the content of the memory cell as ASCII"),
    IN(                 new In(),           "IN",           ',',        new Color(255, 255, 0),     MetricsType.DATA_WRITE,     "Read the value present in the input as an ASCII character"),
    JUMP(               new JumpOptimised(),"JUMP",         '[',        new Color(255, 127, 0),     MetricsType.DATA_READ,      "Jump to the instruction right after the associated BACK if the pointed memory is equals to zero"),
    BACK(               new BackOptimised(),"BACK",         ']',        new Color(255, 0, 0),       MetricsType.DATA_READ,      "Go back to the instruction right after the associated JUMP if the pointer memory cell is not equals to zero");

    public enum MetricsType
    {
        DATA_MOVE,
        DATA_WRITE,
        DATA_READ
    }

    private Executable executable;
    private String instruction;
    private Character shortcut;
    private Color color;
    private MetricsType type;
    private String semantics;
    private Pattern pattern;

    Instructions(Executable executable, String instruction, Character shortcut, Color color, MetricsType type, String semantics)
    {
        this.executable = executable;
        this.instruction = instruction;
        this.shortcut = shortcut;
        this.color = color;
        this.type = type;
        this.semantics = semantics;

        // TODO: 25/11/2016 improve pattern
        this.pattern = Pattern.compile("(\\" + shortcut + "(?![0-9])|(?:^\\s*)" + instruction + "(?:\\s*)(?:[" + Language.COMMENT +"].*)?$|^" + color.getRGB() + "$)");
    }

    public String getInstruction()
    {
        return instruction;
    }

    public Character getShortcut()
    {
        return shortcut;
    }

    public Color getColor()
    {
        return color;
    }

    public Pattern getPattern()
    {
        return pattern;
    }

    public MetricsType getType()
    {
        return type;
    }

    public String getSemantics()
    {
        return semantics;
    }

    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        executable.execute(executionContext);
    }
}
