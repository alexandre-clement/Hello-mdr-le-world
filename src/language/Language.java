package language;

import language.instruction.*;
import system.OperatingSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Brainfuck Project
 *
 * Execute the Brainfuck language with different types of instructions.
 *
 * @author SmartCoding
 */
public class Language {
    public static final List<String> longSyntax = new ArrayList<>(Arrays.asList("INCR","DECR","LEFT","RIGHT"));//"OUT","IN","JUMP","BACK"
    public static final List<Character> shortSyntax = new ArrayList<>(Arrays.asList('+','-','<','>'));//'.',',','[',']'
    public static final List<Instruction> instructions  = new ArrayList<>(Arrays.asList(new Incr(),new Decr(),new Left(),new Right()));

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
            } else {
                char[] characters = line.toCharArray();
                for (char character : characters) {
                    if (shortSyntax.contains(character)) {
                        int i = shortSyntax.indexOf(character);
                        inst.add(instructions.get(i));
                    }
                }
            }
        }
    }

    public void execute(OperatingSystem os) {
        int instSize = inst.size();
        for (int i=os.getI(); i < instSize; i = os.getI()) {
            inst.get(i).exec(os);
        }
    }

    public List<Instruction> getInst() {
        return inst;
    }

    @Override
    public String toString() {
        return inst.stream().map(Instruction::toString).collect(Collectors.joining(", "));
    }
}
