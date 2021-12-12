package fr.uge.splendor.object;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Class representing all the static function for Deck
 */
public class Decks {

    /**
     * Constant of the string separator of the argument in the file
     */
    private static final String ARGS_SEPARATOR = ";";

    /**
     * Constant of the string separator of the token in the file
     */
    private static final String TOKEN_SEPARATOR = ",";

    /**
     * Constant of the string separator of the value in the file
     */
    private static final String VALUE_SEPARATOR = ":";

    /**
     * Create a deck of card created with the file "basicDeck.txt" and return it
     * @return a deck of card created with the file "basicDeck.txt"
     * @throws IOException if file "basicDeck.txt" in resource directory doesn't exist
     */
    public static Deck<Card> basicDeck() throws IOException {
        return makeDeck(Path.of("resources", "basicDeck.txt"), "Basic Deck");
    }

    /**
     * Create a deck of card created with the file "nobleDeck.txt" and return it
     * @return a deck of card created with the file "nobleDeck.txt"
     * @throws IOException if file "nobleDeck.txt" in resource directory doesn't exist
     */
    public static Deck<Card> nobleDeck() throws IOException {
        return makeNobleDeck(Path.of("resources", "nobleDeck.txt"), "Noble");
    }

    /**
     * Create a deck of card created with the file "firstDevelopmentDeck.txt" and return it
     * @return a deck of card created with the file "firstDevelopmentDeck.txt"
     * @throws IOException if file "firstDevelopmentDeck.txt" in resource directory doesn't exist
     */
    public static Deck<Card> firstDevelopmentDeck() throws IOException {
        return makeDeck(Path.of("resources", "firstDevelopmentDeck.txt"), "Développement 1");
    }

    /**
     * Create a deck of card created with the file "secondDevelopmentDeck.txt" and return it
     * @return a deck of card created with the file "secondDevelopmentDeck.txt"
     * @throws IOException if file "secondDevelopmentDeck.txt" in resource directory doesn't exist
     */
    public static Deck<Card> secondDevelopmentDeck() throws IOException {
        return makeDeck(Path.of("resources", "secondDevelopmentDeck.txt"), "Développement 2");
    }

    /**
     * Create a deck of card created with the file "thirdDevelopmentDeck.txt" and return it
     * @return a deck of card created with the file "thirdDevelopmentDeck.txt"
     * @throws IOException if file "thirdDevelopmentDeck.txt" in resource directory doesn't exist
     */
    public static Deck<Card> thirdDevelopmentDeck() throws IOException {
        return makeDeck(Path.of("resources", "thirdDevelopmentDeck.txt"), "Développement 3");
    }

    /**
     * This method make a nobleDeck from the file in path and with the specified name and return it
     * @param path of file
     * @param name of deck
     * @return a deck from the file in path and with the specified name
     * @throws IOException if file in path don't exist
     */
    private static Deck<Card> makeNobleDeck(Path path, String name) throws IOException {
        Objects.requireNonNull(name);
        var nobleDeck = new NobleDeck(name);
        parseDeck(path, nobleDeck);
        return nobleDeck;
    }

    /**
     * This method make a deck from the file in path and with the specified name
     * @param path of file
     * @param name of deck
     * @return a deck from the file in path and with the specified name
     * @throws IOException if file in path don't exist
     */
    private static Deck<Card> makeDeck(Path path, String name) throws IOException {
        Objects.requireNonNull(name);
        var deck = new BasicDeck<Card>(name);
        parseDeck(path, deck);
        return deck;
    }

    /**
     * This method parse the file and add all card to the deck specified
     * @param path of the file
     * @param deck with card to add
     * @throws IOException if file in path doesn't exist
     */
    private static void parseDeck(Path path, Deck<Card> deck) throws IOException {
        Objects.requireNonNull(path);
        var tokenManager = new TokenManager();

        try (var reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() == 0) continue; // Allow Empty Line

                var params = line.split(ARGS_SEPARATOR);
                var args = List.of(params).subList(1, params.length);

                int count = Integer.parseInt(params[0]);
                for (int i = 0; i < count; i++) {
                    deck.add(parseCard(line, tokenManager, args));
                    tokenManager.clear();
                }
            }
        }
    }

    /**
     * Create a card from the information given by the args by using the tokenManger to create the cost
     * @param line
     * @param tokenManager to add cost to card
     * @param args list of string with all card information
     * @return card created
     */
    private static Card parseCard(String line, TokenManager tokenManager, List<String> args) { //todo demander à seb pour utilité de line
        Objects.requireNonNull(line);
        Objects.requireNonNull(tokenManager);
        Objects.requireNonNull(args);

        var className = args.get(0);
        for (var tokenFormat : args.get(2).split(TOKEN_SEPARATOR)) {
            var entrytoken = tokenFormat.split(VALUE_SEPARATOR);
            var token = Token.valueOf(entrytoken[0]);
            var value = Integer.parseInt(entrytoken[1]);

            tokenManager.addToken(token, value);
        }

        var prestige = Integer.parseInt(args.get(3));

        return switch (className) {
            case Noble.TYPE -> new Noble(tokenManager.tokenNotEmpty(), args.get(1), Path.of(args.get(4)), prestige);
            case Development.TYPE -> new Development(tokenManager.tokenNotEmpty(), args.get(1), Path.of(args.get(4)), prestige, Token.valueOf(args.get(5)));
            default -> throw new IllegalStateException("Unexpected value: " + className);
        };
    }
}
