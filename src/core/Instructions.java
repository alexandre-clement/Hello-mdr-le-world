package core;

import Language.BitmapImage;
import exception.LanguageException;
import exception.OutOfMemoryException;
import exception.OverflowException;

import java.awt.*;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author Alexandre Clement
 *         Created the 16/11/2016.
 */
public abstract class Instructions {
    private Pattern pattern;
    private String longSyntax;
    private Character shortSyntax;
    private Color colorSyntax;

    public Instructions(Pattern pattern, String instruction, Character shortcut, Color color) {
        this.pattern = pattern;
        this.longSyntax = instruction;
        this.shortSyntax = shortcut;
        this.colorSyntax = color;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getLongSyntax() {
        return longSyntax;
    }

    public String getShortSyntax() {
        return shortSyntax.toString();
    }

    public int getColorSyntax() {
        return colorSyntax.getRGB();
    }

    public abstract void execute() throws OverflowException, OutOfMemoryException, LanguageException;
}