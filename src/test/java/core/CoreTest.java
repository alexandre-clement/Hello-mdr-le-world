package core;

import exception.ExitException;
import instructions.*;
import interpreter.Interpreter;
import language.Language;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class CoreTest
{
    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void runPrint() throws Exception
    {
        Interpreter interpreter = Interpreter.buildInterpreter("-p", "src/test/test.bf");
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());

        new Core("src/test/test.bf").run(interpreter.getOptions(), interpreter.getProbes(), context);

        assertEquals(0, context.getValue());
        new Right().execute(context);
        new Right().execute(context);
        assertEquals(12, context.getValue());
    }

    @Test
    public void runRewrite() throws Exception
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Interpreter interpreter = Interpreter.buildInterpreter("-p", "src/test/test.bf", "--rewrite");
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());

        new Core("src/test/test.bf").run(interpreter.getOptions(), interpreter.getProbes(), context);

        assertEquals("+++>++++<[->[->+>+<<]>[-<+>]<<]>[-]\n", out.toString());
        System.setOut(null);
    }

    @Test
    public void runTranslate() throws Exception
    {
        Interpreter interpreter = Interpreter.buildInterpreter("-p", "src/test/test.bf", "--translate");
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());

        new Core("src/test/test.bf").run(interpreter.getOptions(), interpreter.getProbes(), context);

        Interpreter interpreter1 = Interpreter.buildInterpreter("-p", "src/test/test.bf");
        Language language1 = new Language(interpreter);
        ExecutionContext context1 = new ExecutionContextBuilder().buildFromFile(language1.getFile());

        new Core("src/test/test.bf").run(interpreter1.getOptions(), interpreter1.getProbes(), context1);

        Interpreter interpreter2 = Interpreter.buildInterpreter("-p", "src/test/test_out.bmp");
        Language language2 = new Language(interpreter);
        ExecutionContext context2 = new ExecutionContextBuilder().buildFromFile(language2.getFile());

        new Core("src/test/test.bf").run(interpreter2.getOptions(), interpreter2.getProbes(), context2);


        assertEquals(context1.getMemorySnapshot(), context2.getMemorySnapshot());
        assertEquals(context1.getInstruction(), context2.getInstruction());
        assertEquals(context1.getPointer(), context2.getPointer());
    }

    @Test
    public void runCheck() throws Exception
    {
        Interpreter interpreter = Interpreter.buildInterpreter("-p", "src/test/test.bf", "--check");
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());

        new Core("src/test/test.bf").run(interpreter.getOptions(), interpreter.getProbes(), context);

        Deque<Executable> notWellFormed = new ArrayDeque<>();
        notWellFormed.add(new BackOptimised()); // ]
        ExecutionContext context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();

        try
        {
            new Core("src/test/test.bf").run(interpreter.getOptions(), interpreter.getProbes(), context1);
            fail("le programme est mal forme");
        }
        catch (ExitException e)
        {
            assertEquals(4, e.getExit());
            assertEquals("Not well Formed program at instruction 0", e.getMessage());
        }

        notWellFormed.push(new Jump());
        notWellFormed.push(new JumpOptimised()); // [(]
        context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();

        try
        {
            new Core("src/test/test.bf").run(interpreter.getOptions(), interpreter.getProbes(), context1);
            fail("le programme est mal forme");
        }
        catch (ExitException e)
        {
            assertEquals(4, e.getExit());
            assertEquals("Not well Formed program at instruction 1", e.getMessage());
        }

        notWellFormed.add(new Back());
        notWellFormed.add(new JumpOptimised()); // [(])[
        context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();

        try
        {
            new Core("src/test/test.bf").run(interpreter.getOptions(), interpreter.getProbes(), context1);
            fail("le programme est mal forme");
        }
        catch (ExitException e)
        {
            assertEquals(4, e.getExit());
            assertEquals("Not well Formed program at instruction 4", e.getMessage());
        }

        notWellFormed.add(new Back()); // [(])[)
        context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();

        try
        {
            new Core("src/test/test.bf").run(interpreter.getOptions(), interpreter.getProbes(), context1);
            fail("le programme est mal forme");
        }
        catch (ExitException e)
        {
            assertEquals(4, e.getExit());
            assertEquals("Not well Formed program at instruction 5", e.getMessage());
        }

        notWellFormed.push(new Jump());
        notWellFormed.add(new BackOptimised()); // ([(])[)]
        context1 = new ExecutionContextBuilder().setProgram(notWellFormed).build();
        new Core("src/test/test.bf").run(interpreter.getOptions(), interpreter.getProbes(), context1);
    }

}