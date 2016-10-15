package option;

import interpreter.Display;
import language.Instruction;

import java.util.List;

/**
 * @author SmartCoding
 *         Created the 12 octobre 2016.
 */
public class Rewrite extends StdoutOption {

    @Override
    public String getName() {
        return "--rewrite";
    }

    /**
     * Display the program in its shorter version
     *
     * @param program the String version of the file
     */
    @Override
    public void Call(String program) {
        if (language.getInst().size() == 0) language.setInst(program); // avoid reset the instructions
        List<Instruction> inst = language.getInst();
        Display.display(rewrite(inst), '\n');
    }

    /**
     * @return the program in its shortest version
     */
    private String rewrite(List<Instruction> inst) {
        StringBuilder stringBuilder = new StringBuilder();
        // for each instruction in the program, we add its short syntax to the string builder
        inst.forEach(instruction -> stringBuilder.append(instruction.getShortSyntax()));
        return stringBuilder.toString();
    }
}
