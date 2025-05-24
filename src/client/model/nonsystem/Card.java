package client.model.nonsystem;

import client.model.gameenum.Rank;
import client.model.gameenum.Suit;
import java.util.*;
import java.util.stream.Collectors;

public class Card {
    private final Suit suit;
    private final Rank rank;
    private final String color;

    public Card(Suit suit, Rank rank) {
        this.suit= suit;
        this.rank = rank;
        if(suit.equals(Suit.HEART) || suit.equals(Suit.DIAMOND)) color = "RED";
        else color = "BLACK";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Card card = (Card) obj;
        return this.rank.getRankOrder() == card.rank.getRankOrder()
                && this.suit.getSuitOrder() == card.suit.getSuitOrder();
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


    public static boolean sameSuits(List<List<Card>> cards) {
        int listSize = cards.getFirst().size();

        // 1. Make sure all sublists are the same size
        if (!cards.stream().allMatch(list -> list.size() == listSize)) return false;

        // 2. For each index/column, check if all suits match
        for (int i = 0; i < listSize; i++) {
            Suit suit = cards.getFirst().get(i).getSuit(); // take suit from the first row
            for (int j = 1; j < cards.size(); j++) {
                if (cards.get(j).get(i).getSuit() != suit) return false;
            }
        }

        return true;
    }



    /**
     * This method compare cards you're going to play with field cards
     * @param cards1 Your cards which you choose to play
     * @param cards2 Current cards on field
     * @return true if your card2 bigger than field cards
     */
    public static boolean isBigger(List<Card> cards1, List<Card> cards2) { // 2 set of cards has been checked for same suit before
        if(quadCombo(cards2)) {
            if(!quadCombo(cards1)) return false;
            else {
                return cards1.getFirst().rank.getRankOrder() > cards2.getFirst().rank.getRankOrder();
            }
        } else if(quadCombo(cards1)) return true;

        if(doubleCombo(cards1) && doubleCombo(cards2)) {
            return cards1.getFirst().rank.getRankOrder() > cards2.getFirst().rank.getRankOrder();
        }
        if(tripleCombo(cards1) && tripleCombo(cards2)) {
            return cards1.getFirst().rank.getRankOrder() > cards2.getFirst().rank.getRankOrder();
        }
        if(straight(cards2) && !straight(cards1)) return false;
        return cards1.getFirst().rank.getRankOrder() > cards2.getFirst().rank.getRankOrder();
    }

    public static boolean doubleCombo(List<Card> cards) {
        return cards.get(0).color.equals(cards.get(1).color) &&
                cards.get(0).rank.getRankOrder() == cards.get(1).rank.getRankOrder();
    }

    public static boolean tripleCombo(List<Card> cards) {
        return cards.get(0).rank.getRankOrder() == cards.get(1).rank.getRankOrder() &&
                cards.get(1).rank.getRankOrder() == cards.get(2).rank.getRankOrder();
    }

    public static boolean quadCombo(List<Card> cards) {
        int rankCombo = cards.getFirst().rank.getRankOrder();
        return cards.stream().allMatch(card -> card.getRank().getRankOrder() == rankCombo);
    }

    public static boolean straight(List<Card> cards) {
        Suit suitCombo = cards.getFirst().suit;
        for(int i = 1; i < cards.size(); i++) {
            if(!cards.get(i).suit.equals(suitCombo)) return false;
        }

        List<Card> sorted = sortCardRank(cards);
        int prevRank = sorted.getFirst().rank.getRankOrder();
        for(int i = 1; i < sorted.size(); i++) {
            if(sorted.get(i).rank.getRankOrder() - prevRank != 1) return false;
            prevRank = sorted.get(i).rank.getRankOrder();
        }

        return true;
    }
}
