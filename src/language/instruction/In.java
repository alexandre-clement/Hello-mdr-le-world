package language.instruction;

import language.Instruction;
import language.Language;
import model.OperatingSystem;

import java.awt.*;

/**
 * @author SmartCoding
 *         Created the 12 octobre 2016.
 */
public class In implements Instruction {

    @Override
    public void exec(OperatingSystem os, Language language) {
        os.in();
        os.nextI();
    }

    @Override
    public String getLongSyntax() {
        return "IN";
    }

    @Override
    public Character getShortSyntax() {
        return ',';
    }

    @Override
    public Color getColorCode() {
        return new Color(255, 255, 0);
    }

    @Override
    public String toString() {
        return "In";
    }
}
