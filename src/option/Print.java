package option;

import interpreter.Display;
import language.Instruction;

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

public class Print extends StdoutOption{

    @Override
    public String getName() {
        return "-p";
    }

    /**
     * Print the content of the memory
     * call the execute method of the instructions i.e executes the program on the system
     */
    @Override
    public void Call(String filename, Object[] objects) {
        if (language.getRunningInstructions().size() == 0) language.setRunningInstructions(objects); // avoid reset the instructions
        List<Instruction> inst = language.getRunningInstructions();
        int instSize = inst.size();
        for (int i=os.getI(); i < instSize; i = os.getI()) {
            inst.get(i).exec(this);
        }
        Display.display('\n', os.getMemoryContent());
    }
}
