package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.*;

import static fr.uge.splendor.object.Utils.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * This class represent all the string to print of the game
 */
public class View {
    /**
     * Print the starting menu
     */
    public static void printStartingMenu() {
        var string = """
                Choississez un menu:
                0: Menu des Joueurs
                1: Choix du jeu
                2: Lancer la partie""";
        System.out.println(string);
    }

    /**
     * Print the game mode menu
     */
    public static void printGameModeMenu() {
        var string = """
                Choississez le mode de jeu:
                1: Mode de jeu partie 1
                2: Mode de jeu partie 2 / 3""";
        System.out.println(string);
    }

    /**
     * Print the player menu
     * @param gameData data of the game
     */
    public static void printPlayerMenu(Model gameData) {
        var sb = new StringBuilder();
        sb.append("Joueur Actuelle:\n");
        gameData.getPlayers().forEach(player -> sb.append(player.getName()).append('\n'));

        sb.append("Que voulez vous faire?\n")
            .append("0: Modifier un nom\n")
            .append("1: Ajouter un joueur\n")
            .append("2: Supprimer un joueur\n")
            .append("3: Quitter le menu joueur");

        System.out.println(sb);
    }

    /**
     * Print error when maximum number of player is reached and player wants to add another one
     */
    public static void printTooMuchPlayer() {
        System.out.println("Le nombre maximum de joueur est de 4 !");
    }

    /**
     * Print error when minimum number of player is reached and player wants to delete another one
     */
    public static void printNotEnoughPlayer() {
        System.out.println("Le nombre minimum de joueur est de 2 !");
    }

    /**
     * Print error when choice doesn't exist
     * @param n choice made
     */
    public static void printChoiceDoNotExist(int n) {
        System.out.println("Le choix: \"" + n + "\" n'existe pas");
    }

    /**
     * Print error when choice of the player isn't a number
     */
    public static void printNeedNumber() {
        System.out.println("Vous devez écrire un nombre !");
    }

    /**
     * Print menu to choose a player
     * @param players list of all players
     */
    public static void printChoosePlayer(List<Player> players) {
        var text = new StringBuilder();
        text.append("Qui choisissez vous?");
        for (int i = 0; i < players.size(); i++) {
            text.append('\n').append(i).append(": ").append(players.get(i).getName());
        }
        System.out.println(text);
    }

    /**
     * Print menu to choose a name
     */
    public static void printChooseName() {
        System.out.println("Choisissez un nom :");
    }

    /**
     * Print current player playing
     * @param player player playing
     */
    public static void printPlayerPlaying(Player player) {
        System.out.println("C'est au tour du joueur: \"" + player.getName() + "\" de joué! ");
    }

    /**
     * Print resources of the current player
     * @param playerPlaying player playing
     * @param gameMode game mode
     */
    public static void printPlayerResource(Player playerPlaying, int gameMode) {
        var keySet = getKeySet(playerPlaying.getWallet());
        if(gameMode == 2) keySet = playerPlaying.getWallet().keySet();
        System.out.println(playerPlaying.getName() + ' ' + printTokenManager(playerPlaying.getWallet(), keySet));
        System.out.println("Prestige : " + playerPlaying.getPrestige());
    }

    /**
     * Print choice of the player
     * @param gameData data of the game
     */
    public static void printFirstChoicePlayer(Model gameData) {
        var text = new StringBuilder();
        text.append("Que voulez vous faire ?\n")
            .append("1: Prendre 2 jetons de la même couleur\n")
            .append("2: Prendre 3 jetons de couleurs différentes\n")
            .append("3: Acheter 1 carte développement face visible au centre de la table ou préalablement réservée.\n")
            .append("4: Afficher les bonus du joueur.");
        if (gameData.reservePossible()) {
            text.append("\n5: Réserver 1 carte développement et prendre 1 or\n")
                    .append("6: Afficher les cartes réservés");
        }

        System.out.println(text);
    }

    /**
     * Print menu to choose token
     * @param gameData data of the game
     */
    public static void printTokens(Model gameData) {
        var text = new StringBuilder();
        text.append("Quel jeton ?(0 pour annuler)");
        var gameTokens = gameData.getGameTokens();
        var iteration = 1;
        for (var token: Token.cardValues()) {
            text.append('\n').append(iteration).append(": ").append(token).append("(quantity:").append(gameTokens.get(token)).append(")");
            iteration++;
        }
        System.out.println(text);
    }

    /**
     * Print menu to choose different token
     */
    public static void printMenuChooseToken() {
        String text = """
                Que voulez vous faire?(0 pour annuler)
                1: Choisir un jeton
                2: Retirer un jeton choisis
                3: Confirmer le choix""";
        System.out.println(text);
    }

