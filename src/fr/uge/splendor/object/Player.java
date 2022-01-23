package fr.uge.splendor.object;

import java.util.*;

/**
 * Instance that represents the player during the game.
 */
public class Player {

    /**
     * Name of the player.
     */
    private final String name;
    /**
     * Represents all the tokens the player own. Used to buy cards.
     */
    private final TokenManager wallet;
    /**
     * List of card the player already purchased.
     */
    private final List<Development> cardBuy;
    /**
     * List of card that the player reserved. All card reserved can be purchased at any moments.
     * To remove one, the player need to buy it.
     */
    private final List<Development> cardReserved;
    /**
     * Bonus of the player. This element represents the token the player own passively.
     * All token of bonus are reduced during the purchase of other card.
     */
    private final TokenManager bonus;
    /**
     * prestige point the player possess
     */
    private int prestige;

    /**
     * Construct a player with the name given and initialise the other champ at zero/empty
     * @param name name of the player
     */
    public Player(String name) {
        this.name = Objects.requireNonNull(name);
        wallet = new TokenManager();
        cardBuy = new ArrayList<>();
        bonus = new TokenManager();
        cardReserved = new ArrayList<>();
    }

    /**
     * This method return the name of the player
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * This method return the prestige point the player has
     * @return the prestige point of the player
     */
    public int getPrestige() {
        return prestige;
    }

    /**
     * This method return the wallet of the player
     * @return the wallet of the player
     */
    public TokenManager getWallet() {
        return wallet;
    }

    /**
     * This method add token to the wallet of the player
     * @param token name of the token
     * @param number to add
     */
    public void addToken(Token token, int number) {
        wallet.addToken(token, number);
    }

    /**
     * This methode add the card in parameter to the reserved list.
     * @param card the player choose to reserve
     */
    public void reserve(Development card) {
        Objects.requireNonNull(card);
        cardReserved.add(card);
    }

    /**
     * This method return true if the player can reserve and false otherwise.
     * The player can reserve if he has less than 3 card already reserved
     * @return true if the player can reserve and false otherwise
     */
    public boolean canReserve() {
        return cardReserved.size() < 3;
    }

    /**
     * This method return the list of card reserved by the player
     * @return list of card reserved by the player
     */
    public List<Development> getCardReserved() {
        return cardReserved;
    }

    /**
     * This method return true if the card can be purchased by the player, false otherwise
     * @param card the player wants to buy
     * @return true if the card is purchasable false otherwise
     */
    public boolean canBuy(Development card) {
        return card.canBeBought(wallet, bonus);
    }

    /**
     * This method buy the card by adding the card bonus to bonus, adding prestige point and using token needed.
     * The token used are returned to the form of a map with the token as the key and the number(int) as the value
     * @param card purchased card
     * @return map containing token used. Token as key and number of token used(int) as value.
     */
    public Map<Token, Integer> buy(Development card) {
        var bonus = card.bonus();
        if (bonus != null) {
            this.bonus.addToken(bonus, 1);
        }
        cardBuy.add(card);
        prestige += card.prestige();
        return manageTokenPurchase(card);
    }

    /**
     * This method remove the token used during the purchase and return them with a map containing token as key and
     * the number of token used (int) as value
     * @param card purchased
     * @return map containing token as key and the number of token used (int) as value
     */
    private Map<Token, Integer> manageTokenPurchase(Development card){
        var tokens = new HashMap<Token, Integer>();
        for (var token : card.cost().keySet()) {
            if (getTokenPlusBonus(wallet, bonus, token) >= card.cost().get(token)) {
                var tokenToPay = -(card.cost().get(token) - bonus.get(token));
                wallet.addToken(token, tokenToPay);
                tokens.put(token, -(tokenToPay));
                continue;
            }
            tokens.put(token, wallet.get(token));
            tokens.merge(Token.GOLD, card.cost().get(token) - getTokenPlusBonus(wallet, bonus, token), Integer::sum);
            wallet.addToken(Token.GOLD, getTokenPlusBonus(wallet, bonus, token) - card.cost().get(token));
            wallet.addToken(token, -wallet.get(token));
        }
        wallet.tokens().values().removeIf(v -> v == 0);
        return tokens;
    }

    /**
     * This method return the number obtained by sum of the specified token owned in the wallet and
     * the token specified owned passively in bonus
     * @param wallet wallet of the player
     * @param bonus bonus of the player
     * @param token specified token
     * @return sum of the chosen token in wallet and the chosen token in the bonus
     */
    public static int getTokenPlusBonus(TokenManager wallet, TokenManager bonus, Token token) {
        return wallet.get(token) + bonus.get(token);
    }

    /**
     * This method return the bonus of the player
     * @return bonus of the player
     */
    public TokenManager getBonus() {
        return bonus;
    }

    /**
     * This method add prestige of the noble to the player.
     * @param card gained by visit of noble
     */
    public void addNoble(Noble card) {
        prestige += card.prestige();
    }

    /**
     * This method return the number of the purchased cards
     * @return number of purchased cards
     */
    public int getCardPurchased() {
        return cardBuy.size();
    }
}
