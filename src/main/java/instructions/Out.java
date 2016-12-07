package instructions;

import core.Core;
import exception.CoreException;
import exception.LanguageException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 */
public class Out implements Executable
{

    @Override
    public void execute(Core core) throws CoreException, LanguageException
    {
        core.out.print((char) core.printValue());
        core.out.flush();
    }
}