    /**
     * Print error when there isn't enough token chosen
     */
    public static void printNotEnoughToken() {
        System.out.println("Le nombre de jeton choisis restant est insuffisant !");
    }

    /**
     * Print error when player already has 3 token in list
     */
    public static void printTooMuchToken() {
        System.out.println("Vous avez déjà séléctionner 3 jetons !");
    }

    /**
     * Print error when token chosen is already selected
     * @param tokenName name of token chosen
     */
    public static void printTokenAlreadyChosen(String tokenName) {
        System.out.println("Jeton déjà séléctionné: " + tokenName);
    }

    /**
     * Print all tokens chosen by the player
     * @param tokenChosen list of all token chosen
     */
    public static void printTokenChosen(List<Token> tokenChosen) {
        var text = new StringBuilder();
        var separator = "";
        text.append("Jeton(s) Choisis: [");
        for (var token : tokenChosen){
            text.append(separator).append(token.name());
            separator = ", ";
        }
        text.append("]");
        System.out.println(text);
    }

    /**
     * Print error when player doesn't have enough token selected and try to confirm
     * @param number number of token chosen
     * @param size max size of list of token
     */
    public static void printNotEnoughTokenChosen(int number, int size) {
        var s = (size > 1)? "s": "";
        System.out.println("Vous n'avez choisis que " + number + " / " + size + " jeton" + s);
    }

    /**
     * Print all token chosen with index and remove menu
     * @param tokenChosen  list of token chosen
     */
    public static void printTokenChosenWithIndex(List<Token> tokenChosen) {
        var text = new StringBuilder();
        text.append("Jeton(s) Choisis: [");
        for (int i = 0; i < tokenChosen.size(); i++) {
            text.append("\n").append(i + 1).append(": ").append(tokenChosen.get(i).name());
        }
        text.append("\n]\nLequel voulez vous supprimer? (").append(tokenChosen.size() + 1).append(" pour annuler)");
        System.out.println(text);
    }

    /**
     * Print error when player hasn't chosen any token but want to remove one
     */
    public static void printNoTokenChosen() {
        System.out.println("Vous n'avez pas encore choisis de jeton !");
    }

    /**
     * Print all grounds with each ground card left to right
     * @param grounds grounds to print
     * @param decks decks of the corresponding grounds
     */
    public static void printGround(Map<Integer, List<Development>> grounds, Map<Integer, Deck<Development>> decks){
        var print = new StringBuilder();
        for (var deck: grounds.keySet()) {
            printCardLeftToRight(grounds.get(deck), print, decks.get(deck), true, false);
        }
        System.out.println(print);
    }

    /**
     * This method add string of cards of grounds to StringBuilder print to print them left to right
     * @param grounds cards to print
     * @param print StringBuilder to add string
     * @param deck deck of the corresponding grounds
     * @param deckSize true if print of deck size needed
     * @param withIndex true if print of index needed
     */
    private static void printCardLeftToRight(List<Development> grounds, StringBuilder print, Deck<Development> deck, boolean deckSize, boolean withIndex) {
        var index = 1;
        var indexString = "  ";
        for (int i = 0; i < Constants.DISPLAY_SIZE; i++) {
            for (var card : grounds) {
                if(i == 1){
                    indexString = index + ":";
                    index++;
                }
                if(withIndex) print.append(indexString);
                print.append(getDisplayCard(card, i));
            }
            indexString = "  ";
            if(deckSize) print.append(getDisplayDeck(deck, i));
            print.append("\n");
        }
    }

    /**
     * Print choosing card menu with all card of cards indexed
     * @param cards cards to print
     * @param reserve string to add after the question (need at least a parenthesis)
     */
    public static void printCards(List<Development> cards, String reserve) {
        var print = new StringBuilder();
        print.append("Choisissez la carte ?").append(reserve);
        print.append("0 pour annuler)\n");
        printCardLeftToRight(cards, print, null, false, true);
        System.out.print(print);
    }

    /**
     * Print error if no tokens currently have at least 4 tokens in the game
     */
    public static void printNotEnoughTokens() {
        System.out.println("Il doit y avoir au minimum 4 jetons de la même couleur restant pour pouvoir en prendre 2 de la même couleur !");
    }

    /**
     * Print error if there isn't any token left to take
     */
    public static void printNoTokenLeft() {
        System.out.println("Il n'y a plus aucun jeton a récupérer !");
    }

    /**
     * Print error if player already have 3 cards reserved
     */
    public static void printCantReserve() {
        System.out.println("Vous ne pouvez pas réserver plus de 3 cartes !");
    }

    /**
     * Print index with a list of cards
     * @param cards cards to print
     * @param index index of the list
     */
    public static void printCardsWithIndex(List<Development> cards, int index) {
        System.out.println(index + ":");
        printCardsGround(cards);
    }

