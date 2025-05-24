package client.model.nonsystem;

import client.model.gamecore.Game;

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
