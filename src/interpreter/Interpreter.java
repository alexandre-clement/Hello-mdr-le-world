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
    private boolean hasUniqueOption;
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

    void resetFilenames() {
        for (Filenames filename: Filenames.values())
            filename.setName(null);
    }

    public void run() {
        hasUniqueOption = !noUniqueOption(options);
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

    private boolean noUniqueOption(Deque<Argument> options) {
        return options.stream().filter(option -> option instanceof UniqueOption).collect(Collectors.counting()) == 0;
    }

    private List<Argument> getNewArgument() {
        return new ArrayList<>(Arrays.asList(new InFile(), new OutFile(), new Print(), new Check(), new Rewrite(), new Translate()));
    }

    private static void exit(int code) {
        System.exit(code);
    }


    private class Print extends Argument {
        private Print() {
            super("-p");
        }

        @Override
        public boolean match(Deque<String> commandline) {
            if (!super.match(commandline))
                return false;
            Filenames.source.setName(commandline.pollFirst());
            return true;
        }

        @Override
        public void call() throws IOException, OverflowException, OutOfMemoryException, InvalidFile, MalFormedException {
            if (!hasUniqueOption) {
                // run
            }
        }
    }

    private class Rewrite extends UniqueOption {
        private Rewrite() {
            super("--rewrite");
        }

        @Override
        public void call() throws IOException, OverflowException, OutOfMemoryException, MalFormedException {
        }
    }

    private class Translate extends UniqueOption {
        private Translate() {
            super("--translate");
        }

        @Override
        public void call() throws IOException {
        }
    }

    private class InFile extends Argument {
        private InFile() {
            super("-i");
        }

        @Override
        public boolean match(Deque<String> commandline) {
            if (!super.match(commandline))
                return false;
            Filenames.input.setName(commandline.pollFirst());
            return true;
        }

        @Override
        public void call() throws InvalidFile {
        }
    }

    private class OutFile extends Argument {
        private OutFile() {
            super("-o");
        }

        @Override
        public boolean match(Deque<String> commandline) {
            if (!super.match(commandline))
                return false;
            Filenames.output.setName(commandline.pollFirst());
            return true;
        }

        @Override
        public void call() throws IOException, OverflowException, OutOfMemoryException, InvalidFile, MalFormedException {
        }
    }

    private class Check extends UniqueOption {
        private Check() {
            super("--check");
        }

        @Override
        public void call() throws IOException, OverflowException, OutOfMemoryException, InvalidFile, MalFormedException {
        }
    }
}
