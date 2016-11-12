package interpreter;

import exception.IllegalCommandlineException;
import main.Main;
import org.apache.commons.cli.*;

import java.util.Arrays;

/**
 * @author Alexandre Clement
 *         Created the 04 novembre 2016.
 *
 * @version 1.0
 *
 * Interpret the input commandline with Flag as key for options.
 */
public class Interpreter
{

    private CommandLine commandLine;
    private final Options options;
    private final Options helps;
    private final OptionGroup standardOutputOption;
    private boolean hasStandardOutputOption;

    /**
     * Initializes a new Interpreter object.
     */
    public Interpreter()
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

        for (Flag flag: Flag.values())
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
     * @param args the commandline i.e [-i input file] [-o output file] -p program [--rewrite | --translate | --check]
     * @return the current instance
     * @throws IllegalCommandlineException  if the commandline given is incorrect i.e
     *                                      the p options is not given or without argument,
     *                                      the i/o options are given without argument,
     *                                      multiple standard output option are given (--rewrite, --translate, --check)
     */
    public Interpreter build(String... args) throws IllegalCommandlineException
    {
        try
        {
            commandLine = new DefaultParser().parse(helps, args, true);
        } catch (ParseException exception) {
            System.err.println(exception.getMessage()); // this should not happen
        }

        options.addOptionGroup(standardOutputOption);

        if (hasOption(Flag.HELP))
            return help();
        if (hasOption(Flag.VERSION))
            return version();

        try
        {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException exception) {
            help();
            throw new IllegalCommandlineException(exception);
        }
        hasStandardOutputOption = countStandardOutputOption();
        return this;
    }

    /**
     * Return the argument in the commandline of the flag.
     *
     * For example, if the commandline is {"-p", "filename"}, getOptionValue(Flag.PRINT) will return "filename".
     *
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
     *
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
     *
     * For the commandline {"-p", "filename"}, hasStandardOutputOption() will return false,
     * For the commandline {"-p", "filename", "--rewrite"}, hasStandardOutputOption() will return true.
     *
     * @return true if the commandline have standard output option, false otherwise
     */
    public boolean hasStandardOutputOption()
    {
        return hasStandardOutputOption;
    }

    /**
     * display the help
     *
     * @return the current instance
     */
    public Interpreter help()
    {
        helps.getOptions().forEach(options::addOption);
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(100);
        helpFormatter.printHelp("bfck", "Brainfuck interpreter in Java\n\n", options, "\nVersion " + Main.VERSION, true);
        return this;
    }

    /**
     * display the version
     *
     * @return the current instance
     */
    public Interpreter version()
    {
        System.out.println("Version " + Main.VERSION);
        return this;
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
