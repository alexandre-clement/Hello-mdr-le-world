package language.instruction;

import language.Instruction;
import language.Language;
import model.OperatingSystem;

import java.awt.*;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
public class Left implements Instruction {

    @Override
    public void exec(OperatingSystem os, Language language) {
        os.left();
        os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "LEFT";
    }

    @Override
    public Character getShortSyntax() {
        return '<';
    }

    @Override
    public Color getColorCode() {
        return new Color(148, 0, 211);
    }

    @Override
    public String toString() {
        return "Left";
    }
}
