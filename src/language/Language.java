package language;

import language.instruction.*;

import java.awt.*;
import java.util.*;
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
    /**
     * List with the instructions
     */
    private static final List<Instruction> instructions  = new ArrayList<>(Arrays.asList(
            new Incr(),new Decr(),new Left(),new Right(),new Out(), new In(), new Jump(), new Back()));

    /**
     * list with the Loop instructions
     */
    private final static List<Loop> loops = instructions.stream()
            .filter(instruction -> instruction instanceof Loop)
            .map(instruction -> (Loop) instruction)
            .collect(Collectors.toList());

    /**
     * Linking Jump instruction to the associated instruction Back
     * @param jumpTo the jump instruction
     * @param backTo the associated back instruction
     */
    private static void linkLoopObject(String jumpTo, String backTo) {
        List<String> name = new ArrayList<>(loops.stream().map(Loop::toString).collect(Collectors.toList()));
        (loops.get(name.indexOf(jumpTo))).setAssociatedLoopObject(loops.get(name.indexOf(backTo)));
        // the controversy is not required
    }

    static {
        // we can add another loop shape following the same scheme
        linkLoopObject("Jump", "Back");
    }

    /**
     *  List of the instructions in the running program
     */
    private List<Instruction> runningInstructions;

    public Language() {
        runningInstructions = new ArrayList<>();
    }

    /**
     * Find and add the instructions in the running program to the list
     */
    public void setRunningInstructions(Object[] objects) {
        if (objects instanceof Color[]) {
            for (Object object : objects) {
                runningInstructions.addAll(instructions.stream() // add instruction when Color match
                        .filter(instruction -> instruction.getColorCode().equals(object)).collect(Collectors.toList()));
            }
        }
        if (objects instanceof String[]) {
            for (Object object : objects) {
                for (Character character : ((String) object).toCharArray()) {
                    runningInstructions.addAll(instructions.stream() // add instruction when characters matches
                            .filter(instruction -> instruction.getShortSyntax().equals(character)).collect(Collectors.toList()));
                }
                runningInstructions.addAll(instructions.stream() // add instruction when String match
                        .filter(instruction -> instruction.getLongSyntax().equals(object)).collect(Collectors.toList()));
            }
        }
    }

    /**
     * @param instructions a list of instructions
     * @return true if the list of instructions is well-formed,
     * i.e if every jump instruction have an associated back instruction.
     * false otherwise
     */
    public boolean check(List<Instruction> instructions) {
        // for each loop object used by the interpreter
        for (Loop loop: loops) {
            // if the loop object got an associated object
            if (loop.getAssociatedLoopObject() != null) {
                int wellFormed = 0;
                // we add 1 to the value wellFormed for each opening loop
                // and we subtract 1 for each closing loop
                for (Instruction instruction: instructions) {
                    if (instruction.getClass() == loop.getClass()) {
                        wellFormed += 1;
                    } else if (instruction.getClass() == loop.getAssociatedLoopObject().getClass())
                        wellFormed -=1;
                    // if the value is negative, there is a closing object but the loop was not open before
                    if (wellFormed < 0) return false;
                }
                // if the value is different from 0, there is at least one non-closed loop
                if (wellFormed != 0) return false;
            }
        }
        // the program is well formed
        return true;
    }

    /**
     * find the position of the associated back instruction
     * @param i the position of the jump instruction
     * @return the position of the back instruction
     */
    public int jumpTo(int i) {
        int j = i + 1; // seeks the smallest sub-list such that it is well-formed
        while (!check(runningInstructions.subList(i, j)))
            j += 1;
        return j-1;
    }

    /**
     *find the position of the associated jump instruction
     * @param i the position of the back instruction
     * @return the position of the associated jump instruction
     */
    public int backTo(int i) {
        int j = i++ - 1; // seeks the smallest sub-list such that it is well-formed
        while (!check(runningInstructions.subList(j, i)))
            j -= 1;
        return j;
    }

    /**
     * @return the list of instructions in the running program
     */
    public List<Instruction> getRunningInstructions() {
        return runningInstructions;
    }

    /**
     * @return a string with the name of the instructions separated by a coma
     */
    @Override
    public String toString() {
        return runningInstructions.stream().map(Instruction::toString).collect(Collectors.joining(", "));
    }
}
