package gamecore;

import gameenum.GameType;
import nonsystem.*;

import java.util.*;

public abstract class Game {
    public static final Queue<Player> players = new LinkedList<>();
    public static final int MAXPLAYER = 4;
    protected final Deck deck;
    protected int currentTurn = 0;

    public Game() {
        super();
        deck = new Deck();
    }

    public static void addPlayer(Player player) {
        if(players.size() + 1 > MAXPLAYER) {
            System.out.println("Player full!");
            return;
        }
        players.add(player);
    }
    public static void removePlayer(Player player) {
        if(!players.isEmpty()) players.remove(player);
    }

    public abstract void deal();

    public abstract void startGame();
}
