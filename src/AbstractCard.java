import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

abstract class AbstractCard implements Card {
    private final HashMap<Token, Integer> cost;

    public AbstractCard(Map<Token, Integer> map) {
        cost = new HashMap<>(Objects.requireNonNull(map));
    }

    public int getCost(Token token) {
        return cost.get(token);
    }
}
