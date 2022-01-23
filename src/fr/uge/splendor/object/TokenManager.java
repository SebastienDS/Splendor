package fr.uge.splendor.object;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represent an instance of a map with token as key and integer as value.
 * Is used for saving tokens of the player, bonus of the player and price of the cards.
 */
public record TokenManager(Map<Token, Integer> tokens) {
    /**
     * Create an instance of tokenManager with his field map associated with the non-null specified value tokenManager
     * @param tokens instance of tokenManager
     */
    public TokenManager {
        Objects.requireNonNull(tokens);
    }

    /**
     * Create an instance of tokenManager
     */
    public TokenManager() {
        this(new LinkedHashMap<>());
    }

    /**
     * This method add the specified value to the chosen token.
     * @param token specified token
     * @param value specified value
     */
    public void addToken(Token token, int value) {
        Objects.requireNonNull(token);
        tokens.merge(token, value, Integer::sum);
    }

    /**
     * This method clear all the value of the token manager
     */
    public void clear() {
        tokens.clear();
    }

    /**
     * This method return the set of all keys of the map
     * @return the set of all keys of the map
     */
    public Set<Token> keySet() {
        return tokens.keySet();
    }

    /**
     * This method return the value associated to the specified token
     * @param token specified token
     * @return the value associated to the specified token
     */
    public int get(Token token) {
        Objects.requireNonNull(token);
        return tokens.getOrDefault(token, 0);
    }

    /**
     * This method return the numbers of tokens left which is not GOLD token
     * @return sum of tokens left without golds
     */
    public int numbersOfTokensLeft() {
        return tokens.entrySet()
                .stream()
                .filter(e -> e.getKey() != Token.GOLD && e.getValue() != 0)
                .map(Map.Entry::getValue)
                .reduce(0, (a, b) -> a + 1);
    }
}
