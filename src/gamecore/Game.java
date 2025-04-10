package gamecore;

import gameenum.GameType;
import nonsystem.*;

import java.util.*;

public abstract class Game {
    public static GameType type;
    public static final Queue<Player> players = new LinkedList<>();
    public static final int MAXPLAYER = 4;
    public static Field field;
    private final Deck deck;

    public Game() {
        super();
        Game.type = GameType.NorthernPoker;
        field = new NorthenPokerField();
        deck = new Deck();
    }
    public Game(GameType type, HostPlayer player) {
        Game.type = type;
        players.add(player);
        player.setType(type);
        if(Game.type.equals(GameType.NorthernPoker)) {
            field = new NorthenPokerField();
        }
        else {
            field = new PhomField();
        }
        deck = new Deck();
    }

    public static void addPlayer(Player player) {
        if(players.size() + 1 > MAXPLAYER) {
            System.out.println("Player full!");
            return;
        }
        players.add(player);
        player.setType(Game.type);
    }
    public static void removePlayer(Player player) {
        if(!players.isEmpty()) players.remove(player);
    }

    private void deal() {
        while(deck.remainingCards() != 0) {
            Player current = players.poll();
            Objects.requireNonNull(current).receiveCard(deck.dealCard());
            players.offer(current);
        }
    }

    public void startGame() {
        deck.shuffle();
        deal();
    }
}
