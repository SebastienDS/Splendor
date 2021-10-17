package object;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {

    private final String name;
    private final TokenManager wallet;
    private final List<Development> cardBuy;
    private final TokenManager bonus;
    private int prestige;

    public Player(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        wallet = new TokenManager();
        cardBuy = new ArrayList<>();
        bonus = new TokenManager();
        prestige = 0;
    }

    public String getName() {
        return name;
    }

    public int getPrestige() {
        return prestige;
    }

    public TokenManager getWallet() {
        return wallet;
    }

    public void addToken(Token token, int number) {
        wallet.addToken(token, number);
    }
}
