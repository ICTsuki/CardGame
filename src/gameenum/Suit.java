package gameenum;

public enum Suit {
    HEART(4), SPADE(1), CLUB(2), DIAMOND(3);

    private final int suitOrder;

    Suit(int suitOrder) {
        this.suitOrder = suitOrder;
    }

    public int getSuitOrder() {
        return this.suitOrder;
    }
}
