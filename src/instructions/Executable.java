package instructions;

import core.Core;
import exception.CoreException;
import exception.LanguageException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 */
public interface Executable
{
    void execute(Core core) throws CoreException, LanguageException;
}
