package ModeleVueController;

import object.NumbersDisplays;
import object.Player;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        NumbersDisplays.loadNumbers();
        var gameData = new Model(initPlayer(), new HashMap<>(), new LinkedHashMap<>());
        try (var scanner = new Scanner(System.in)) {
            Controller.startingMenu(scanner, gameData);
        }
    }

    private static List<Player> initPlayer() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        return players;
    }
}
