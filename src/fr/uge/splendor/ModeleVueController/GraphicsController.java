package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Button;
import fr.uge.splendor.object.Player;
import fr.uge.splendor.object.TextField;

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

    /**
     * this method let the player choose the option he wants to modify and start the game.
     * He can change the number of player and their name or/and change the game mode
     * @param context for the display
     * @param gameData data of the game
     * @throws IOException if an I/O exception occurs
     */
    public static void startingMenu(ApplicationContext context, Model gameData) throws IOException {
        gameData.setGameMode(2);
        var buttons = initButtonStartingMenu();
        while (true) {
            GraphicsView.drawStartingMenu(context, buttons);
            var event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) continue;
            var action = event.getAction();
            if (action == Event.Action.POINTER_DOWN) {
                var finish = haveClickedStartingMenu(event.getLocation(), buttons, context, gameData);
                if(finish) return;
            }
        }
    }

    /**
     * This methode see if player clicked on a button and start the adapted menu
     * @param mousePos click position
     * @param buttons list of all buttons
     * @param context display context
     * @param gameData data of the game
     * @throws IOException if an I/O occur
     */
    private static boolean haveClickedStartingMenu(Point2D mousePos, List<Button> buttons, ApplicationContext context,
                                                Model gameData) throws IOException {
        for (int i = 0; i < buttons.size(); i++) {
            var button = buttons.get(i);
            if (button.rect().contains(mousePos)) {
                switch (i) {
                    case 0 -> playerMenu(context, gameData);
                    case 1 -> gameModeMenu(context, gameData);
                    case 2 -> {
                        startGame(context, gameData);
                        return true;
                    }
                    case 3 -> context.exit(0);
                }
            }
        }
        return false;
    }

    /**
     * This methode create button for the starting menu and return them
     * @return return list of button created
     */
    private static List<Button> initButtonStartingMenu() {
        return List.of(
                new Button(
                        "Menu des Joueurs",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN / 5,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                ),
                new Button(
                        "Choix du jeu",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN * 2 / 5,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                ),
                new Button(
                        "Lancer la partie",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN * 3 / 5,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                ),
                new Button(
                        "Quitter",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN * 4 / 5,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                )
        );
    }

    /**
     * Let the user choose between all the game mode
     * @param context Display context
     * @param gameData data of the game
     */
    private static void gameModeMenu(ApplicationContext context, Model gameData) {
        var buttons = initButtonModeMenu();
        GraphicsView.drawModeMenu(context, buttons);
        while (true) {
            var event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) continue;

            var action = event.getAction();
            if (action == Event.Action.POINTER_DOWN) {
                var finish = haveClickedModeMenu(event.getLocation(), buttons, gameData);
                if(finish) return;
            }
        }
    }

    /**
     * This method see if player clicked on a button and change mode of game if this is the case
     * @param mousePos position of click
     * @param buttons all buttons of menu
     * @param gameData data of the game
     */
    private static boolean haveClickedModeMenu(Point2D.Float mousePos, List<Button> buttons, Model gameData) {
        for (int i = 0; i < buttons.size(); i++) {
            var button = buttons.get(i);
            if (button.rect().contains(mousePos)) {
                if (i == 0) {
                    gameData.setGameMode(1);
                } else {
                    gameData.setGameMode(2);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Initialize all button of mode menu and return them in a list
     * @return list of all button of mode menu
     */
    private static List<Button> initButtonModeMenu() {
        return List.of(
                new Button(
                        "Phase 1",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN / 3,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                ),
                new Button(
                        "Phase 2 / 3",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN / 2,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                )
        );
    }

    /**
     * This method open player menu
     * @param context display context
     * @param gameData data of the game
     */
    private static void playerMenu(ApplicationContext context, Model gameData) {
        var buttons = initButtonPlayerMenu();
        var players = gameData.getPlayers();
        while (true) {
            GraphicsView.drawPlayerMenu(context, buttons, gameData);
            var event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) continue;
            var action = event.getAction();
            if (action == Event.Action.POINTER_DOWN) {
                var finish = haveClickedPlayerMenu(event.getLocation(), buttons, context, gameData, players);
                if(finish) return;
            }
        }
    }

    /**
     * This method see if player clicked on a button in player menu and open the corresponding menu
     * @param mousePos position of mouse
     * @param buttons all buttons
     * @param context display context
     * @param gameData data of the game
     * @param players list of players
     */
    private static boolean haveClickedPlayerMenu(Point2D.Float mousePos, List<Button> buttons, ApplicationContext context,
                                              Model gameData, List<Player> players) {
        for (int i = 0; i < buttons.size(); i++) {
            var button = buttons.get(i);
            if (button.rect().contains(mousePos)) {
                switch (i) {
                    case 0 -> changeName(context, gameData);
                    case 1 -> addPlayer(context, players);
                    case 2 -> removePlayer(context, players);
                    default -> {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Initialise all button of player menu and return them in a list
     * @return list of all button of player menu
     */
    private static List<Button> initButtonPlayerMenu() {
        return List.of(
                new Button(
                        "Modifier un nom",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN / 5,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                ),
                new Button(
                        "Ajouter un joueur",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN * 2 / 5,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                ),
                new Button(
                        "Supprimer un joueur",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN * 3 / 5,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                ),
                new Button(
                        "Confirmer",
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN * 4 / 5,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100
                )
        );
    }

    /**
     * This method remove a player of list of players if there is more than 2 player
     * @param context display context
     * @param players all players
     */
    private static void removePlayer(ApplicationContext context, List<Player> players) {
        if (players.size() == 2) {
            // print not enough player
            return;
        }
        players.remove(choosePlayer(context, players));
    }

    /**
     * This method add a player with the name given by the function changeName.
     * @param context display context
     * @param players list of all players
     */
    private static void addPlayer(ApplicationContext context, List<Player> players) {
        if (players.size() == 4) {
//            GraphicsView.printTooMuchPlayer();
            return;
        }
        players.add(new Player("Player" + (players.size() + 1)));
        changeName(context, players, players.size() - 1);
    }

    /**
     * make the user choose a player from all the players and change the name by the input of text field.
     * @param context display context
     * @param gameData data of the game
     */
    private static void changeName(ApplicationContext context, Model gameData) {
        var players = gameData.getPlayers();
        var player = choosePlayer(context, players);
        changeName(context, players, player);
    }

    /**
     * Change name of player at index in players list by the input of text field
     * @param context display context
     * @param players list of players
     * @param index index of player chosen
     */
    private static void changeName(ApplicationContext context, List<Player> players, int index) {
        var textField = initTextField(players.get(index).getName());
        var buttons = initButtonChangeName();
        while (true) {
            GraphicsView.drawChangeNameMenu(context, textField, buttons);
            var event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) continue;
            var action = event.getAction();
            if (action == Event.Action.KEY_PRESSED) keyPressed(event.getKey(), textField);
            else if (action == Event.Action.POINTER_DOWN) {
                var finish = haveClickedChangeNameMenu(event.getLocation(), buttons, textField, players, index);
                if(finish) return;
            }
        }
    }

    /**
     * This method see if player have clicked on a button to do the according action (deleting last letter or confirming)
     * @param mousePos position of mouse
     * @param buttons all buttons of menu
     * @param textField text field for name
     * @param players list of all players
     * @param index index of player chosen
     */
    private static boolean haveClickedChangeNameMenu(Point2D.Float mousePos, List<Button> buttons, TextField textField,
                                                  List<Player> players, int index) {
        if (buttons.get(0).rect().contains(mousePos)) {
            players.set(index, new Player(textField.label()));
            return true;
        }
        if (buttons.get(1).rect().contains(mousePos)) {
            textField.remove();
        }
        return false;
    }

    /**
     * if key is pressed add key character to text field label if key pressed is a letter or a number
     * @param key key pressed
     * @param textField text field to add character
     */
    private static void keyPressed(KeyboardKey key, TextField textField) {
        if (key.name().length() == 1) {
            textField.add(key.name());
        }
    }

    /**
     * Initialise all button of change name menu and return them in a list
     * @return list of all button of change name menu
     */
    private static List<Button> initButtonChangeName() {
        return List.of(
            new Button(
                    "Confirmer",
                    GraphicsView.WIDTH_SCREEN / 2,
                    GraphicsView.HEIGHT_SCREEN * 2  / 5,
                    GraphicsView.WIDTH_SCREEN / 5,
                    100
            ),
            new Button(
                    "<==",
                    3 * GraphicsView.WIDTH_SCREEN / 4,
                    GraphicsView.HEIGHT_SCREEN / 5,
                    GraphicsView.WIDTH_SCREEN / 15,
                    100
            )
        );
    }

    /**
     * Initialise a text field with label and return it
     * @param label string of label
     * @return text field created
     */
    private static TextField initTextField(String label) {
        return new TextField(
                label,
                GraphicsView.WIDTH_SCREEN / 2,
                GraphicsView.HEIGHT_SCREEN  / 5,
                GraphicsView.WIDTH_SCREEN / 5,
                100
        );
    }

    /**
     * This method open a menu to choose a player from a list.
     * @param context display context
     * @param players list of all players
     * @return index of player chosen
     */
    private static int choosePlayer(ApplicationContext context, List<Player> players) {
        var buttons = initButtonChoosePlayer(players);
        while (true) {
            GraphicsView.drawChoosePlayerMenu(context, buttons);
            Event event = context.pollOrWaitEvent(20); // modifier pour avoir un affichage fluide
            if (event == null) { // no event
                continue;
            }
            Event.Action action = event.getAction();
            if (action == Event.Action.POINTER_DOWN) {
                var i = haveClickedChoosePlayer(event.getLocation(), buttons);
                if(i != -1) return 1;
            }
        }
    }

    /**
     * This method see if the user clicked on a button and return index of the button
     * @param mousePos position of mouse
     * @param buttons list of all buttons
     * @return index of button clicked or -1 if no button clicked
     */
    private static int haveClickedChoosePlayer(Point2D.Float mousePos, List<Button> buttons) {
        for (int i = 0; i < buttons.size(); i++) {
            var button = buttons.get(i);
            if (button.rect().contains(mousePos)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Initialise all buttons for the choose player menu and return them in a list
     * @param players list of all players
     * @return list of buttons created
     */
    private static List<Button> initButtonChoosePlayer(List<Player> players) {
        return IntStream.range(0, players.size())
                .boxed()
                .map(i -> new Button(
                        players.get(i).getName(),
                        GraphicsView.WIDTH_SCREEN / 2,
                        GraphicsView.HEIGHT_SCREEN * (i + 1) / 5,
                        GraphicsView.WIDTH_SCREEN / 5,
                        100)
                )
                .toList();
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
