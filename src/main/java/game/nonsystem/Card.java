package main.java.game.nonsystem;

import main.java.game.gameenum.Rank;
import main.java.game.gameenum.Suit;
import java.util.*;
import java.util.stream.Collectors;

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


    public static List<Card> sortCardSuit(Collection<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(c -> c.getSuit().getSuitOrder()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Card> sortCardRank(Collection<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(c -> c.getRank().getRankOrder()))
                .collect(Collectors.toCollection(ArrayList :: new));
    }



    /**
     * This method compare cards you're going to play with field cards
     * @param cards1 Your cards which you choose to play
     * @param cards2 Current cards on field
     * @return true if your card2 bigger than field cards
     */
    public static boolean isBigger(List<Card> cards1, List<Card> cards2) {
        if (cards1 == null || cards2 == null || cards1.size() != cards2.size()) return false;

        // QUAD
        if (quadCombo(cards2)) {
            return quadCombo(cards1) &&
                    cards1.getFirst().rank.getRankOrder() > cards2.getFirst().rank.getRankOrder();
        } else if (quadCombo(cards1)) {
            return true;
        }

        // TRIPLE
        if (tripleCombo(cards2)) {
            return tripleCombo(cards1) &&
                    cards1.getFirst().rank.getRankOrder() > cards2.getFirst().rank.getRankOrder();
        }

        // DOUBLE
        if (doubleCombo(cards2)) {
            return doubleCombo(cards1) &&
                    cards1.getFirst().rank.getRankOrder() > cards2.getFirst().rank.getRankOrder();
        }

        // STRAIGHT
        if (straight(cards2)) {
            return straight(cards1) &&
                    cards1.getFirst().rank.getRankOrder() > cards2.getFirst().rank.getRankOrder();
        }

        // SINGLE
        return cards1.size() == 1 && cards2.size() == 1 &&
                cards1.getFirst().rank.getRankOrder() > cards2.getFirst().rank.getRankOrder();
    }


    public static boolean doubleCombo(List<Card> cards) {
        if(cards.size() < 2) return false;
        return cards.get(0).rank.getRankOrder() == cards.get(1).rank.getRankOrder();
    }

    public static boolean tripleCombo(List<Card> cards) {
        if(cards.size() < 3) return false;
        return cards.get(0).rank.getRankOrder() == cards.get(1).rank.getRankOrder() &&
                cards.get(1).rank.getRankOrder() == cards.get(2).rank.getRankOrder();
    }

    public static boolean quadCombo(List<Card> cards) {
        if(cards.size() != 4) return  false;
        int rankCombo = cards.getFirst().rank.getRankOrder();
        return cards.stream().allMatch(card -> card.getRank().getRankOrder() == rankCombo);
    }

    public static boolean straight(List<Card> cards) {
        if(cards.size() < 3) return  false;
        List<Card> sorted = sortCardRank(cards);
        int prevRank = sorted.getFirst().rank.getRankOrder();
        for(int i = 1; i < sorted.size(); i++) {
            if(sorted.get(i).rank.getRankOrder() - prevRank != 1) return false;
            prevRank = sorted.get(i).rank.getRankOrder();
        }

        return cards.getFirst().rank.getRankOrder() != 13;
    }
}
