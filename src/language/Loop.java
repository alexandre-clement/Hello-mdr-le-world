package language;


/**
 * @author Alexandre Clement
 *         Created the 13 octobre 2016.
 */
public class Loop {
    private Loop associatedLoopObject;

    void setAssociatedLoopObject(Loop associatedLoopObject) {
        this.associatedLoopObject = associatedLoopObject;
    }

    Loop getAssociatedLoopObject() {
        return associatedLoopObject;
    }
}
