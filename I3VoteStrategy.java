
import java.util.HashMap;
import java.util.Optional;

/**
 * An interface for a modular "vote for 3 people" strategy
 */
public interface I3VoteStrategy {

    /** Converts current vote count into a winner if possible
     *
     * @param votes a HashMap with key of type string and values of type Votes
     * @return a string of the winner if someone could win
     */
    Optional<String> calculateWinner(HashMap<String, Votes> votes);
}
