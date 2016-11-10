package interpreter;

import exception.ExitException;
import org.apache.commons.cli.*;

import java.util.Arrays;

/**
 * @author Alexandre Clement
 *         Created the 04 novembre 2016.
 */
public class Interpreter {
    private CommandLine commandLine;
    private Options options;

    public Interpreter() {
        options = new Options();
        Arrays.stream(Flag.values()).forEach(flag -> options.addOption(Option.builder(flag.getOpt())
                .required(flag.isRequired())
                .longOpt(flag.getLongOpt())
                .hasArg(flag.hasArg())
                .desc(flag.getDescription())
                .build()));
    }

    public Interpreter build(String... args) throws ExitException {
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException exception) {
            throw new ExitException(126, exception);
        }
        return this;
    }

    public String getOptionValue(Flag flag) {
        return commandLine.getOptionValue(flag.name());
    }

    public boolean hasOption(Flag flag) {
        return commandLine.hasOption(flag.name());
    }

    public boolean hasStandardOutputOption() throws ExitException {
        long numberOfStandardOutputOption = Arrays.stream(Flag.values())
                .filter(flag -> flag.isStandardOutputOption() && hasOption(flag)).count();
        if (numberOfStandardOutputOption == 0)
            return false;
        if (numberOfStandardOutputOption == 1)
            return true;
        throw new ExitException(127, "Multiple standard output options.");
    }
}
