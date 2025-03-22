import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards = new ArrayList<>();
    
    public void addCard(Card card) {
        cards.add(card);
    }
    
    public Card getCard(int index) {
        return cards.get(index);
    }
    
    public List<Card> getCards() {
        return cards;
    }
    
    public void printHand() {
        for (Card card : cards) {
            System.out.println(card.getCard());
        }
    }
    
    public int getTotalValue() {
        int total = 0;
        for (Card card : cards) {
            total += card.getValueOfCard();
        }
        return total;
    }
}
