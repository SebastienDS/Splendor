package object;

import java.util.*;
import java.util.stream.Collectors;

abstract class AbstractCard implements Card {
    private final Map<Token, Integer> cost;
    private final String name;
    private final String image;
    private final int prestige;
    private final List<String> price;

    public AbstractCard(Map<Token, Integer> map, String name, String image, int prestige) {
        cost = new LinkedHashMap<>(Objects.requireNonNull(map));
        this.name = Objects.requireNonNull(name);
        this.image = Objects.requireNonNull(image);
        this.prestige = prestige;
        price = cost.keySet().stream().map(token -> token.name() + " : " + cost.get(token)).toList();
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

    public String stringPrice(int i){
        return (cost.size() >  i)? price.get(i) : "";
    }

    protected int getCenterName(){
        return (Constants.MAX_LENGTH - name.length()) / 2;
    }

    protected String getName(){
        return name;
    }
}
