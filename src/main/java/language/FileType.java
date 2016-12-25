package language;


/**
 * Les extensions de fichiers supportees.
 *
 * @author Alexandre Clement
 * @since 17/11/2016.
 */
public enum FileType
{
    /**
     * Fichier texte standard .bf
     */
    BF(".bf"),
    /**
     * Fichier image bitmap .bmp
     */
    BMP(".bmp");

    /**
     * L'extension supportee.
     */
    private final String extension;

    FileType(String extension)
    {
        this.extension = extension;
    }

    /**
     * Recupere l'extension associer.
     *
     * @return l'extension.
     */
    public String getExtension()
    {
        return extension;
    }
}

