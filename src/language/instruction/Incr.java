package language.instruction;

import system.OperatingSystem;

public class Incr implements Instruction {

    @Override
    public void exec(OperatingSystem os) {
        os.incr();
    }
}
