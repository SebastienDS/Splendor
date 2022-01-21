package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents all the controls of the players during the game
 */
public class Controller {

    /**
     * Let the user choose an integer between min and max
     * @param scanner to have the input
     * @param min min value of the integer
     * @param max max value of the integer
     * @return integer chosen
     */
    public static int getInteger(Scanner scanner, int min, int max) {
        int input_integer;
        while (true) {
            try {
                input_integer = scanner.nextInt();
                if (input_integer >= min && input_integer <= max) break;
                View.printChoiceDoNotExist(input_integer);
            } catch (InputMismatchException e) {
                View.printNeedNumber();
                scanner.nextLine();
            }
        }
        return input_integer;
    }

    /**
     * Let the user choose an Integer
     * @param scanner to have the input
     * @return integer chosen by the user
     */
    public static int getInteger(Scanner scanner) {
        return getInteger(scanner, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * this method let the player choose the option he wants to modify and start the game.
     * He can change the number of player and their name or/and change the game mode
     * @param scanner to have the input
     * @param gameData data of the game
     * @throws IOException if an I/O exception occurs
     */
    public static void startingMenu(Scanner scanner, Model gameData) throws IOException {
        gameData.setGameMode(2);

        while (true) {
            View.printStartingMenu();
            var input_menu = Controller.getInteger(scanner);
            switch (input_menu) {
                case (0) -> playerMenu(scanner, gameData);
                case (1) -> gameModeMenu(scanner, gameData);
                case (2) -> {
                    startGame(scanner, gameData);
                    return;
                }
                default -> View.printChoiceDoNotExist(input_menu);
            }
        }
    }

    /**
     * This method start the game with the option chosen by the user
     * @param scanner to have the input
     * @param gameData data of the game
     */
    private static void startGame(Scanner scanner, Model gameData){
        gameData.startGame();
        while (!gameData.getLastRound() || !gameData.startNewRound()) {
            firstChoice(scanner, gameData);
            gameData.endTurn();
        }
    }

    /**
     * This method show all the information of the round for the player playing
     * @param gameData data of the game
     */
    private static void allInformationChoice(Model gameData){
        View.printPlayerPlaying(gameData.getPlayerPlaying());
        View.printPlayerResource(gameData.getPlayerPlaying(), gameData.getGameMode());
        View.printBank(gameData.getGameTokens(), gameData.getGameMode());
        View.printFirstChoicePlayer(gameData);
    }

    /**
     * Let the player choose the action he wants to do during his tour. He can choose:
     * -to take 2 same tokens if the number of this token is at least 4
     * -to take 3 different tokens
     * -to buy a card
     * -to reserve card and get a gold token
     * -to see his bonus(don't change player playing)
     * -to see his card reserved(don't change player playing)
     * @param scanner to have the input
     * @param gameData data of the game
     */
    private static void firstChoice(Scanner scanner, Model gameData){
        var turnFinished = false;

        View.printNoble(gameData.getNobles());
        View.printGround(gameData.getGrounds(), gameData.getDecks());
        while (true) {
            allInformationChoice(gameData);
            var input_choice = getInteger(scanner);
            switch (input_choice) {
                case(1): turnFinished = manageChoiceToken(scanner, gameData); break;
                case(2): turnFinished = manageChoiceTokens(scanner, gameData); break;
                case(3): turnFinished = Controller.buyCard(scanner, gameData); break;
                case(4):
                    var bonus = gameData.getPlayerPlaying().getBonus();
                    View.printPlayerBonus(bonus);
                    break;
                case(5): if (gameData.reservePossible()) {
                    turnFinished = Controller.reserveCard(scanner, gameData); break;
                }
                case(6): if (gameData.reservePossible()) {
                    showReservedCards(gameData);
                    break;
                }
                default: View.printChoiceDoNotExist(input_choice);
            }
            if(endOfTurn(scanner, gameData, turnFinished)) return;
        }
    }

    /**
     * This method manage if the player have too many tokens and make the noble go to the player when he has enough bonus
     * if the turn is finished
     * @param scanner to have the input
     * @param gameData data of the game
     * @param turnFinished true if turn is finished
     * @return true if turn is finished
     */
    private static boolean endOfTurn(Scanner scanner, Model gameData, boolean turnFinished) {
        if (turnFinished) {
            var wallet = gameData.getPlayerPlaying().getWallet();
            if (sizeWithoutGold(wallet) > 10) {
                removeExcessTokens(scanner, gameData, wallet, 10);
            }
            manageNoble(gameData);
            return true;
        }
        return false;
    }

    /**
     * This method make the noble go to the player that finished playing if he has enough bonus. The noble is removed
     * from the ground and his prestige are added to the player.
     * @param gameData data of the game
     */
    private static void manageNoble(Model gameData) {
        if(gameData.getGameMode() == 2){
            var bonus = gameData.getPlayerPlaying().getBonus();
            var nobles =   gameData.getNobles();
            nobles.removeIf(noble -> {
                        if(noble.cost().keySet()
                                .stream()
                                .filter(token -> noble.cost().get(token) > bonus.get(token))
                                .findAny()
                                .isEmpty()){
                            gameData.getPlayerPlaying().addNoble(noble);
                            return true;
                        }
                        return false;
                    });
        }
    }

    /**
     * launch the menu that let the player choose 3 different token to take
     * @param scanner to have the input
     * @param gameData data of the game
     * @return true if the player have chosen, false if there was no token or the player canceled
     */
    private static boolean manageChoiceTokens(Scanner scanner, Model gameData) {
        if(Arrays.stream(Token.cardValues()).noneMatch(token -> gameData.getGameTokens().get(token) > 0)) {
            View.printNoTokenLeft();
            return false;
        }
        return Controller.chooseTokens(scanner, gameData);
    }

    /**
     * Let the user choose a token. Then he gains 2 tokens of it if the number is superior to 4
     * @param scanner to have the input
     * @param gameData data of the game
     * @return true if the player have chosen a valid token false otherwise
     */
    private static boolean manageChoiceToken(Scanner scanner, Model gameData) {
        if(Arrays.stream(Token.cardValues()).noneMatch(token -> gameData.getGameTokens().get(token) >= 4)){
            View.printNotEnoughTokens();
            return false;
        }
        var token = Controller.chooseToken(scanner, gameData, 4);
        if(token == null) return false;
        gameData.takeToken(token, 2);
        return true;
    }

    /**
     * Let the player choose a list from grounds to choose a card in it. The card then be purchased or reserved
     * @param scanner to have the input
     * @param gameData data of the game
     * @param display verb to display(purchase or reserve)
     * @param buy true if the player is buying
     */
    private static Development chooseACardWithGrounds(Scanner scanner, Model gameData, String display, boolean buy){
        var player = gameData.getPlayerPlaying();
        var grounds = gameData.getGrounds();
        var decks = gameData.getDecks();
        showPurchasableCard(grounds, player, display, buy);
        var total = gameData.getGrounds().values().size() + ((player.getCardReserved().size() > 0 && buy)? 1 : 0);
        var input_choice = (total == 1)? 1 : getInteger(scanner, 0, total);
        if(input_choice == 0){
            return null;
        }
        if(input_choice == total && player.getCardReserved().size() > 0 && buy){
            return chooseCard(scanner, player.getCardReserved(), null, player, true);
        }
        var deckName = new ArrayList<>(gameData.getGrounds().keySet());
        var deckNameChosen = deckName.get(input_choice - 1);
        return chooseCard(scanner, grounds.get(deckNameChosen), decks.get(deckNameChosen), player, buy);
    }

    /**
     * Let player choose a list of cards to buy a card from. Then the player choose a card from the list chosen to
     * buy it.
     * @param scanner to have the input
     * @param gameData data of the game
     * @return true if player have chosen a card, false if the player canceled
     */
    private static boolean buyCard(Scanner scanner, Model gameData){
        var card = chooseACardWithGrounds(scanner, gameData, View.getBuy(), true);
        if(card == null) return false;
        var tokens = gameData.getPlayerPlaying().buy(card);
        gameData.addTokenUsed(tokens);
        return true;
    }

    /**
     * This method show all the cards the player can purchase
     * @param grounds grounds of game
     * @param player player currently playing
     * @param display verb to show (purchase or reserve)
     */
    private static void showPurchasableCard(Map<Integer, List<Development>> grounds, Player player, String display, boolean buy) {
        var i = 1;
        var reserve = player.getCardReserved();
        View.printChooseGround(display);
        for (var deckName: grounds.keySet()) {
            View.printCardsWithIndex(grounds.get(deckName), i);
            i++;
        }
        if(reserve.size() > 0 && buy) View.printCardsWithIndex(reserve, i);
    }

    /**
     * Let player choose a list of cards to reserve a card from. Then the player choose a card from the list chosen to
     * reserve it.
     * @param scanner to have the input
     * @param gameData data of the game
     * @return true if a card was chosen false if player canceled
     */
    private static boolean reserveCard(Scanner scanner, Model gameData){
        if(!gameData.getPlayerPlaying().canReserve()){
            View.printCantReserve();
            return false;
        }
        var card = chooseACardWithGrounds(scanner, gameData, View.getReserved(), false);
        if(card == null) return false;
        gameData.getPlayerPlaying().reserve(card);
        gameData.getPlayerPlaying().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? 1: 0);
        return true;
    }

    /**
     * Let the user choose a card from cards to buy or reserve it in function of boolean buy. Then, if he purchased one
     * another card of deck will be added to cards
     * @param scanner to have the input
     * @param cards list of cards to chose from
     * @param deck deck corresponding to the cards
     * @param player player currently chosing
     * @param buy true if player is buying false if he is reserving
     * @return card chosen (null if canceled)
     */
    private static Development chooseCard(Scanner scanner, List<Development> cards, Deck<Development> deck, Player player, boolean buy){
        while(true){
            View.printCards(cards, (buy)? View.getParenthesis(): View.getReserve(cards.size() + 1));
            var input_choice = (buy)? getInteger(scanner, 0, cards.size() + 1):getInteger(scanner, 0, cards.size() + 2);
            if(input_choice == 0) return null;
            if(input_choice > 0 && input_choice <= cards.size()) {
                if(!buy || player.canBuy(cards.get(input_choice - 1))){
                    drawDeck(cards, deck);
                    return cards.remove(input_choice - 1);
                }
                View.printDontHaveEnoughToken();
                continue;
            }
            if(!buy && input_choice == cards.size() + 1){
                return deck.draw();
            }
            View.printChoiceDoNotExist(input_choice);
        }
    }

    /**
     * This method draw a card from the deck to add it to the list cards
     * @param cards list of cards where card drawn will be added
     * @param deck deck where card will be drawn
     */
    private static void drawDeck(List<Development> cards, Deck<Development> deck) {
        if(deck != null) cards.add(deck.draw());
    }

    /**
     * Let the user take 2 tokens if the number of token chosen if superior to number
     * @param scanner to have the input
     * @param gameData data of the game
     * @param number least number of token chosen
     * @return true if player have chosen a token and false if he canceled
     */
    private static Token chooseToken(Scanner scanner, Model gameData, int number) {
        var tokens = Token.cardValues();
        while (true){
            View.printTokens(gameData);
            var input_choice = getInteger(scanner, 0, tokens.length);
            if(input_choice == 0) return null;
            var token = tokens[input_choice - 1];
            if (gameData.getGameTokens().get(token) >= number) {
               return token;
            }
            View.printNotEnoughToken();
        }
    }

    /**
     * Let the user choose what he wants to do to choose the token he wants to take. He can add a token, remove a token
     * already chosen, confirm his choice or cancel.
     * @param scanner to have the input
     * @param gameData data of the game
     * @return true if player confirmed his choice and false if he canceled
     */
    private static boolean chooseTokens(Scanner scanner, Model gameData) {
        var tokenChosen = new ArrayList<Token>();
        while (true) {
            View.printTokenChosen(tokenChosen);
            View.printMenuChooseToken();
            var input_choice = getInteger(scanner);
            switch (input_choice) {
                case(0): return false;
                case(1): manageAddToken(scanner, tokenChosen, gameData); break;
                case(2): manageRemoveToken(scanner, tokenChosen); break;
                case(3): if (manageConfirmTokens(tokenChosen, gameData)) return true; break;
                default: View.printChoiceDoNotExist(input_choice);
            }
        }
    }

    /**
     * Let the player confirm his choice of tokens if he chose enough token
     * @param tokenChosen list of token chosen by the player
     * @param gameData data of the game
     * @return true if player have chosen enough token
     */
    private static boolean manageConfirmTokens(List<Token> tokenChosen, Model gameData) {
        var size = gameData.getGameTokens()
                .keySet().stream().filter(token -> Token.GOLD != token)
                .collect(Collectors.toSet()).size();
        var maxSize = Math.min(size, 3);
        if (tokenChosen.size() == maxSize) {
            tokenChosen.forEach(token -> gameData.takeToken(token, 1));
            return true;
        }
        View.printNotEnoughTokenChosen(tokenChosen.size(), maxSize);
        return false;
    }

    /**
     * Let the user remove a token from his tokenChosen list
     * @param scanner to have the input
     * @param tokenChosen list of token chosen by the player
     */
    private static void manageRemoveToken(Scanner scanner, List<Token> tokenChosen) {
        if (tokenChosen.size() == 0) {
            View.printNoTokenChosen();
            return;
        }
        View.printTokenChosenWithIndex(tokenChosen);
        var input_choice = getInteger(scanner, 1, tokenChosen.size() + 1);
        if (input_choice == tokenChosen.size() + 1) {
            return;
        }
        tokenChosen.remove(input_choice - 1);
    }

    /**
     * Let the user choose a token to add to his tokenChosen list
     * @param scanner to have the input
     * @param tokenChosen list of token chosen by the player
     * @param gameData data of the game
     */
    private static void manageAddToken(Scanner scanner, List<Token> tokenChosen, Model gameData) {
        if (tokenChosen.size() >= 3) {
            View.printTooMuchToken();
            return;
        }
        var token = chooseToken(scanner, gameData, 1);
        if(token == null) return;
        if (tokenChosen.contains(token)) {
            View.printTokenAlreadyChosen(token.name());
            return;
        }
        tokenChosen.add(token);
    }

    /**
     * Let the user choose between all the game mode
     * @param scanner to have the input
     * @param gameData data of the game
     * @throws IOException if an I/O exception occurs
     */
    private static void gameModeMenu(Scanner scanner, Model gameData) throws IOException {
        View.printGameModeMenu();
        var input_choice = Controller.getInteger(scanner, 1, 2);
        switch (input_choice) {
            case 1 -> gameData.setGameMode(1);
            case 2 -> gameData.setGameMode(2);
        }
    }

    /**
     * This method let the user choose the option of the game(number of player, player name, game mode) and start the game
     * @param scanner to have the input
     * @param gameData data of the game
     */
    private static void playerMenu(Scanner scanner, Model gameData) {
        while (true) {
            View.printPlayerMenu(gameData);
            var input_choice = getInteger(scanner, 0, 3);
            var players = gameData.getPlayers();
            switch (input_choice) {
                case(0): changeName(scanner, gameData); break;
                case(1): addPlayer(players, scanner); break;
                case(2): removePlayer(players, scanner); break;
                case(3): return;
            }
        }
    }

    /**
     * This method let the user remove another player in the games if the number of player is superior to 2
     * @param players list of all players
     * @param scanner scanner to have the input
     */
    private static void removePlayer(List<Player> players, Scanner scanner) {
        if (players.size() == 2) {
            View.printNotEnoughPlayer();
            return;
        }
        players.remove(choosePlayer(players, scanner));
    }

    /**
     * This method let the user add a players in the games if the number of player is inferior to 4
     * @param players list of all players
     * @param scanner scanner to have the input
     */
    private static void addPlayer(List<Player> players, Scanner scanner) {
        if (players.size() == 4) {
            View.printTooMuchPlayer();
            return;
        }
        players.add(new Player(Controller.getName(scanner)));
    }

    /**
     * This method let the user choose a player in the players list
     * @param players list of all players
     * @param scanner scanner to have the input
     * @return index of the player chosen
     */
    private static int choosePlayer(List<Player> players, Scanner scanner) {
        View.printChoosePlayer(players);
        return getInteger(scanner, 0, players.size() - 1);
    }

    /**
     * This method make the user choose a player to change his name
     * @param scanner scanner to have the input
     * @param gameData data of the game
     */
    private static void changeName(Scanner scanner, Model gameData) {
        var players = gameData.getPlayers();
        var indexPlayer = choosePlayer(players, scanner);
        var name = getName(scanner);
        players.set(indexPlayer, new Player(name));
    }

    /**
     * This method return the string of the name chosen from the user
     * @param scanner scanner to have the input of player
     * @return string of the name chosen from the user
     */
    private static String getName(Scanner scanner) {
        View.printChooseName();
        var firstToken = scanner.next();
        return firstToken + scanner.nextLine();
    }

    /**
     * This method return the number of tokens in tokenManager without taking into account gold token in
     * @param tokenManager contains all tokens
     * @return the number of tokens in tokenManager without taking into account gold token
     */
    private static int sizeWithoutGold(TokenManager tokenManager) {
        return tokenManager.tokens()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() != Token.GOLD)
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);
    }

    /**
     * This method create a list of all tokens that doesn't have a value of 0 and aren't gold
     * @param tokenManager contains all tokens and their value
     * @return list created
     */
    private static List<Token> getNotEmptyTokens(TokenManager tokenManager) {
        return tokenManager.tokens()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() != Token.GOLD && entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * This method manage the removing of token to have a total of maxTokens tokens at the end
     * @param scanner scanner to have input of player
     * @param gameData data of the game
     * @param wallet player wallet
     * @param maxTokens max total of tokens
     */
    private static void removeExcessTokens(Scanner scanner, Model gameData, TokenManager wallet, int maxTokens) {
        while (sizeWithoutGold(wallet) > maxTokens) {
            var notEmptyTokens = getNotEmptyTokens(wallet);
            View.printAskRemoveExcessToken();
            View.printTokens(wallet, notEmptyTokens);
            var input_choice = getInteger(scanner, 1, notEmptyTokens.size());
            var token = notEmptyTokens.get(input_choice - 1);
            wallet.addToken(token, -1);
            gameData.getGameTokens().addToken(token, 1);
        }
    }

    /**
     * This class show the card reserved by the player playing
     * @param gameData data of the game
     */
    private static void showReservedCards(Model gameData) {
        var cards = gameData.getPlayerPlaying().getCardReserved();

        if (cards.size() == 0) {
            View.printNoCardsReserved();
        } else {
            View.printCardsGround(cards);
        }
    }
}
