package nonsystem;

import gameenum.GameType;
import gameenum.Status;
import gameinterface.Action;

import java.util.ArrayList;
import java.util.List;

public abstract class Participant implements Action {
    protected String name;
    protected Status state;
    protected List<Card> hand;
    protected Account participantAccount;
    protected int rank;


    public Participant() {
        super();
        hand = new ArrayList<>();
    }
    public Participant(String name) {
        this();
        this.name = name;
    }
    public Participant(String name, Account participantAccount) {
        this(name);
        this.participantAccount = participantAccount;
        hand = new ArrayList<>();
    }

    public void createAccount(String email, String password) {
        participantAccount = new Account(email, password);
    }
    public void changePassword(String newPassword) {
        participantAccount.changePassword(newPassword);
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

    public int getCardsAmount() {
        if(hand.isEmpty()) return 0;
        return hand.size();
    }
}
