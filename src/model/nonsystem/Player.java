package model.nonsystem;

import model.gamecore.Game;

public class Player extends Participant {

    public Player() {
        super();
    }

    public Player(String name) {
        super(name);
    }
    public void joinGame(Game game) {
        game.addPlayer(this);
    }

}
