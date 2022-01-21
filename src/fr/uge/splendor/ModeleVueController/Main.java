package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.NumbersDisplays;
import fr.uge.splendor.object.Player;

import java.io.IOException;
import java.util.*;

/**
 * This class represents the Main
 */
public class Main {
    /**
     * Main of the project
     * @param args args
     */
    public static void main(String[] args) {
        NumbersDisplays.loadNumbers();
        try (var scanner = new Scanner(System.in)) {
            var gameData = new Model(initPlayer());
            Controller.startingMenu(scanner, gameData);
            View.printWinner(gameData.getWinner());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
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
