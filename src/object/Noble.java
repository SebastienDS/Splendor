package object;

import java.util.Map;
import java.util.Objects;

public class Noble extends AbstractCard {

    private final String name;

    public Noble(Map<Token, Integer> cost, String name, String image, int prestige) {
        super(cost, image, prestige);
        this.name = Objects.requireNonNull(name);
    }
}
