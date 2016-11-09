package interpreter;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
abstract class UniqueOption extends Argument implements Option {
    UniqueOption(String name) {
        super(name);
    }
}
