import java.util.Optional;
import java.util.Scanner;

public class VotingMachine {

    /** ElectionData object for this voting machine */
    private ElectionData eData = new ElectionData(new MostFirstVotesStrategy());

    /** Scanner to take in voting data from user */
    private Scanner scanner;

    public VotingMachine() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        VotingMachine vm = new VotingMachine();

        String input = " ";

        while (!(input.charAt(0) == 'q')) {
            vm.printCandidates();
            input = vm.getMainAction();
            if (input.length() == 0) {
                input = " ";
            }

            if (input.charAt(0) == 'n') {
                vm.doNomination();

            } else if (input.charAt(0) == 'v') {
                vm.doVote();
            } else if (input.charAt(0) == 's') {
                vm.doStrategyChange();
            } else if (input.charAt(0) == 'w') {
                vm.doWinCalculation();
            }
        }
        System.out.println("Thanks for voting!");
    }

    private void printCandidates() {
        String str = "Current candidates are: [";
        for (String name : this.eData.getCandidates()) {
            str += name + ", ";
        }
        if (this.eData.getCandidates().size() > 0) {
            str = str.substring(0, str.length() - 2);
        }
        System.out.println(str + "]");
    }

    private String getMainAction() {
        System.out.println("Do you want to [n]ominate someone, [v]ote for someone, change winner [s]trategy, see who [w]on, or [q]uit?");
        return this.scanner.nextLine();
    }

    private void doNomination() {
        System.out.println("Enter the name of the nominee: ");
        String input = scanner.nextLine();
        if (input.trim().length() > 0) {
            try {
                this.eData.nominateCandidate(input);
            } catch (AlreadyNominatedException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("No name was entered.");
        }

    }

    private void doVote() {
        System.out.println("Who is your first choice?");
        String vote1 = scanner.nextLine();
        System.out.println("Who is your second choice?");
        String vote2 = scanner.nextLine();
        System.out.println("Who is your third choice?");
        String vote3 = scanner.nextLine();
        try {
            this.eData.submitVote(vote1, vote2, vote3);
        } catch (MoreThanOnceException e) {
            System.out.println(e.getMessage());
        } catch (CandidateNotNominatedException e) {
            System.out.println(e.getMessage());
            System.out.println("Vote not counted, but would you like to nominate them for the ballot? [y]/[n]");
            String input = scanner.nextLine();
            try {
                if (input.charAt(0) == 'y'){
                    try {
                        this.eData.nominateCandidate(e.getCandidate());
                    } catch (AlreadyNominatedException ignored) {

                    }
                }
            } catch (IndexOutOfBoundsException Ignore) {

            }

        }
    }
    private void doStrategyChange() {
        System.out.println("Which strategy would you like to use? most [f]irst votes or most [a]greeable?");
        String input = scanner.nextLine();
        try {
            if (input.charAt(0) == 'f') {
                this.eData.setStrategy(new MostFirstVotesStrategy());
            }
            else if (input.charAt(0) == 'a') {
                this.eData.setStrategy(new MostAgreeableStrategy());
            }
            else {
                System.out.print("Not a strategy.");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not a strategy.");
        }
    }

    private void doWinCalculation() {
        Optional<String> winner = this.eData.calculateWinner();
        if (winner.isPresent()) {
            System.out.println("The winner is: " + winner.get());
        } else {
            System.out.println("No clear winner under the current strategy.");
        }
    }

}
