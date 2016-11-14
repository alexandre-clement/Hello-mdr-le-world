package interpreter;

import static org.junit.Assert.*;

import exception.IllegalCommandlineException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class InterpreterTest {

    private Interpreter interpreter;

    @Before
    public void setUp() {
        interpreter = new Interpreter();
    }

    /*
        On test l'option p
     */

    /**
     * Si il manque l'option -p, alors, on retourne une erreur
     */
    @Test
    public void missingPrintOption() {
        int exit = 0;
        try {
            interpreter.build("");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing required option: p
            assertEquals("Missing required option: p", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * Si il manque le nom du fichier après l'option -p, on retourne une erreur
     */
    @Test
    public void missingPrintArgument() {
        int exit = 0;
        try {
            interpreter.build("-p");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: p
            assertEquals("Missing argument for option: p", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * Si on remplace le fichier de l'option p par une autre option, on retourne une erreur
     */
    @Test
    public void optionAsPrintArgument() {
        int exit = 0;
        try {
            interpreter.build("-p", "--rewrite");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: p
            assertEquals("Missing argument for option: p", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * Si on remplace le fichier de l'option p par une option avec un fichier, on retourne une erreur
     */
    @Test
    public void optionWithFileAsPrintArgument() {
        int exit = 0;
        try {
            interpreter.build("-p", "-i", "inputfile");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: p
            assertEquals("Missing argument for option: p", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * Si on remplace le fichier de l'option p par une option n'existant pas, l'execution continue
     */
    @Test
    public void fakeOptionAsPrintArgument() {
        int exit = 0;
        try {
            interpreter.build("-p", "--fake");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: p
            assertEquals("Missing argument for option: p", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(0, exit);
    }

    /*
        Désormais, on build avec -p filename pour ne pas avoir d'erreur
        On suppose par ailleurs que filename n'est pas semblable a une option (ex: --check)

        On test l'option i
     */

    /**
     * Si il manque le nom du fichier après l'option -i, on retourne une erreur
     */
    @Test
    public void missingInArgument() {
        try {
            interpreter.build("-p", "filename", "-i");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: i
            assertEquals("Missing argument for option: i", exception.getMessage());
            assertEquals(126, exception.getExit());
        }
    }

    /**
     * Si on remplace le fichier de l'option i par une autre option, on retourne une erreur
     */
    @Test
    public void optionAsInArgument() {
        int exit = 0;
        try {
            interpreter.build("-p", "filename", "-i", "--rewrite");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: i
            assertEquals("Missing argument for option: i", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * Si on remplace le fichier de l'option i par une option avec un fichier, on retourne une erreur
     */
    @Test
    public void optionWithFileAsInArgument() {
        int exit = 0;
        try {
            interpreter.build("-p", "filename", "-i", "-o", "outputfile");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: i
            assertEquals("Missing argument for option: i", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * Si on remplace le fichier de l'option i par une option n'existant pas, l'execution continue
     */
    @Test
    public void fakeOptionAsInArgument() {
        int exit = 0;
        try {
            interpreter.build("-p", "filename", "-i", "--fake");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: i
            assertEquals("Missing argument for option: i", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(0, exit);
    }

    /*
        De même pour out
     */


    /**
     * Si il manque le nom du fichier après l'option -o, on retourne une erreur
     */
    @Test
    public void missingOutArgument() {
        try {
            interpreter.build("-p", "filename", "-o");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: o
            assertEquals("Missing argument for option: o", exception.getMessage());
            assertEquals(126, exception.getExit());
        }
    }

    /**
     * Si on remplace le fichier de l'option o par une autre option, on retourne une erreur
     */
    @Test
    public void optionAsOutArgument() {
        int exit = 0;
        try {
            interpreter.build("-p", "filename", "-o", "--rewrite");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: o
            assertEquals("Missing argument for option: o", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * Si on remplace le fichier de l'option o par une option avec un fichier, on retourne une erreur
     */
    @Test
    public void optionWithFileAsOutArgument() {
        int exit = 0;
        try {
            interpreter.build("-p", "filename", "-o", "-i", "outputfile");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: o
            assertEquals("Missing argument for option: o", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * Si on remplace le fichier de l'option o par une option n'existant pas, l'execution continue
     */
    @Test
    public void fakeOptionAsOutArgument() {
        int exit = 0;
        try {
            interpreter.build("-p", "filename", "-o", "--fake");
        } catch (IllegalCommandlineException exception) {
            // renvoie IllegalCommandlineException et affiche Missing argument option: o
            assertEquals("Missing argument for option: o", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(0, exit);
    }

    /*
        Le groupe d'option standardOutput
     */


    /**
     * Si on a une seule option standardOutput, on éxécute normalement
     */
    @Test
    public void hasStandardOutputOption() throws IllegalCommandlineException {
        interpreter.build("-p", "test.bf", "--rewrite");
    }

    /**
     * On ne peut pas avoir plus d'une option utilisant le standard output, on renvoie une erreur
     * (on ne peut pas avoir --rewrite, --tanslate, --check ensemble)
     */
    @Test
    public void hasMultipleStandardOutputOption() {
        int exit = 0;
        try {
            interpreter.build("-p", "test.bf", "--rewrite", "--check");
        } catch (IllegalCommandlineException exception) {
            assertEquals("The option 'check' was specified but an option from this group has already been selected: 'rewrite'", exception.getMessage());
           exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * si on a toute les options du groupe standardOutput, renvoie une erreur
     */
    @Test
    public void allMultipleStandardOutputOption() {
        int exit = 0;
        try {
            interpreter.build("-p", "test.bf", "--rewrite", "--translate", "--check");
        } catch (IllegalCommandlineException exception) {
            assertEquals("The option 'translate' was specified but an option from this group has already been selected: 'rewrite'", exception.getMessage());
            exit = exception.getExit();
        }
        assertEquals(126, exit);
    }

    /**
     * On peut avoir une option standardOutput et d'autres options comme i et o, on éxécute normalement
     */
    @Test
    public void hasMultipleOption() throws IllegalCommandlineException {
        interpreter.build("-p", "test.bf", "-i", "input.txt", "-o", "output.txt", "--rewrite");
    }

    /**
     * Maintenant avec une autre option standardOutput
     */
    @Test(expected = IllegalCommandlineException.class)
    public void hasMultipleOptionAndStandardOutputOption() throws IllegalCommandlineException {
        interpreter.build("-p", "test.bf", "-i", "input.txt", "-o", "output.txt", "--rewrite", "--check");
    }

    /*
        On test à présent les méthodes
     */

    /**
     * On test si une option est présente dans la commandline
     */
    @Test
    public void getOptTrueTest() throws IllegalCommandlineException {
        // l'option p est présente, on renvoie true
        assertTrue(interpreter.build("--rewrite", "-p", "test.bf").hasOption(Flag.PRINT));
        // l'option rewrite est présente, on renvoie true
        assertTrue(interpreter.build("--rewrite", "-p", "test.bf").hasOption(Flag.REWRITE));
    }

    /**
     * On test si une option n'est pas présente dans la commandline
     */
    @Test
    public void getOptFalseTest() throws IllegalCommandlineException {
        // l'option check n'est pas présente, on renvoie false
        assertFalse(interpreter.build("--rewrite", "-p", "test.bf").hasOption(Flag.CHECK));
        // l'option input n'est pas présente, on renvoie false
        assertFalse(interpreter.build("--rewrite", "-p", "test.bf").hasOption(Flag.INPUT));
    }

    /**
     * On récupère le fichier attacher a une option:
     * si l'option prend effectivement un argument et, est dans la commandline, renvoie un String
     */
    @Test
    public void getArgTest() throws IllegalCommandlineException {
        assertEquals(   "test.bf",      interpreter.build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.PRINT));
        assertEquals(   "input.txt",    interpreter.build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.INPUT));
    }

    /**
     * On récupère le fichier attacher a une option:
     * si l'option ne prend pas d'argument, renvoie null
     */
    @Test
    public void getArgNullTest() throws IllegalCommandlineException {
        assertNull(interpreter.build("-p", "test.bf", "-i", "input.txt", "--rewrite").getOptionValue(Flag.REWRITE));
    }

    /**
     * On récupère le fichier attacher a une option:
     * si l'option n'est pas dans la commandline, renvoie null
     */
    @Test
    public void optionNotFoundGetArgTest() throws IllegalCommandlineException {
        assertNull(interpreter.build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.OUTPUT));
        assertNull(interpreter.build("-p", "test.bf", "-i", "input.txt").getOptionValue(Flag.CHECK));
    }

    /**
     * il n'y a pas de option standardOutput, on renvoie false
     */
    @Test
    public void noStandardOutputOptionTest() throws IllegalCommandlineException {
        assertFalse(interpreter.build("-p", "test.bf", "-i", "input.txt").hasStandardOutputOption());
    }

    /**
     * il y a une option standardOutput, on renvoie true
     */
    @Test
    public void hasStandardOutputOptionTest() throws IllegalCommandlineException {
        assertTrue(interpreter.build("-p", "test.bf", "-i", "input.txt", "--rewrite").hasStandardOutputOption());
    }

    /**
     * help don't throw an error
     */
    @Test
    public void helpTest() throws IllegalCommandlineException {
        interpreter.build("-h");
    }

    /**
     * version don't throw an error
     */
    @Test
    public void versionTest() throws IllegalCommandlineException {
        interpreter.build("-v");
    }
}
