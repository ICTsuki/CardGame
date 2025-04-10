package gamecore;

import gameenum.GameType;
import nonsystem.*;

import java.util.*;

public class Game {
    public static GameType type;
    public static final Queue<Player> players = new LinkedList<>();
    public static final int MAXPLAYER = 4;
    public static Field field;

    public Game() {
        super();
        Game.type = GameType.NorthernPoker;
        field = new NorthenPokerField();
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
    }

    public static void addPlayer(Player player) {
        players.add(player);
        player.setType(Game.type);
    }
    public static void removePlayer(Player player) {
        players.remove(player);
    }
}
