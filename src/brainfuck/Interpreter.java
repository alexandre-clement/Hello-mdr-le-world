package brainfuck;

import exception.MalFormedException;
import exception.OutOfMemoryException;
import exception.OverflowException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author Alexandre Clement
 *         Created the 04 novembre 2016.
 */
class Interpreter {
    private List<Option> options;
    private Language language;
    private String filename;

    Interpreter() {
        options = getNewOption();
        language = new Language();
    }

    void build(String... commandline) {
        List<Option> activeOption = new ArrayList<>();
        activeOption.addAll(options.stream().filter(option -> option.isIn(commandline)).collect(Collectors.toList()));
        // activeOption.stream().filter(active -> active instanceof UniqueOption).collect(Collectors.toList())
        activeOption.forEach(Option::call);
    }

    private static void exit(int code) {
        System.exit(code);
    }

    private List<Option> getNewOption() {
        return new ArrayList<>(Arrays.asList(
                new Check(), new Input(), new Output(), new Rewrite(), new Translate(), new Print()));
    }

    interface Callable {
        void call();
    }

    interface Option extends Callable {
        String getName();
        boolean isIn(String... commandline);
    }

    abstract class FileOption implements Option {
        private String name;

        FileOption(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isIn(String... commandline) {
            for (int i = 0; i < commandline.length; i++) {
                if (name.equals(commandline[i])) {
                    if (i + 1 < commandline.length && !options.stream().map(Option::getName).collect(Collectors.toList()).contains(commandline[i + 1])) {
                        filename = commandline[i + 1];
                        commandline[i + 1] = null;
                        return true;
                    } else
                        exit(3);
                }
            }
            return false;
        }
    }

    abstract class UniqueOption implements Option {
        private String name;

        UniqueOption(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isIn(String... commandline) {
            for (String command: commandline) {
                if (getName().equals(command)) return true;
            }
            return false;
        }
    }

    private class Print extends FileOption {
        private Print() {
            super("-p");
        }

        @Override
        public void call() {
            try {
                language.execute(filename);
            } catch (IOException | NoSuchElementException exception) {
                exit(127);
            } catch (OverflowException exception) {
                exit(1);
            } catch (OutOfMemoryException exception) {
                exit(2);
            }
        }
    }

    private class Rewrite extends UniqueOption {
        private Rewrite() {
            super("--rewrite");
        }

        @Override
        public void call() {
            try {
                language.rewrite(filename);
            } catch (IOException | NoSuchElementException exception) {
                exit(127);
            }
        }
    }

    private class Translate extends UniqueOption {
        private Translate() {
            super("--translate");
        }

        @Override
        public void call() {
            try {
                language.translate(filename);
            } catch (IOException | NoSuchElementException exception) {
                exit(127);
            }
        }
    }

    private class Input extends FileOption {
        private Input() {
            super("-i");
        }

        @Override
        public void call() {
        }
    }

    private class Output extends FileOption {
        private Output() {
            super("-o");
        }

        @Override
        public void call() {

        }
    }

    private class Check extends UniqueOption {
        private Check() {
            super("--check");
        }

        @Override
        public void call() {
            try {
                language.check(filename);
            } catch (IOException | NoSuchElementException exception) {
                exit(127);
            } catch (MalFormedException exception) {
                exit(4);
            }
        }
    }
}
