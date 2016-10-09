package option;

import interpreter.Display;


/**
 * Brainfuck Project
 * Print Class
 *
 * The Class associated to the -p macro
 * Print Call will Print on standard output the content of the memory
 *
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public class Print implements BfckOption{

    @Override
    public void Call(String program) {
        execute(program);
        Display.display(os.getMemory().toString());
    }

    private void execute(String program) {
        if (language.getInst().size() == 0) language.setInst(program);
        // System.out.println(language);
        language.execute(os);
    }
}
