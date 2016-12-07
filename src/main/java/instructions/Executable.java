package instructions;

import core.ExecutionContext;
import exception.CoreException;
import exception.LanguageException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 */
public interface Executable
{
    void execute(ExecutionContext executionContext) throws CoreException, LanguageException;
}
