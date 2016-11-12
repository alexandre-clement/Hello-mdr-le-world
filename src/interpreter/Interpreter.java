package interpreter;

import exception.IllegalCommandlineException;
import main.Main;
import org.apache.commons.cli.*;

import java.util.Arrays;

/**
 * @author Alexandre Clement
 *         Created the 04 novembre 2016.
 */
public class Interpreter {

    private CommandLine commandLine;
    private Options options;
    private Options helps;
    private OptionGroup standardOutputOption;
    private boolean hasStandardOutputOption;

    public Interpreter() {
        options = new Options();
        helps = new Options();

        standardOutputOption = new OptionGroup();

        for (Flag flag: Flag.values()) {
            if (flag.isHelp())
                    helps.addOption(optionBuilder(flag));
            else if (flag.isStandardOutputOption())
                standardOutputOption.addOption(optionBuilder(flag));
            else
                options.addOption(optionBuilder(flag));
        }
    }

    public Interpreter build(String... args) throws IllegalCommandlineException {
        try {
            commandLine = new DefaultParser().parse(helps, args, true);
        } catch (ParseException exception) {
            System.err.println(exception.getMessage());
        }

        options.addOptionGroup(standardOutputOption);

        if (hasOption(Flag.HELP))
            return help();
        if (hasOption(Flag.VERSION))
            return version();

        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException exception) {
            help();
            throw new IllegalCommandlineException(exception);
        }
        hasStandardOutputOption = countStandardOutputOption();
        return this;
    }

    public String getOptionValue(Flag flag) {
        return flag.getOpt() == null ? commandLine.getOptionValue(flag.getLongOpt()) : commandLine.getOptionValue(flag.getOpt());
    }

    public boolean hasOption(Flag flag) {
        return flag.getOpt() == null ? commandLine.hasOption(flag.getLongOpt()) : commandLine.hasOption(flag.getOpt());
    }

    public boolean hasStandardOutputOption() {
        return hasStandardOutputOption;
    }

    public Interpreter help() {
        for (Option help: helps.getOptions())
            options.addOption(help);
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(100);
        helpFormatter.printHelp("bfck", "Brainfuck interpreter in Java\n\n", options, "\nVersion " + Main.VERSION, true);
        return this;
    }

    public Interpreter version() {
        System.out.println("Version " + Main.VERSION);
        return this;
    }

    private boolean countStandardOutputOption() {
        return Arrays.stream(Flag.values()).filter(flag -> flag.isStandardOutputOption() && hasOption(flag)).count() == 1;
    }

    private Option optionBuilder(Flag flag) {
        return Option.builder(flag.getOpt())
                .required(flag.isRequired())
                .longOpt(flag.getLongOpt())
                .hasArg(flag.hasArg())
                .argName(flag.getArgName())
                .desc(flag.getDescription())
                .build();
    }
}
