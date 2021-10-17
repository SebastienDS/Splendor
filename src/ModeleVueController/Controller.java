package ModeleVueController;

import object.Deck;
import object.DeckName;
import object.Decks;
import object.Player;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                case(2): return;
                default: View.printChoiceDoNotExist(input_menu);
            }
        }
    }

    private static void gameModeMenu(Scanner scanner, Model gameData) {
        View.printGameModeMenu();
        while (true){
            var input_choice = Controller.getInteger(scanner);
            var decks = gameData.getDecks();
            switch (input_choice) {
                case(1): firstPhaseDeck(decks);  return;
                case(2): secondPhaseDeck(decks); return;
            }
        }
    }

    private static void firstPhaseDeck(Map<DeckName, Deck> decks){
        decks.clear();
        decks.put(DeckName.BASIC_DECK, Decks.basicDeck());
    }

    private static void secondPhaseDeck(Map<DeckName, Deck> decks){
        decks.clear();
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
        return ""; //to do
    }
}
