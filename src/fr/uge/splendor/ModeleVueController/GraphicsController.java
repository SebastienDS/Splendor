package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.*;
import fr.uge.splendor.object.Button;
import fr.uge.splendor.object.TextField;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class represents the controller for the graphics version
 */
public class GraphicsController {

    /**
     * this method let the player choose the option he wants to modify and start the game.
     * He can change the number of player and their name or/and change the game mode
     * @param context for the display
     * @param gameData data of the game
     * @param images all images
     * @throws IOException if an I/O exception occurs
     */
    public static void startingMenu(ApplicationContext context, Model gameData, ImageManager images) throws IOException {
        gameData.setGameMode(2);
        var buttons = initButtonStartingMenu();
        while (true) {
            GraphicsView.drawStartingMenu(context, buttons, images);
            var event = context.pollOrWaitEvent(20);
            if (event == null) continue;
            var action = event.getAction();
            if (action == Event.Action.POINTER_DOWN) {
                var finish = haveClickedStartingMenu(event.getLocation(), buttons, context, gameData, images);
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
     */
    private static boolean haveClickedStartingMenu(Point2D mousePos, List<Button> buttons, ApplicationContext context,
                                                Model gameData, ImageManager images) {
        for (int i = 0; i < buttons.size(); i++) {
            var button = buttons.get(i);
            if (button.rect().contains(mousePos)) {
                switch (i) {
                    case 0 -> playerMenu(context, gameData, images);
                    case 1 -> gameModeMenu(context, gameData, images);
                    case 2 -> {
                        startGame(context, gameData, images);
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
    private static void gameModeMenu(ApplicationContext context, Model gameData, ImageManager images) {
        var buttons = initButtonModeMenu();
        GraphicsView.drawModeMenu(context, buttons, images);
        while (true) {
            var event = context.pollOrWaitEvent(20);
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
    private static void playerMenu(ApplicationContext context, Model gameData, ImageManager images) {
        var buttons = initButtonPlayerMenu();
        var players = gameData.getPlayers();
        while (true) {
            GraphicsView.drawPlayerMenu(context, buttons, gameData, images);
            var event = context.pollOrWaitEvent(20);
            if (event == null) continue;
            var action = event.getAction();
            if (action == Event.Action.POINTER_DOWN) {
                var finish = haveClickedPlayerMenu(event.getLocation(), buttons, context, gameData, players, images);
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
                                              Model gameData, List<Player> players, ImageManager images) {
        for (int i = 0; i < buttons.size(); i++) {
            var button = buttons.get(i);
            if (button.rect().contains(mousePos)) {
                switch (i) {
                    case 0 -> changeName(context, gameData, images);
                    case 1 -> addPlayer(context, players, images);
                    case 2 -> removePlayer(context, players, images);
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
    private static void removePlayer(ApplicationContext context, List<Player> players, ImageManager images) {
        if (players.size() == 2) {
            // print not enough player
            return;
        }
        players.remove(choosePlayer(context, players, images));
    }

    /**
     * This method add a player with the name given by the function changeName.
     * @param context display context
     * @param players list of all players
     */
    private static void addPlayer(ApplicationContext context, List<Player> players, ImageManager images) {
        if (players.size() == 4) {
//            GraphicsView.printTooMuchPlayer();
            return;
        }
        players.add(new Player("Player" + (players.size() + 1)));
        changeName(context, players, players.size() - 1, images);
    }

    /**
     * make the user choose a player from all the players and change the name by the input of text field.
     * @param context display context
     * @param gameData data of the game
     */
    private static void changeName(ApplicationContext context, Model gameData, ImageManager images) {
        var players = gameData.getPlayers();
        var player = choosePlayer(context, players, images);
        changeName(context, players, player, images);
    }

    /**
     * Change name of player at index in players list by the input of text field
     * @param context display context
     * @param players list of players
     * @param index index of player chosen
     */
    private static void changeName(ApplicationContext context, List<Player> players, int index, ImageManager images) {
        var textField = initTextField(players.get(index).getName());
        var buttons = initButtonChangeName();
        while (true) {
            GraphicsView.drawChangeNameMenu(context, textField, buttons, images);
            var event = context.pollOrWaitEvent(20);
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
    private static int choosePlayer(ApplicationContext context, List<Player> players, ImageManager images) {
        var buttons = initButtonChoosePlayer(players);
        while (true) {
            GraphicsView.drawChoosePlayerMenu(context, buttons, images);
            var event = context.pollOrWaitEvent(20);
            if (event == null) continue;
            var action = event.getAction();
            if (action == Event.Action.POINTER_DOWN) {
                var i = haveClickedChoosePlayer(event.getLocation(), buttons);
                if(i != -1) return i;
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
                System.out.println(i);
                return i;
            }
        }
        return -1;
    }

    /**
     * Initialise all buttons for choose player menu and return them in a list
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

    /**
     * Function managing all the game
     * @param context application context
     * @param gameData data of the game
     * @param images all images
     */
    private static void startGame(ApplicationContext context, Model gameData, ImageManager images) {
        images.initCards(Path.of("resources", "images", "cards"), gameData);
        images.initNoble(Path.of("resources", "images", "nobles"), gameData);
        var buttons = initGameButtons();
        var actionManager = new ActionManager();
        gameData.startGame();
        GraphicsView.drawGame(context, gameData, images, buttons, actionManager);
        while (gameData.getLastRound() || gameData.startNewRound()) {
            var event = context.pollOrWaitEvent(20);
            if (event == null) continue;
            GraphicsView.drawGame(context, gameData, images, buttons, actionManager);
            var action = event.getAction();
            if (action == Event.Action.KEY_PRESSED) {
                if (event.getKey() == KeyboardKey.E) { // EXIT
                    System.out.println("Quitting ... !");
                    context.exit(0);
                }
            } else if (action == Event.Action.POINTER_DOWN) {
                haveClickedGameStarted(event.getLocation(), actionManager, gameData, images, buttons);
            }
        }
    }

    /**
     * manage click on button endTurn
     * @param buttons all buttons
     * @param location location of click
     * @return true if button is clicked on
     */
    private static boolean manageEndTurn(List<Button> buttons, Point2D.Float location) {
        return buttons.get(3).rect().contains(location);
    }

    /**
     * manage all action to select object
     * @param location location of click
     * @param actionManager manage action
     * @param gameData data of the game
     * @param images all images
     */
    private static void manageAction(Point2D.Float location, ActionManager actionManager, Model gameData, ImageManager images) {
        if (!manageCardAction(location, actionManager, gameData, images) && !manageTokenAction(location, actionManager, gameData) && !manageDeckAction(location, actionManager, gameData, images)) {
            manageReservedCardAction(location, actionManager, gameData, images);
        }
    }

    /**
     * Manage reserve card action
     * @param location location of click
     * @param actionManager manage action
     * @param gameData data of game
     * @param images all images
     */
    private static void manageReservedCardAction(Point2D.Float location, ActionManager actionManager, Model gameData, ImageManager images) {
        var index = 2;
        var length = gameData.getNumberOfDecks();
        var cards = gameData.getPlayerPlaying().getCardReserved();
        for (int i = 0; i < cards.size(); i++) {
            var e = i + 7;
            var card = cards.get(i);
            var image = images.get(card);
            var spacingX = GraphicsView.WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
            var spacingY = GraphicsView.HEIGHT_SCREEN / length;
            var x = spacingX * e + spacingX / 2 - image.getWidth() / 2;
            var y = spacingY * index + spacingY / 2 - image.getHeight() / 2;

            var rect = new Rectangle(x, y, image.getWidth(), image.getHeight());
            if (rect.contains(location)) {
                actionManager.setAction(ActionManager.Action.RESERVED_CARD);
                actionManager.selectCard(card, i, 0);
                return;
            }
        }
    }

    /**
     * verify if a card is clicked on. If that's the case, select it in actionManager and return true
     * @param location location of click
     * @param actionManager manage action
     * @param gameData data of the game
     * @param images all images
     * @return true if user clicked on card
     */
    private static boolean manageCardAction(Point2D.Float location, ActionManager actionManager, Model gameData, ImageManager images) {
        var length = gameData.getNumberOfDecks();
        for (var index : gameData.getGrounds().keySet()) {
            var cards = gameData.getGrounds().get(index);
            for (int i = 0; i < cards.size(); i++) {
                var card = cards.get(i);
                var image = images.get(card);
                var spacingX = GraphicsView.WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
                var spacingY = GraphicsView.HEIGHT_SCREEN / length;
                var x = spacingX * i + spacingX / 2 - image.getWidth() / 2;
                var y = spacingY * index + spacingY / 2 - image.getHeight() / 2;
                var rect = new Rectangle(x, y, image.getWidth(), image.getHeight());
                if (rect.contains(location)) {
                    actionManager.setAction(ActionManager.Action.CARD);
                    actionManager.selectCard(card, index, i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Manage action click on token and return true if user clicked on token
     * @param location location of click
     * @param actionManager manage action
     * @param gameData data of the game
     * @return true if user clicked on token
     */
    private static boolean manageTokenAction(Point2D.Float location, ActionManager actionManager, Model gameData) {
        var tokensGame = gameData.getGameTokens().tokens();
        var i = 0;
        for (var token : tokensGame.keySet()) {
            if (token == Token.GOLD) continue;
            var rect = new Rectangle(
                    GraphicsView.WIDTH_SCREEN / 2,
                    GraphicsView.HEIGHT_SCREEN / 3 + i * GraphicsView.HEIGHT_SCREEN / 14,
                    GraphicsView.HEIGHT_SCREEN / 15,
                    GraphicsView.HEIGHT_SCREEN / 15
            );
            if (tokensGame.get(token) != 0 && rect.contains(location)) {
                actionManager.setAction(ActionManager.Action.TOKEN);
                actionManager.selectToken(token);
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * Manage action of deck and return true if user clicked on deck
     * @param location location of click
     * @param actionManager manage action
     * @param gameData data of game
     * @param images all images
     * @return
     */
    private static boolean manageDeckAction(Point2D.Float location, ActionManager actionManager, Model gameData, ImageManager images) {
        var length = gameData.getNumberOfDecks();
        for (var index : gameData.getGrounds().keySet()) {
            var cards = gameData.getGrounds().get(index);
            var i = cards.size();
            var card = cards.get(0);
            var image = images.get(card);
            var spacingX = GraphicsView.WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
            var spacingY = GraphicsView.HEIGHT_SCREEN / length;
            var x = spacingX * i + spacingX / 2 - image.getWidth() / 2;
            var y = spacingY * index + spacingY / 2 - image.getHeight() / 2;

            var rect = new Rectangle(x, y, image.getWidth(), image.getHeight());
            if (rect.contains(location)) {
                actionManager.setAction(ActionManager.Action.DECK);
                actionManager.selectDeck(index);
                return true;
            }
        }
        return false;
    }

    /**
     * Initialise all buttons of game and return them in a list
     * @return list of button created
     */
    private static List<Button> initGameButtons() {
        return List.of(
                new Button(
                        "Acheter",
                        GraphicsView.WIDTH_SCREEN - GraphicsView.WIDTH_SCREEN / 20 - 50 - GraphicsView.WIDTH_SCREEN / 10 - 50,
                        (int) (GraphicsView.HEIGHT_SCREEN * 0.9),
                        GraphicsView.WIDTH_SCREEN / 10,
                        75
                ),
                new Button(
                        "Réserver",
                        GraphicsView.WIDTH_SCREEN - GraphicsView.WIDTH_SCREEN / 20 - 50,
                        (int) (GraphicsView.HEIGHT_SCREEN * 0.9),
                        GraphicsView.WIDTH_SCREEN / 10,
                        75
                ),
                new Button(
                        "Prendre jetons selectionnés",
                        GraphicsView.WIDTH_SCREEN - GraphicsView.WIDTH_SCREEN / 8 - 50,
                        (int) (GraphicsView.HEIGHT_SCREEN * 0.9),
                        GraphicsView.WIDTH_SCREEN / 4,
                        75
                ),
                new Button(
                        "Fin de tour",
                        GraphicsView.WIDTH_SCREEN - GraphicsView.WIDTH_SCREEN / 20 - 50,
                        (int) (GraphicsView.HEIGHT_SCREEN * 0.9),
                        GraphicsView.WIDTH_SCREEN / 10,
                        75
                )
        );
    }

    /**
     * Manage button to activate depending on object selected and return true if an action is done
     * @param buttons all buttons
     * @param location location of click
     * @param actionManager manage action
     * @param gameData data of the game
     */
    private static void manageButtons(List<Button> buttons, Point2D.Float location, ActionManager actionManager, Model gameData) {
        switch (actionManager.getAction()) {
            case CARD -> manageCardButton(buttons, location, gameData, actionManager);
            case DECK -> manageDeckButton(buttons, location, gameData, actionManager);
            case TOKEN -> manageTokenButton(buttons, location, gameData, actionManager);
            case RESERVED_CARD -> manageReservedCardButton(buttons, location, gameData, actionManager);
            default -> {
            }
        }
    }

    /**
     * Manage action token if tokens are selected. Return true if an action is done
     * @param buttons all buttons
     * @param location location of click
     * @param gameData data of the game
     * @param actionManager manage action
     */
    private static void manageTokenButton(List<Button> buttons, Point2D.Float location, Model gameData, ActionManager actionManager) {
        var selectedTokens = actionManager.getSelectedTokens();
        var tokens = gameData.getGameTokens();
        var takeSelectedTokens = buttons.get(2);

        if (takeSelectedTokens.rect().contains(location)) {
            if (selectedTokens.size() == 1 && tokens.get(selectedTokens.get(0)) >= 4) {
                actionManager.take2Tokens(gameData);
            }
            else if (selectedTokens.size() >= 1 && selectedTokens.size() == Math.min(3, tokens.numbersOfTokensLeft())) {
                actionManager.takeTokens(gameData);
            }
        }


    }

    /**
     * Manage action click when deck is selected to reserve card of deck. Return true if an action is done.
     * @param buttons all buttons
     * @param location location of click
     * @param gameData data of the game
     * @param actionManager manage action
     */
    private static void manageDeckButton(List<Button> buttons, Point2D.Float location, Model gameData, ActionManager actionManager) {
        var reserve = buttons.get(1);
        var selectedDeck = actionManager.getSelectedDeck();

        if (gameData.reservePossible() && gameData.getPlayerPlaying().canReserve() && reserve.rect().contains(location)) {
            var card = gameData.getDecks().get(selectedDeck).draw();
            actionManager.selectCard(card, 0, selectedDeck);
            actionManager.reserveDeck(gameData);
        }
    }

    /**
     * Manage action click when card is selected to buy or reserve if user click on button and return true if an action
     * is done
     * @param buttons all buttons
     * @param location location of click
     * @param gameData data of the game
     * @param actionManager manage action
     */
    private static void manageCardButton(List<Button> buttons, Point2D.Float location, Model gameData, ActionManager actionManager) {
        var buy = buttons.get(0);
        var reserve = buttons.get(1);
        var card = actionManager.getSelectedCard();

        if (gameData.getPlayerPlaying().canBuy(card) && buy.rect().contains(location)) {
            actionManager.buyCard(gameData);
        } else if (gameData.reservePossible() && gameData.getPlayerPlaying().canReserve() && reserve.rect().contains(location)) {
            actionManager.reserveCard(gameData);
        }
    }

    /**
     * Manage click on reserve card button
     * @param buttons all buttons
     * @param location location of click
     * @param gameData data of game
     * @param actionManager manage action
     */
    private static void manageReservedCardButton(List<Button> buttons, Point2D.Float location, Model gameData, ActionManager actionManager) {
        var buy = buttons.get(0);
        var card = actionManager.getSelectedCard();

        if (gameData.getPlayerPlaying().canBuy(card) && buy.rect().contains(location)) {
            actionManager.buyReservedCard(gameData);
        }
    }

    /**
     * Manage event when user clicked
     * @param location location of click
     * @param actionManager manage action
     * @param gameData data of the game
     * @param images all images
     * @param buttons all buttons
     */
    private static void haveClickedGameStarted(Point2D.Float location, ActionManager actionManager,
                                               Model gameData, ImageManager images, List<Button> buttons) {
        if (actionManager.getAction() != ActionManager.Action.END_TURN) {
            manageAction(location, actionManager, gameData, images);
            manageButtons(buttons, location, actionManager, gameData);
        }
        else if (manageEndTurn(buttons, location)) {
            gameData.endTurn();
            actionManager.setAction(ActionManager.Action.NONE);
        }
    }
}
