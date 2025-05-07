package nonsystem;

import gamecore.Game;

public class Player extends Participant {


    public Player() {
        super();
    }

    public Player(String name) {
        super(name);
    }
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
