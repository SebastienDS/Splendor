package ModeleVueController;

import object.*;

import javax.lang.model.type.ArrayType;
import java.util.*;

public class Controller {

    public static int getInteger(Scanner scanner) {
        while (true){
            try {
                var input_integer = scanner.nextInt();
                return input_integer;
            } catch (InputMismatchException e) {
                View.printNeedNumber();
                scanner.nextLine();
            }
        }
    }

    public static void startingMenu(Scanner scanner, Model gameData) {
        while(true){
            View.printStartingMenu();
            var input_menu = Controller.getInteger(scanner);
            switch (input_menu){
                case(0): playerMenu(scanner, gameData); break;
                case(1): gameModeMenu(scanner, gameData); break;
                case(2): startGame(scanner, gameData); return;
                default: View.printChoiceDoNotExist(input_menu);
            }
        }
    }

    private static void startGame(Scanner scanner, Model gameData) {
        gameData.startGame();
        while(!gameData.getLastRound() || !gameData.startNewRound()) {
            View.printPlayerPlaying(gameData.getPlayerPlaying());
            View.printPlayerResource(gameData.getPlayerPlaying());
            firstChoice(scanner, gameData);
            gameData.endTurn();
        }
    }

    private static void firstChoice(Scanner scanner, Model gameData) {
        while(true){
            View.printFirstChoicePlayer();
            var input_choice = getInteger(scanner);
            switch (input_choice){
                case(1): gameData.takeToken(Controller.chooseToken(scanner, gameData, 4), 2);return;
                case(2): Controller.chooseTokens(scanner, gameData);return;
                case(3): Controller.reserveCard(scanner, gameData);return;
                case(4): Controller.buyCard(scanner, gameData);return;
                default: View.printChoiceDoNotExist(input_choice);
            }
        }
    }

    private static void buyCard(Scanner scanner, Model gameData) {
    }

    private static void reserveCard(Scanner scanner, Model gameData) {
    }

    private static Token chooseToken(Scanner scanner, Model gameData, int number) {
        var tokens = Token.values();
        while(true){
            View.printTokens(gameData);
            var input_choice = getInteger(scanner) - 1;
            if(input_choice >= tokens.length - 1 || input_choice < 0){
                View.printChoiceDoNotExist(input_choice);
                continue;
            }
            var token = tokens[input_choice];
            if(gameData.getGameTokens().get(token) >= number){
                return token;
            }
            View.printNotEnoughToken();
        }
    }

    private static void chooseTokens(Scanner scanner, Model gameData) {
        var tokenChosen = new ArrayList<Token>();
        while(true){
            View.printTokenChosen(tokenChosen);
            View.printMenuChooseToken();
            var input_choice = getInteger(scanner);
            switch (input_choice){
                case(1): manageAddToken(scanner, tokenChosen, gameData);break;
                case(2): manageRemoveToken(scanner, tokenChosen);break;
                case(3): if(manageConfirmTokens(scanner, tokenChosen, gameData)) return; break;
                default:View.printChoiceDoNotExist(input_choice);
            }
        }
    }

    private static boolean manageConfirmTokens(Scanner scanner, ArrayList<Token> tokenChosen, Model gameData){
        if(tokenChosen.size() == 3){
            tokenChosen.stream().forEach(token -> gameData.takeToken(token, 1));
            return true;
        }
        View.printNotEnoughTokenChosen(tokenChosen.size());
        return false;
    }

    private static void manageRemoveToken(Scanner scanner, List<Token> tokenChosen) {
        if(tokenChosen.size() == 0){
            View.printNoTokenChosen();
            return;
        }
        while (true) {
            View.printTokenChosenWithIndex(tokenChosen);
            var input_choice = getInteger(scanner);
            if(input_choice < 1 || input_choice > tokenChosen.size() + 1){
                View.printChoiceDoNotExist(input_choice);
                continue;
            }
            if(input_choice == tokenChosen.size() + 1){
                return;
            }
            tokenChosen.remove(input_choice - 1);
            return;
        }
    }

    private static void manageAddToken(Scanner scanner, List<Token> tokenChosen, Model gameData) {
        if(tokenChosen.size() >= 3){
            View.printTooMuchToken();
            return;
        }
        var token = chooseToken(scanner, gameData, 1);
        if(tokenChosen.contains(token)){
            View.printTokenAlreadyChosen(token.name());
            return;
        }
        tokenChosen.add(token);
    }

    private static void gameModeMenu(Scanner scanner, Model gameData) {
        while (true){
            View.printGameModeMenu();
            var input_choice = Controller.getInteger(scanner);
            var decks = gameData.getDecks();
            switch (input_choice) {
                case(1): firstPhaseDeck(decks); return;
                case(2): secondPhaseDeck(decks); return;
                default: View.printChoiceDoNotExist(input_choice);
            }
        }
    }

    private static void firstPhaseDeck(Map<DeckName, Deck> decks){
        decks.clear();
        decks.put(DeckName.BASIC_DECK, Decks.basicDeck());
    }

    private static void secondPhaseDeck(Map<DeckName, Deck> decks){
        decks.clear();
        decks.put(DeckName.NOBLE_DECK, Decks.nobleDeck());
        decks.put(DeckName.FIRST_DEV_DECK, Decks.firstDevelopmentDeck());
        decks.put(DeckName.SECOND_DEV_DECK, Decks.secondDevelopmentDeck());
        decks.put(DeckName.THIRD_DEV_DECK, Decks.thirdDevelopmentDeck());
    }

    private static void playerMenu(Scanner scanner, Model gameData) {
        while(true) {
            View.printPlayerMenu(gameData);
            var input_choice = getInteger(scanner);
            var players = gameData.getPlayers();
            switch (input_choice){
                case(0): changeName(scanner, gameData);break;
                case(1): addPlayer(players, scanner); break;
                case(2): removePlayer(players, scanner);break;
                case(3): return;
                default: View.printChoiceDoNotExist(input_choice);
            }
        }
    }

    private static void removePlayer(List<Player> players, Scanner scanner) {
        if(players.size() == 2) {
            View.printNotEnoughPlayer();
            return;
        }
       players.remove(choosePlayer(players, scanner));
    }

    private static void addPlayer(List<Player> players, Scanner scanner) {
        if(players.size() == 4) {
            View.printTooMuchPlayer();
            return;
        }
        players.add(new Player(Controller.getName(scanner)));
    }

    private static int choosePlayer(List<Player> players, Scanner scanner) {
        while(true){
            View.printChoosePlayer(players);
            var i = getInteger(scanner);
            if(i < 0 || i >= players.size()){
                View.printChoiceDoNotExist(i);
                continue;
            }
            return i;
        }
    }

    private static void changeName(Scanner scanner, Model gameData) {
        var players = gameData.getPlayers();
        var indexPlayer = choosePlayer(players, scanner);
        var name = getName(scanner);
        players.set(indexPlayer, new Player(name));
    }

    private static String getName(Scanner scanner) {
        View.printChooseName();
        String firstToken = scanner.next();
        return firstToken + scanner.nextLine();
    }
}
