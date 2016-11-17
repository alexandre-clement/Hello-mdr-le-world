package Language;


/**
 * @author Alexandre Clement
 *         Created the 17/11/2016.
 */
public enum FileType {
    Bf(".bf"),
    Bmp(".bmp");

    private String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}

