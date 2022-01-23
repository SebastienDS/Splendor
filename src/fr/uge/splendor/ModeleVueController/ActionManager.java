package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Development;
import fr.uge.splendor.object.Token;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class manage all action
 */
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
         * Represents reserved card action
         */
        RESERVED_CARD,
        /**
         * Represents end turn action
         */
        END_TURN,
        /**
         * Represents remove excess token action
         */
        REMOVE_EXCESS_TOKEN
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
    private final List<Token> tokens = new ArrayList<>();

    /**
     * Set the new action
     * @param action action to set
     */
    public void setAction(Action action) {
        Objects.requireNonNull(action);
        if (action != Action.TOKEN) tokens.clear();
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
        selectedCard = card;
        groundLevel = level;
        indexSelectedCard = index;
    }


    /**
     * This method return card selected
     * @return card selected
     */
    public Development getSelectedCard() {
        return selectedCard;
    }

    /**
     * This method return the position of the card selected with the index and the ground
     * @return position of the card selected
     */
    public Point2D getSelectedCardPosition() {
        return new Point(indexSelectedCard, groundLevel);
    }

    /**
     * This method buy a card
     * @param gameData data of the game
     */
    public void buyCard(Model gameData) {
        Objects.requireNonNull(gameData);
        var tokens = gameData.getPlayerPlaying().buy(selectedCard);
        gameData.addTokenUsed(tokens);
        replaceCard(gameData);
        endTurn();
    }


    /**
     * Thi method buy a card reserved
     * @param gameData data of the game
     */
    public void buyReservedCard(Model gameData) {
        Objects.requireNonNull(gameData);
        var tokens = gameData.getPlayerPlaying().buy(selectedCard);
        gameData.addTokenUsed(tokens);
        gameData.getPlayerPlaying().getCardReserved().remove(selectedCard);
        endTurn();
    }

    /**
     * This method reserve a card
     * @param gameData data of the game
     */
    public void reserveCard(Model gameData) {
        Objects.requireNonNull(gameData);
        gameData.getPlayerPlaying().reserve(selectedCard);
        gameData.getPlayerPlaying().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? 1: 0);
        gameData.getGameTokens().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? -1: 0);
        replaceCard(gameData);
        endTurn();
    }


    /**
     * Thi method reserve a deck
     * @param gameData data of the game
     */
    public void reserveDeck(Model gameData) {
        Objects.requireNonNull(gameData);
        gameData.getPlayerPlaying().reserve(selectedCard);
        gameData.getPlayerPlaying().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? 1: 0);
        gameData.getGameTokens().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? -1: 0);
        endTurn();
    }

    /**
     * This method replace card selected
     * @param gameData data of the game
     */
    private void replaceCard(Model gameData) {
        Objects.requireNonNull(gameData);
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

    /**
     * Select a token
     * @param token to select
     */
    public void selectToken(Token token) {
        Objects.requireNonNull(token);

        if (tokens.remove(token)) return;
        if (tokens.size() < 3) tokens.add(token);
    }

    /**
     * This method return the list of selected tokens
     * @return list of selected tokens
     */
    public List<Token> getSelectedTokens() {
        return tokens;
    }

    /**
     * This method take 2 identical token
     * @param gameData data of the game
     */
    public void take2Tokens(Model gameData) {
        Objects.requireNonNull(gameData);
        var token = tokens.get(0);
        gameData.takeToken(token, 2);
        tokens.clear();
        endTurn();
    }

    /**
     * This method take one token of all tokens selected
     * @param gameData data of the game
     */
    public void takeTokens(Model gameData) {
        Objects.requireNonNull(gameData);
        tokens.forEach(t -> gameData.takeToken(t, 1));
        tokens.clear();
        endTurn();
    }

    /**
     * This method select level of the deck
     * @param level level of the deck
     */
    public void selectDeck(int level) {
        groundLevel = level;
    }

    /**
     * This method return the level of the deck selected
     * @return the level of the deck selected
     */
    public int getSelectedDeck() {
        return groundLevel;
    }

}
