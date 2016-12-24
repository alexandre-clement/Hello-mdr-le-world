package language;


/**
 * Les extensions de fichiers supportées
 *
 * @author Alexandre Clement
 * @since 17/11/2016.
 */
public enum FileType
{
    BF(".bf"),
    BMP(".bmp");

    /**
     * L'extension supportée
     */
    private final String extension;

    FileType(String extension)
    {
        this.extension = extension;
    }

    /**
     * @return l'extension
     */
    public String getExtension()
    {
        return extension;
    }
}

