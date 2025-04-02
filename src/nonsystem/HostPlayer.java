package nonsystem;

import gameenum.GameType;
import gamecore.Game;

public class HostPlayer extends Player{
    public HostPlayer() {
        super();
    }
    public void CreateGame(GameType type) {
        new Game(type, this);
    }
}
