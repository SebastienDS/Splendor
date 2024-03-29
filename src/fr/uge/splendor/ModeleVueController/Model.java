package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.*;

import java.io.IOException;
import java.util.*;

/**
 * This class represents all data of the game
 */
public class Model {

    /**
     * Game mode of the game
     */
    private int gameMode = 1;

    /**
     * List of all players of the game
     */
    private final List<Player> players;

    /**
     * Integer corresponding at the current player playing on the list of players
     */
    private int playerPlaying;

    /**
     * True if current round is the last
     */
    private boolean lastRound = false;

    /**
     * Represents the card that will be drawn for the grounds
     */
    private final Map<Integer, Deck<Development>> decks;

    /**
     * Represents the cards visible from the deck
     */
    private final Map<Integer, List<Development>> grounds;

    /**
     * Represents the nobles
     */
    private final List<Noble> nobles;

    /**
     * represents all the tokens that player can take
     */
    private final TokenManager gameTokens;
    /**
     * numberOfDeck
     */
    private int numberOfDeck;

    /**
     * Create an instance of model
     * @param players list of all players
     * @param decks decks of the games
     * @param grounds grounds of the games
     * @param nobles nobles of the games
     */
    public Model(List<Player> players, Map<Integer, Deck<Development>> decks, Map<Integer, List<Development>> grounds, List<Noble> nobles) {
        Objects.requireNonNull(nobles);
        this.players = Objects.requireNonNull(players);
        this.decks = Objects.requireNonNull(decks);
        this.grounds = Objects.requireNonNull(grounds);
        gameTokens = new TokenManager();
        this.nobles = nobles;
        Collections.shuffle(nobles);
    }

    /**
     * Create an instance of model with default value for decks, grounds and nobles
     * @param players list of all players
     * @throws IOException if an I/O error occur
     */
    public Model(List<Player> players) throws IOException {
        this(players, Decks.developmentDecks(), new LinkedHashMap<>(), Decks.nobleDeck());
    }

    /**
     * This method set the game mode
     * @param gameMode new game mode
     */
    public void setGameMode(int gameMode) {
        if (gameMode <= 0) throw new IllegalArgumentException("GameMode must be >= 0");
        this.gameMode = gameMode;
    }

    /**
     * This method return the game mode
     * @return game mode
     */
    public int getGameMode() {
        return gameMode;
    }

    /**
     * This method return the list of all the players
     * @return list of all the players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * This method return the decks. A map containing the deckName as a key and a deck as a value.
     * @return return map containing all the deck with deck name as key
     */
    public Map<Integer, Deck<Development>> getDecks() {
        return decks;
    }

    /**
     * This method return the grounds. A map containing the deckName as a key and a list of card as a value
     * @return grounds
     */
    public Map<Integer, List<Development>> getGrounds() {
        return grounds;
    }

    /**
     * This method initialise value to begin the game
     */
    public void startGame() {
        initDecks();
        initNobles();
        shuffleDecks();
        initGrounds();
        initGameTokens(players.size());
        numberOfDeck += decks.size() + ((nobles.size() > 0)? 1 : 0);
    }

    /**
     * This method shuffles all the decks of the games
     */
    private void shuffleDecks() {
        decks.values().forEach(Deck::shuffle);
    }

    /**
     * This method see verify if current round is the last and change the player that play to the next one
     */
    public void endTurn() {
        manageNoble();
        lastRound = lastRound || players.get(playerPlaying).getPrestige() >= 15;
        playerPlaying = (playerPlaying + 1) % players.size();

    }

    /**
     * This method return true if the current round is the last
     * @return ture if the current round is the last
     */
    public boolean getLastRound(){
        return !lastRound;
    }

    /**
     * This method return the player currently playing
     * @return player playing
     */
    public Player getPlayerPlaying() {
        return players.get(playerPlaying);
    }

    /**
     * This method return true if the current player is the first player
     * @return true if the current player is the first player
     */
    public boolean startNewRound() {
        return playerPlaying != 0;
    }

