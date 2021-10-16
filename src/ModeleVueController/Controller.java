package ModeleVueController;

import object.DeckName;
import object.Decks;
import object.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {

    public static int getInteger(Scanner scanner) {
        while (true){
            try {
                var input_integer = scanner.nextInt();
                return input_integer;
            } catch (InputMismatchException e) {
                System.out.println("Vous devez écrire un nombre !");
                scanner.next();
            }
        }
    }

    public static void startingMenu(Scanner scanner, Model gameData){
        while(true){
            View.printStartingMenu();
            var input_menu = Controller.getInteger(scanner);
            switch (input_menu){
                case(0): Controller.playerMenu(scanner, gameData); break;
                case(1): Controller.gameModeMenu(scanner, gameData); break;
                case(2): return;
            }
        }
    }

    private static void gameModeMenu(Scanner scanner, Model gameData) {
        View.printGameModeMenu();
        while (true){
            var input_choice = Controller.getInteger(scanner);
            switch (input_choice){
                case(1): {
                    var decks = gameData.getDecks();
                    decks.clear();
                    decks.put(DeckName.BASIC_DECK, Decks.BasicDeck());
                    return;
                }
                case(2): {
                    var decks = gameData.getDecks();
                    decks.clear();
                    decks.put(DeckName.FIRST_DEV_DECK, Decks.FirstDevelopmentDeck());
                    decks.put(DeckName.SECOND_DEV_DECK, Decks.SecondDevelopmentDeck());
                    decks.put(DeckName.THIRD_DEV_DECK, Decks.ThirdDevelopmentDeck());
                    return;
                }
            }
        }
    }

    private static void playerMenu(Scanner scanner, Model gameData) {
        while(true) {
            View.printPlayerMenu(gameData);
            var input_choice = Controller.getInteger(scanner);
            var players = gameData.getPlayers();
            switch (input_choice){
                case(0): Controller.changeName(scanner, gameData);break;
                case(1): {
                    if((players.size() == 4)) {
                        View.printTooMuchPlayer();
                        break;
                    }
                    players.add(new Player(Controller.getName()));
                    break;
                }
                case(2): {
                    if((players.size() == 2)){
                        View.printNotEnoughPlayer();
                        break;
                    }
                    Player playerToRemove = Controller.getPlayerToRemove();
                    players.remove(playerToRemove);
                }
            }
        }
    }

    private static Player getPlayerToRemove() {
        //toDO
        return new Player("cc");
    }

    private static void changeName(Scanner scanner, Model gameData) {
        //toDO
    }

    private static String getName() {
        //toDO
        return "";
    }
}
