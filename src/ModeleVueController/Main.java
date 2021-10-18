package ModeleVueController;

import object.Player;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        var gameData = new Model(initPlayer(), new HashMap<>(), new LinkedHashMap<>());
        var scanner = new Scanner(System.in);
        Controller.startingMenu(scanner, gameData);
        scanner.close();
    }

    private static List<Player> initPlayer() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        return players;
    }
}
