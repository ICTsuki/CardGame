package gamecore;

import nonsystem.*;

import java.util.*;

public abstract class Game {
    public static final Queue<Player> players = new LinkedList<>();
    public static Player previousPlayer;
    public static final int MAXPLAYER = 4;
    protected final Deck deck;

    public Game() {
        super();
        deck = new Deck();
    }

    public abstract void addPlayer(Player player);

    public abstract void deal();

    public abstract void startGame();
}
