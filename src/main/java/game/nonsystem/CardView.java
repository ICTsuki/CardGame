package main.java.game.nonsystem;

import javafx.scene.image.ImageView;

public class CardView {
    private final Card card;
    private final ImageView front;
    private final ImageView back;
    private boolean selected = false;

    public CardView(Card card, ImageView front, ImageView back) {
        this.card = card;
        this.front = front;
        this.back = back;
    }

    public Card getCard() { return card; }
    public ImageView getFront() { return front; }
    public ImageView getBack() { return back; }

    public void enableSelection() {
        front.setOnMouseClicked(e -> toggleSelection());
    }

    private void toggleSelection() {
        selected = !selected;
        if(selected) {
            front.setOpacity(0.5);
        } else {
            front.setOpacity(1.0);
        }
    }
    public boolean isSelected() {
        return selected;
    }
}
