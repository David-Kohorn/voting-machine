/**
 * Class for exception when candidate is nominated more than once
 */

public class AlreadyNominatedException extends Exception {

    /** Tells the user the candidate has already been nominated
     *
     * @param name the name of already nominated candidate
     */
    public AlreadyNominatedException(String name) {
        super(name + " has already been nominated.");
    }
}
