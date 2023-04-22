import java.util.*;
import java.util.stream.Collectors;

class Game {
    private static final int QUARTERS = 4;
    private static final int QUARTER_LENGTH_SECONDS = 10 * 60;
    private static final int SHOT_CLOCK_SECONDS = 24;
    Team teamA;
    Team teamB;
    int currentQuarter;
    private double gameClock;
    private double shotClock;
    private boolean teamAOnOffense;


    public Game(Team teamA, Team teamB){
        this.teamA = teamA;
        this.teamB = teamB;
        this.currentQuarter = 1;
        this.gameClock = QUARTER_LENGTH_SECONDS;
        this.shotClock = SHOT_CLOCK_SECONDS;
        this.teamAOnOffense = true;
    }

    public void simulateGame() {
        while (currentQuarter <= QUARTERS) {
            while (gameClock > 0) {
                simulatePossession();
                resetShotClock();
                teamAOnOffense = !teamAOnOffense; // Switch the team on offense
            }
            advanceToNextQuarter();
        }
    }

    private void resetShotClock() {
        shotClock = SHOT_CLOCK_SECONDS;
    }

    private void advanceToNextQuarter() {
        if (currentQuarter < QUARTERS) {
            currentQuarter++;
            gameClock = QUARTER_LENGTH_SECONDS;
        } else {
            endGame();
        }
    }

