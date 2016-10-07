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
    static final List<String> longSyntax = new ArrayList<>(Arrays.asList(new String[]{"INCR","DECR","LEFT","RIGHT"}));//"OUT","IN","JUMP","BACK"
    static final List<Character> shortSyntax = new ArrayList<>(Arrays.asList(new Character[]{'+','-','<','>'}));//'.',',','[',']'
    static final List<Instruction> instructions  = new ArrayList<>(Arrays.asList(new Instruction[]{new Incr(),new Decr(),new Left(),new Right()}));

    public static void execute(String program) {
        // delete the blank spaces and indents
        //program.replaceAll(" ","");
        String[] lines = program.split("\n");
        for (String line:lines) {

        }
    }

    public static void main(String[] args) {
        String program = "hello wor hqpp \nbut            e";
        System.out.println(program+"\n------\n"+program.replaceAll(" ",""));
    }
}
