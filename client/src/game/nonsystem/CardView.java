package game.nonsystem;

import javafx.scene.image.ImageView;

public class CardView {
    private final Card card;
    private final ImageView front;
    private final ImageView back;

    public CardView(Card card, ImageView front, ImageView back) {
        this.card = card;
        this.front = front;
        this.back = back;
    }

    public Card getCard() { return card; }
    public ImageView getFront() { return front; }
    public ImageView getBack() { return back; }
}
