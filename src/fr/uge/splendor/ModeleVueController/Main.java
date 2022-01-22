package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.NumbersDisplays;
import fr.uge.splendor.object.Player;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

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
            System.out.println("Console ? (y/n)");
//            var a = scanner.next();
            var a = "n";
            if (a.toLowerCase(Locale.ROOT).equals("y")) {
                Controller.startingMenu(scanner, gameData);
                View.printWinner(gameData.getWinner());
            } else {
                Application.run(Color.BLACK, context -> {
                    try {
                        ScreenInfo screenInfo = context.getScreenInfo();
                        GraphicsView.WIDTH_SCREEN = (int)screenInfo.getWidth();
                        GraphicsView.HEIGHT_SCREEN = (int)screenInfo.getHeight();
                        GraphicsController.startingMenu(context, gameData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
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
