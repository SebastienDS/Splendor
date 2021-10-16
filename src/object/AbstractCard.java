package object;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

abstract class AbstractCard implements Card {
    private final HashMap<Token, Integer> cost;
    private final String image;

    public AbstractCard(Map<Token, Integer> map, String image) {
        cost = new HashMap<>(Objects.requireNonNull(map));
        this.image = image;
    }

    public int getCost(Token token) {
        return cost.get(token);
    }
}
