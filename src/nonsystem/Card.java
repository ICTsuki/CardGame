package nonsystem;

import gameenum.Rank;
import gameenum.Suit;

public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit= suit;
        this.rank = rank;
    }
    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    public static int compareRank(Rank r1, Rank r2) {
        return Integer.compare(r1.getRankOrder(), r2.getRankOrder());
    }
}
