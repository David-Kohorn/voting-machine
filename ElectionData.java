import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Class that holds info and methods for dealing with and modifying election data
 */
public class ElectionData {

    /** Stores the votes for every nominated candidate */
    private HashMap<String, Votes> candidates;

    /** Stores the current winner calculation strategy */
    private I3VoteStrategy strategy;



    /** ElectionData constructor with one parameter
     *
     * @param strategy an I3VoteStrategy for the election data to use
     */
    public ElectionData(I3VoteStrategy strategy) {
        this.candidates = new HashMap<String, Votes>();
        this.strategy = strategy;
    }

    /** Switches the strategy for this ElectionData
     *
     * @param strategy an I3VoteStrategy to switch to
     */
    public void setStrategy(I3VoteStrategy strategy) {
        this.strategy = strategy;
    }

    /** Gets the candidates in the election data
     *
     * @return a list of candidates in this election
     */
    public Set<String> getCandidates() {
        return cloneHashMap().keySet();
    }

    /** Adds the person to the ballot by putting them in the hashmap with 0 votes.
     *
     * @param candidate the name of the candidate
     * @throws AlreadyNominatedException throws an exception if the person is already on the ballot
     */
    public void nominateCandidate(String candidate) throws AlreadyNominatedException {
        if (this.candidates.containsKey(candidate)) {
            throw new AlreadyNominatedException(candidate);
        }
        else {
            this.candidates.put(candidate, new Votes(0, 0, 0));
        }
    }

    /** Increases the candidates votes, respectively
     *
     * @param first name of top candidate
     * @param second name of second candidate
     * @param third name of third candidate
     * @throws CandidateNotNominatedException throws a CandidateNotNominatedException  if any of the choices are not on the ballot
     * @throws MoreThanOnceException throws a MoreThanOnceException if any of the choices are duplicates
     */
    public void submitVote(String first, String second, String third) throws CandidateNotNominatedException, MoreThanOnceException {
        //Check for not nominated errors
        if (!this.candidates.containsKey(first)) {
            throw new CandidateNotNominatedException(first);
        }
        if (!this.candidates.containsKey(second)) {
            throw new CandidateNotNominatedException(second);
        }
        if (!this.candidates.containsKey(third)) {
            throw new CandidateNotNominatedException(third);
        }

        //Check for more than once error
        if (first.equals(second)) {
            throw new MoreThanOnceException(first);
        }
        if (first.equals(third)) {
            throw new MoreThanOnceException(first);
        }
        if (third.equals(second)) {
            throw new MoreThanOnceException(third);
        }

        //Add votes
        this.candidates.get(first).voteFirst();
        this.candidates.get(second).voteSecond();
        this.candidates.get(third).voteThird();
    }

    /** Produces the name of the candidate if a clear winner was chosen;
     *  produces Optional.empty() if there is no clear winner based on the current strategy.
     *
     * @return Optional.of(condidate) if a winner is found, or Optional.empty() if not
     */
    public Optional<String> calculateWinner() {
        return this.strategy.calculateWinner(cloneHashMap());
    }

    private HashMap<String, Votes> cloneHashMap() {
        HashMap<String, Votes> cloned = new HashMap<String, Votes>();
        for (Map.Entry<String, Votes> candidate : this.candidates.entrySet()) {
            cloned.put(candidate.getKey(), candidate.getValue());
        }
        return cloned;
    }
}
