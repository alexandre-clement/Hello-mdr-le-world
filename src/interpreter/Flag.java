package interpreter;

/**
 * @author Alexandre Clement
 *         Created the 10/11/2016.
 */
public enum Flag {
    // Flag     opt     longOpt         required    hasArg  isStandardOutputOption  description
    p(          "p",    null,           true,       true,   false,                  "Specify the file name of program to execute. If no print on the standard output option are selected, it will execute the program and print the memory content."),
    rewrite(    null,   "rewrite",      false,      false,  true,                   "Print on the standard output the shortened version of the program given as input (and not execute it)."),
    translate(  null,   "translate",    false,      false,  true,                   "Create a square bitmap image with the content of the program (and not execute it)."),
    i(          "i",    null,           false,      true,   false,                  "Specify the file name of the input file (the file given must exists)"),
    o(          "o",    null,           false,      true,   false,                  "Specify the file name of the output file (the file given must exists)"),
    check(      null,   "check",        false,      false,  true,                   "Verify if the program is well-formed i.e each JUMP instruction is bound to a Back one, and exit silently (without executing the program).");

    private String opt;
    private String longOpt;
    private boolean required;
    private boolean hasArg;
    private boolean isStandardOutputOption;
    private String description;

    Flag(String opt, String longOpt, boolean required, boolean hasArg, boolean isStandardOutputOption, String description) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.required = required;
        this.hasArg = hasArg;
        this.isStandardOutputOption = isStandardOutputOption;
        this.description = description;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean hasArg() {
        return hasArg;
    }

    public boolean isStandardOutputOption() {
        return isStandardOutputOption;
    }

    public String getDescription() {
        return description;
    }
}
