package language.instruction;

import language.Instruction;
import language.*;
import model.OperatingSystem;

import java.awt.*;

/**
 * Brainfuck Project
 *
 * @author SmartCoding
 */
public class Decr implements Instruction {

    @Override
    public void exec(OperatingSystem os, Language language) {
        os.decr();
        os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "DECR";
    }

    @Override
    public Character getShortSyntax() {
        return '-';
    }

    @Override
    public Color getColorCode() {
        return new Color(75, 0, 130);
    }

    @Override
    public String toString() {
        return "Decr";
    }
}
