package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.CoreException;
import exception.LanguageException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 */
public interface Executable
{
    Instructions getInstructions();
    void execute(ExecutionContext executionContext) throws CoreException, LanguageException;
}
