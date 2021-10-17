package object;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record TokenManager(Map<Token, Integer> tokenManager) {
    public TokenManager {
        Objects.requireNonNull(tokenManager);
    }

    public TokenManager() {
        this(emptyTokens());
    }

    private static Map<Token, Integer> emptyTokens() {
        Map<Token, Integer> tokenManager = new HashMap<>();
        tokenManager.put(Token.DIAMOND, 0);
        tokenManager.put(Token.EMERALD, 0);
        tokenManager.put(Token.GOLD, 0);
        tokenManager.put(Token.ONYX, 0);
        tokenManager.put(Token.RUBY, 0);
        tokenManager.put(Token.SAPPHIRE, 0);
        return tokenManager;
    }

    public void addToken(Token token, int value) {
        Objects.requireNonNull(token);
        tokenManager.replace(token, tokenManager.get(token) + value);
    }

    public Map<Token, Integer> tokenNotEmpty(){
        var tokenNotEmpty = new HashMap<Token, Integer>();
        tokenManager.keySet().forEach(token -> {
            if (tokenManager.get(token) > 0) tokenNotEmpty.put(token, tokenManager.get(token));
        });
        return tokenNotEmpty();
    }

    public void clear() {
        tokenManager.clear();
    }
}
