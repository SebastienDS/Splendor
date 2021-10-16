package object;

import java.util.Map;

public class Noble extends AbstractCard {

    private final String name;

    public Noble(Map<Token, Integer> cost, String name, String image) {
        super(cost, image);
        this.name = name;
    }
}
