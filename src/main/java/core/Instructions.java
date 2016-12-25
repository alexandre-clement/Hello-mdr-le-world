package core;

import java.awt.*;
import java.util.regex.Pattern;

/**
 * Definit les proprietes de chaque instructions.
 *
 * @author Alexandre Clement
 * @since 24/11/2016.
 */
public enum Instructions
{
    // Instructions     instructions        shortcut    color                       loop type           metrics metricsType         instructions semantics
    INCREMENT(          "INCR",             '+',        new Color(255, 255, 255),   null,               MetricsType.DATA_WRITE,     "Increment the pointed cell by one"),
    DECREMENT(          "DECR",             '-',        new Color(75, 0, 130),      null,               MetricsType.DATA_WRITE,     "Decrement the pointed cell by one"),
    LEFT(               "LEFT",             '<',        new Color(148, 0, 211),     null,               MetricsType.DATA_MOVE,      "Move the memory pointer to the left"),
    RIGHT(              "RIGHT",            '>',        new Color(0, 0, 255),       null,               MetricsType.DATA_MOVE,      "Move the memory pointer to the right"),
    OUT(                "OUT",              '.',        new Color(0, 255, 0),       null,               MetricsType.DATA_READ,      "Print out the content of the memory cell as ASCII"),
    IN(                 "IN",               ',',        new Color(255, 255, 0),     null,               MetricsType.DATA_WRITE,     "Read the value present in the input as an ASCII character"),
    OPTIMISED_JUMP(     "OPTIMISED_JUMP",   '[',        new Color(255, 127, 0),     LoopType.OPTIMISED, MetricsType.DATA_READ,      "Jump to the instruction right after the associated OPTIMISED_BACK if the pointed memory is equals to zero (using a jump table)"),
    OPTIMISED_BACK(     "OPTIMISED_BACK",   ']',        new Color(255, 0, 0),       LoopType.OPTIMISED, MetricsType.DATA_READ,      "Go back to the instruction right after the associated OPTIMISED_JUMP if the pointer memory cell is not equals to zero (using a jump table)"),
    ITERATIVE_JUMP(     "ITERATIVE_JUMP",   '(',        new Color(255, 127, 127),   LoopType.ITERATIVE, MetricsType.DATA_READ,      "Jump to the instruction right after the associated ITERATIVE_BACK if the pointed memory is equals to zero"),
    ITERATIVE_BACK(     "ITERATIVE_BACK",   ')',        new Color(255, 0, 127),     LoopType.ITERATIVE, MetricsType.DATA_READ,      "Go back to the instruction right after the associated ITERATIVE_JUMP if the pointer memory cell is not equals to zero");

    /**
     * Enumeration definissant les types de metriques.
     */
    public enum MetricsType
    {
        DATA_MOVE,
        DATA_WRITE,
        DATA_READ
    }

    /**
     * Enumeration definissant les type de boucles.
     */
    public enum LoopType
    {
        ITERATIVE,
        OPTIMISED
    }

    /**
     * Les caracteres commentaires = {@value}.
     */
    public static final String COMMENT = "#";
    /**
     * La syntaxe longue.
     */
    private final String instruction;
    /**
     * La syntaxe courte.
     */
    private final Character shortcut;
    /**
     * La syntaxe couleur.
     */
    private final Color color;
    /**
     * Le type de boucle auquel appartient l'instruction.
     */
    private final LoopType loopType;
    /**
     * Le type de metriques auquel appartient l'instruction.
     */
    private final MetricsType metricsType;
    /**
     * La description.
     */
    private final String semantics;
    /**
     * Le pattern pour identifie l'instruction dans un fichier.
     */
    private final Pattern pattern;

    /**
     * Definie les proprietes d'une instruction.
     *
     * @param instruction   la syntaxe longue
     * @param shortcut      la syntaxe courte
     * @param color         la syntaxe couleur
     * @param loopType      le type de boucle
     * @param metricsType   le type de metrique
     * @param semantics     la description
     */
    Instructions(String instruction, Character shortcut, Color color, LoopType loopType, MetricsType metricsType, String semantics)
    {
        this.instruction = instruction;
        this.shortcut = shortcut;
        this.color = color;
        this.loopType = loopType;
        this.metricsType = metricsType;
        this.semantics = semantics;

        this.pattern = Pattern.compile("(\\" + shortcut + "|(?:^\\s*)" + instruction + "(?:\\s*)(?:[" + COMMENT + "].*)?$|^" + Math.abs(color.getRGB()) + "$)");
    }

    /**
     * @return la syntaxe longue
     */
    public String getInstruction()
    {
        return instruction;
    }

    /**
     * @return la syntaxe courte
     */
    public Character getShortcut()
    {
        return shortcut;
    }

    /**
     * @return la syntaxe couleur
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * @return le pattern permettant d'identifier l'instruction dans un fichier
     */
    public Pattern getPattern()
    {
        return pattern;
    }

    /**
     * @return le type de boucle (null si l'instruction n'est pas une instruction de ouvrant ou fermant une boucle)
     */
    public LoopType getLoopType()
    {
        return loopType;
    }

    /**
     * @return le type de metriques
     */
    public MetricsType getMetricsType()
    {
        return metricsType;
    }

    /**
     * @return la description
     */
    public String getSemantics()
    {
        return semantics;
    }

}
