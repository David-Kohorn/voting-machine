/**
 * Class for exception when candidate is voted for but not nominated
 */
public class CandidateNotNominatedException extends Exception{

    private String candidate;

    /** Tells user the person has not been nominated and saves their name
     *
     * @param name the name of person who isn't a candidate
     */
    public CandidateNotNominatedException(String name) {
        super(name + " is not one of the candidates.");
        this.candidate = name;
    }

    public String getCandidate() {
        return this.candidate;
    }
}
