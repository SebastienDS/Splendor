package object;

import java.util.*;
import java.util.stream.Collectors;

class BasicCard implements Card {
    private final Map<Token, Integer> cost;
    private final String name;
    private final String image;
    private final int prestige;

    public BasicCard(Map<Token, Integer> map, String name, String image, int prestige) {
        cost = new LinkedHashMap<>(Objects.requireNonNull(map));
        this.name = Objects.requireNonNull(name);
        this.image = Objects.requireNonNull(image);
        this.prestige = prestige;
    }

    @Override
    public int getCost(Token token) {
        return cost.get(token);
    }

    @Override
    public Set<Token> getTokens() {
        return cost.keySet();
    }

    @Override
    public int getPrestige() {
        return prestige;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplay(int i) {
        return "";
    }
}
