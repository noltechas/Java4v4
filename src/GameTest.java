import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTest {

    public static void main(String[] args) {
        // Generate two teams with random players
        List<Player> teamAPlayers = generateRandomPlayers(5);
        List<Player> teamBPlayers = generateRandomPlayers(5);

        HeadCoach teamACoach = createRandomHeadCoach("Coach A");
        HeadCoach teamBCoach = createRandomHeadCoach("Coach B");

        Team teamA = new Team("Team A", teamACoach, teamAPlayers);
        Team teamB = new Team("Team B", teamBCoach, teamBPlayers);

        // Create a new game with the two teams
        Game game = new Game(teamA,teamB);

        game.simulateGame();
    }

    private static List<Player> generateRandomPlayers(int numberOfPlayers) {
        Random random = new Random();
        List<Player> players = new ArrayList<>();

        String[] firstNames = {
                "James", "Michael", "John", "David", "Chris", "Kevin", "Paul", "Mark", "Stephen", "Andrew"
        };

        String[] lastNames = {
                "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"
        };
        for (int i = 0; i < numberOfPlayers; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            String playerName = firstName + " " + lastName;
            int randomOverall = 60 + random.nextInt(40); // Generates a random overall between 60 and 99

            players.add(new Player(playerName, randomOverall));
        }

        return players;
    }

    private static HeadCoach createRandomHeadCoach(String name) {
        Random random = new Random();
        double dunkPreference = random.nextDouble();
        double layupPreference = random.nextDouble();
        double midRangePreference = random.nextDouble();
        double threePointPreference = random.nextDouble();
        double postUpPreference = random.nextDouble();

        return new HeadCoach(name, dunkPreference, layupPreference, midRangePreference, threePointPreference, postUpPreference);
    }
}