    /**
     * Print menu to choose ground
     * @param display string representing purchase or reserve
     */
    public static void printChooseGround(String display) {
        System.out.println("Choisissez sur quel terrain vous voulez " + display + " la carte ?(0 pour annuler)");
    }

    /**
     * This method return the string to buy
     * @return string to buy
     */
    public static String getBuy() {
        return "acheter";
    }

    /**
     * This method return the string to reserve
     * @return string to reserve
     */
    public static String getReserved() {
        return "réserver";
    }

    /**
     * Print error when player don't have enough token to purchase the card
     */
    public static void printDontHaveEnoughToken() {
        System.out.println("La carte est trop chère pour être acheté.");
    }

    /**
     * This method return a string of a parenthesis
     * @return string of a parenthesis
     */
    public static String getParenthesis() {
        return "(";
    }

    /**
     * This method return the string for the option to reserve the next card to draw
     * @param number number the player need to input to reserve the next card to draw
     * @return string for the option to reserve the next card to draw
     */
    public static String getReserve(int number){
        return "(" + number + " pour choisir de réserver la carte pioché et ";
    }

    /**
     * Print token currently owned by the player
     * @param tokenManager wallet of the player
     * @param tokens list of token that need to be printed
     */
    public static void printTokens(TokenManager tokenManager, List<Token> tokens) {
        var string = IntStream.range(0, tokens.size())
                .mapToObj(i -> "" + (i + 1) + " : " + tokens.get(i) + " [" + tokenManager.get(tokens.get(i)) + "]")
                .collect(Collectors.joining("\n"));

        System.out.println(string);
    }

    /**
     * Print error when player have more than 10 tokens
     */
    public static void printAskRemoveExcessToken() {
        System.out.println("Vous avez trop jetons, quel token voulez-vous retirer ?");
    }

    /**
     * Prints card of cards from left to right
     * @param cards list of cards to print
     */
    public static void printCardsGround(List<Development> cards) {
        var print = new StringBuilder();
        printCardLeftToRight(cards, print, null, false, false);
        System.out.print(print);
    }

    /**
     * This method return set of Token from tokenManager without the gold token.
     * @param tokenManager bonus of the player
     * @return set of Token from tokenManager without the gold token.
     */
    private static Set<Token> getKeySet(TokenManager tokenManager) { //todo change set to have same token order
        return tokenManager.keySet().stream()
                .filter(t -> t != Token.GOLD)
                .collect(Collectors.toSet());
    }

    /**
     * Print the bonus the current playing player has
     * @param bonus of the player
     */
    public static void printPlayerBonus(TokenManager bonus) {
        var keySet = getKeySet(bonus);
        System.out.println("Bonus : " + printTokenManager(bonus, keySet) + "\n");
    }

    /**
     * Print error when player doesn't have any card reserved but try to see them
     */
    public static void printNoCardsReserved() {
        System.out.println("Vous n'avez pas de cartes réservées");
    }

    /**
     * Print the winner of the game
     * @param winner winner of the game
     */
    public static void printWinner(Player winner) {
        System.out.println("Le joueur : \"" + winner.getName() + "\" a gagné ! Bien joué à toi !");
    }

    /**
     * Print all game tokens
     * @param gameTokens game tokens
     * @param gameMode game mode
     */
    public static void printBank(TokenManager gameTokens, int gameMode) {
        StringBuilder text = new StringBuilder();
        String separator = "";
        text.append("Banque : [");
        for (var token : gameTokens.keySet()) {
            if(token == Token.GOLD && gameMode == 1){
                continue;
            }
            text.append(separator).append(token.name()).append(": ").append(gameTokens.get(token));
            separator = ", ";
        }
        text.append("]");
        System.out.println(text);
    }

    /**
     * This method return a string corresponding of the values of the specified Set with the form [key: value, ...]
     * @param keySet set of all token to add to string
     * @return the string corresponding of the values of the specified Set with the form [key: value, ...]
     */
    private static String printTokenManager(TokenManager tokenManager, Set<Token> keySet) {
        StringBuilder text = new StringBuilder();
        String separator = "";
        text.append("[");
        for (var token : keySet) {
            text.append(separator).append(token.name()).append(": ").append(tokenManager.tokens().get(token));
            separator = ", ";
        }
        text.append("]");
        return text.toString();
    }

    private static String getDisplayCard(Development card, int i) {
        var display = stringDisplayCard(card);
        if (i > display.length) {
            throw new IllegalArgumentException("i(" + i +") is higher than length of display(" + display.length + ")");
        }
        return display[i];
    }

