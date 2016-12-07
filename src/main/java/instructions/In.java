package instructions;

import core.Core;
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
    public void execute(Core core) throws CoreException, LanguageException
    {
        try
        {
            core.memory[core.pointer] = (byte) core.in.read();
        }
        catch (IOException exception)
        {
            throw new LanguageException(3, "In file not found");
        }
    }
}