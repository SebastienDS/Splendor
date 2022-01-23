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
     * Create a deck of card created with the file "nobleDeck.txt" and return it
     * @return a deck of card created with the file "nobleDeck.txt"
     * @throws IOException if file "nobleDeck.txt" in resource directory doesn't exist
     */
    public static List<Noble> nobleDeck() throws IOException {
        return makeNobleDeck(Path.of("resources", "nobleDeck.txt"));
    }

    /**
     * Create decks of cards created with the file "DevelopmentDeck.txt" and return it
     * @return decks of cards created with the file "DevelopmentDeck.txt"
     * @throws IOException if file "DevelopmentDeck.txt" in resource directory doesn't exist
     */
    public static Map<Integer, Deck<Development>> developmentDecks() throws IOException {
        return makeDevelopmentDecks(Path.of("resources", "DevelopmentDeck.txt"));
    }

    /**
     * This method make a nobleDeck from the file in path and with the specified name and return it
     * @param path of file
     * @return a deck from the file in path and with the specified name
     * @throws IOException if file in path don't exist
     */
    private static List<Noble> makeNobleDeck(Path path) throws IOException {
        Objects.requireNonNull(path);

        var nobleDeck = new ArrayList<Noble>();
        parseNobleDeck(path, nobleDeck);
        return nobleDeck;
    }

    /**
     * This method make a deck from the file in path and with the specified name
     * @param path of file
     * @return a deck from the file in path and with the specified name
     * @throws IOException if file in path don't exist
     */
    private static Map<Integer, Deck<Development>> makeDevelopmentDecks(Path path) throws IOException {
        Objects.requireNonNull(path);

        var decks = new LinkedHashMap<Integer, Deck<Development>>();
        parseDevelopmentDecks(path, decks);
        return decks;
    }

    /**
     * This method parse the file and add all card to the deck specified
     * @param path of the file
     * @param deck with card to add
     * @throws IOException if file in path doesn't exist
     */
    private static void parseNobleDeck(Path path, List<Noble> deck) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(deck);

        try (var reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() == 0) continue; // Allow Empty Line

                var params = line.split(ARGS_SEPARATOR);
                var args = List.of(params).subList(1, params.length);

                int count = Integer.parseInt(params[0]);

                var card = parseNoble(args);

                for (int i = 0; i < count; i++) {
                    deck.add(card);
                }
            }
        }
    }

    /**
     * This method parse the file and add the card to the deck of his level
     * @param path of the file
     * @param decks with card to add to the right level
     * @throws IOException if file in path doesn't exist
     */
    private static void parseDevelopmentDecks(Path path, Map<Integer, Deck<Development>> decks) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(decks);

        try (var reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() == 0) continue; // Allow Empty Line

                var params = line.split(ARGS_SEPARATOR);
                var args = List.of(params).subList(2, params.length);

                int count = Integer.parseInt(params[0]);
                int level = Integer.parseInt(params[1]);

                var card = parseDevelopment(args);

                for (int i = 0; i < count; i++) {
                    decks.computeIfAbsent(level, k -> new Deck<>()).add(card);
                }
            }
        }
    }

    /**
     * Create a token manager from a string
     * @param string String containing tokenManager informations
     * @return tokenManager
     */
    private static TokenManager parseTokens(String string) {
        var tokens = new TokenManager();

        for (var tokenFormat : string.split(TOKEN_SEPARATOR)) {
            var entryToken = tokenFormat.split(VALUE_SEPARATOR);
            var token = Token.valueOf(entryToken[0]);
            var value = Integer.parseInt(entryToken[1]);

            if (value != 0) tokens.addToken(token, value);
        }

        return tokens;
    }

    /**
     * Create a development card from the information given by the args
     * @param args list of string with all card information
     * @return card created
     */
    private static Development parseDevelopment(List<String> args) {
        Objects.requireNonNull(args);

        var className = args.get(0);
        if (!className.equals(Development.TYPE)) throw new IllegalArgumentException("Incompatible type " + className + " and " + Development.TYPE);

        var tokenManager = parseTokens(args.get(2));
        var prestige = Integer.parseInt(args.get(3));

        return new Development(tokenManager, args.get(1), prestige, Token.valueOf(args.get(4)));
    }

    /**
     * Create a noble card from the information given by the args
     * @param args list of string with all card information
     * @return card created
     */
    private static Noble parseNoble(List<String> args) {
        Objects.requireNonNull(args);

        var className = args.get(0);
        if (!className.equals(Noble.TYPE)) throw new IllegalArgumentException("Incompatible type " + className + " and " + Noble.TYPE);

        var tokenManager = parseTokens(args.get(2));
        var prestige = Integer.parseInt(args.get(3));

        return new Noble(tokenManager, args.get(1), prestige);
    }
}
