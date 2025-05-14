package gamecore;

import gameenum.GameType;
import nonsystem.*;

import java.util.*;

public abstract class Game {
    public final Queue<Player> players = new LinkedList<>();
    public static final int MAXPLAYER = 4;
    protected final Deck deck;
    protected int currentTurn = 0;

    public Game() {
        super();
        deck = new Deck();
    }

    public abstract void addPlayer(Player player);
    public void removePlayer(Player player) {
        if(!players.isEmpty()) players.remove(player);
    }

    public abstract void deal();

    public abstract void startGame();

    public Queue<Player> getPlayers() {
        return players;
    }
}
