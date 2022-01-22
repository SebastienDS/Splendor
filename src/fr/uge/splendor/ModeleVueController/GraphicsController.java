package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Button;
import fr.uge.splendor.object.Player;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class GraphicsController {
    public static void startingMenu(ApplicationContext context, Model gameData) throws IOException {
        gameData.setGameMode(2);

        ScreenInfo screenInfo = context.getScreenInfo();
        int width = (int)screenInfo.getWidth();
        int height = (int)screenInfo.getHeight();

        Point2D.Float mousePos;
        var buttons = List.of(
                new Button("Menu des Joueurs", width / 2, height / 5, width / 5, 100),
                new Button("Choix du jeu",width / 2, height * 2 / 5, width / 5, 100),
                new Button("Lancer la partie",width / 2, height * 3 / 5, width / 5, 100),
                new Button("Quitter",width / 2, height * 4 / 5, width / 5, 100)
        );

        while (true) {
            context.renderFrame(graphics -> {
                graphics.setColor(Color.LIGHT_GRAY);
                graphics.fill(new Rectangle(0, 0, width, height));
                buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
            });

            Event event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) { // no event
                continue;
            }

            Event.Action action = event.getAction();
            if (action == Event.Action.KEY_PRESSED) {
                if (event.getKey() == KeyboardKey.E) { // EXIT
                    System.out.println("Quitting ... !");
                    context.exit(0);
                }
            } else if (action == Event.Action.POINTER_DOWN) {
                mousePos = event.getLocation();

                for (int i = 0; i < buttons.size(); i++) {
                    var button = buttons.get(i);

                    if (button.rect().contains(mousePos)) {
                        System.out.println(i);
                        switch (i) {
                            case 0 -> playerMenu(context, gameData);
                            case 1 -> gameModeMenu(context, gameData);
                            case 2 -> {
                                startGame(context, gameData);
                                return;
                            }
                            case 3 -> context.exit(0);
                        }
                    }
                }
            }
        }
    }

    private static void gameModeMenu(ApplicationContext context, Model gameData) {
        ScreenInfo screenInfo = context.getScreenInfo();
        int width = (int)screenInfo.getWidth();
        int height = (int)screenInfo.getHeight();

        Point2D.Float mousePos;
        var buttons = List.of(
                new Button("Phase 1", width / 2, height / 3, width / 5, 100),
                new Button("Phase 2 / 3",width / 2, height / 2, width / 5, 100)
        );

        context.renderFrame(graphics -> {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fill(new Rectangle(0, 0, width, height));
            buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
        });

        while (true) {
            Event event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) { // no event
                continue;
            }

            Event.Action action = event.getAction();
            if (action == Event.Action.KEY_PRESSED) {
                if (event.getKey() == KeyboardKey.E) { // EXIT
                    System.out.println("Quitting ... !");
                    context.exit(0);
                }
            } else if (action == Event.Action.POINTER_DOWN) {
                mousePos = event.getLocation();

                for (int i = 0; i < buttons.size(); i++) {
                    var button = buttons.get(i);

                    if (button.rect().contains(mousePos)) {
                        System.out.println(i);
                        if (i == 0) {
                            gameData.setGameMode(1);
                        } else {
                            gameData.setGameMode(2);
                        }
                        return;
                    }
                }
            }
        }
    }

    private static void playerMenu(ApplicationContext context, Model gameData) {
        ScreenInfo screenInfo = context.getScreenInfo();
        int width = (int)screenInfo.getWidth();
        int height = (int)screenInfo.getHeight();

        Point2D.Float mousePos;
        var buttons = List.of(
                new Button("Modifier un nom", width / 2, height / 5, width / 5, 100),
                new Button("Ajouter un joueur",width / 2, height * 2 / 5, width / 5, 100),
                new Button("Supprimer un joueur",width / 2, height * 3 / 5, width / 5, 100),
                new Button("Confirmer",width / 2, height * 4 / 5, width / 5, 100)
        );

        var players = gameData.getPlayers();

        while (true) {
            context.renderFrame(graphics -> {
                graphics.setColor(Color.LIGHT_GRAY);
                graphics.fill(new Rectangle(0, 0, width, height));
                buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
            });

            Event event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) { // no event
                continue;
            }

            Event.Action action = event.getAction();
            if (action == Event.Action.KEY_PRESSED) {
                if (event.getKey() == KeyboardKey.E) { // EXIT
                    System.out.println("Quitting ... !");
                    context.exit(0);
                }
            } else if (action == Event.Action.POINTER_DOWN) {
                mousePos = event.getLocation();

                for (int i = 0; i < buttons.size(); i++) {
                    var button = buttons.get(i);

                    if (button.rect().contains(mousePos)) {
                        System.out.println(i);
                        switch (i) {
                            case 0 -> changeName(context, gameData);
                            case 1 -> addPlayer(context, players);
                            case 2 -> removePlayer(context, players);
                            default -> {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void removePlayer(ApplicationContext context, List<Player> players) {
        if (players.size() == 2) {
//            View.printNotEnoughPlayer();
            return;
        }
        players.remove(choosePlayer(context, players));
    }

    private static void addPlayer(ApplicationContext context, List<Player> players) {
        if (players.size() == 4) {
//            GraphicsView.printTooMuchPlayer();
            return;
        }
        players.add(new Player("Player " + (players.size() + 1)));
    }

    private static void changeName(ApplicationContext context, Model gameData) {
    }

    private static int choosePlayer(ApplicationContext context, List<Player> players) {
        ScreenInfo screenInfo = context.getScreenInfo();
        int width = (int)screenInfo.getWidth();
        int height = (int)screenInfo.getHeight();

        Point2D.Float mousePos;

        var buttons = IntStream.range(0, players.size())
                .boxed()
                .map(i -> new Button(players.get(i).getName(), width / 2, height * (i + 1) / 5, width / 5, 100))
                .toList();

        while (true) {
            context.renderFrame(graphics -> {
                graphics.setColor(Color.LIGHT_GRAY);
                graphics.fill(new Rectangle(0, 0, width, height));
                buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
            });

            Event event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) { // no event
                continue;
            }

            Event.Action action = event.getAction();
            if (action == Event.Action.KEY_PRESSED) {
                if (event.getKey() == KeyboardKey.E) { // EXIT
                    System.out.println("Quitting ... !");
                    context.exit(0);
                }
            } else if (action == Event.Action.POINTER_DOWN) {
                mousePos = event.getLocation();

                for (int i = 0; i < buttons.size(); i++) {
                    var button = buttons.get(i);

                    if (button.rect().contains(mousePos)) {
                        return i;
                    }
                }
            }
        }
    }

    private static void startGame(ApplicationContext context, Model gameData) throws IOException {
        ScreenInfo screenInfo = context.getScreenInfo();
        int width = (int)screenInfo.getWidth();
        int height = (int)screenInfo.getHeight();

        Point2D.Float mousePos;

        gameData.startGame();

        var length = gameData.getDecks().keySet().size();
        var w = Integer.MAX_VALUE;
        var h = Math.min(height / length, 400);

        var image = GraphicsView.loadImage(Path.of("resources", "images", "Alliance Frigate.png"), w, h);

        while (!gameData.getLastRound() || !gameData.startNewRound()) {
            context.renderFrame(graphics -> {
                graphics.setColor(Color.LIGHT_GRAY);
                graphics.fill(new Rectangle(0, 0, width, height));

                IntStream.range(0, length).forEach(i -> GraphicsView.drawImage(graphics, 0, h * i, image));
            });

            Event event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) { // no event
                continue;
            }

            Event.Action action = event.getAction();
            if (action == Event.Action.KEY_PRESSED) {
                if (event.getKey() == KeyboardKey.E) { // EXIT
                    System.out.println("Quitting ... !");
                    context.exit(0);
                }
            } else if (action == Event.Action.POINTER_DOWN) {
                mousePos = event.getLocation();

            }
        }
    }
}
