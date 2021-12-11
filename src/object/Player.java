package object;

import javax.swing.*;
import java.util.*;

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
        var gold = 0;
        for(var token: card.getTokens()){
            if(getTokenPlusBonus(token) >= card.getCost(token)){
                continue;
            }
            if(getTokenPlusBonus(token) + (wallet.get(Token.GOLD) - gold) >= card.getCost(token)){
                gold += card.getCost(token) - getTokenPlusBonus(token);
                continue;
            }
            System.out.println(token + " " + getTokenPlusBonus(token) + " " + card.getCost(token));
            return false;
        }
        return true;
    }

    private int getTokenPlusBonus(Token token) {
        return wallet.get(token) + bonus.get(token);
    }

    public Map<Token, Integer> buy(Card card) {
        var tokens = new HashMap<Token, Integer>();
        for (var token : card.getTokens()) {
            if (getTokenPlusBonus(token) >= card.getCost(token)) {
                var tokenToPay = -(card.getCost(token) - bonus.get(token));
                wallet.addToken(token, Math.min(tokenToPay, 0));
                tokens.put(token, Math.min(tokenToPay, 0));
                continue;
            }
            tokens.put(token, wallet.get(token));
            tokens.merge(Token.GOLD, card.getCost(token) - getTokenPlusBonus(token), Integer::sum);
            wallet.addToken(Token.GOLD, getTokenPlusBonus(token) - card.getCost(token));
            wallet.addToken(token, -wallet.get(token));
        }
        var bonus = card.getBonus();
        if (bonus != null) {
            this.bonus.addToken(bonus, 1);
        }

        prestige += card.getPrestige();
        return tokens;
    }

    public TokenManager getBonus() {
        return bonus;
    }
}
