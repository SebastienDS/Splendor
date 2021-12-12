package fr.uge.splendor.object;

import java.util.*;

/**
 * Represent an instance of a map with token as key and integer as value.
 * Is used for saving tokens of the player, bonus of the player and price of the cards.
 */
public record TokenManager(Map<Token, Integer> tokenManager) {
    /**
     * Create an instance of tokenManager with his field map associated with the non-null specified value tokenManager
     * @param tokenManager instance of tokenManager
     */
    public TokenManager {
        Objects.requireNonNull(tokenManager);
    }

    /**
     * Create an instance of tokenManager with token associated to null value(0)
     */
    public TokenManager() {
        this(emptyTokens());
    }

    /**
     * This method return the string corresponding of the values of the map with the form [key: value, ...]
     * @return the string corresponding of the values of the map with the form [key: value, ...]
     */
    @Override
    public String toString() {
        return toString(tokenManager.keySet());
    }

    /**
     * This method return a string corresponding of the values of the specified Set with the form [key: value, ...]
     * @param keySet set of all token to add to string
     * @return the string corresponding of the values of the specified Set with the form [key: value, ...]
     */
    public String toString(Set<Token> keySet) {
        StringBuilder text = new StringBuilder();
        String separator = "";
        text.append("[");
        for (var token : keySet) {
            text.append(separator).append(token.name()).append(": ").append(tokenManager.get(token));
            separator = ", ";
        }
        text.append("]");
        return text.toString();
    }

    /**
     * This method add the specified value to the chosen token.
     * @param token specified token
     * @param value specified value
     */
    public void addToken(Token token, int value) {
        Objects.requireNonNull(token);
        tokenManager.replace(token, tokenManager.get(token) + value);
    }

    /**
     * This method return a map of all the tokens associate with non-null value (0) with token as key and number of
     * token as value (int)
     * @return map of all the tokens associate with non-null value (0) with token as key and number of token as value (int)
     */
    public Map<Token, Integer> tokenNotEmpty() {
        var tokenNotEmpty = new LinkedHashMap<Token, Integer>();
        tokenManager.keySet().forEach(token -> {
            if (tokenManager.get(token) > 0) tokenNotEmpty.put(token, tokenManager.get(token));
        });
        return tokenNotEmpty;
    }

    /**
     * This method clear all the value of the token manager to reinitialise them at 0 for each token
     */
    public void clear() {
        tokenManager.clear();
        initToken(tokenManager);
    }

    /**
     * This method return the set of all keys of the map
     * @return the set of all keys of the map
     */
    public Set<Token> keySet() {
        return tokenManager.keySet();
    }

    /**
     * This method return the value associated to the specified token
     * @param token specified token
     * @return the value associated to the specified token
     */
    public int get(Token token) {
        return tokenManager.get(token);
    }

    /**
     * This method return a map with all token initialise at 0
     * @return a map with all token initialise at 0
     */
    private static Map<Token, Integer> emptyTokens() {
        var tokenManager = new LinkedHashMap<Token, Integer>();
        initToken(tokenManager);
        return tokenManager;
    }

    /**
     * This method add all token to tokenManager and init them at 0
     * @param tokenManager map that need to be initiated
     */
    private static void initToken(Map<Token, Integer> tokenManager) {
        tokenManager.put(Token.EMERALD, 0);
        tokenManager.put(Token.SAPPHIRE, 0);
        tokenManager.put(Token.RUBY, 0);
        tokenManager.put(Token.DIAMOND, 0);
        tokenManager.put(Token.ONYX, 0);
        tokenManager.put(Token.GOLD, 0);
    }
}
