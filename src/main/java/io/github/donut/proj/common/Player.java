package io.github.donut.proj.common;

import static io.github.donut.proj.common.Token.*;

import io.github.donut.proj.PlayerType.Human;
import io.github.donut.proj.PlayerType.IPlayerType;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.listener.ISubject;
import java.util.Objects;

/**
 * Player is a data-only class that holds the data pertaining to a player.
 */
public class Player implements ISubject, IObserver {

    /**
     * Container for data to be sent out to IObservers subscribed to this class
     * @see EventManager#notify(ISubject, Object)
     * @author Kord Boniadi
     */
    public static class MoveInfo {
        private final Player playerInstance;
        private final int x;
        private final int y;

        MoveInfo(Player current, int x, int y) {
            this.playerInstance = current;
            this.x = x;
            this.y = y;
        }

        public Player getPlayerInstance() {
            return playerInstance;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MoveInfo)) return false;
            MoveInfo moveInfo = (MoveInfo) o;
            return getX() == moveInfo.getX() &&
                    getY() == moveInfo.getY() &&
                    getPlayerInstance().equals(moveInfo.getPlayerInstance());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getPlayerInstance(), getX(), getY());
        }
    }

    private String playerName;
    private Token playerToken;

    // IPlayerType instance - handles how this "Player" makes/calculates moves
    // basically the brain
    private IPlayerType playerType;

    /**
     * Constructs a Player object with no arguments.
     */
    public Player() { }

    /**
     * Constructs a Player object with a set player name.
     * @param playerName The name of the player
     */
    public Player(String playerName) {
        this(playerName, X, new Human());
    }

    /**
     * Constructs a Player object with a set player name and player token ['X', 'O'].
     * @param playerName The name of the player
     * @param playerToken The token of the player ['X', 'O']
     */
    public Player(String playerName, Token playerToken) {
        this(playerName, playerToken, new Human());
    }

    public Player(String playerName, Token playerToken, IPlayerType playerType) {
        this.playerName = playerName;
        this.playerToken = playerToken;
        setPlayerType(playerType);
    }
    /**
     * Gets the player's name.
     * @return A String holding the player's name
     */
    public String getPlayerName() { return playerName; }

    /**
     * Gets the player's token.
     * @return A char holding the player's token
     */
    public Token getPlayerToken() { return playerToken; }

    public IPlayerType getPlayerType() {
        return playerType;
    }

    /**
     * Sets the player's name.
     * @param playerName A String holding the player's token.
     */
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    /**
     * Sets the player's token.
     * @param playerToken A char holding the player's token.
     */
    public void setPlayerToken(Token playerToken) { this.playerToken = playerToken; }

    public void setPlayerType(IPlayerType playerType) {
        if (this.playerType != null)
            EventManager.unregister(this.playerType, this);

        this.playerType = playerType;

        if (this.playerType != null) {
            EventManager.register(this.playerType, this);
        }
    }

    public void makeMove(Board board) {
        playerType.makeMove(board, playerToken);
    }
    /**
     * Checks the equality of two Player objects.
     * @param o The Player that is being checked for equality
     * @return A boolean representing the equality of two Player objects
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerToken == player.playerToken && Objects.equals(playerName, player.playerName);
    }

    /**
     * Returns the hash code of the calling Player object.
     * @return An integer representing the hashcode of the calling Player object
     */
    @Override
    public int hashCode() {
        return Objects.hash(playerName, playerToken);
    }

    /**
     * Returns a String containing the name and token of the Player object
     * @return A String containing the name and token of the Player object
     */
    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", playerToken=" + playerToken +
                '}';
    }

    /**
     * New info is received through this method. Object decoding is needed
     *
     * @param eventType General Object type
     * @author Kord Boniadi
     */
    @Override
    public void update(Object eventType) {
        if (eventType instanceof IPlayerType.BoardMoveInfo) {
            IPlayerType.BoardMoveInfo temp = (IPlayerType.BoardMoveInfo) eventType;

            EventManager.notify(this, new Player.MoveInfo(this, temp.getX(), temp.getY()));
        }
    }
}
