package nonsystem;

import gamecore.Game;

import java.util.ArrayList;
import java.util.List;

public class PhomField extends Field{
    public static List<List<Card>> playerField = new ArrayList<>(Game.players.size());
    public PhomField() {
        for(List<Card> field : playerField) {
            field = new ArrayList<>();
        }
    }

}
