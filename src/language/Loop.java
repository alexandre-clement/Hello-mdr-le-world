package language;


/**
 * @author SmartCoding
 *         Created the 13 octobre 2016.
 */
public abstract class Loop implements Instruction {
    /**
     * The associated Loop object, null if it doesn't exist
     */
    private Loop associatedLoopObject;

    /**
     * @param associatedLoopObject the associated object to set
     */
    void setAssociatedLoopObject(Loop associatedLoopObject) {
        this.associatedLoopObject = associatedLoopObject;
    }

    /**
     * @return the associated Loop object with this one
     */
    Loop getAssociatedLoopObject() {
        return associatedLoopObject;
    }
}
