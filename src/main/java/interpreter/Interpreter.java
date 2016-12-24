package interpreter;

import core.Instructions;
import exception.ExitException;
import main.Main;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Interpret the input commandline with Flag as key for options.
 *
 * @author Alexandre Clement
 * @see Flag
 * @since 04 novembre 2016.
 */
public class Interpreter
{

    private final Options options;
    private final Options helps;
    private final OptionGroup standardOutputOption;
    private CommandLine commandLine;
    private boolean hasStandardOutputOption;

    /**
     * Initializes a new Interpreter object.
     */
    private Interpreter()
    {
        /*
        Fill the options with the data contained in Flag
        options are filtered in two categories :
            helps display some help about usage of the program (and stop the program execution)
            options contains all options
         */
        options = new Options();
        helps = new Options();

        standardOutputOption = new OptionGroup();

        for (Flag flag : Flag.values())
        {
            if (flag.isHelp())
                helps.addOption(optionBuilder(flag));
            else if (flag.isStandardOutputOption())
                standardOutputOption.addOption(optionBuilder(flag));
            else
                options.addOption(optionBuilder(flag));
        }
    }

    /**
     * Initializes the CommandLine with the given command line.
     *
     * @param commandline the commandline i.e [-i input file] [-o output file] -p program [--rewrite | --translate | --check]
     * @return a new Interpreter object initialized with the command line given
     * @throws exception.ExitException if the commandline given is incorrect i.e
     *                                 the p options is not given or without argument,
     *                                 the i/o options are given without argument,
     *                                 multiple standard output option are given (--rewrite, --translate, --check)
     */
    public static Interpreter buildInterpreter(String... commandline) throws ExitException
    {
        return new Interpreter().build(commandline);
    }

    /**
     * Initializes the CommandLine with the given command line.
     *
     * @param args the commandline i.e [-i input file] [-o output file] -p program [--rewrite | --translate | --check]
     * @return the current instance
     * @throws ExitException if the commandline given is incorrect i.e
     *                       the p options is not given or without argument,
     *                       the i/o options are given without argument,
     *                       multiple standard output option are given (--rewrite, --translate, --check)
     */
    private Interpreter build(String... args) throws ExitException
    {
        try
        {
            commandLine = new DefaultParser().parse(helps, args, true);
        }
        catch (ParseException exception)
        {
            throw new ExitException(127, this.getClass().getSimpleName(), "build", exception);
        }

        options.addOptionGroup(standardOutputOption);

        if (hasOption(Flag.HELP))
            help();
        if (hasOption(Flag.VERSION))
            version();
        if (hasOption(Flag.SYNTAX))
            syntax();

        try
        {
            commandLine = new DefaultParser().parse(options, args);
        }
        catch (ParseException exception)
        {
            throw new ExitException(126, this.getClass().getSimpleName(), "#build", exception.getMessage());
        }
        hasStandardOutputOption = countStandardOutputOption();
        return this;
    }

    /**
     * Return the argument in the commandline of the flag.
     * <p>
     * For example, if the commandline is {"-p", "filename"}, getOptionValue(Flag.PRINT) will return "filename".
     * <p>
     * If the option is not in the commandline, getOptionValue return null.
     * For example, if the commandline is {"-p", "filename"}, getOptionValue(Flag.INPUT) will return null.
     *
     * @param flag The flag of the option
     * @return the argument of the option
     */
    public String getOptionValue(Flag flag)
    {
        return flag.getOpt() == null ? commandLine.getOptionValue(flag.getLongOpt()) : commandLine.getOptionValue(flag.getOpt());
    }

    /**
     * Return true if the option is in the commandline, false otherwise.
     * <p>
     * For example, if the commandline is {"-p", "filename"}, hasOption(Flag.PRINT) will return true,
     * and hasOption(Flag.REWRITE) will return false.
     *
     * @param flag the flag of the option
     * @return true if the option is in the commandline, false otherwise
     */
    public boolean hasOption(Flag flag)
    {
        return flag.getOpt() == null ? commandLine.hasOption(flag.getLongOpt()) : commandLine.hasOption(flag.getOpt());
    }

    /**
     * Return true if the commandline have standard output option, false otherwise.
     * <p>
     * For the commandline {"-p", "filename"}, hasStandardOutputOption() will return false,
     * For the commandline {"-p", "filename", "--rewrite"}, hasStandardOutputOption() will return true.
     *
     * @return true if the commandline have standard output option, false otherwise
     */
    private boolean hasStandardOutputOption()
    {
        return hasStandardOutputOption;
    }

    /**
     * Create an array which contains the flags present in the commandline.
     * (Print is removed if a standard option is also present in the commandline)
     *
     * @return the flags present in the commandline
     */
    public Flag[] getOptions()
    {
        Predicate<Flag> printNorStandardOutputOption = flag -> hasOption(flag) && (flag != Flag.PRINT || !hasStandardOutputOption());
        return Arrays.stream(Flag.values()).filter(printNorStandardOutputOption).toArray(Flag[]::new);
    }

    /**
     * Create an array which only contains the probes present in the commandline.
     *
     * @return Probes options is the commandline
     */
    public Flag[] getProbes()
    {
        return Arrays.stream(Flag.values()).filter(Flag::isProbe).filter(this::hasOption).toArray(Flag[]::new);
    }

    /**
     * Display the help
     */
    private void help()
    {
        helps.getOptions().forEach(options::addOption);
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(150);
        helpFormatter.printHelp("bfck", "Brainfuck interpreter in Java\n\n", options, "\nVersion " + Main.VERSION, true);
        System.exit(0);
    }

    /**
     * Display the version
     */
    private void version()
    {
        Main.standardOutput("Version " + Main.VERSION);
        System.exit(0);
    }

    /**
     * Display the syntaxe of the instructions
     */
    private void syntax()
    {
        Main.standardOutput("Brainfuck instructions syntax:\n\n");
        Main.standardOutput(String.format("%-20s%-10s%-10s%-10s%n", "Instruction", "Shortcut", "Hex Code", "Semantic"));
        for (Instructions instructions : Instructions.values())
        {
            Main.standardOutput(String.format("%-20s%-10s%-10x%s%n", instructions.getInstruction(), instructions.getShortcut(), instructions.getColor().getRGB(), instructions.getSemantics()));
        }
        System.exit(0);
    }

    private boolean countStandardOutputOption()
    {
        return Arrays.stream(Flag.values()).filter(flag -> flag.isStandardOutputOption() && hasOption(flag)).count() == 1;
    }

    private Option optionBuilder(Flag flag)
    {
        return Option.builder(flag.getOpt())
                .required(flag.isRequired())
                .longOpt(flag.getLongOpt())
                .hasArg(flag.hasArg())
                .argName(flag.getArgName())
                .desc(flag.getDescription())
                .build();
    }
}
