package nonsystem;

import gamecore.Game;

import java.util.ArrayList;
import java.util.List;

public class PhomField extends Field{
    public List<List<Card>> playerField;
    public PhomField(Game game) {
        playerField = new ArrayList<>(Game.players.size() * 4);
        for(List<Card> field : playerField) {
            field = new ArrayList<>();
        }
    }

}
