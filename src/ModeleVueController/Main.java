package ModeleVueController;

import object.NumbersDisplays;
import object.Player;

import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        NumbersDisplays.loadNumbers();
        var gameData = new Model(initPlayer(), new LinkedHashMap<>(), new LinkedHashMap<>());
        try (var scanner = new Scanner(System.in)) {
            Controller.startingMenu(scanner, gameData);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }
        View.printWinner(gameData.getWinner());
    }

    /**
     * This method initialise a list of 2 players named Player1 and Player2
     * @return the list initialised
     */
    private static List<Player> initPlayer() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        return players;
    }
}
