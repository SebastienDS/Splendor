package object;

import java.util.Map;
import java.util.Objects;

public class Noble extends AbstractCard {

    private final String name;
    private final int prestige;

    public Noble(Map<Token, Integer> cost, String name, String image, int prestige) {
        super(cost, image);
        this.name = Objects.requireNonNull(name);
        this.prestige = prestige;
    }
}
