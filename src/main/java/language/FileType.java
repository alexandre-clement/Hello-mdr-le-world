package language;


/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 */
public enum FileType
{
    BF(".bf"),
    BMP(".bmp");

    private final String extension;

    FileType(String extension)
    {
        this.extension = extension;
    }

    public String getExtension()
    {
        return extension;
    }
}

