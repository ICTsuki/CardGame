package nonsystem;

import gameenum.Status;
import gameinterface.Action;

public abstract class Participant implements Action {
    protected String name;
    protected Status state;
    protected Deck participantDeck;
    protected Account participantAccount;
    protected int rank;

    public Participant() {
        super();
    }
    public Participant(String name, Account participantAccount) {
        super();
        this.name = name;
        this.participantAccount = participantAccount;
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
    public String getName() {
        return this.name;
    }
}
