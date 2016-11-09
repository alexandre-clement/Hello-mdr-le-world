package interpreter;

import exception.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alexandre Clement
 *         Created the 04 novembre 2016.
 */
public class Interpreter {
    private Deque<Argument> options;

    public Interpreter build(final String... args) {
        Deque<String> commandline = new ArrayDeque<>();
        commandline.addAll(Arrays.asList(args));

        List<Argument> arguments = new ArrayList<>();
        arguments.addAll(getNewArgument());

        resetFilenames();

        options = new ArrayDeque<>();

        try {
            while (!commandline.isEmpty())
                options.add(getOptionFromCommand(arguments, commandline));
        } catch (UnknownOption exception) {
            exit(126);
        }
        return this;
    }

    public String getOptionSnapshot() {
        return options.stream().map(Argument::getName).collect(Collectors.joining(", "));
    }

    void resetFilenames() {
        for (Filenames filename: Filenames.values())
            filename.setName(null);
    }

    public void run() {
        options.forEach(this::callOption);
    }

    private Argument getOptionFromCommand(List<Argument> arguments, Deque<String> commandline) throws UnknownOption {
        for (Argument argument: arguments) {
            if (argument.match(commandline)) {
                arguments.remove(argument);
                return argument;
            }
        }
        throw new UnknownOption();
    }

    private void callOption(Option option) {
        try {
            option.call();
        } catch (IOException exception) {
            exit(127);
        } catch (OverflowException exception) {
            exit(1);
        } catch (OutOfMemoryException exception) {
            exit(2);
        } catch (InvalidFile exception) {
            exit(3);
        } catch (MalFormedException exception) {
            exit(4);
        }
    }

    public boolean noUniqueOption() {
        return options.stream().filter(option -> option instanceof UniqueOption).count() == 0;
    }

    public void removeOption(Option type) {
        type.getClass().getName();
        if (!noUniqueOption())
            options.removeAll(options.stream().filter(option -> type.getClass().isInstance(option)).collect(Collectors.toList()));
    }

    private List<Argument> getNewArgument() {
        return new ArrayList<>(Arrays.asList(new InFile(), new OutFile(), new Print(), new Check(), new Rewrite(), new Translate()));
    }

    private static void exit(int code) {
        System.exit(code);
    }
}