    public void endGame() {
        int homeTeamScore = teamA.getScore();
        int awayTeamScore = teamB.getScore();

        System.out.println("Final Score:");
        System.out.println(teamA.getName() + ": " + homeTeamScore);
        System.out.println(teamB.getName() + ": " + awayTeamScore);

        if (homeTeamScore > awayTeamScore) {
            System.out.println(teamA.getName() + " wins!");
        } else if (homeTeamScore < awayTeamScore) {
            System.out.println(teamB.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }


    public Player selectInitialBallHandler(Team team) {
        Player initialBallHandler = null;
        double maxBallHandling = Double.NEGATIVE_INFINITY;

        for (Player player : team.getPlayers()) {
            if (player.getBallHandle() > maxBallHandling) {
                maxBallHandling = player.getBallHandle();
                initialBallHandler = player;
            }
        }

        return initialBallHandler;
    }

    // Constructor and other methods
    public void simulatePossession() {
        boolean possessionOver = false;
        Team offense = teamAOnOffense ? teamA : teamB;
        Team defense = teamAOnOffense ? teamB : teamA;
        Player ballHandler = selectInitialBallHandler(offense);
        Random random = new Random();

        while (!possessionOver && shotClock > 0 && gameClock > 0) {
            // Determine the next action based on player attributes, score, shot clock, and game clock
            ActionType nextAction = ActionType.determineNextAction(ballHandler, offense, defense, shotClock, currentQuarter);

            switch (nextAction) {
                case PASS:
                    // Select a teammate to pass the ball to, considering game clock and quarter
                    Player targetTeammate = selectPassTarget(ballHandler, offense);

                    // Select a defender to contest the pass
                    Player passDefender = selectPassDefender(ballHandler, defense.getPlayers());

                    // Attempt the pass and determine if it's successful
                    boolean passSuccessful = attemptPass(ballHandler, targetTeammate, passDefender);

                    if (passSuccessful) {
                        ballHandler = targetTeammate;
                    } else {
                        possessionOver = true; // Turnover occurs
                    }

                    // Deduct time for the pass action
                    int timeDeducted = 1 + random.nextInt(2); // Pass takes 1-2 seconds
                    shotClock -= timeDeducted;
                    gameClock -= timeDeducted;
                    break;

                case SHOOT:
                    // Select a defender to contest the shot
                    Player shotDefender = selectShotDefender(ballHandler, defense.getPlayers());

                    // Determine the type of shot to take, considering game clock and quarter
                    int scoreDiff = offense.getScore()-defense.getScore();
                    ShotType shotType = determineShotType(ballHandler, currentQuarter, gameClock, scoreDiff);

                    // Attempt the shot and determine if it's successful
                    boolean shotSuccessful = attemptShot(ballHandler, shotType, shotDefender);

                    if (shotSuccessful) {
                        possessionOver = true; // Shot made, possession ends
                    } else {
                        // Attempt to rebound the missed shot
                        Player rebounder = attemptRebound(ballHandler, offense.getPlayers(), defense.getPlayers());
                        if (rebounder.getTeam() == defense) {
                            possessionOver = true; // Defensive rebound, possession ends
                        } else {
                            ballHandler = rebounder;
                        }
                    }

                    // Deduct time for the shoot action
                    timeDeducted = 2 + random.nextInt(2);
                    shotClock -= timeDeducted;
                    gameClock -= timeDeducted;
                    break;

                case DRIVE:
                    // Select a defender to contest the drive
                    Player driveDefender = selectDriveDefender(ballHandler,defense.getPlayers());

                    // Attempt the drive and determine if it's successful
                    boolean driveSuccessful = attemptDrive(ballHandler, driveDefender);

                    if (driveSuccessful) {
                        possessionOver = true; // Layup or dunk made, possession ends
                    } else {
                        // Attempt to rebound the missed shot
                        Player rebounder = attemptRebound(ballHandler, offense.getPlayers(), defense.getPlayers());
                        if (rebounder.getTeam() == defense) {
                            possessionOver = true; // Defensive rebound, possession ends
                        } else {
                            ballHandler = rebounder;
                        }
                    }

                    // Deduct time for the drive action
                    int timeDeductedForDrive = 2 + random.nextInt(3); // Drive takes 2-4 seconds
                    shotClock -= timeDeductedForDrive;
                    gameClock -= timeDeductedForDrive;
                    break;

                case POST_UP:
                    // Select a defender to contest the post-up
                    Player postDefender = selectPostDefender(ballHandler, defense.getPlayers());

                    // Determine the post move to perform, considering game clock and quarter
                    PostMoveType postMove = determinePostMove(ballHandler, postDefender);

                    // Attempt the post move and determine if it's successful
                    boolean postMoveSuccessful = attemptPostMove(ballHandler, postDefender, postMove);

                    if (postMoveSuccessful) {
                        possessionOver = true; // Post move made, possession ends
                    } else {
                        // Attempt to rebound the missed shot
                        Player rebounder = attemptRebound(ballHandler, offense.getPlayers(), defense.getPlayers());
                        if (rebounder.getTeam() == defense) {
                            possessionOver = true; // Defensive rebound, possession ends
                        } else {
                            ballHandler = rebounder;
                        }
                    }

                    // Deduct time for the post-up action
                    int timeDeductedForPostUp = 3 + random.nextInt(3); // Post-up takes 3-5 seconds
                    shotClock -= timeDeductedForPostUp;
                    gameClock -= timeDeductedForPostUp;
                    break;

                default:
                    throw new IllegalStateException("Invalid action type: " + nextAction);
            }
        }
    }

    // Select a target for the pass based on teammates' positions, abilities, etc.
    private Player selectPassTarget(Player ballHandler, Team offense) {
        List<Player> teammates = offense.getPlayers();
        teammates.remove(ballHandler); // Remove the ball handler from the list of potential targets

        HeadCoach headCoach = offense.getHeadCoach();
        double passPreference = headCoach.getPassPreference(); // Value between 0 and 1

        // Calculate the weighted openness score for each teammate based on the passer's passVision
        List<Double> weightedOpennessScores = new ArrayList<>();
        double totalWeightedOpenness = 0.0;
        for (Player teammate : teammates) {
            double openness = calculateOpenness(teammate); // Function to calculate the openness score for a player
            double passQuality = calculatePassQuality(ballHandler.getxPosition(), ballHandler.getyPosition(), teammate.getxPosition(), teammate.getyPosition(), headCoach); // The farther the pass, the lower the pass quality
            double shotPotential = (1 - passPreference) * teammate.getCloseShot() + passPreference * teammate.getThreePointShot();
            double weightedOpenness = openness * (1 + ballHandler.getPassVision() / 100.0) * passQuality * shotPotential;
            weightedOpennessScores.add(weightedOpenness);
            totalWeightedOpenness += weightedOpenness;
        }

        // Normalize the weighted openness scores to get probabilities
        double finalTotalWeightedOpenness = totalWeightedOpenness;
        List<Double> passProbabilities = weightedOpennessScores.stream()
                .map(weightedOpenness -> weightedOpenness / finalTotalWeightedOpenness)
                .collect(Collectors.toList());

        // Select the pass target based on the probabilities
        double randomValue = new Random().nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < passProbabilities.size(); i++) {
            cumulativeProbability += passProbabilities.get(i);
            if (randomValue <= cumulativeProbability) {
                return teammates.get(i);
            }
        }

        // If for some reason a target was not selected, return the first teammate in the list as a fallback
        return teammates.get(0);
    }

    private double calculateOpenness(Player player) {
        // Implement a function to calculate the openness score for a player
        // This can be based on the player's position, defender's position, team strategy, etc.
        // For now, we'll return a random value as a placeholder
        return new Random().nextDouble();
    }

    // Simulate the pass attempt
    private boolean attemptPass(Player ballHandler, Player passTarget, Player passDefender) {
        HeadCoach offenseCoach = ballHandler.getTeam().getHeadCoach();
        HeadCoach defenseCoach = passDefender.getTeam().getHeadCoach();

        double passQuality = calculatePassQuality(ballHandler.getxPosition(), ballHandler.getyPosition(), passTarget.getxPosition(),passTarget.getyPosition(),ballHandler.getTeam().getHeadCoach()); // A function to calculate the pass quality
        double stealPotential = calculateStealPotential(passDefender, ballHandler); // A function to calculate the steal potential

        double passSuccessThreshold = offenseCoach.getPassSuccessThreshold(); // Value between 0 and 1, the higher it is, the more risk-averse the coach
        double stealSuccessThreshold = defenseCoach.getStealSuccessThreshold(); // Value between 0 and 1, the higher it is, the more risk-averse the coach

        boolean passSuccess = passQuality > passSuccessThreshold;
        boolean stealSuccess = stealPotential > stealSuccessThreshold;

        // Unsuccessful pass, either intercepted or simply failed
        return passSuccess && !stealSuccess; // Successful pass
    }

    public double calculatePassQuality(int passerX, int passerY, int receiverX, int receiverY, HeadCoach headCoach) {
        // Calculate the distance between passer and receiver
        double distance = Math.sqrt(Math.pow(receiverX - passerX, 2) + Math.pow(receiverY - passerY, 2));

        // Normalize the distance to a value between 0 and 1
        double maxCourtDistance = Math.sqrt(Math.pow(94, 2) + Math.pow(50, 2));
        double normalizedDistance = distance / maxCourtDistance;

        // Calculate pass quality based on distance and coach preferences
        // Assume that coach preferences are weighted and sum up to 1.

        return (1 - normalizedDistance) * (headCoach.getMidRangePreference() + headCoach.getThreePointPreference() + headCoach.getPostUpPreference());
    }

    private double calculateStealPotential(Player passDefender, Player passer) {
        double distance = Math.sqrt(Math.pow(passDefender.getxPosition() - passer.getxPosition(), 2) + Math.pow(passDefender.getyPosition() - passer.getyPosition(), 2));
        return passDefender.getSteal() * (1 - distance / 100.0);
    }

    // Determine the type of shot being attempted (close, mid-range, or three-pointer)
    public ShotType determineShotType(Player player, int currentQuarter, double timeRemainingInGame, int scoreDifference) {
        HeadCoach headCoach = player.getTeam().getHeadCoach();
        double dunkChance = headCoach.getDunkPreference() * player.getDunk() / 99.0;
        double layupChance = headCoach.getLayupPreference() * player.getLayup() / 99.0;
        double midRangeChance = headCoach.getMidRangePreference() * player.getMidRangeShot() / 99.0;
        double threePointChance = headCoach.getThreePointPreference() * player.getThreePointShot() / 99.0;
        double postUpChance = headCoach.getPostUpPreference() * player.getPostFade() / 99.0;

        // Adjust shot selection based on game situation
        if (currentQuarter == 4 && timeRemainingInGame <= 120) { // Last 2 minutes of the 4th quarter
            if (scoreDifference > 0) { // Team is leading
                // Increase the preference for layups and dunks to increase the chance of an easy basket
                dunkChance *= 1.5;
                layupChance *= 1.5;
            } else if (scoreDifference < 0) { // Team is trailing
                if (scoreDifference >= -2) { // If trailing by 2 points or less, prioritize layups and dunks
                    dunkChance *= 1.5;
                    layupChance *= 1.5;
                } else { // If trailing by more than 2 points, increase the preference for three-pointers
                    threePointChance *= 1.5;
                }
            }
        }

        double totalChance = dunkChance + layupChance + midRangeChance + threePointChance + postUpChance;
        double randomValue = Math.random() * totalChance;

        ShotType selectedShotType;
        if (randomValue <= dunkChance) {
            selectedShotType = ShotType.DUNK;
        } else if (randomValue <= dunkChance + layupChance) {
            selectedShotType = ShotType.LAYUP;
        } else if (randomValue <= dunkChance + layupChance + midRangeChance) {
            selectedShotType = ShotType.MID_RANGE;
        } else if (randomValue <= dunkChance + layupChance + midRangeChance + threePointChance) {
            selectedShotType = ShotType.THREE_POINT;
        } else {
            selectedShotType = ShotType.POST_UP;
        }

        double minTimeToShotSpot = Double.MAX_VALUE;

        for (ShotType shotType : ShotType.values()) {
            double timeToShotSpot = player.timeToReachShotSpot(shotType);
            if (timeToShotSpot < minTimeToShotSpot) {
                minTimeToShotSpot = timeToShotSpot;
                selectedShotType = shotType;
            }
        }

        // Deduct the time taken to move to the shot spot from the shot clock
        decrementGameClock(minTimeToShotSpot);

        return selectedShotType;
    }

    private void decrementGameClock(double minTimeToShotSpot) {
        this.gameClock -= minTimeToShotSpot;
    }

    // Simulate the shot attempt
    private boolean attemptShot(Player shooter, ShotType shotType, Player shotDefender) {
        // Define the base success rate factors for each shot type
        final double LAYUP_BASE_SUCCESS_RATE = 0.75;
        final double DUNK_BASE_SUCCESS_RATE = 0.9;
        final double CLOSE_SHOT_BASE_SUCCESS_RATE = 0.6;
        final double MID_RANGE_SHOT_BASE_SUCCESS_RATE = 0.45;
        final double THREE_POINT_SHOT_BASE_SUCCESS_RATE = 0.3;
        final double POST_FADE_BASE_SUCCESS_RATE = 0.35;

        // Calculate the base shot success probability based on the shot type, the shooter's attributes, and the base success rate factor
        double shotSuccessProbability;
        switch (shotType) {
            case LAYUP:
                shotSuccessProbability = LAYUP_BASE_SUCCESS_RATE + (shooter.getLayup() / 100.0) * (1 - LAYUP_BASE_SUCCESS_RATE);
                break;
            case DUNK:
                shotSuccessProbability = DUNK_BASE_SUCCESS_RATE + (shooter.getDunk() / 100.0) * (1 - DUNK_BASE_SUCCESS_RATE);
                break;
            case CLOSE_SHOT:
                shotSuccessProbability = CLOSE_SHOT_BASE_SUCCESS_RATE + (shooter.getCloseShot() / 100.0) * (1 - CLOSE_SHOT_BASE_SUCCESS_RATE);
                break;
            case MID_RANGE_SHOT:
                shotSuccessProbability = MID_RANGE_SHOT_BASE_SUCCESS_RATE + (shooter.getMidRangeShot() / 100.0) * (1 - MID_RANGE_SHOT_BASE_SUCCESS_RATE);
                break;
            case THREE_POINT:
                shotSuccessProbability = THREE_POINT_SHOT_BASE_SUCCESS_RATE + (shooter.getThreePointShot() / 100.0) * (1 - THREE_POINT_SHOT_BASE_SUCCESS_RATE);
                break;
            case POST_FADE:
                shotSuccessProbability = POST_FADE_BASE_SUCCESS_RATE + (shooter.getPostFade() / 100.0) * (1 - POST_FADE_BASE_SUCCESS_RATE);
                break;
            default:
                throw new IllegalArgumentException("Invalid shot type");
        }

        // Calculate the impact of the shot defender on the success probability
        double defenderImpact = shotDefender.getPerimeterDefense() * shotDefender.getDefensiveConsistency() / 10000.0;
        double adjustedShotSuccessProbability = Math.max(0, shotSuccessProbability - defenderImpact);

        // Determine if the shot is successful based on the adjusted probability
        double randomValue = new Random().nextDouble();
        return randomValue <= adjustedShotSuccessProbability;
    }

    // Simulate the rebound attempt
    private Player attemptRebound(Player shooter, List<Player> offenseTeam, List<Player> defenseTeam) {
        // Constants for balancing offensive and defensive rebound chances
        final double OFFENSIVE_REBOUND_FACTOR = 0.3;
        final double DEFENSIVE_REBOUND_FACTOR = 0.7;
        final double SHOOTER_REBOUND_PENALTY = 0.5;

        // Calculate the rebound chances for each player
        List<Player> allPlayers = new ArrayList<>(offenseTeam);
        allPlayers.addAll(defenseTeam);
        Map<Player, Double> reboundChances = new HashMap<>();
        for (Player player : allPlayers) {
            double reboundChance;
            if (offenseTeam.contains(player)) {
                reboundChance = player.getOffensiveRebounding() * OFFENSIVE_REBOUND_FACTOR;
                if (player.equals(shooter)) {
                    reboundChance *= SHOOTER_REBOUND_PENALTY;
                }
            } else {
                reboundChance = player.getDefensiveRebounding() * DEFENSIVE_REBOUND_FACTOR;
            }
            reboundChances.put(player, reboundChance);
        }

        // Normalize the rebound chances to get probabilities
        double totalReboundChance = reboundChances.values().stream().mapToDouble(Double::doubleValue).sum();
        Map<Player, Double> reboundProbabilities = reboundChances.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() / totalReboundChance));

