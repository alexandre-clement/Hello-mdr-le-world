package interpreter;

/**
 * Les options de l'interpreteur
 *
 * @author Alexandre Clement
 * @since 10/11/2016.
 */
public enum Flag
{
    // Flag     opt     longOpt         help        required    hasArg  argName     isStandardOutputOption  isProbe     description
    HELP(       "h",    "help",         true,       false,      false,  null,       false,                  false,      "Print the help"),
    VERSION(    "v",    "version",      true,       false,      false,  null,       false,                  false,      "Print the version"),
    SYNTAX(     "s",    "syntax",       true,       false,      false,  null,       false,                  false,      "Print the syntax of the instructions"),
    PRINT(      "p",    null,           false,      true,       true,   "FILE",     false,                  false,      "Specify the file name of program to execute. If no print on the standard output option are selected, it will execute the program and print the memory content"),
    REWRITE(    null,   "rewrite",      false,      false,      false,  null,       true,                   false,      "Print on the standard output the shortened version of the program given as input (and not execute it)"),
    TRANSLATE(  null,   "translate",    false,      false,      false,  null,       true,                   false,      "Create a square bitmap image with the content of the program (and not execute it)"),
    INPUT(      "i",    null,           false,      false,      true,   "INPUT",    false,                  false,      "Specify the file name of the input file (the file given must exists)"),
    OUTPUT(     "o",    null,           false,      false,      true,   "OUTPUT",   false,                  false,      "Specify the file name of the output file (the file given must exists)"),
    CHECK(      null,   "check",        false,      false,      false,  null,       true,                   false,      "Verify if the program is well-formed i.e each OPTIMISED_JUMP instruction is bound to a OPTIMISED_BACK one, and exit silently (without executing the program)"),
    METRICS(    "m",    "metrics",      false,      false,      false,  null,       false,                  true,       "Display the metrics of the execution of the program on the standard output"),
    TRACE(      null,   "trace",        false,      false,      false,  null,       false,                  true,       "Create a log file with execution data named filename.log (for -p filename.bf)"),
    TIME(       "t",    "time",         false,      false,      false,  null,       false,                  true,       "Time the programme execution");

    /**
     * La syntaxe courte
     */
    private String opt;
    /**
     * La syntaxe longue
     */
    private String longOpt;
    /**
     * L'option est une aide
     */
    private boolean help;
    /**
     * L'option est requise (sauf si une option d'aide a été sélectionnée)
     */
    private boolean required;
    /**
     * L'option possède un argument
     */
    private boolean hasArg;
    /**
     * Le nom donnée a l'argument lorsque l'aide est affiché (null si l'option ne possède pas d'argument)
     */
    private String argName;
    /**
     * L'option est une option utilisant la sortie standard
     * (une seule option de ce type peut être exécutée à la fois
     * si aucune option de ce type n'est exécutée alors le programme est exécuté normalement)
     */
    private boolean isStandardOutputOption;
    /**
     * L'option est une métrique
     */
    private boolean isProbe;
    /**
     * La description
     */
    private String description;

    Flag(String opt, String longOpt, boolean help, boolean required, boolean hasArg, String argName, boolean isStandardOutputOption, boolean isProbe, String description)
    {
        this.opt = opt;
        this.longOpt = longOpt;
        this.help = help;
        this.required = required;
        this.hasArg = hasArg;
        this.argName = argName;
        this.isStandardOutputOption = isStandardOutputOption;
        this.isProbe = isProbe;
        this.description = description;
    }

    /**
     * @return la syntaxe courte
     */
    public String getOpt()
    {
        return opt;
    }

    /**
     * @return la syntaxe longue
     */
    public String getLongOpt()
    {
        return longOpt;
    }

    /**
     * @return true si l'option est une aide, false sinon
     */
    public boolean isHelp()
    {
        return help;
    }

    /**
     * @return true si l'option est requise, false sinon
     */
    public boolean isRequired()
    {
        return required;
    }

    /**
     * @return true si l'option possède un argument, false sinon
     */
    public boolean hasArg()
    {
        return hasArg;
    }

    /**
     * @return le nom de l'argument dans l'aide
     */
    public String getArgName()
    {
        return argName;
    }

    /**
     * @return true si l'option est une option utilisant la sortie standard, false sinon
     */
    public boolean isStandardOutputOption()
    {
        return isStandardOutputOption;
    }

    /**
     * @return true si l'option est une métrique, false sinon
     */
    public boolean isProbe()
    {
        return isProbe;
    }

    /**
     * @return la description de l'option
     */
    public String getDescription()
    {
        return description;
    }
}
