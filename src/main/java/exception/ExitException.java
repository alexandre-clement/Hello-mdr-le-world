package exception;

/**
 * Exception comportant un exit code
 *
 * @author Alexandre Clement
 * @since 11/11/2016.
 */
public class ExitException extends Exception
{
    /**
     * L'exit code
     */
    private final int exit;
    /**
     * La classe source
     */
    private final String sourceClass;
    /**
     * La méthode source
     */
    private final String sourceMethod;

    /**
     * Construit une exception à partir d'un message
     *
     * @param exit         le code de sortie
     * @param sourceClass  la classe lançant l'exception
     * @param sourceMethod la méthode lançant l'exception
     * @param message      le message
     */
    public ExitException(int exit, String sourceClass, String sourceMethod, String message)
    {
        super(message);
        this.exit = exit;
        this.sourceClass = sourceClass;
        this.sourceMethod = sourceMethod;
    }

    /**
     * Construit une exception à partir d'une exception
     *
     * @param exit         le code de sortie
     * @param sourceClass  la classe lançant l'exception
     * @param sourceMethod la méthode lançant l'exception
     * @param throwable    l'exception
     */
    public ExitException(int exit, String sourceClass, String sourceMethod, Throwable throwable)
    {
        this(exit, sourceClass, sourceMethod, throwable.getMessage());
    }

    /**
     * Récupère le code de sortie
     *
     * @return l'exit code
     */
    public int getExit()
    {
        return exit;
    }

    /**
     * Récupère la classe source
     *
     * @return la classe source
     */
    public String getSourceClass()
    {
        return sourceClass;
    }

    /**
     * Récupère la méthode source
     *
     * @return la méthode source
     */
    public String getSourceMethod()
    {
        return sourceMethod;
    }
}
