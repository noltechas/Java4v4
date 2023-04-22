import java.util.Random;

public class Player {
    private String name;
    private Team team;
    private int overall;
    private int closeShot;
    private int midRangeShot;
    private int threePointShot;
    private int freeThrow;
    private int postHook;
    private int postUpAndUnder;
    private int shotIQ;
    private int offensiveConsistency;
    private int layup;
    private int dunk;
    private int postFade;
    private int hands;
    private int interiorDefense;
    private int perimeterDefense;
    private int postDefense;
    private int steal;
    private int block;
    private int lateralQuickness;
    private int defensiveConsistency;
    private int athleticism;
    private int passAccuracy;
    private int ballHandle;
    private int speedWithBall;
    private int passIQ;
    private int passVision;
    private int offensiveRebounding;
    private int defensiveRebounding;
    private int xPosition;
    private int yPosition;

    public Player(String name, int overall) {
        this.name = name;
        this.overall = overall;

        // Set initial position on the court (you can adjust these values based on your game design)
        this.xPosition = 0;
        this.yPosition = 0;

        // Generate attributes based on the overall
        generateAttributes(overall);
    }

    private void generateAttributes(int overall) {
        Random random = new Random();

        int[] attributes = new int[27]; // There are 26 attributes in your list
        int sum = 0;

        // Generate random attributes, sum them up
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = random.nextInt(100);
            sum += attributes[i];
        }

        // Adjust attributes based on the overall
        double scaleFactor = (double) overall * attributes.length / sum;
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = (int) Math.round(attributes[i] * scaleFactor);
        }

        // Assign the generated attributes to the player attributes
        closeShot = attributes[0];
        midRangeShot = attributes[1];
        threePointShot = attributes[2];
        freeThrow = attributes[3];
        postHook = attributes[4];
        postUpAndUnder = attributes[5];
        shotIQ = attributes[6];
        offensiveConsistency = attributes[7];
        layup = attributes[8];
        dunk = attributes[9];
        postFade = attributes[10];
        hands = attributes[11];
        interiorDefense = attributes[12];
        perimeterDefense = attributes[13];
        postDefense = attributes[14];
        steal = attributes[15];
        block = attributes[16];
        lateralQuickness = attributes[17];
        defensiveConsistency = attributes[18];
        athleticism = attributes[19];
        passAccuracy = attributes[20];
        ballHandle = attributes[21];
        speedWithBall = attributes[22];
        passIQ = attributes[23];
        passVision = attributes[24];
        offensiveRebounding = attributes[25];
        defensiveRebounding = attributes[26];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getCloseShot() {
        return closeShot;
    }

    public void setCloseShot(int closeShot) {
        this.closeShot = closeShot;
    }

    public int getMidRangeShot() {
        return midRangeShot;
    }

    public void setMidRangeShot(int midRangeShot) {
        this.midRangeShot = midRangeShot;
    }

    public int getThreePointShot() {
        return threePointShot;
    }

    public void setThreePointShot(int threePointShot) {
        this.threePointShot = threePointShot;
    }

    public int getFreeThrow() {
        return freeThrow;
    }

    public void setFreeThrow(int freeThrow) {
        this.freeThrow = freeThrow;
    }

    public int getShotIQ() {
        return shotIQ;
    }

    public void setShotIQ(int shotIQ) {
        this.shotIQ = shotIQ;
    }

    public int getOffensiveConsistency() {
        return offensiveConsistency;
    }

    public void setOffensiveConsistency(int offensiveConsistency) {
        this.offensiveConsistency = offensiveConsistency;
    }

    public int getLayup() {
        return layup;
    }

    public void setLayup(int layup) {
        this.layup = layup;
    }

    public int getDunk() {
        return dunk;
    }

    public void setDunk(int dunk) {
        this.dunk = dunk;
    }

    public int getPostFade() {
        return postFade;
    }

    public void setPostFade(int postFade) {
        this.postFade = postFade;
    }

    public int getHands() {
        return hands;
    }

    public void setHands(int hands) {
        this.hands = hands;
    }

    public int getInteriorDefense() {
        return interiorDefense;
    }

    public void setInteriorDefense(int interiorDefense) {
        this.interiorDefense = interiorDefense;
    }

    public int getPerimeterDefense() {
        return perimeterDefense;
    }

    public void setPerimeterDefense(int perimeterDefense) {
        this.perimeterDefense = perimeterDefense;
    }

    public int getSteal() {
        return steal;
    }

    public void setSteal(int steal) {
        this.steal = steal;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getLateralQuickness() {
        return lateralQuickness;
    }

    public void setLateralQuickness(int lateralQuickness) {
        this.lateralQuickness = lateralQuickness;
    }

    public int getDefensiveConsistency() {
        return defensiveConsistency;
    }

    public void setDefensiveConsistency(int defensiveConsistency) {
        this.defensiveConsistency = defensiveConsistency;
    }

    public int getAthleticism() {
        return athleticism;
    }

    public void setAthleticism(int athleticism) {
        this.athleticism = athleticism;
    }

    public int getPassAccuracy() {
        return passAccuracy;
    }

    public void setPassAccuracy(int passAccuracy) {
        this.passAccuracy = passAccuracy;
    }

    public int getBallHandle() {
        return ballHandle;
    }

    public void setBallHandle(int ballHandle) {
        this.ballHandle = ballHandle;
    }

    public int getSpeedWithBall() {
        return speedWithBall;
    }

    public void setSpeedWithBall(int speedWithBall) {
        this.speedWithBall = speedWithBall;
    }

    public int getPassIQ() {
        return passIQ;
    }

    public void setPassIQ(int passIQ) {
        this.passIQ = passIQ;
    }

    public int getPassVision() {
        return passVision;
    }

    public void setPassVision(int passVision) {
        this.passVision = passVision;
    }

    public int getOffensiveRebounding() {
        return offensiveRebounding;
    }

    public void setOffensiveRebounding(int offensiveRebounding) {
        this.offensiveRebounding = offensiveRebounding;
    }

    public int getDefensiveRebounding() {
        return defensiveRebounding;
    }

    public void setDefensiveRebounding(int defensiveRebounding) {
        this.defensiveRebounding = defensiveRebounding;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public double getSpeed() {
        return this.speedWithBall;
    }

    public double timeToReachShotSpot(ShotType shotType) {
        // Get the x and y coordinates of the closest shot spot for the given shot type
        double targetX = shotType.getOptimalXPosition();
        double targetY = shotType.getOptimalYPosition();

        // Calculate the distance between the player's current position and the target position
        double distance = Math.sqrt(Math.pow(targetX - this.xPosition, 2) + Math.pow(targetY - this.yPosition, 2));

        // Calculate the time taken to reach the shot spot based on the player's speed
        double timeToReach = distance / this.speedWithBall;

        return timeToReach;
    }

    public int getPostHook() {
        return postHook;
    }

    public void setPostHook(int postHook) {
        this.postHook = postHook;
    }

    public int getPostUpAndUnder() {
        return postUpAndUnder;
    }

    public int getPostDefense() {
        return postDefense;
    }
}
