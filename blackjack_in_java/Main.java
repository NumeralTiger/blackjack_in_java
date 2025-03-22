
/**
 * Problem Description: My java project brings the traditional game of blackjack to life for anyone to enjoy on their terminal all because of java programming. 
 * The game follows normal rules, you against the dealer. The dealer is programmed to hit when their score is 16 and below, and stay when 17 or higher.
 *
 * The program consists of the following classes:
 * 
 * - Main.java:
 *   - Is the set entry point of the program, defines Scanner to handle user input, initializes variables, starts a loop which allows the user to continue playing until they wish to.
 *   - Two Hand objects are created and conditions for a blackjack (an ace and a ten) are checked. If nobody has a blackjack, the user can hit or stay, they bust if their score is over 21 and lose.
 *   - The user's total card value is calculated using the "calculateTotal" method, which also handles the special case of an ace where the user can choose a 1 or an 11.
 *   - The dealer's total card value is calculated using the "calculateDealerTotal" method, which also ensures the dealer follows the rules of hitting on 16 or less and staying on 17 or more.
 *   - The "checkBlackjack", "calculateTotal", and "calculateDealerTotal" methods are helper functions used to manage the game logic. The game loops until the user wishes to stop (by typing 'n').
 *
 * - Card.java:
 *   - Imports "java.util.Random" to ensure deck is shuffled and chooses random cards for the dealer and the player.
 *   - The Card constructor generates a random card by selecting a random suit and rank from the suits and ranks arrays.
 *   - Has appropriate setters and getter methods like "getCard", "getValueOfCard", "setCardValue" which also handles the special case of an ace, which can be 1 or 11.
 *
 * - Hand.java:
 *   - Deals with managing cards dealt to the user and the dealer, It uses an ArrayList to store the cards.
 *   - The "addCard" method allows a new Card to be added to the hand.
 *   - The "getCard" method retrieves a Card from the hand based on its position in the list. It takes an integer index as a parameter and returns the Card at that position.
 *   - The "getCards" method returns the entire list of Cards in the hand.
 *   - The "printHand" method iterates over the list of cards and prints the string representation of each card to the console.
 *   - The "getTotalValue" method calculates the total value of the hand by summing the values of each card in the list and returning the total, useful in determining who won.
 * 
 * - By Zaid Mohiuddin Run the main file to play!
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        int userScore = 0;
        int dealerScore = 0;

        System.out.println("Welcome to TMU's Blackjack table!");
        System.out.println("BlackJack in your terminal!");
        System.out.println("Rules: The dealer must hit when their cards total 16 or less and must stay when their cards total 17 or more.");

        while (!stop) {
            Hand userHand = new Hand();
            Hand dealerHand = new Hand();

            userHand.addCard(new Card());
            userHand.addCard(new Card());

            boolean gameOver = checkBlackjack(userHand, "You got a perfect blackjack (an ace and a 10) you win!");
            if (gameOver) {
                userScore++;
                continue;
            }

            dealerHand.addCard(new Card());
            dealerHand.addCard(new Card());

            gameOver = checkBlackjack(dealerHand, "The dealer got a perfect blackjack (an ace and a 10) the dealer wins!");
            if (gameOver) {
                dealerScore++;
                continue;
            }

            System.out.println("One of the dealer's cards is: \n" + dealerHand.getCard(0).getCard());

            boolean roundOver = false;
            boolean userPlaying = true;
            int userTotal = 0;
            int dealerTotal = 0;

            while (!roundOver) {
                if (userPlaying) {
                    System.out.println("Your two cards are:");
                    userTotal = calculateTotal(userHand, scanner);
                    System.out.println("Your cards total value is: " + userTotal);

                    if (userTotal > 21) {
                        roundOver = true;
                        dealerScore++;
                        System.out.println("BUST! Your cards are over 21. You lose.");
                    } else {
                        System.out.println("Do you want to hit or stay:");
                        String choice = scanner.nextLine().toLowerCase();
                        if (choice.equals("hit")) {
                            userHand.addCard(new Card());
                        } else if (choice.equals("stay")) {
                            userPlaying = false;
                        }
                    }
                } else {
                    dealerTotal = calculateDealerTotal(dealerHand);
                    if (dealerTotal > 21) {
                        System.out.println("The dealer has the cards:");
                        dealerHand.printHand();
                        userScore++;
                        roundOver = true;
                        System.out.println("BUST! Dealer got over 21. You win!");
                    } else if (dealerTotal >= 17) {
                        System.out.println("The dealer has the cards:");
                        dealerHand.printHand();
                        if (dealerTotal > userTotal) {
                            System.out.println("Dealer wins! The dealer got a total value of " + dealerTotal);
                            dealerScore++;
                        } else if (dealerTotal < userTotal) {
                            System.out.println("You win! The dealer got a total value of " + dealerTotal);
                            userScore++;
                        } else {
                            System.out.println("You and the dealer draw.");
                            userScore++;
                            dealerScore++;
                        }
                        roundOver = true;
                    }
                }
            }

            System.out.println("Your score is: " + userScore + " And the dealer's score is: " + dealerScore);
            System.out.println("Do you want to play again (y/n)?");
            String playAgain = scanner.nextLine().toLowerCase();
            stop = playAgain.equals("n");
        }
        scanner.close();
    }

    private static boolean checkBlackjack(Hand hand, String message) {
        if ((hand.getCard(0).getValueOfCard() == 0 && hand.getCard(1).getValueOfCard() == 10) ||
            (hand.getCard(0).getValueOfCard() == 10 && hand.getCard(1).getValueOfCard() == 0)) {
            System.out.println(message);
            return true;
        }
        return false;
    }

    private static int calculateTotal(Hand hand, Scanner scanner) {
        int total = 0;
        for (Card card : hand.getCards()) {
            System.out.println(card.getCard());
            if (card.getValueOfCard() == 0) {
                System.out.println("You have an ace, do you want a 1 or an 11?");
                int oneOrEleven = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (oneOrEleven == 1) {
                    card.setCardValue(1);
                } else {
                    card.setCardValue(11);
                }
            }
            total += card.getValueOfCard();
        }
        return total;
    }

    private static int calculateDealerTotal(Hand hand) {
        int total = 0;
        for (Card card : hand.getCards()) {
            if (card.getValueOfCard() == 0) card.setCardValue(11);
        }
        total = hand.getTotalValue();
        while (total <= 16) {
            hand.addCard(new Card());
            total = hand.getTotalValue();
        }
        total = 0;
        for (Card card : hand.getCards()) {
            if (card.getValueOfCard() == 0) {
                if (total + 11 <= 21) {
                    card.setCardValue(11);
                } else {
                    card.setCardValue(1);
                }
            }
            total += card.getValueOfCard();
        }
        return total;
    }
}
