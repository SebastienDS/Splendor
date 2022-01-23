package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Development;
import fr.uge.splendor.object.Token;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActionManager {

    public enum Action {
        NONE,
        CARD,
        DECK,
        TOKEN,
        RESERVED_CARD,
        END_TURN
    }

    private Action action = Action.NONE;
    private int groundLevel;
    private int indexSelectedCard;
    private Development selectedCard;

    private final List<Token> tokens = new ArrayList<>();

    public void setAction(Action action) {
        Objects.requireNonNull(action);
        if (action != Action.TOKEN) tokens.clear();
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void selectCard(Development card, int level, int index) {
        Objects.requireNonNull(card);
        selectedCard = card;
        groundLevel = level;
        indexSelectedCard = index;
    }

    public Development getSelectedCard() {
        return selectedCard;
    }

    public Point2D getSelectedCardPosition() {
        return new Point(indexSelectedCard, groundLevel);
    }

    public void buyCard(Model gameData) {
        var tokens = gameData.getPlayerPlaying().buy(selectedCard);
        gameData.addTokenUsed(tokens);
        replaceCard(gameData);
        endTurn();
    }

    public void buyReservedCard(Model gameData) {
        var tokens = gameData.getPlayerPlaying().buy(selectedCard);
        gameData.addTokenUsed(tokens);
        gameData.getPlayerPlaying().getCardReserved().remove(selectedCard);
        endTurn();
    }

    public void reserveCard(Model gameData) {
        gameData.getPlayerPlaying().reserve(selectedCard);
        gameData.getPlayerPlaying().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? 1: 0);
        gameData.getGameTokens().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? -1: 0);
        replaceCard(gameData);
        endTurn();
    }

    public void reserveDeck(Model gameData) {
        gameData.getPlayerPlaying().reserve(selectedCard);
        gameData.getPlayerPlaying().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? 1: 0);
        gameData.getGameTokens().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? -1: 0);
        endTurn();
    }

    private void replaceCard(Model gameData) {
        var selectedGround = gameData.getGrounds().get(groundLevel);
        selectedGround.remove(indexSelectedCard);
        var card = gameData.getDecks().get(groundLevel).draw();
        selectedGround.add(card);
    }

    private void endTurn() {
        action = Action.END_TURN;
    }

    public void selectToken(Token token) {
        Objects.requireNonNull(token);

        if (tokens.remove(token)) return;
        if (tokens.size() < 3) tokens.add(token);
    }

    public List<Token> getSelectedTokens() {
        return tokens;
    }

    public void take2Tokens(Model gameData) {
        var token = tokens.get(0);
        gameData.takeToken(token, 2);
        tokens.clear();
        endTurn();
    }

    public void takeTokens(Model gameData) {
        tokens.forEach(t -> gameData.takeToken(t, 1));
        tokens.clear();
        endTurn();
    }

    public void selectDeck(int level) {
        groundLevel = level;
    }

    public int getSelectedDeck() {
        return groundLevel;
    }

}
