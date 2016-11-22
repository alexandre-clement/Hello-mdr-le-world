package core;

import exception.*;

import java.awt.*;
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

    Instructions(String instruction, Character shortcut, Color color) {
        String sh = shortcut.toString();
        if ("+.[]".contains(sh))
            sh = "\\" + sh;
        this.pattern = Pattern.compile("(" + sh + "(?![0-9])|\\b" + instruction + "\\b|" + color.getRGB() + "(?![0-9]))");
        this.longSyntax = instruction;
        this.shortSyntax = shortcut;
        this.colorSyntax = color;
    }

    Pattern getPattern() {
        return pattern;
    }

    String getLongSyntax() {
        return longSyntax;
    }

    String getShortSyntax() {
        return shortSyntax.toString();
    }

    int getColorSyntax() {
        return colorSyntax.getRGB();
    }

    public abstract void execute() throws CoreException, LanguageException;
}