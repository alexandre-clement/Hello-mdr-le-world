package language.instruction;

import system.OperatingSystem;

public class Decr implements Instruction {

    @Override
    public void exec(OperatingSystem os) {
        os.decr();
        os.nextI();
    }
}