    /**
     * This method return the game tokens
     * @return gameTokens
     */
    public TokenManager getGameTokens() {
        return gameTokens;
    }

    /**
     * This method take token from game token and add them to the player wallet
     * @param chooseToken specified token
     * @param number number of token to take
     */
    public void takeToken(Token chooseToken, int number) {
        Objects.requireNonNull(chooseToken);
        gameTokens.addToken(chooseToken, -number);
        players.get(playerPlaying).addToken(chooseToken, number);
    }

    /**
     * This methode initialise the game tokens in function of the number of player
     * @param size size of player list
     */
    private void initGameTokens(int size) {
        var numberOfTokens = size + 2 + size / 4;
        for (var token: Token.cardValues()) {
            gameTokens.addToken(token, numberOfTokens);
        }
        gameTokens.addToken(Token.GOLD, 5);
    }

    /**
     * This method initialise all grounds by drawing card of deck and putting them on grounds
     */
    private void initGrounds() {
        for (var deckName: decks.keySet()) {
            var deck = decks.get(deckName);
            var list = new ArrayList<>(deck.drawCards(Constants.DRAW_NUMBER));
            grounds.put(deckName, list);
        }
    }

    /**
     * This method return true if reserve is available for the gameMode chosen
     * @return true if reverse is available
     */
    public boolean reservePossible(){
        return gameMode >= 2;
    }

    /**
     * This method add token of tokens to the game tokens
     * @param tokens map containing token as key and integer as value
     */
    public void addTokenUsed(Map<Token, Integer> tokens) {
        Objects.requireNonNull(tokens);
        for (var token: tokens.keySet()){
            gameTokens.addToken(token, tokens.get(token));
        }
    }

    /**
     * This method return the winning player
     * @return Player winner
     */
    public Player getWinner() {
        var maxPrestigePlayer = players.stream()
                .filter(player ->
                        player.getPrestige() >= players.stream()
                                .mapToInt(Player::getPrestige)
                                .max()
                                .orElse(0)).toList();
        if(maxPrestigePlayer.size() == 1){
            return maxPrestigePlayer.get(0);
        }
        return maxPrestigePlayer.stream().min(Comparator.comparingInt(Player::getCardPurchased)).orElse(null);
    }

    /**
     * This methode return lise of all nobles
     * @return list of all nobles
     */
    public List<Noble> getNobles() {
        return nobles;
    }

    /**
     * Keep only decks used in game mode
     */
    private void initDecks() {
        if (gameMode == 1) {
            decks.keySet().removeIf(key -> key != 0);
        } else {
            decks.remove(0);
        }
    }

    /**
     * Clear nobles if game mode don't have them else let only size of player + 1 noble in list
     */
    private void initNobles() {
        if (gameMode == 1) {
            nobles.clear();
            return;
        }
        nobles.removeAll(nobles.stream().limit(nobles.size() - (players.size() + 1)).toList());
    }

    /**
     * Get total number of decks in game (counting noble list)
     * @return number of decks in game
     */
    public int getNumberOfDecks() {
        return numberOfDeck;
    }

    /**
     * This method return true if index is equals to index of player playing
     * @param index index of player
     * @return true if index is equals to index of player playing
     */
    public boolean isPlaying(int index) {
        return playerPlaying == index;
    }

    /**
     * This method make the noble go to the player that finished playing if he has enough bonus. The noble is removed
     * from the ground and his prestige are added to the player.
     */
    private void manageNoble() {
        if(gameMode == 2){
            var bonus = getPlayerPlaying().getBonus();
            nobles.removeIf(noble -> {
                if(noble.cost().keySet()
                        .stream()
                        .filter(token -> noble.cost().get(token) > bonus.get(token))
                        .findAny()
                        .isEmpty()){
                    getPlayerPlaying().addNoble(noble);
                    return true;
                }
                return false;
            });
        }
    }
}
