package option;

import interpreter.Display;
import model.*;


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
    public void Call(String program) {
        Memory memory = execute(program);
        Display.display(memory.toString());
    }

    private Memory execute(String program) {
        Memory memory = new Memory();
        String[] instructions = program.split("\n");
        Display.display(instructions);
        //while(memory.getI() < instructions.length) {

        //}
        return memory;
    }
}
