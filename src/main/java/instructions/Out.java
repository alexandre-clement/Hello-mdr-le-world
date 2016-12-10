package instructions;

import core.ExecutionContext;
import core.Instructions;
import exception.CoreException;
import exception.LanguageException;

/**
 * @author Alexandre Clement
 *         Created the 25/11/2016.
 */
public class Out implements Executable
{

    @Override
    public Instructions getInstructions()
    {
        return Instructions.OUT;
    }

    @Override
    public void execute(ExecutionContext executionContext) throws CoreException, LanguageException
    {
        executionContext.out((char) executionContext.printValue());
    }
}