    /**
     * This method create the tab of all string to display to draw the card and return it
     * @return the tab of all string to display to draw the card
     */
    private static String[] stringDisplayCard(Development card) {
        var prestigeLength = String.valueOf(card.prestige()).length() + "Prestige: ".length();
        var bonusLength =  card.bonus().name().length() + "Bonus: ".length();
        var priceLength = "Price: ".length();
        var name = card.name();
        var centerName = getCenterName(card);
        return new String[] {
                " "+ "_".repeat(Constants.MAX_LENGTH) + " ",
                "|" + space(centerName) + name + space((name.length() % 2 == 1)? centerName + 1 : centerName) + "|",
                "|Prestige: " + card.prestige() + space(Constants.MAX_LENGTH - prestigeLength) + "|",
                "|Bonus: " + card.bonus().name() + space(Constants.MAX_LENGTH - bonusLength) + "|",
                "|Price: " + space(Constants.MAX_LENGTH - priceLength) + "|",
                "|" + stringPrice(card, 0) + space(Constants.MAX_LENGTH - stringPrice(card, 0).length()) + "|",
                "|" + stringPrice(card, 1) + space(Constants.MAX_LENGTH - stringPrice(card, 1).length()) + "|",
                "|" + stringPrice(card, 2) + space(Constants.MAX_LENGTH - stringPrice(card, 2).length()) + "|",
                "|" + stringPrice(card, 3) + space(Constants.MAX_LENGTH - stringPrice(card, 3).length()) + "|",
                " " + "-".repeat(Constants.MAX_LENGTH) + " "
        };
    }

    /**
     * This method return the string value of the i-th price of the card or an empty list if the price doesn't exist
     * @param i th price
     * @return string value of the i-th price of the card or an empty list if the price doesn't exist
     */
    private static String stringPrice(Development card, int i){
        var price = card.cost().keySet().stream().map(token -> token.name() + " : " + card.cost().get(token)).toList();
        return (card.cost().keySet().size() >  i)? price.get(i) : "";
    }

    /**
     * This method return the spaces needed to center the card's name
     * @return spaces needed to center the name
     */
    private static int getCenterName(Development card) {
        return (Constants.MAX_LENGTH - card.name().length()) / 2;
    }


    /**
     * This method refresh the value saved of the display
     */
    private static String[] refreshDisplay(Deck<Development> deck) {
        var size = deck.size();
        var numberOfDigit = (size == 0)? 1: (int) Math.log10(size) + 1;
        var numberDisplay = getArrayNumber(numberOfDigit, size);
        var spacing = numberOfDigit * Constants.LENGTH_NUMBER_DISPLAY + (numberOfDigit - 1);
        return getStringDeck(spacing,numberDisplay);
    }

    /**
     * This method create a table of string corresponding of the display of the deck
     * @param spacing space between the start of the card and the end
     * @param numberDisplay List of table of string corresponding to the display of the number of element in the deck
     * @return the table created
     */
    private static String[] getStringDeck(int spacing, List<String[]> numberDisplay){
        return new String[]{
                " " + "_".repeat(spacing + 2) + " ",
                "|" + " ".repeat(spacing + 2) + "|",
                "|" + " ".repeat(spacing + 2) + "|",
                "| " + getNumberDisplay(numberDisplay, 0) + " |",
                "| " + getNumberDisplay(numberDisplay, 1) + " |",
                "| " + getNumberDisplay(numberDisplay, 2) + " |",
                "| " + getNumberDisplay(numberDisplay, 3) + " |",
                "| " + getNumberDisplay(numberDisplay, 4) + " |",
                "|" + " ".repeat(spacing + 2) + "|",
                " " + "-".repeat(spacing + 2) + " "
        };
    }

    /**
     * This method return the list of table of string corresponding to the display of number
     * @param numberOfDigit number of digits in the size
     * @param size size of deck
     * @return list of table of string corresponding to the display of number
     */
    private static List<String[]> getArrayNumber(int numberOfDigit, int size){
        var numberDisplay = new ArrayList<String[]>();
        for (var i = numberOfDigit - 1; i >= 0; i--){
            numberDisplay.add(NumbersDisplays.NUMBERS[size /(int) Math.pow(10, i)]);
            size %= Math.pow(10, i);
        }
        return numberDisplay;
    }

    /**
     * This method return the sum of all the string of the i-th element of each table of number display and separate them by a space
     * @param numberDisplay list of table of string corresponding to display of the size of deck
     * @param i th line
     * @return joining of all the string of the i-th element of each table of numberDisplay with a space delimiter
     */
    private static String getNumberDisplay(List<String[]> numberDisplay, int i) {
        return numberDisplay.stream().map(strings -> strings[i]).collect(Collectors.joining(" "));
    }

    /**
     * This method return the string of the display of the deck for the i-th line
     * @param i th line
     * @return string of the display of the deck for the i-th line
     */
    public static String getDisplayDeck(Deck<Development> deck, int i) {
        var display = refreshDisplay(deck);
        return display[i];
    }
}
