package core;

import language.Language;

import java.awt.*;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 24/11/2016.
 */
public enum Instructions
{
    // Instructions     instructions    shortcut    color                       loop type           metrics metricsType                instructions semantics
    INCREMENT(          "INCR",         '+',        new Color(255, 255, 255),   null,               MetricsType.DATA_WRITE,     "Increment the pointed cell by one"),
    DECREMENT(          "DECR",         '-',        new Color(75, 0, 130),      null,               MetricsType.DATA_WRITE,     "Decrement the pointed cell by one"),
    LEFT(               "LEFT",         '<',        new Color(148, 0, 211),     null,               MetricsType.DATA_MOVE,      "Move the memory pointer to the left"),
    RIGHT(              "RIGHT",        '>',        new Color(0, 0, 255),       null,               MetricsType.DATA_MOVE,      "Move the memory pointer to the right"),
    OUT(                "OUT",          '.',        new Color(0, 255, 0),       null,               MetricsType.DATA_READ,      "Print out the content of the memory cell as ASCII"),
    IN(                 "IN",           ',',        new Color(255, 255, 0),     null,               MetricsType.DATA_WRITE,     "Read the value present in the input as an ASCII character"),
    JUMP(               "JUMP",         '[',        new Color(255, 127, 0),     LoopType.OPTIMISED, MetricsType.DATA_READ,      "Jump to the instruction right after the associated BACK if the pointed memory is equals to zero (using a jump table)"),
    BACK(               "BACK",         ']',        new Color(255, 0, 0),       LoopType.OPTIMISED, MetricsType.DATA_READ,      "Go back to the instruction right after the associated JUMP if the pointer memory cell is not equals to zero (using a jump table)"),
    OLD_JUMP(           "OLD_JUMP",     '(',        new Color(255, 127, 127),   LoopType.OLD,       MetricsType.DATA_READ,      "Jump to the instruction right after the associated BACK if the pointed memory is equals to zero"),
    OLD_BACK(           "OLD_BACK",     ')',        new Color(255, 0, 127),     LoopType.OLD,       MetricsType.DATA_READ,      "Go back to the instruction right after the associated JUMP if the pointer memory cell is not equals to zero");

    public enum MetricsType
    {
        DATA_MOVE,
        DATA_WRITE,
        DATA_READ
    }

    public enum LoopType
    {
        OLD,
        OPTIMISED
    }

    private String instruction;
    private Character shortcut;
    private Color color;
    private LoopType loopType;
    private MetricsType metricsType;
    private String semantics;
    private Pattern pattern;

    Instructions(String instruction, Character shortcut, Color color, LoopType loopType, MetricsType metricsType, String semantics)
    {
        this.instruction = instruction;
        this.shortcut = shortcut;
        this.color = color;
        this.loopType = loopType;
        this.metricsType = metricsType;
        this.semantics = semantics;

        // TODO: 25/11/2016 improve pattern
        this.pattern = Pattern.compile("(\\" + shortcut + "(?![0-9])|(?:^\\s*)" + instruction + "(?:\\s*)(?:[" + Language.COMMENT + "].*)?$|^" + color.getRGB() + "$)");
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

    public LoopType getLoopType()
    {
        return loopType;
    }

    public MetricsType getMetricsType()
    {
        return metricsType;
    }

    public String getSemantics()
    {
        return semantics;
    }

}
