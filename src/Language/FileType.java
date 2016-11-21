package Language;


/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 */
public enum FileType {
    BF(".bf"),
    BMP(".bmp");

    private String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}

