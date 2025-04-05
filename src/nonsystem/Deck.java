package nonsystem;

import java.util.HashSet;
import java.util.Set;

public class Deck {
    private Set<Card> deck = new HashSet<>();

    public Deck(Set<Card> deck) {
        this.deck = deck;
    }
}
