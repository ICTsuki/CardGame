package gamecore;

import nonsystem.Player;

public class ThreeCards extends Game{
    @Override
    public void addPlayer(Player player) {
        if(players.size() + 1 > MAXPLAYER) {
            System.out.println("Player full!");
            return;
        }
        players.add(player);
        System.out.println("Player added !");
        System.out.println("Current player: " + players.size());
    }

    @Override
    public void deal() {

    }

    @Override
    public void startGame() {

    }
}
