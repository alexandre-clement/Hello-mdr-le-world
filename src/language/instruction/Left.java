package language.instruction;


import model.Memory;
import system.OperatingSystem;

public class Left implements Instruction {

    @Override
    public void exec(OperatingSystem os) {
        os.left();
    }
}