        // Select the player who gets the rebound based on the probabilities
        double randomValue = new Random().nextDouble();
        double cumulativeProbability = 0.0;
        for (Map.Entry<Player, Double> entry : reboundProbabilities.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomValue <= cumulativeProbability) {
                return entry.getKey();
            }
        }

        // If for some reason a rebounder was not selected, return a random player as a fallback
        return allPlayers.get(new Random().nextInt(allPlayers.size()));
    }

    // Attempt a drive towards the basket
    private boolean attemptDrive(Player driver, Player driveDefender) {
        // Constants for success factors
        final double DRIVER_SPEED_FACTOR = 0.6;
        final double DRIVER_BALL_HANDLE_FACTOR = 0.4;
        final double DEFENDER_LAT_QUICKNESS_FACTOR = 0.6;
        final double DEFENDER_INTERIOR_DEFENSE_FACTOR = 0.4;

        // Calculate the success probability based on the driver's and defender's attributes
        double driverSuccessFactor = DRIVER_SPEED_FACTOR * driver.getSpeedWithBall() + DRIVER_BALL_HANDLE_FACTOR * driver.getBallHandle();
        double defenderSuccessFactor = DEFENDER_LAT_QUICKNESS_FACTOR * driveDefender.getLateralQuickness() + DEFENDER_INTERIOR_DEFENSE_FACTOR * driveDefender.getInteriorDefense();

        double successProbability = (driverSuccessFactor / (driverSuccessFactor + defenderSuccessFactor));

        // Determine if the drive is successful based on the success probability
        double randomValue = new Random().nextDouble();
        return randomValue <= successProbability;
    }
    public boolean attemptPostMove(Player player, Player postDefender, PostMoveType postMoveType) {
        double postMoveSuccessRate = 0;
        double defenderResistance = 0;

        switch (postMoveType) {
            case HOOK_SHOT:
                postMoveSuccessRate = player.getPostHook() / 99.0;
                defenderResistance = postDefender.getPostDefense() / 99.0;
                break;
            case FADEAWAY:
                postMoveSuccessRate = player.getPostFade() / 99.0;
                defenderResistance = postDefender.getPostDefense() / 99.0;
                break;
            case UP_AND_UNDER:
                postMoveSuccessRate = player.getPostUpAndUnder() / 99.0;
                defenderResistance = postDefender.getPostDefense() / 99.0;
                break;
        }

        // Factor in the defender's resistance
        postMoveSuccessRate *= (1 - defenderResistance);

        return Math.random() < postMoveSuccessRate;
    }

    // Determine the type of post move to be attempted
    private PostMoveType determinePostMove(Player postPlayer, Player postDefender) {
        // Constants for move selection factors
        final double POST_FADE_FACTOR = 0.5;
        final double LAYUP_FACTOR = 0.3;
        final double HOOK_SHOT_FACTOR = 0.2;

        // Calculate the player's propensity for each post move type
        double postFadePropensity = postPlayer.getPostFade() * POST_FADE_FACTOR;
        double layupPropensity = postPlayer.getLayup() * LAYUP_FACTOR;
        double hookShotPropensity = postPlayer.getShotIQ() * HOOK_SHOT_FACTOR;

        // Normalize the propensities to get probabilities
        double totalPropensity = postFadePropensity + layupPropensity + hookShotPropensity;
        double postFadeProbability = postFadePropensity / totalPropensity;
        double layupProbability = layupPropensity / totalPropensity;
        double hookShotProbability = hookShotPropensity / totalPropensity;

        // Determine the post move type based on the probabilities
        double randomValue = new Random().nextDouble();
        if (randomValue <= postFadeProbability) {
            return PostMoveType.POST_FADE;
        } else if (randomValue <= postFadeProbability + layupProbability) {
            return PostMoveType.LAYUP;
        } else {
            return PostMoveType.HOOK_SHOT;
        }
    }

    // Select the player who will defend the pass attempt
    private Player selectPassDefender(Player passer, List<Player> defenseTeam) {
        // Choose the closest defender to the passer
        return getClosestDefender(passer, defenseTeam);
    }

    // Select the player who will defend the shot attempt
    private Player selectShotDefender(Player shooter, List<Player> defenseTeam) {
        // Choose the closest defender to the shooter
        return getClosestDefender(shooter, defenseTeam);
    }

    // Select the player who will defend the drive attempt
    private Player selectDriveDefender(Player driver, List<Player> defenseTeam) {
        // Choose the closest defender to the driver
        return getClosestDefender(driver, defenseTeam);
    }

    // Select the player who will defend the post move attempt
    private Player selectPostDefender(Player postPlayer, List<Player> defenseTeam) {
        // Choose the closest defender to the post player
        return getClosestDefender(postPlayer, defenseTeam);
    }

    // Get the distance between two players
    private double getDistance(Player p1, Player p2) {
        double xDiff = p1.getxPosition() - p2.getxPosition();
        double yDiff = p1.getyPosition() - p2.getyPosition();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    // Get the closest defender to the offensive player
    private Player getClosestDefender(Player offensivePlayer, List<Player> defenseTeam) {
        return defenseTeam.stream()
                .min(Comparator.comparingDouble(defender -> getDistance(offensivePlayer, defender)))
                .orElse(null);
    }

    // Move player towards a target position based on their speed
    private void movePlayerTowards(Player player, double targetX, double targetY) {
        double distanceX = targetX - player.getxPosition();
        double distanceY = targetY - player.getyPosition();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        double speed = player.getSpeed(); // Assuming the Player class has a getSpeed() method
        double moveDistance = Math.min(distance, speed);

        if (distance > 0) {
            player.setxPosition((int) (player.getxPosition() + distanceX / distance * moveDistance));
            player.setyPosition((int) (player.getyPosition() + distanceY / distance * moveDistance));
        }
    }

    private void updatePositionsForPass(Player passer, Player receiver) {
        // Move the passer and receiver towards their desired positions
        movePlayerTowards(passer, passer.getxPosition(), passer.getyPosition());
        movePlayerTowards(receiver, receiver.getxPosition(), receiver.getyPosition());

        // Move other offensive players to create space or cut to the basket
        for (Player player : passer.getTeam().getPlayers()) {
            if (player != passer && player != receiver) {
                // Example: cut to the basket if close to the passer, otherwise move away to create space
                if (getDistance(player, passer) < 10) {
                    movePlayerTowards(player, 47, 25);
                } else {
                    movePlayerTowards(player, player.getxPosition() + 5, player.getyPosition());
                }
            }
        }

        // Move defensive players to follow their assignments or help on the receiver
        for (Player player : receiver.getTeam().getOpponent().getPlayers()) {
            if (player != selectPassDefender(passer, receiver.getTeam().getOpponent().getPlayers())) {
                if (getDistance(player, receiver) < 5) {
                    movePlayerTowards(player, receiver.getxPosition(), receiver.getyPosition());
                } else {
                    movePlayerTowards(player, player.getxPosition(), player.getyPosition());
                }
            }
        }
    }

    private void updatePositionsForDrive(Player driver, Player driveDefender) {
        // Move the driver towards the basket
        double basketX = driver.getTeam().isOffensiveTeam() ? 94 : 0;
        double basketY = 25;
        movePlayerTowards(driver, basketX, basketY);

        // Move the drive defender to follow the driver
        movePlayerTowards(driveDefender, driver.getxPosition(), driver.getyPosition());

        // Move other offensive players to space out or cut to the basket
        for (Player player : driver.getTeam().getPlayers()) {
            if (player != driver) {
                if (getDistance(player, driver) < 10) {
                    movePlayerTowards(player, 47, 25);
                } else {
                    movePlayerTowards(player, player.getxPosition() + 5, player.getyPosition());
                }
            }
        }

        // Move other defensive players to help on the drive or stay with their assignments
        for (Player player : driver.getTeam().getOpponent().getPlayers()) {
            if (player != driveDefender) {
                if (getDistance(player, driver) < 10) {
                    movePlayerTowards(player, driver.getxPosition(), driver.getyPosition());
                } else {
                    movePlayerTowards(player, player.getxPosition(), player.getyPosition());
                }
            }
        }
    }

    private void updatePositionsForShot(Player shooter) {
        // Move the shooter to their shooting spot if necessary
        movePlayerTowards(shooter, shooter.getxPosition(), shooter.getyPosition());

        // Move offensive players to crash the boards for rebounds or space out for a potential kick-out pass
        for (Player player : shooter.getTeam().getPlayers()) {
            if (player != shooter) {
                if (Math.random() < 0.5) {
                    movePlayerTowards(player, 47, 25); // Crash the boards
                } else {
                    movePlayerTowards(player, player.getxPosition() + 5, player.getyPosition()); // Space out
                }
            }
        }

        // Move defensive players to box out or contest the shot
        for (Player player : shooter.getTeam().getOpponent().getPlayers()) {
            if (player != selectShotDefender(shooter, shooter.getTeam().getOpponent().getPlayers())) {
                if (getDistance(player, shooter) < 10) {
                    movePlayerTowards(player, 47, 25); // Box out for rebound
                } else {
                    movePlayerTowards(player, player.getxPosition(), player.getyPosition()); // Stay with assignments
                }
            }
        }
    }

    private void updatePositionsForPostMove(Player postPlayer, Player postDefender) {
        // Move the post player to their desired post position
        movePlayerTowards(postPlayer, postPlayer.getxPosition(), postPlayer.getyPosition());

        // Move the post defender to follow the post player
        movePlayerTowards(postDefender, postPlayer.getxPosition(), postPlayer.getyPosition());

        // Move other offensive players to space out or cut to the basket
        for (Player player : postPlayer.getTeam().getPlayers()) {
            if (player != postPlayer) {
                if (getDistance(player, postPlayer) < 10) {
                    movePlayerTowards(player, 47, 25); // Cut to the basket
                } else {
                    movePlayerTowards(player, player.getxPosition() + 5, player.getyPosition()); // Space out
                }
            }
        }

        // Move other defensive players to help on the post player or stay with their assignments
        for (Player player : postPlayer.getTeam().getOpponent().getPlayers()) {
            if (player != postDefender) {
                if (getDistance(player, postPlayer) < 10) {
                    movePlayerTowards(player, postPlayer.getxPosition(), postPlayer.getyPosition()); // Help on post player
                } else {
                    movePlayerTowards(player, player.getxPosition(), player.getyPosition()); // Stay with assignments
                }
            }
        }
    }
}
