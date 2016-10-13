package language;

import language.instruction.*;
import model.OperatingSystem;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Brainfuck Project
 *
 * Execute the Brainfuck language with different types of instructions.
 *
 * @author SmartCoding
 */
public class Language {
    private static final List<Instruction> instructions  = new ArrayList<>(Arrays.asList(
            new Incr(),new Decr(),new Left(),new Right(),new Out(), new In(), new Jump(), new Back()));

    private static final List<String> instructionsName = new ArrayList<>(
            instructions.stream().map(Instruction::toString).collect(Collectors.toList()));

    private static final List<String> longSyntax = new ArrayList<>(
            instructions.stream().map(Instruction::getLongSyntax).collect(Collectors.toList()));

    private static final List<Character> shortSyntax = new ArrayList<>(
            instructions.stream().map(Instruction::getShortSyntax).collect(Collectors.toList()));

    private final static List<Loop> loops = instructions.stream()
            .filter(instruction -> instruction instanceof Loop)
            .map(instruction -> (Loop) instruction)
            .collect(Collectors.toList());

    private static void linkLoopObject(String jumpTo, String backTo) {
        ((Loop) instructions.get(instructionsName.indexOf(jumpTo)))
                .setAssociatedLoopObject((Loop) instructions.get(instructionsName.indexOf(backTo)));
    }

    static {
        linkLoopObject("Jump", "Back");
    }

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
            inst.get(i).exec(os, this);
        }
    }

    public String rewrite() {
        StringBuilder stringBuilder = new StringBuilder();
        inst.forEach(instruction -> stringBuilder.append(instruction.getShortSyntax()));
        return stringBuilder.toString();
    }

    public void setInput() {}

    public void setOutput() {}

    public boolean check(List<Instruction> instructions) {
        List<Instruction> loopInstructions = instructions.stream()
                .filter(instruction -> instruction instanceof Loop)
                .collect(Collectors.toList());

        for (Loop loop: loops) {
            if (loop.getAssociatedLoopObject() != null) {
                int jump = Collections.frequency(loopInstructions, loop);
                int back = Collections.frequency(loopInstructions, loop.getAssociatedLoopObject());
                if (jump != back) return false;
            }
        }
        return true;
    }

    public int jumpTo(int i) {
        int j = i;
        while (!check(inst.subList(i-1, ++j)));
        return j;
    }

    public int backTo(int i) {
        int j = i;
        while (!check(inst.subList(--j, i+1)));
        return j;
    }

    public List<Instruction> getInst() {
        return inst;
    }

    @Override
    public String toString() {
        return inst.stream().map(Instruction::toString).collect(Collectors.joining(", "));
    }
}
