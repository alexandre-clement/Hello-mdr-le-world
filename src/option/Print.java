package option;

import interpreter.Display;
import system.OperatingSystem;


/**
 * Brainfuck Project
 * Print Class
 *
 * The Class associated to the -p macro
 * Print Call will Print on standard output the content of the memory
 *
 *
 * @author SmartCoding
 * @version Slice 0
 *
 * Created the 4 October 2016
 */

public class Print implements BfckOption{

    @Override
    public void Call(String program, OperatingSystem os) {
        execute(program,os);
        Display.display(os.getMemory().toString());
    }

    private void execute(String program,OperatingSystem os) {
        language.setInst(program);
        language.execute(os);

    }
}
