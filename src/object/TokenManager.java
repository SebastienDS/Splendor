package object;

import java.util.*;

public record TokenManager(Map<Token, Integer> tokenManager) {
    public TokenManager {
        Objects.requireNonNull(tokenManager);
    }

    public TokenManager() {
        this(emptyTokens());
    }

    private static Map<Token, Integer> emptyTokens() {
        var tokenManager = new LinkedHashMap<Token, Integer>();
        initToken(tokenManager);
        return tokenManager;
    }

    private static void initToken(Map<Token, Integer> tokenManager){
        tokenManager.put(Token.EMERALD, 0);
        tokenManager.put(Token.SAPPHIRE, 0);
        tokenManager.put(Token.RUBY, 0);
        tokenManager.put(Token.DIAMOND, 0);
        tokenManager.put(Token.ONYX, 0);
        tokenManager.put(Token.GOLD, 0);
    }

    public void addToken(Token token, int value) {
        Objects.requireNonNull(token);
        tokenManager.replace(token, tokenManager.get(token) + value);
    }

    public Map<Token, Integer> tokenNotEmpty(){
        var tokenNotEmpty = new LinkedHashMap<Token, Integer>();
        tokenManager.keySet().forEach(token -> {
            if (tokenManager.get(token) > 0) tokenNotEmpty.put(token, tokenManager.get(token));
        });
        return tokenNotEmpty;
    }

    public void clear() {
        tokenManager.clear();
        initToken(tokenManager);
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        String separator = "";
        text.append("[");
        for (var token : tokenManager.keySet()) {
            text.append(separator).append(token.name()).append(": ").append(tokenManager.get(token));
            separator = ", ";
        }
        text.append("]");
        return text.toString();
    }

    public Set<Token> keySet() {
        return tokenManager.keySet();
    }

    public int get(Token token) {
        return tokenManager.get(token);
    }
}
