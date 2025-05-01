package nonsystem;

import gameenum.GameType;
import gameenum.Status;
import gameinterface.Action;

import java.util.ArrayList;
import java.util.List;

public abstract class Participant implements Action {
    protected String name;
    protected Status state;
    protected List<Card> participantCards;
    protected Account participantAccount;
    protected int rank;


    public Participant() {
        super();
        participantCards = new ArrayList<>();
    }
    public Participant(String name) {
        this();
        this.name = name;
    }
    public Participant(String name, Account participantAccount) {
        this(name);
        this.participantAccount = participantAccount;
        participantCards = new ArrayList<>();
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
        participantCards.add(card);
    }

    public String getName() {
        return this.name;
    }

    public int getCardsAmount() {
        return participantCards.size();
    }
}
