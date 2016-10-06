package language;

import language.instruction.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Brainfuck Project
 *
 * Execute the Brainfuck language with different types of instructions.
 *
 * @author SmartCoding
 */
public class Language {
    static final List<String> longSyntax = new ArrayList<>(Arrays.asList(new String[]{"INCR","DECR","LEFT","RIGHT","OUT","IN","JUMP","BACK"}));
    static final List<Character> shortSyntax = new ArrayList<>(Arrays.asList(new Character[]{'+','-','<','>','.',',','[',']'}));
    static final List<Instruction> instructions  = new ArrayList<>(Arrays.asList(new Instruction[]{new Incr(),new Decr(),new Left(),new Right()}));

    public void execute(String program) {
        // delete the blanket and tablette
        String[] lines = program.split("\n");
        for (String line:lines) {

        }
    }
}
