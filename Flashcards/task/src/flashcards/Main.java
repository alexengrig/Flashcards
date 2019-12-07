package flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Input the number of cards:");
        int count = Integer.parseInt(SCANNER.nextLine());
        List<Card> cards = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            System.out.println("The card #" + i + ":");
            String term = SCANNER.nextLine();
            System.out.println("The definition of the card #" + i + ":");
            String definition = SCANNER.nextLine();
            Card card = Card.of(term, definition);
            cards.add(card);
        }
        for (Card card : cards) {
            System.out.println("Print the definition of \"" + card.term + "\":");
            String answer = SCANNER.nextLine();
            boolean right = card.right(answer);
            if (right) {
                System.out.println("Correct answer.");
            } else {
                System.out.println("Wrong answer. The correct one is \"" + card.definition + "\".\n");
            }
        }
    }

    static class Card {
        final String term;
        final String definition;

        private Card(String term, String definition) {
            this.term = term;
            this.definition = definition;
        }

        static Card of(String term, String definition) {
            return new Card(term, definition);
        }

        boolean right(String answer) {
            return definition.equals(answer);
        }
    }
}
