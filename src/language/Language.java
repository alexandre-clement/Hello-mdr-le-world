package language;

import language.instruction.*;
import option.BrainfuckOption;

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
    /**
     * List with the instructions
     */
    private static final List<Instruction> instructions  = new ArrayList<>(Arrays.asList(
            new Incr(),new Decr(),new Left(),new Right(),new Out(), new In(), new Jump(), new Back()));

    /**
     * List with the names of instructions
     */
    private static final List<String> instructionsName = new ArrayList<>(
            instructions.stream().map(Instruction::toString).collect(Collectors.toList()));

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
        ((Loop) instructions.get(instructionsName.indexOf(jumpTo)))
                .setAssociatedLoopObject((Loop) instructions.get(instructionsName.indexOf(backTo)));
        // the controversy is not required
    }

    static {
        // we can add another loop shape following the same scheme
        linkLoopObject("Jump", "Back");
    }

    /**
     *  List of the instructions in the running program
     */
    private List<Instruction> inst;

    public Language() {
        inst = new ArrayList<>();
    }

    /**
     * Find and add the instructions in the running program to the list
     * @param program the running program
     */
    public void setInst(String program) {
        String programeHandled = program.replaceAll(" ","");// delete blank spaces
        programeHandled = programeHandled.replaceAll("\t","");// delete indents
        String[] lines = programeHandled.split("\n");
        for (String line:lines) {
            if (instructions.stream().filter(instruction -> instruction.getLongSyntax().equals(line)).findFirst().isPresent()) {
                inst.add(instructions.stream().filter(instruction -> instruction.getLongSyntax().equals(line)).findFirst().get());
            } else {
                char[] characters = line.toCharArray();
                for (char character : characters) {
                    if (instructions.stream().filter(instruction -> instruction.getShortSyntax().equals(character)).findFirst().isPresent()) {
                        inst.add(instructions.stream().filter(instruction -> instruction.getShortSyntax().equals(character)).findFirst().get());
                    }
                }
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
        int j = i + 1;
        while (!check(inst.subList(i, j)))
            j += 1;
        return j-1;
    }

    /**
     *find the position of the associated jump instruction
     * @param i the position of the back instruction
     * @return the position of the associated jump instruction
     */
    public int backTo(int i) {
        int j = i++ - 1;
        while (!check(inst.subList(j, i)))
            j -= 1;
        return j;
    }

    /**
     * @return the list of instructions in the running program
     */
    public List<Instruction> getInst() {
        return inst;
    }

    /**
     * @return a string with the name of the instructions separated by a coma
     */
    @Override
    public String toString() {
        return inst.stream().map(Instruction::toString).collect(Collectors.joining(", "));
    }
}
