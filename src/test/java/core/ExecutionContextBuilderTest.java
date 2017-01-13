package core;

import interpreter.Interpreter;
import language.Language;
import main.MainTest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.assertEquals;

/**
 * @author Alexandre Clement
 * @since 25/12/2016.
 */
public class ExecutionContextBuilderTest
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

    @Test
    public void buildFromFile() throws Exception
    {
        Interpreter interpreter = Interpreter.buildInterpreter("-p", MainTest.FILENAME);
        Language language = new Language(interpreter);
        ExecutionContext context = new ExecutionContextBuilder().buildFromFile(language.getFile());
        assertEquals(Instructions.INCREMENT, context.getCurrentInstruction());
        context.nextInstruction();
        assertEquals(Instructions.INCREMENT, context.getCurrentInstruction());
        context.nextInstruction();
        assertEquals(Instructions.INCREMENT, context.getCurrentInstruction());
        context.nextInstruction();
        assertEquals(Instructions.RIGHT, context.getCurrentInstruction());
        context.nextInstruction();
        assertEquals(Instructions.INCREMENT, context.getCurrentInstruction());
        context.nextInstruction();
        assertEquals(Instructions.INCREMENT, context.getCurrentInstruction());
        context.nextInstruction();
        assertEquals(Instructions.INCREMENT, context.getCurrentInstruction());
        context.nextInstruction();
        assertEquals(Instructions.INCREMENT, context.getCurrentInstruction());
        context.nextInstruction();
        assertEquals(Instructions.LEFT, context.getCurrentInstruction());
        context.nextInstruction();
        assertEquals(Instructions.OPTIMISED_JUMP, context.getCurrentInstruction());
        assertEquals(30, context.getJumpLink());
    }


}