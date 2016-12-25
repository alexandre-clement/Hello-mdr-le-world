package exception;

/**
 * Exception lors de l'execution du programme.
 *
 * @author Alexandre Clement
 * @see ExitException
 * @since 16/11/2016.
 */
class CoreException extends ExitException
{
    /**
     * Creer une exception lorsque l'execution du programme rencontre une erreur.
     *
     * @param exit         le code de sortie
     * @param sourceClass  la classe source
     * @param sourceMethod la methode source
     * @param message      le message a affiche
     * @param instruction  l'instruction lorsque l'exception s'est produite
     * @param pointer      la position du pointeur memoire lorsque l'exception s'est produite
     */
    CoreException(int exit, String sourceClass, String sourceMethod, String message, int instruction, int pointer)
    {
        super(exit, sourceClass, sourceMethod, message + " at instructions " + instruction + ", memory pointer was at " + pointer);
    }
}
