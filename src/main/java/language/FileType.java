package language;


/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 *         <p>
 *         Les extensions de fichiers supportées
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

