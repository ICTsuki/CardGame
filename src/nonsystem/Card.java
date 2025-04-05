package nonsystem;

import gameenum.Suit;

public class Card {
    private final Suit type;
    private String name;

    public Card(Suit type, String name) {
        this.type = type;
        this.name = name;
    }
}
