package ModeleVueController;

import object.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Model {

    private final List<Player> players;
    private boolean lastRound = false;
    private int playerPlaying;
    private int gameMode;
    private final Map<DeckName, Deck> decks;
    private final Map<DeckName, List<Card>> grounds;
    private final TokenManager gameTokens;

    public Model(List<Player> players, Map<DeckName, Deck> decks, Map<DeckName, List<Card>> grounds) {
        this.players = players;
        playerPlaying = 0;
        this.decks = decks;
        this.grounds = grounds;
        gameTokens = initGameTokens(players.size());
    }

    private TokenManager initGameTokens(int size) {
        var numberOfTokens = size + 2 + size / 4;
        var tokenManager = new TokenManager();
        for (var token: Token.cardValues()){
            tokenManager.addToken(token, numberOfTokens);
        }
        tokenManager.addToken(Token.GOLD, 5);
        return tokenManager;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<DeckName, Deck> getDecks() {
        return decks;
    }

    public Map<DeckName, List<Card>> getGrounds() {
        return grounds;
    }

    public void startGame() {
        initGrounds();
    }

    public void endTurn() {
        lastRound = players.get(playerPlaying).getPrestige() >= 15;
        playerPlaying = (playerPlaying + 1) % players.size();
    }

    public boolean getLastRound(){
        return lastRound;
    }

    private void initGrounds() {
        for (var deckName : decks.keySet()){
            var list = new ArrayList<Card>();
            list.addAll(decks.get(deckName).drawMultiple(4));
            grounds.put(deckName, list);
        }
    }

    public Player getPlayerPlaying() {
        return players.get(playerPlaying);
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public boolean startNewRound() {
        return playerPlaying == 0;
    }

    public TokenManager getGameTokens() {
        return gameTokens;
    }

    public void takeToken(Token chooseToken, int number) {
        gameTokens.addToken(chooseToken, -number);
        players.get(playerPlaying).addToken(chooseToken, number);
    }
}
