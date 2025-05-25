package main.java.game.gamecore;

import main.java.game.nonsystem.Deck;
import main.java.game.nonsystem.Player;

import java.util.*;

public abstract class Game {
    public static final Queue<Player> players = new LinkedList<>();
    public static Player previousPlayer;
    public static final int MAXPLAYER = 4;
    protected Deck deck;

    public Game() {
        super();
        deck = new Deck();
    }

    public abstract void addPlayer(Player player);

    public abstract void deal();

    public abstract void startGame();

    public Deck getDeck() {
        return deck;
    }
}
