package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Development;
import fr.uge.splendor.object.Token;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Objects;

public class ActionManager {

    public enum Action {
        NONE,
        CARD,
        DECK,
        TOKEN,
        END_TURN
    }

    private Action action = Action.NONE;
    private int groundLevel;
    private int indexSelectedCard;
    private Development selectedCard;

    private List<Token> tokens;

    public void setAction(Action action) {
        Objects.requireNonNull(action);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void selectCard(Development card, int level, int index) {
        Objects.requireNonNull(card);
        if (action != Action.CARD) throw new IllegalStateException("Cannot select a card with Action: " + action);
        selectedCard = card;
        groundLevel = level;
        indexSelectedCard = index;
    }

    public void selectToken(Token token) {
        Objects.requireNonNull(token);

        tokens.add(token);
    }

    public Development getSelectedCard() {
        if (action != Action.CARD) throw new IllegalStateException("Cannot get selected card with Action: " + action);
        return selectedCard;
    }

    public Point2D getSelectedCardPosition() {
        if (action != Action.CARD) throw new IllegalStateException("Cannot get selected card with Action: " + action);
        return new Point(indexSelectedCard, groundLevel);
    }

    public void buyCard(Model gameData) {
        var tokens = gameData.getPlayerPlaying().buy(selectedCard);
        gameData.addTokenUsed(tokens);
        replaceCard(gameData);
        endTurn();
    }

    public void reserveCard(Model gameData) {
        gameData.getPlayerPlaying().reserve(selectedCard);
        gameData.getPlayerPlaying().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? 1: 0);
        gameData.getGameTokens().addToken(Token.GOLD, (gameData.getGameTokens().get(Token.GOLD) > 0)? -1: 0);
        replaceCard(gameData);
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
}
