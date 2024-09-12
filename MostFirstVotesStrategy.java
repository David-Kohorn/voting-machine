import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Strategy class that calculates winner based on the most first votes for candidates
 */

public class MostFirstVotesStrategy implements I3VoteStrategy{

    /** Converts current vote count into a winner if possible
     *
     * @param votes a HashMap with key of type string and values of type Votes
     * @return a string of the winner if someone could win
     */
    @Override
    public Optional<String> calculateWinner(HashMap<String, Votes> votes) {
        String topCandidate = "";
        int totalFirstVotes = 0;
        int topCandidateFirstVoteCount = 0;

        for (Map.Entry<String, Votes> candidate : votes.entrySet()) {
            totalFirstVotes += candidate.getValue().getFirstVotes();
            if (candidate.getValue().getFirstVotes() > topCandidateFirstVoteCount) {
                topCandidate = candidate.getKey();
                topCandidateFirstVoteCount = candidate.getValue().getFirstVotes();
            }
        }

        if (topCandidateFirstVoteCount > totalFirstVotes / 2) {
            return Optional.of(topCandidate);
        }
        return Optional.empty();
    }
}
