package option;

import interpreter.Display;

import static interpreter.Interpreter.MEMORY;


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
        execute(program);
        Display.displayMemory();
    }

    private void execute(String program) {
        String[] instructions = program.split("\n");
        //Display.display(commands);
        while(MEMORY.getI() < instructions.length) {
            switch (instructions[MEMORY.getI()]) {
                case "INCR":
                    MEMORY.incr();
                    break;
                case "RIGHT":
                    MEMORY.right();
                    break;
                case "LEFT":
                    MEMORY.left();
                    break;
                default:
                    break;
            }
        }
    }
}