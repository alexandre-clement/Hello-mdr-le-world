package exception;

/**
 * Exception comportant un exit code.
 *
 * @author Alexandre Clement
 * @since 11/11/2016.
 */
public class ExitException extends Exception
{
    /**
     * L'exit code.
     */
    private final int exit;
    /**
     * La classe source.
     */
    private final String sourceClass;
    /**
     * La methode source.
     */
    private final String sourceMethod;

    /**
     * Construit une exception a partir d'un message.
     *
     * @param exit         le code de sortie
     * @param sourceClass  la classe lancant l'exception
     * @param sourceMethod la methode lancant l'exception
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
     * Construit une exception a partir d'une exception.
     *
     * @param exit         le code de sortie
     * @param sourceClass  la classe lancant l'exception
     * @param sourceMethod la methode lancant l'exception
     * @param throwable    l'exception
     */
    public ExitException(int exit, String sourceClass, String sourceMethod, Throwable throwable)
    {
        this(exit, sourceClass, sourceMethod, throwable.getMessage());
    }

    /**
     * Recupere le code de sortie.
     *
     * @return l'exit code
     */
    public int getExit()
    {
        return exit;
    }

    /**
     * Recupere la classe source.
     *
     * @return la classe source
     */
    public String getSourceClass()
    {
        return sourceClass;
    }

    /**
     * Recupere la methode source.
     *
     * @return la methode source
     */
    public String getSourceMethod()
    {
        return sourceMethod;
    }
}
