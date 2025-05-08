package nonsystem;

import gamecore.Game;
import gamecore.NorthernPokerGame;
import gamecore.PhomGame;

public class Player extends Participant {

    public Player() {
        super();
    }

    public Player(String name) {
        super(name);
    }
    public void joinGame(String gametype, Game game) {
        game.addPlayer(this);
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


    public void release() {
    }
}
