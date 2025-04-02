package gamecore;

import gameenum.GameType;
import nonsystem.HostPlayer;
import nonsystem.Player;

import java.util.HashSet;
import java.util.Set;

public class Game {
    private GameType type;
    private int numberOfPlayer;
    public static final Set<Player> players = new HashSet<>();
    public static final int MAXPLAYER = 4;

    public Game() {
        super();
    }
    public Game(GameType type, HostPlayer player) {
        this.type = type;
        players.add(player);
    }

    public static void addPlayer(Player player) {
        players.add(player);
    }
    public static void removePlayer(Player player) {
        players.remove(player);
    }
}
