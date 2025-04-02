package nonsystem;

import gamecore.Game;

public class Player extends Participant {
    public void joinGame() {
        Game.addPlayer(this);
    }
    public void quitGame() {
        Game.removePlayer(this);
    }
    public void login() {}
    public void logout(){}
    public void inspectRules(){}
}
