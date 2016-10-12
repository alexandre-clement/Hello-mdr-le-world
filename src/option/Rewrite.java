package option;

import interpreter.Display;
import language.Language;
import language.instruction.Instruction;

/**
 * @author SmartCoding
 *         Created the 12 octobre 2016.
 */
public class Rewrite implements BrainfuckOption {

    /**
     * Display the program in its shorter version
     *
     * @param program the String version of the file
     */
    @Override
    public void Call(String program) {
        language.setInst(program);

        for (Instruction instruction: language.getInst()) {
            Display.display(Language.shortSyntax.get(Language.instructions.indexOf(instruction)));
        }
    }
}
