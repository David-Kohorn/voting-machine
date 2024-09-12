import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Strategy class that calculates winner based on the most votes in a single category for candidates
 */

public class MostAgreeableStrategy implements I3VoteStrategy{
    /**
     * Converts current vote count into a winner if possible
     *
     * @param votes a HashMap with key of type string and values of type Votes
     * @return a string of the winner if someone could win
     */
    @Override
    public Optional<String> calculateWinner(HashMap<String, Votes> votes) {
        String topCandidate = "";
        int topCandidateVoteCount = 0;

        for (Map.Entry<String, Votes> candidate : votes.entrySet()) {
            if (candidate.getValue().getFirstVotes() > topCandidateVoteCount) {
                topCandidate = candidate.getKey();
                topCandidateVoteCount = candidate.getValue().getFirstVotes();
            }
            if (candidate.getValue().getSecondVotes() > topCandidateVoteCount) {
                topCandidate = candidate.getKey();
                topCandidateVoteCount = candidate.getValue().getSecondVotes();
            }
            if (candidate.getValue().getThirdVotes() > topCandidateVoteCount) {
                topCandidate = candidate.getKey();
                topCandidateVoteCount = candidate.getValue().getThirdVotes();
            }
        }

        return Optional.of(topCandidate);
    }
}
