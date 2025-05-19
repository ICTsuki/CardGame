package nonsystem;

import gameenum.Rank;
import gameenum.Suit;
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

    public static int compareRank(Rank r1, Rank r2) {
        return Integer.compare(r1.getRankOrder(), r2.getRankOrder());
    }

    public static List<Card> sortCard(Collection<Card> cards1) {
        return cards1.stream()
                .sorted(Comparator.comparing(c -> c.getSuit().getSuitOrder()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Map<String, List<Card>> sortCards(List<Collection<Card>> cardsList) {
        Map<String, List<Card>> sortedCards = new HashMap<>();

        // Loop through each Collection<Card> in the list
        for (int i = 0; i < cardsList.size(); i++) {
            List<Card> sortedList = sortCard(cardsList.get(i));
            sortedCards.put("sort" + (i + 1), sortedList);  // Add each sorted collection to the map
        }

        return sortedCards;
    }

    /**
     * This method compare if 2 cards have the same suit of every element card
     * @param cards1 set of card 1
     * @param cards2 set of card 2
     * @return true if both cards have same suit of all element card
     */
    public static boolean sameSuits(Collection<Card> cards1, Collection<Card> cards2) {
        Map<String, List<Card>> sort = Card.sortCards(Arrays.asList(cards2, cards1));

        for(int i = 0; i < sort.get("sort1").size(); i++) {
            if(sort.get("sort1").get(i).suit.getSuitOrder() != sort.get("sort2").get(i).suit.getSuitOrder()) return false;
        }
        return true;
    }


    /**
     * This method compare cards you're going to play with field cards
     * @param cards1 Your cards which you choose to play
     * @param cards2 Current cards on field
     * @return true if your card2 bigger than field cards
     */
    public static boolean isBigger(Collection<Card> cards1, Collection<Card> cards2) {

        Map<String, List<Card>> sort = sortCards(Arrays.asList(cards1, cards2));

        for(int i = 0; i < sort.get("sort1").size(); i++) {
            if(compareRank(sort.get("sort1").get(i).rank, sort.get("sort2").get(i).rank) <= 0){
                System.out.println("Card chose smaller than field.\nPlease choose others or pass turn!");
                return false;
            }
        }
        return true;
    }
}
