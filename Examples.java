import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

public class Examples {
    ElectionData eData;

    public Examples() {
        eData = new ElectionData(new MostFirstVotesStrategy());
    }

    @Test
    public void testOneVote() {
        try {
            this.eData.nominateCandidate("gompei");
            this.eData.nominateCandidate("husky");
            this.eData.nominateCandidate("bristaco");
            this.eData.submitVote("gompei", "husky", "bristaco");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(Optional.of("gompei"), this.eData.calculateWinner());
    }

    @Test
    public void testMostAgreeableStrategy() {
        eData.setStrategy(new MostAgreeableStrategy());
        try {
            this.eData.nominateCandidate("gompei");
            this.eData.nominateCandidate("husky");
            this.eData.nominateCandidate("bristaco");
            this.eData.submitVote("gompei", "husky", "bristaco");
            this.eData.submitVote("gompei", "husky", "bristaco");
            this.eData.submitVote("husky", "gompei", "bristaco");
            this.eData.submitVote("husky", "gompei", "bristaco");


        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(Optional.of("bristaco"), this.eData.calculateWinner());
    }

    @Test(expected=AlreadyNominatedException.class)
    public void testCandidateAlreadyNominated() throws AlreadyNominatedException{
        this.eData.nominateCandidate("gompei");
        this.eData.nominateCandidate("gompei");

        fail("did not throw exception properly");
    }

    @Test(expected=CandidateNotNominatedException.class)
    public void testCandidateNotNominated() throws CandidateNotNominatedException, MoreThanOnceException{
        this.eData.submitVote("Kevin", "gompei", "husky");

        fail("did not throw exception properly");
    }

    @Test(expected=MoreThanOnceException.class)
    public void testMoreThanOnceVoted() throws CandidateNotNominatedException, MoreThanOnceException, AlreadyNominatedException{
        this.eData.nominateCandidate("gompei");
        this.eData.nominateCandidate("husky");
        this.eData.submitVote("gompei", "gompei", "husky");

        fail("did not throw exception properly");
    }

    @Test
    public void testMostFirstVotesBug() {
        try {
            this.eData.nominateCandidate("gompei");
            this.eData.nominateCandidate("husky");
            this.eData.nominateCandidate("bristaco");
            this.eData.submitVote("gompei", "husky", "bristaco");
            this.eData.submitVote("gompei", "husky", "bristaco");
            this.eData.submitVote("husky", "gompei", "bristaco");
            this.eData.submitVote("bristaco", "gompei", "husky");


        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertEquals(Optional.empty(), this.eData.calculateWinner());
    }

    @Test
    public void testGetCandidatesBug() {
        HashSet<String> candidates = new HashSet<>();
        candidates.add("gompei");
        candidates.add("husky");
        candidates.add("bristaco");
        try {
            this.eData.nominateCandidate("gompei");
            this.eData.nominateCandidate("husky");
            this.eData.nominateCandidate("bristaco");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        this.eData.getCandidates().add("Billy");
        assertEquals(candidates, this.eData.getCandidates());
    }



}