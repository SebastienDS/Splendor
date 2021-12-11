package object;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Decks {

    private static final String ARGS_SEPARATOR = ";";
    private static final String TOKEN_SEPARATOR = ",";
    private static final String VALUE_SEPARATOR = ":";

    public static Deck<Card> basicDeck() throws IOException {
        return makeDeck(Path.of("resources", "basicDeck.txt"), "Basic Deck");
    }

    public static Deck<Card> nobleDeck() throws IOException {
        return makeNobleDeck(Path.of("resources", "nobleDeck.txt"), "Noble");
    }

    public static Deck<Card> firstDevelopmentDeck() throws IOException {
        return makeDeck(Path.of("resources", "firstDevelopmentDeck.txt"), "Développement 1");
    }

    public static Deck<Card> secondDevelopmentDeck() throws IOException {
        return makeDeck(Path.of("resources", "secondDevelopmentDeck.txt"), "Développement 2");
    }

    public static Deck<Card> thirdDevelopmentDeck() throws IOException {
        return makeDeck(Path.of("resources", "thirdDevelopmentDeck.txt"), "Développement 3");
    }

    private static Deck<Card> makeNobleDeck(Path path, String name) throws IOException {
        Objects.requireNonNull(name);
        var nobleDeck = new NobleDeck(name);
        parseDeck(path, nobleDeck);
        return nobleDeck;
    }

    private static Deck<Card> makeDeck(Path path, String name) throws IOException {
        Objects.requireNonNull(name);
        var deck = new BasicDeck<Card>(name);
        parseDeck(path, deck);
        return deck;
    }

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

    private static Card parseCard(String line, TokenManager tokenManager, List<String> args) {
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
            case Noble.TYPE -> new Noble(tokenManager.tokenNotEmpty(), args.get(1), args.get(4), prestige);
            case Development.TYPE -> new Development(tokenManager.tokenNotEmpty(), args.get(1), args.get(4), prestige, Token.valueOf(args.get(5)));
            default -> throw new IllegalStateException("Unexpected value: " + className);
        };
    }
}
