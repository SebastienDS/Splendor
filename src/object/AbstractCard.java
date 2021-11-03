package object;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

abstract class AbstractCard implements Card {
    private final Map<Token, Integer> cost;
    private final String name;
    private final String image;
    private final int prestige;
    private final String price;

    public AbstractCard(Map<Token, Integer> map, String name, String image, int prestige) {
        cost = new LinkedHashMap<>(Objects.requireNonNull(map));
        this.name = Objects.requireNonNull(name);
        this.image = Objects.requireNonNull(image);
        this.prestige = prestige;
        price = cost.keySet().stream().map(token -> token.name() + " : " + cost.get(token)).collect(Collectors.joining("\n"));
    }

    public int getCost(Token token) {
        return cost.get(token);
    }

    @Override
    public int getPrestige() {
        return prestige;
    }

    public String stringPrice(int i){
        return (cost.size() >  i)? price.split("\n")[i] : "";
    }

    protected int getCenterName(){
        return (Constants.MAX_LENGTH - name.length()) / 2;
    }

    protected String getName(){
        return name;
    }
}
