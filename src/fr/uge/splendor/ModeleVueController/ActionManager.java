package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Development;

import java.util.Objects;

public class ActionManager {

    public enum Action {
        NONE,
        CARD,
        DECK,
        TOKEN,
    }

    private Action action = Action.NONE;
    private Development selectedCard;

    public void setAction(Action action) {
        Objects.requireNonNull(action);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void selectCard(Development card) {
        Objects.requireNonNull(card);
        if (action != Action.CARD) throw new IllegalStateException("Cannot select a card with Action: " + action);
        selectedCard = card;
    }

    public Development getSelectedCard() {
        if (action != Action.CARD) throw new IllegalStateException("Cannot get seleected card with Action: " + action);
        return selectedCard;
    }
}
