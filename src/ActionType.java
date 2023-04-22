import java.util.Random;


public enum ActionType {
    PASS,
    SHOOT,
    DRIVE,
    POST_UP;

    public static ActionType determineNextAction(Player ballHandler, Team offense, Team defense, double gameClockRemaining, int quarter) {
        // Create a random number generator for determining the action
        Random random = new Random();

        // Calculate chances for each action based on player attributes
        double passChance = ballHandler.getPassIQ() * 0.8 + ballHandler.getPassAccuracy() * 0.2;
        double shootChance = ballHandler.getShotIQ() * 0.8 + ballHandler.getOffensiveConsistency() * 0.2;
        double driveChance = ballHandler.getLayup() * 0.4 + ballHandler.getDunk() * 0.4 + ballHandler.getSpeedWithBall() * 0.2;
        double postUpChance = ballHandler.getPostFade() * 0.7 + ballHandler.getHands() * 0.3;

        // Adjust chances based on game clock, shot clock, and quarter using the urgency factor
        double urgencyFactor = calculateUrgencyFactor(offense, defense, gameClockRemaining, quarter);
        shootChance *= urgencyFactor;
        driveChance *= urgencyFactor;
        postUpChance *= urgencyFactor;

        // Normalize chances to get probabilities for each action
        double totalChance = passChance + shootChance + driveChance + postUpChance;
        double passProbability = passChance / totalChance;
        double shootProbability = shootChance / totalChance;
        double driveProbability = driveChance / totalChance;
        double postUpProbability = postUpChance / totalChance;

        // Generate a random number to determine the action based on probabilities
        double actionRandom = random.nextDouble();

        // Return the action based on the random number and probabilities
        if (actionRandom <= passProbability) {
            return ActionType.PASS;
        } else if (actionRandom <= passProbability + shootProbability) {
            return ActionType.SHOOT;
        } else if (actionRandom <= passProbability + shootProbability + driveProbability) {
            return ActionType.DRIVE;
        } else {
            return ActionType.POST_UP;
        }
    }

    // Function to calculate the urgency factor based on score difference, game clock, and quarter
    private static double calculateUrgencyFactor(Team offense, Team defense, double gameClockRemaining, int quarter) {
        // Calculate score difference
        int scoreDifference = offense.getScore() - defense.getScore();
        // Default urgency factor is 1 (no change in action chances)
        double urgencyFactor = 1.0;

        // Adjust urgency factor based on score difference and game clock in the last quarter
        if (quarter == 4 && gameClockRemaining < 120) { // Last 2 minutes of the game
            if (scoreDifference < -8) {
                urgencyFactor = 1.5; // Team is down by more than 8 points, increase urgency
            } else if (scoreDifference > 8) {
                urgencyFactor = 0.7; // Team is up by more than 8 points, decrease urgency
            }
        }

        // Return the calculated urgency factor
        return urgencyFactor;
    }

}
