package client.game.nonsystem;

import client.game.gamecore.Game;
import client.game.gameenum.Status;
import client.game.gameinterface.Action;

import java.util.ArrayList;
import java.util.List;

public abstract class Participant implements Action {
    protected String name;
    protected Status state;
    protected List<Card> hand = new ArrayList<>();

    public Participant() {
        super();
        this.state = Status.READY;
    }
    public Participant(String name) {
        this();
        this.name = name;
        this.state = Status.READY;
    }

    public void joinGame(Game game){}
    public void inspectRules(){}
    public void playATurn(){}

    public void receiveCard(Card card) {
        hand.add(card);
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getHand() {
        if(hand.isEmpty()) return new ArrayList<>();
        return hand;
    }

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public void showInfo() {
        System.out.println(name + " " + state.toString());
    }
}
