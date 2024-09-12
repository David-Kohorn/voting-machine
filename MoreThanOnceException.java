/**
 * Class for exception when candidate is voted for more than once in a single vote
 */

public class MoreThanOnceException extends Exception{

    /** Tells the user they have voted for the same person more than once in a single vote
     *
     * @param name name of the candidate voted for multiple
     */
    public MoreThanOnceException(String name) {
        super(name + " has been voted for more than once in a single vote.");
    }

}
