import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private HeadCoach headCoach;
    private List<Player> players;
    private boolean offensiveTeam;
    private Team opponent;
    private int score;

    public Team(String name, HeadCoach headCoach, List<Player> players) {
        this.name = name;
        this.headCoach = headCoach;
        this.players = players;
        this.offensiveTeam = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeadCoach getHeadCoach() {
        return headCoach;
    }

    public void setHeadCoach(HeadCoach headCoach) {
        this.headCoach = headCoach;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean isOffensiveTeam() {
        return offensiveTeam;
    }

    public void setOffensiveTeam(boolean offensiveTeam) {
        this.offensiveTeam = offensiveTeam;
    }

    public Team getOpponent() {
        return opponent;
    }

    public void setOpponent(Team opponent) {
        this.opponent = opponent;
    }

    public Player getPlayerByIndex(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index);
        } else {
            return null;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
