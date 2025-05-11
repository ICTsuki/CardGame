package nonsystem;


import java.util.*;

import java.util.stream.IntStream;

public class PhomPlayer extends Player{
    private Deck deck;
    List<List<Card>> phom = new ArrayList<>();

    public PhomPlayer(Deck deck) {
        super();
        this.deck = deck;
    }

    public PhomPlayer(String name) {
        super(name);
    }

    public void playATurn() {
        if(checkU()) {
            return;
        }
        hand.add(draw(deck));
        Card CardToPlay = chooseCards();
        hand.remove(CardToPlay);

    }

    public Card chooseCards() {
        return null;
    }

    public int countPhoms(List<Card> hand) {
        int count = 0;
        Set<Integer> usedIndexes = new HashSet<>();

        // Duyệt mọi tổ hợp 3 lá trong tay bài
        for (int a = 0; a < hand.size(); a++) {
            for (int b = a + 1; b < hand.size(); b++) {
                for (int c = b + 1; c < hand.size(); c++) {

                    List<Integer> trio = List.of(a, b, c);

                    // Kiểm tra xem các lá này đã bị dùng cho phỏm khác chưa
                    if (trio.stream().anyMatch(usedIndexes::contains)) continue;

                    ArrayList<Card> cards = new ArrayList<>(List.of(
                            hand.get(a), hand.get(b), hand.get(c)
                    ));

                    // Nếu là phỏm thì tăng đếm và đánh dấu index
                    if (isValidPhom(cards)) {
                        // Cập nhật count cho phỏm 3 lá
                        count++;
                        usedIndexes.addAll(trio);



                        // Thử tìm lá thứ 4 (nếu có)
                        Set<Integer> used = new HashSet<>(trio);
                        List<Integer> extra = new ArrayList<>();

                        // Duyệt qua các lá bài khác xem có lá thứ 4 hợp lệ không
                        for (int d = 0; d < hand.size(); d++) {
                            if (used.contains(d)) continue;  // Bỏ qua lá đã dùng

                            List<Card> extendedPhom = new ArrayList<>(cards);
                            extendedPhom.add(hand.get(d));  // Thêm lá thứ 4 vào phỏm

                            // Kiểm tra xem phỏm này có hợp lệ không (cả 4 lá)
                            if (isValidPhom(extendedPhom)) {
                                extra.add(d);  // Lưu index của lá thứ 4 hợp lệ
                            }
                        }

                        // Nếu tìm thấy lá thứ 4 phù hợp thì thêm nó vào phỏm
                        if (!extra.isEmpty()) {
                            usedIndexes.addAll(extra); // Đánh dấu lá thứ 4
                            cards.add(hand.get(extra.getFirst()));
                            phom.add(new ArrayList<>(cards));  // Thêm phỏm 4 lá
                        } else {
                            phom.add(new ArrayList<>(cards));
                        }
                    }
                }
            }
        }

        return count;
    }

    public boolean checkU() {
        return countPhoms(hand) == 3;
    }

    public boolean isValidPhom(List<Card> cards) {
        if (cards.size() != 3) return false;

        hand.sort(Comparator.comparingInt(c -> c.getRank().getValue()));

        boolean sameRank = cards.stream()
                .allMatch(c -> c.getRank() == cards.getFirst().getRank());

        boolean sameSuit = cards.stream()
                .allMatch(c -> c.getSuit().equals(cards.getFirst().getSuit()));

        boolean consecutive = IntStream.range(1, cards.size())
                .allMatch(i -> cards.get(i).getRank().getValue() ==
                        cards.get(i - 1).getRank().getValue() + 1);

        return sameRank || (sameSuit && consecutive);
    }

    private Card draw(Deck deck){
        return deck.cards.removeFirst();
    }

    public void releasePhom() {

    }
}
