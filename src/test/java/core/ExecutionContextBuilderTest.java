package core;

import interpreter.Interpreter;
import language.Language;
import org.junit.Before;
import org.junit.Test;

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

    }

    @Test
    public void buildFromFile() throws Exception
    {
        Interpreter interpreter = Interpreter.buildInterpreter("-p", "src/test/test.bf");
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