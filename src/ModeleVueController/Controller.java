package ModeleVueController;

import object.*;

import java.io.IOException;
import java.util.*;

public class Controller {

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

    public static int getInteger(Scanner scanner) {
        return getInteger(scanner, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void startingMenu(Scanner scanner, Model gameData) throws IOException {
        firstPhaseDeck(gameData.getDecks());
        while (true) {
            View.printStartingMenu();
            var input_menu = Controller.getInteger(scanner);
            switch (input_menu) {
                case(0): playerMenu(scanner, gameData); break;
                case(1): gameModeMenu(scanner, gameData); break;
                case(2): startGame(scanner, gameData); return;
                default: View.printChoiceDoNotExist(input_menu);
            }
        }
    }

    private static void startGame(Scanner scanner, Model gameData) {
        gameData.startGame();
        while (!gameData.getLastRound() || !gameData.startNewRound()) {
            firstChoice(scanner, gameData);
            gameData.endTurn();
        }
    }

    private static void allInformationChoice(Model gameData){
        View.printGround(gameData.getGrounds(), gameData.getDecks());
        View.printPlayerPlaying(gameData.getPlayerPlaying());
        View.printPlayerResource(gameData.getPlayerPlaying());
        View.printFirstChoicePlayer();
    }

    private static void firstChoice(Scanner scanner, Model gameData) { //todo add choice to show reserved card, to show personal stats
        var turnFinished = false;
        while (true) {
           allInformationChoice(gameData);
            var input_choice = getInteger(scanner);
            switch (input_choice) {
                case(1): turnFinished = manageChoiceToken(scanner, gameData);break; //todo verif that player will not be blocked (not enough token)
                case(2): turnFinished = manageChoiceTokens(scanner, gameData);break; //todo verif that player will not be blocked (not enough token)
                case(3): Controller.reserveCard(scanner, gameData); return; //todo verif that player can reserve
                case(4): Controller.buyCard(scanner, gameData); return; //todo all the function
                default: View.printChoiceDoNotExist(input_choice);
            }
            if(turnFinished) return;
        }
    }

    private static boolean manageChoiceTokens(Scanner scanner, Model gameData) {
        if(!(Arrays.stream(Token.cardValues()).anyMatch(token -> gameData.getGameTokens().get(token) > 0))) {
            View.printNoTokenLeft();
            return false;
        }
        return Controller.chooseTokens(scanner, gameData);
    }

    private static boolean manageChoiceToken(Scanner scanner, Model gameData) {
        if(!(Arrays.stream(Token.cardValues()).anyMatch(token -> gameData.getGameTokens().get(token) >= 4))){
            View.printNotEnoughTokens();
            return false;
        }
        var token = Controller.chooseToken(scanner, gameData, 4);
        if(token == null) return false;
        gameData.takeToken(token, 2);
        return true;
    }

    private static void buyCard(Scanner scanner, Model gameData) { //todo

    }

    private static void reserveCard(Scanner scanner, Model gameData) {
        View.printGround(gameData.getGrounds(), gameData.getDecks());
        var deckName = new ArrayList<>(gameData.getGrounds().keySet());
        if(deckName.contains(DeckName.NOBLE_DECK)) deckName.remove(DeckName.NOBLE_DECK);
        View.printChooseDeck(gameData.getGrounds());
        var input_choice = getInteger(scanner, 1, deckName.size());
        if(input_choice > 0 && input_choice <= deckName.size()){
            var deckNameChosen = deckName.get(input_choice - 1);
            chooseCard(scanner, gameData, deckNameChosen);
            return;
        }
    }

    private static void chooseCard(Scanner scanner, Model gameData, DeckName deckName) {
        var groundCards = gameData.getGrounds().get(deckName);
        var deck = gameData.getDecks().get(deckName);
        View.printCards(groundCards, deck.size());
        var input_choice = getInteger(scanner, 1, groundCards.size() + 2);
        if(input_choice > 0 && input_choice < groundCards.size() + 1){

        }
        if(input_choice == groundCards.size() + 2){
            var card = deck.draw();
            gameData.getPlayerPlaying().reserve(card);
        }
    }

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
                case(3): if (manageConfirmTokens(scanner, tokenChosen, gameData)) return true;
                default: View.printChoiceDoNotExist(input_choice);
            }
        }
    }

    private static boolean manageConfirmTokens(Scanner scanner, List<Token> tokenChosen, Model gameData) {
        if (tokenChosen.size() == 3) {
            tokenChosen.stream().forEach(token -> gameData.takeToken(token, 1));
            return true;
        }
        View.printNotEnoughTokenChosen(tokenChosen.size());
        return false;
    }

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

    private static void gameModeMenu(Scanner scanner, Model gameData) throws IOException {
        View.printGameModeMenu();
        var input_choice = Controller.getInteger(scanner, 1, 2);
        var decks = gameData.getDecks();
        switch (input_choice) {
            case(1): firstPhaseDeck(decks); return;
            case(2): secondPhaseDeck(decks); return;
        }
    }

    private static void firstPhaseDeck(Map<DeckName, Deck<Card>> decks) throws IOException {
        decks.clear();
        decks.put(DeckName.BASIC_DECK, Decks.basicDeck());
    }

    private static void secondPhaseDeck(Map<DeckName, Deck<Card>> decks) throws IOException {
        decks.clear();
        decks.put(DeckName.NOBLE_DECK, Decks.nobleDeck());
        decks.put(DeckName.FIRST_DEV_DECK, Decks.firstDevelopmentDeck());
        decks.put(DeckName.SECOND_DEV_DECK, Decks.secondDevelopmentDeck());
        decks.put(DeckName.THIRD_DEV_DECK, Decks.thirdDevelopmentDeck());
    }

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

    private static void removePlayer(List<Player> players, Scanner scanner) {
        if (players.size() == 2) {
            View.printNotEnoughPlayer();
            return;
        }
        players.remove(choosePlayer(players, scanner));
    }

    private static void addPlayer(List<Player> players, Scanner scanner) {
        if (players.size() == 4) {
            View.printTooMuchPlayer();
            return;
        }
        players.add(new Player(Controller.getName(scanner)));
    }

    private static int choosePlayer(List<Player> players, Scanner scanner) {
        View.printChoosePlayer(players);
        var i = getInteger(scanner, 0, players.size() - 1);
        return i;
    }

    private static void changeName(Scanner scanner, Model gameData) {
        var players = gameData.getPlayers();
        var indexPlayer = choosePlayer(players, scanner);
        var name = getName(scanner);
        players.set(indexPlayer, new Player(name));
    }

    private static String getName(Scanner scanner) {
        View.printChooseName();
        var firstToken = scanner.next();
        return firstToken + scanner.nextLine();
    }
}
