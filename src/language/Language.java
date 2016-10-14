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
     * List with the long syntax
     */
    private static final List<String> longSyntax = new ArrayList<>(
            instructions.stream().map(Instruction::getLongSyntax).collect(Collectors.toList()));

    /**
     * List with the short syntax
     */
    private static final List<Character> shortSyntax = new ArrayList<>(
            instructions.stream().map(Instruction::getShortSyntax).collect(Collectors.toList()));

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
    private String inputFile;
    private String outputFile;

    public Language() {
        inst = new ArrayList<>();
    }

    /**
     * Find and add the instructions in the running program to the list
     * @param program the running program
     */
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

    /**
     * call the execute method of the instructions i.e executes the program on the system
     * @param os the operating system of the running program
     */
    public void execute(OperatingSystem os) {
        int instSize = inst.size();
        for (int i=os.getI(); i < instSize; i = os.getI()) {
            inst.get(i).exec(os, this);
        }
    }

    /**
     * @return the program in its shortest version
     */
    public String rewrite() {
        StringBuilder stringBuilder = new StringBuilder();
        // for each instruction in the program, we add its short syntax to the string builder
        inst.forEach(instruction -> stringBuilder.append(instruction.getShortSyntax()));
        return stringBuilder.toString();
    }

    public void setInput() {}

    public void setOutput(String fileName) {
        outputFile = fileName;
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
