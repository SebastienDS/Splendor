package object;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {

    private final String name;
    private final TokenManager wallet;
    private final List<Development> cardBuy;
    private final List<Card> cardReserved;
    private final TokenManager bonus;
    private int prestige;

    public Player(String name) {
        this.name = Objects.requireNonNull(name);
        wallet = new TokenManager();
        cardBuy = new ArrayList<>();
        bonus = new TokenManager();
        cardReserved = new ArrayList<>();
        prestige = 0;
    }

    public String getName() {
        return name;
    }

    public int getPrestige() {
        return prestige;
    }

    public TokenManager getWallet() {
        return wallet;
    }

    public void addToken(Token token, int number) {
        wallet.addToken(token, number);
    }

    public void reserve(Card card) {
        cardReserved.add(card);
    }

    public boolean canReserve() {
        return cardReserved.size() < 3;
    }

    public List<Card> getCardReserved() {
        return cardReserved;
    }

    public boolean canBuy(Card card) {
        int gold = 0;
        for(var token: card.getTokens()){
            if(wallet.get(token) >= card.getCost(token)){
                continue;
            }
            if(wallet.get(token) + (wallet.get(Token.GOLD) - gold) >= card.getCost(token)){
                gold += card.getCost(token) - wallet.get(token);
                continue;
            }
            return false;
        }
        return true;
    }

    public void buy(Card card) {
        for(var token: card.getTokens()){
            if(wallet.get(token) >= card.getCost(token)){
                wallet.addToken(token, -card.getCost(token));
                continue;
            }
            wallet.addToken(Token.GOLD, wallet.get(token) - card.getCost(token));
            wallet.addToken(token, -wallet.get(token));
        }
        var bonus = card.getBonus();
        if(bonus != null){
            this.bonus.addToken(bonus, 1);
        }

        prestige += card.getPrestige();
    }
}
