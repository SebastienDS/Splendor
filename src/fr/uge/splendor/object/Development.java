package fr.uge.splendor.object;

import java.util.Objects;

/**
 * This class represent the development card used during the game.
 */
public record Development(TokenManager cost, String name, int prestige, Token bonus) {

    /**
     * Name of the type of the card.
     */
    public static final String TYPE = "Development";

    /**
     * Create an Instance of Development
     * @param cost of the card
     * @param name of the card
     * @param prestige given by the card
     * @param bonus given by the card
     */
    public Development {
        if (prestige < 0) throw new IllegalArgumentException("Prestige must be >= 0");

        Objects.requireNonNull(cost);
        Objects.requireNonNull(name);
        Objects.requireNonNull(bonus);
    }

    /**
     * This method return if the card can be bought by the player
     * @param wallet wallet of the player
     * @param bonus bonus of the player
     * @return if the card can be bought by the player
     */
    public boolean canBeBought(TokenManager wallet, TokenManager bonus) {
        Objects.requireNonNull(wallet);
        Objects.requireNonNull(bonus);
        var gold = 0;
        for(var token: cost.tokens().keySet()){
            if(Player.getTokenPlusBonus(wallet, bonus, token) >= cost.get(token)){
                continue;
            }
            if(Player.getTokenPlusBonus(wallet, bonus, token) + (wallet.get(Token.GOLD) - gold) >= cost.get(token)){
                gold += cost.get(token) - Player.getTokenPlusBonus(wallet, bonus, token);
                continue;
            }
            return false;
        }
        return true;
    }

}
