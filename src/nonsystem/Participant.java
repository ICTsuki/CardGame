package nonsystem;

import gameenum.Status;
import gameinterface.Action;

import java.util.ArrayList;
import java.util.List;

public abstract class Participant implements Action {
    protected String name;
    protected Status state;
    protected List<Card> hand;
    protected int rank;


    public Participant() {
        super();
        hand = new ArrayList<>();
        this.state = Status.READY;
    }
    public Participant(String name) {
        this();
        this.name = name;
        hand = new ArrayList<>();
        this.state = Status.READY;
    }

    public void joinGame(){}
    public void quitGame(){}
    public void login(){}
    public void logout(){}
    public void inspectRules(){}
    public void playATurn(){}

    public void receiveCard(Card card) {
        hand.add(card);
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getHand() {
        if(hand.isEmpty()) return null;
        return hand;
    }

    public int getRank() {
        return rank;
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
