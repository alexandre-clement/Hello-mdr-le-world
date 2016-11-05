package brainfuck;

import exception.MalFormedException;
import exception.OutOfMemoryException;
import exception.OverflowException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author Alexandre Clement
 *         Created the 04 novembre 2016.
 */
class Language {
    private Core core;
    private List<Core.Instruction> instructions;

    Language() {
        core = new Core();
        instructions = core.getNewInstruction();
    }

    void execute(String filename) throws IOException, NoSuchElementException, OverflowException, OutOfMemoryException {
        Reader source = new Reader(filename);
        while (source.hasNext()) {
            for (Core.Instruction instruction: getInstruction (source.next())) {
                instruction.execute();
            }
        }
        System.out.println('\n' + core.memoryContent());
    }

    void rewrite(String filename) throws IOException, NoSuchElementException {
        Reader source = new Reader(filename);
        while (source.hasNext()) {
                getInstruction(source.next()).forEach(instruction -> System.out.print(instruction.shortSyntax()));
        }
    }

    void translate(String filename) throws IOException, NoSuchElementException {
        Reader source = new Reader(filename);
        while (source.hasNext()) {
            getInstruction(source.next()).forEach(instruction -> System.out.print(instruction.colorSyntax()));
        }
    }

    void check(String filename) throws IOException, NoSuchElementException, MalFormedException {
        Reader source = new Reader(filename);
        int n = 0;

        while (source.hasNext()) {
            for (Core.Instruction instruction: getInstruction (source.next())) {
                if (instruction instanceof Core.Loop) {
                    if (((Core.Loop) instruction).getAssociated() != null)
                        n += 1;
                    else
                        n -= 1;
                }
                if (n < 0) throw new MalFormedException();
            }
        }
        if (n > 0) throw new MalFormedException();
    }

    private List<Core.Instruction> getInstruction(String line) {
        List<Core.Instruction> run = new ArrayList<>();
        Color color = null;
        try {
            color = new Color(Integer.parseInt(line));
        } catch (NumberFormatException exception) {
            // this is not a Color
        }
        for (Core.Instruction instruction : instructions) {
            if (line.contains(instruction.longSyntax())) {
                run.add(instruction);
                break;
            }
            if (color != null && instruction.colorSyntax().equals(color))
                run.add(instruction);
        }
        if (run.size() == 0)
            for (Character character : line.toCharArray()) {
                run.addAll(instructions.stream().filter(instruction -> instruction.shortSyntax().equals(character)).collect(Collectors.toList()));
            }
        return run;
    }
}
