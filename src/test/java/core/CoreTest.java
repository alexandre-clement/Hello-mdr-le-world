package core;

import exception.ExitException;
import instructions.*;
import interpreter.Interpreter;
import language.Language;
import main.MainTest;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test Core.
 *
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class CoreTest
{
    @Before
    public void setUp() throws Exception
    {
        File file = new File(MainTest.FILENAME);
        FileWriter write = new FileWriter(file);
        write.write("macro MULTI_INCR nb_INCR # definition de la macro MULTI_INCR\n" +
                "    apply nb_INCR on\n" +
                "        INCR\n" +
                "\n" +
                "MULTI_INCR 3\n" +
                "# CO: 3\n" +
                "RIGHT\n" +
                "++++ # C1: 4\n" +
                "<[->[->+>+<<]>[-<+>]<<]>[-] # C3: C0 * C1 = 12");
        write.close();
    }

    /**
     * Test l'execution du programme
     */
    @Test
    public void runPrint() throws Exception
    {
        Core core = new Core(MainTest.PATH);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        core.setOut(new PrintStream(out));
        Interpreter interpreter = Interpreter.buildInterpreter("-p", MainTest.FILENAME);
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());

        core.run(interpreter.getOptions(), interpreter.getProbes(), context);

        // Verifie que la mémoire est à 0 sauf pour la cellule 3 qui vaut 12
        for (int i = 0; i < context.getCapacity(); i++)
        {
            if (i == 3)
                assertEquals(12, context.printValue(i));
            else
                assertEquals(0, context.printValue(i));
        }
        // La sortie standard affiche l'état de la cellule 3 à 12
        assertEquals("\nC3:  12   \n", out.toString());
    }

    /**
     * Test la retranscription en syntaxe courte
     */
    @Test
    public void runRewrite() throws Exception
    {
        Core core = new Core(MainTest.PATH);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        core.setOut(new PrintStream(out));
        Interpreter interpreter = Interpreter.buildInterpreter("-p", MainTest.FILENAME, "--rewrite");
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());

        core.run(interpreter.getOptions(), interpreter.getProbes(), context);

        assertEquals("+++>++++<[->[->+>+<<]>[-<+>]<<]>[-]\n", out.toString());
    }

    /**
     * Test la retranscription sous forme d'image bmp
     */
    @Test
    public void runTranslate() throws Exception
    {
        // On appel la méthode translate pour créer une image
        Interpreter interpreter = Interpreter.buildInterpreter("-p", MainTest.FILENAME, "--translate");
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());

        new Core(MainTest.PATH).run(interpreter.getOptions(), interpreter.getProbes(), context);


        // On créer un context référence
        Interpreter interpreter1 = Interpreter.buildInterpreter("-p", MainTest.FILENAME);
        Language language1 = new Language(interpreter1);
        ExecutionContext context1 = new ExecutionContextBuilder().buildFromFile(language1.getFile());

        // On crée un context à partir de l'image
        Interpreter interpreter2 = Interpreter.buildInterpreter("-p", MainTest.BITMAP);
        Language language2 = new Language(interpreter2);
        ExecutionContext context2 = new ExecutionContextBuilder().buildFromFile(language2.getFile());

        // On test l'égalité entre le contexte témoin et le context créer à partir de l'image
        assertEquals(context1.getMemorySnapshot(), context2.getMemorySnapshot());
        assertEquals(context1.getInstruction(), context2.getInstruction());
        assertEquals(context1.getPointer(), context2.getPointer());
        assertEquals(context1.getProgramLength(), context2.getProgramLength());
        for (int i = 0; i < context1.getProgramLength(); i++)
        {
            assertEquals(context1.getCurrentInstruction(), context2.getCurrentInstruction());
            context1.nextInstruction();
            context2.nextInstruction();
        }
    }

    /**
     * Test l'execution de l'option check
     */
    @Test
    public void runCheck() throws Exception
    {
        Interpreter interpreter = Interpreter.buildInterpreter("-p", MainTest.FILENAME, "--check");
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());

        new Core(MainTest.PATH).run(interpreter.getOptions(), interpreter.getProbes(), context);

        /*
        On ajoute des instructions manuellement afin de créer des programmes mal-formées pour différentes raisons
         */
        Deque<Executable> notWellFormed = new ArrayDeque<>();
        notWellFormed.add(new BackOptimised()); // ajout d'une instruction BACK
        ExecutionContext context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();

        // On verifie que le programme est mal-formée
        try
        {
            new Core(MainTest.PATH).run(interpreter.getOptions(), interpreter.getProbes(), context1);
            fail("le programme est mal forme");
        }
        catch (ExitException e)
        {
            assertEquals(4, e.getExit());
            assertEquals("Not well Formed program at instruction 0", e.getMessage());
        }

        notWellFormed.push(new Jump());
        notWellFormed.push(new JumpOptimised());
        // Programme : [ ( ]
        context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();

        // On vérifie que le programme est mal-formées
        try
        {
            new Core(MainTest.PATH).run(interpreter.getOptions(), interpreter.getProbes(), context1);
            fail("le programme est mal forme");
        }
        catch (ExitException e)
        {
            assertEquals(4, e.getExit());
            assertEquals("Not well Formed program at instruction 1", e.getMessage());
        }

        notWellFormed.add(new Back());
        notWellFormed.add(new JumpOptimised());
        // [ ( ] ) [
        context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();

        // On vérifie que le programme est mal-formée
        try
        {
            new Core(MainTest.PATH).run(interpreter.getOptions(), interpreter.getProbes(), context1);
            fail("le programme est mal forme");
        }
        catch (ExitException e)
        {
            assertEquals(4, e.getExit());
            assertEquals("Not well Formed program at instruction 4", e.getMessage());
        }

        notWellFormed.add(new Back());
        // [ ( ] ) [ )
        context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();

        // On vérifie que le programme est mal formée
        try
        {
            new Core(MainTest.PATH).run(interpreter.getOptions(), interpreter.getProbes(), context1);
            fail("le programme est mal forme");
        }
        catch (ExitException e)
        {
            assertEquals(4, e.getExit());
            assertEquals("Not well Formed program at instruction 5", e.getMessage());
        }

        notWellFormed.push(new Jump());
        notWellFormed.add(new BackOptimised());
        // ( [ ( ] ) [ ) ]
        context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();
        // L'execution ne provoque pas d'erreur, le programme est bien formée
        new Core(MainTest.PATH).run(interpreter.getOptions(), interpreter.getProbes(), context1);
    }

}