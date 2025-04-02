package nonsystem;

public class Account {
    private String accountID;
    private String accountPassword;
    private String accountEmail;

    public Account() {
        super();
    }
    public Account(String email, String password) {
        accountEmail = email;
        accountPassword = password;
    }

    public void changePassword(String newPassword) {
        this.accountPassword = newPassword;
    }
}
