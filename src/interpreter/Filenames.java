package interpreter;

/**
 * @author Alexandre Clement
 *         Created the 08/11/2016.
 */
public enum Filenames {
    source,
    input,
    output;

    private String name = null;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
