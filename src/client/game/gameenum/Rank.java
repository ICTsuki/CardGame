package client.game.gameenum;

public enum Rank {
    THREE(1), FOUR(2), FIVE(3), SIX(4), SEVEN(5), EIGHT(6),
    NINE(7), TEN(8), JACK(9), QUEEN(10), KING(11), ACE(12), TWO(13);

    private final int value;       // for display
    private final int rankOrder;   // for comparison

    Rank(int rankOrder) {
        this.value = ordinal() + 1;  // default display value (or customize)
        this.rankOrder = rankOrder;
    }

    public int getValue() {
        return value;
    }

    public int getRankOrder() {
        return rankOrder;
    }
}
