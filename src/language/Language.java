package language;

import language.instruction.*;
import system.OperatingSystem;

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

    private List<Instruction> inst;

    public Language() {
        inst = new ArrayList<>();
    }

    public void setInst(String program) {
        program = program.replaceAll(" ","");// delete blank spaces
        program = program.replaceAll("\t","");// delete indents
        String[] lines = program.split("\n");
        for (String line:lines) {
            if (longSyntax.contains(line)) {
                int i = longSyntax.indexOf(line);
                inst.add(instructions.get(i));
            }//else short... or not exist
        }
    }

    public void execute(OperatingSystem os) {
        int i = os.getI();
        while(i != inst.size()) {
            inst.get(i).exec(os);
        }
    }
}
