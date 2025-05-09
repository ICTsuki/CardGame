package nonsystem;

import java.util.*;
import gameenum.*;

public class Deck {
    public final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (cards.isEmpty()) return null;
        return cards.removeFirst();
    }

    public int remainingCards() {
        return cards.size();
    }
}
