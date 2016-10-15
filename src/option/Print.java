package option;

import interpreter.Display;
import language.Instruction;
import model.OperatingSystem;

import java.util.List;


/**
 * Brainfuck Project
 * Print Class
 *
 * The Class associated to the -p macro
 * Print Call will Print on standard output the content of the memory
 *
 *
 * @author SmartCoding
 *
 * Created the 4 October 2016
 */

public class Print extends StdoutOption {

    @Override
    public String getName() {
        return "-p";
    }

    /**
     * Print the content of the memory
     *
     * @param program the String version of the file
     */
    @Override
    public void Call(String program) {
        if (language.getInst().size() == 0) language.setInst(program); // avoid reset the instructions
        List<Instruction> inst = language.getInst();
        execute(os, inst);
        Display.display('\n', os.getMemoryContent());
    }

    /**
     * call the execute method of the instructions i.e executes the program on the system
     * @param os the operating system of the running program
     */
    private void execute(OperatingSystem os, List<Instruction> inst) {
        for (int i=os.getI(); i < inst.size(); i = os.getI()) {
            inst.get(i).exec(this);
        }
    }
}
