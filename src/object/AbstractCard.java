package object;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

abstract class AbstractCard implements Card {
    private final Map<Token, Integer> cost;
    private final String image;
    private final int prestige;

    public AbstractCard(Map<Token, Integer> map, String image, int prestige) {
        cost = new HashMap<>(Objects.requireNonNull(map));
        this.image = Objects.requireNonNull(image);
        this.prestige = prestige;
    }

    public int getCost(Token token) {
        return cost.get(token);
    }
}
