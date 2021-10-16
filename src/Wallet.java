import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record Wallet(Map<Token, Integer> wallet) {
    public Wallet{
        Objects.requireNonNull(wallet);
    }

    public Wallet() {
        this(emptyWallet());
    }

    private static Map<Token, Integer> emptyWallet() {
        Map<Token, Integer> wallet = new HashMap<>();
        wallet.put(Token.DIAMOND, 0);
        wallet.put(Token.EMERALD, 0);
        wallet.put(Token.GOLD, 0);
        wallet.put(Token.ONYX, 0);
        wallet.put(Token.RUBY, 0);
        wallet.put(Token.SAPPHIRE, 0);
        return wallet;
    }

    public void addMoney(Token token, int value) {
        wallet.replace(token, wallet.get(token) + value);
    }
}
