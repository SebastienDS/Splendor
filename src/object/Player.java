package object;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private final TokenManager wallet;
    private final List<Development> cardBuy;

    public Player(String name) {
        this.name = name;
        wallet = new TokenManager();
        cardBuy = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
}
