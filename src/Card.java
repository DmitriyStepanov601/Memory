import javax.swing.*;

/**
 * Class describing the card
 * @author Dmitriy Stepanov
 */
public class Card extends JButton {
    private int numberCard;
    private boolean matched = false;
    private boolean selected = false;

    // getters and setters for fields
    public int getNumberCard() { return numberCard; }
    public void setNumberCard(int num){
        this.numberCard = num;
    }
    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    public boolean isMatched() { return matched; }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isSelected() { return selected; }

    public boolean sameNumberCard(Card other) {
        if (other == null) {
            System.err.println("sameType(Card) received null");
            return false;
        }
        return this.getNumberCard() == other.getNumberCard();
    }
}