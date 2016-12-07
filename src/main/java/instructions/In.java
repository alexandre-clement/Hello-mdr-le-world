package instructions;

import core.ExecutionContext;
import exception.CoreException;
import exception.LanguageException;

import java.io.IOException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 */
public class In implements Executable
{

    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        try
        {
            executionContext.memory[executionContext.pointer] = (byte) executionContext.in.read();
        }
        catch (IOException exception)
        {
            throw new LanguageException(3, "In file not found");
        }
    }
}