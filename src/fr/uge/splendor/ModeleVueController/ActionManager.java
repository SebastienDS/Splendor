package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Development;
import fr.uge.splendor.object.Token;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Objects;

public class ActionManager {

    /**
     * All action of the game
     */
    public enum Action {
        /**
         * Represents no action
         */
        NONE,
        /**
         * Represents card action
         */
        CARD,
        /**
         * Represents deck action
         */
        DECK,
        /**
         * Represents token action
         */
        TOKEN,
        /**
         * Represents end turn action
         */
        END_TURN
    }

    /**
     * Current action of the game
     */
    private Action action = Action.NONE;
    /**
     * level development card selected
     */
    private int groundLevel;
    /**
     * Index card selected in ground
     */
    private int indexSelectedCard;
    /**
     * Card selected
     */
    private Development selectedCard;
    /**
     * List of token selected
     */
    private List<Token> tokens;

    /**
     * Set the new action
     * @param action action to set
     */
    public void setAction(Action action) {
        Objects.requireNonNull(action);
        this.action = action;
    }

    /**
     * This method return the current action of the game
     * @return current action of the game
     */
    public Action getAction() {
        return action;
    }

    /**
     * This method select a card.
     * @param card card selected
     * @param level level of card selected
     * @param index index of card selected
     */
    public void selectCard(Development card, int level, int index) {
        Objects.requireNonNull(card);
        if (action != Action.CARD) throw new IllegalStateException("Cannot select a card with Action: " + action);
        selectedCard = card;
        groundLevel = level;
        indexSelectedCard = index;
    }

    /**
     * Select a token
     * @param token to select
     */
    public void selectToken(Token token) {
        Objects.requireNonNull(token);

        tokens.add(token);
    }

    /**
     * This method return card selected
     * @return card selected
     */
    public Development getSelectedCard() {
        if (action != Action.CARD) throw new IllegalStateException("Cannot get selected card with Action: " + action);
        return selectedCard;
    }

    /**
     * This method return the position of the card selected with the index and the ground
     * @return position of the card selected
     */
    public Point2D getSelectedCardPosition() {
        if (action != Action.CARD) throw new IllegalStateException("Cannot get selected card with Action: " + action);
        return new Point(indexSelectedCard, groundLevel);
    }

    /**
     * This method buy a card
     * @param gameData data of the game
     */
    public void buyCard(Model gameData) {
        var tokens = gameData.getPlayerPlaying().buy(selectedCard);
        gameData.addTokenUsed(tokens);
        replaceCard(gameData);
        endTurn();
    }

    /**
     * This method reserve a card
     * @param gameData data of the game
     */
    public void reserveCard(Model gameData) {
        gameData.getPlayerPlaying().reserve(selectedCard);
        gameData.getPlayerPlaying().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? 1: 0);
        gameData.getGameTokens().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? -1: 0);
        replaceCard(gameData);
        endTurn();
    }

    /**
     * This method replace card selected
     * @param gameData data of the game
     */
    private void replaceCard(Model gameData) {
        var selectedGround = gameData.getGrounds().get(groundLevel);
        selectedGround.remove(indexSelectedCard);
        var card = gameData.getDecks().get(groundLevel).draw();
        selectedGround.add(card);
    }

    /**
     * Make action equals to end_turns
     */
    private void endTurn() {
        action = Action.END_TURN;
    }
}
