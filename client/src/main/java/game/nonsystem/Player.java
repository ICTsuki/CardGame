package main.java.game.nonsystem;

import main.java.game.gamecore.Game;

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
