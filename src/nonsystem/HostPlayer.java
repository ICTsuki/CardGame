package nonsystem;

import gamecore.NorthernPokerGame;
import gamecore.PhomGame;
import gameenum.GameType;
import gamecore.Game;

public class HostPlayer extends Player{
    public HostPlayer() {
        super();
    }
    public HostPlayer(String name) {
        super(name);
    }
    public void CreateGame(GameType type) {
        if(type.equals(GameType.NorthernPoker)) {
            new NorthernPokerGame(this);
        }
        else {
            new PhomGame(this);
        }
    }
}
