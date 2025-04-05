package nonsystem;

import gamecore.Game;

public class Player extends Participant {

    public void joinGame() {
        Game.addPlayer(this);
    }
    public void quitGame() {
        Game.removePlayer(this);
    }
    public void login() {
        //nothing yet
    }
    public void logout(){
        //nothing yet
    }
    public void inspectRules(){
        //nothing yet
    }
}
