package option;

import interpreter.Display;

/**
 * Brainfuck Project
 * print Class
 *
 * The Class associated to the -p macro
 * print Call will print on standard output the content of the memory
 *
 *
 * @author SmartCoding
 * @version Slice 0
 *
 * Created the 4 October 2016
 */

public class print implements BfckOption{

    @Override
    public void Call(String program) {
        Display.display(program, "\n");
    }
}
