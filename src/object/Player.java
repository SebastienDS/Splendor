package object;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private final Wallet wallet;
    private final List<Development> cardBuy;

    public Player(String name) {
        this.name = name;
        wallet = new Wallet();
        cardBuy = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
}
