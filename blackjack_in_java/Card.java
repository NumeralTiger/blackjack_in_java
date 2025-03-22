import java.util.Random;

public class Card {
    private String card;
    private int valueOfCard;
    private static final Random random = new Random();
    private static final String[] suits = {"Spades", "Clubs", "Hearts", "Diamonds"};
    private static final String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public Card() {
        int suit = random.nextInt(4);
        int rank = random.nextInt(13);
        this.card = ranks[rank] + " of " + suits[suit];
        if (rank == 0) {  // Ace
            this.valueOfCard = 0;  
        } else if (rank >= 10) {  
            this.valueOfCard = 10;
        } else {
            this.valueOfCard = rank + 1;
        }
    }

    public String getCard() {
        return card;
    }

    public int getValueOfCard() {
        return valueOfCard;
    }

    public void setCardValue(int value) {
        this.valueOfCard = value;
    }

}
