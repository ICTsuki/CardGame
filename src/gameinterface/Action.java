package gameinterface;

public interface Action {
    public void joinGame();
    public void quitGame();
    public void login();
    public void logout();
    public void inspectRules();
    public void playATurn();

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) System.out.println();
    }
}
