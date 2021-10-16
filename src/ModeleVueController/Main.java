package ModeleVueController;

import object.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var gameData = new Model(initPlayer(), new HashMap<>(), new HashMap<>());
        var scanner = new Scanner(System.in);
        Controller.startingMenu(scanner, gameData);
    }

    private static List<Player> initPlayer() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        return players;
    }
}
