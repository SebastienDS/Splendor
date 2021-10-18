package object;

import java.util.Map;

public class Development extends AbstractCard {

    private final Token bonus;

    public Development(Map<Token, Integer> cost, String image, int prestige, Token bonus) {
        super(cost, image, prestige);
        this.bonus = bonus;
    }
}
