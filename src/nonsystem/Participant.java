package nonsystem;

import gameenum.ParticipantStatus;
import gameinterface.Action;

public abstract class Participant implements Action {
    protected String name;
    protected ParticipantStatus state;
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
    public void inspectRule(){}
}
