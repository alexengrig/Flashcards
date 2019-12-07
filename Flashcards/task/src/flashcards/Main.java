package flashcards;

import java.util.Scanner;

public class Main {
    static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        String term = SCANNER.nextLine();
        String definition = SCANNER.nextLine();
        Card card = new Card(term, definition);
        String answer = SCANNER.nextLine();
        boolean right = card.right(answer);
        if (right) {
            System.out.println("Your answer is right!");
        } else {
            System.out.println("Your answer is wrong...");
        }
    }

    static class Card{
        final String term;
        final String definition;

        Card(String term, String definition) {
            this.term = term;
            this.definition = definition;
        }

        boolean right(String answer) {
            return definition.equals(answer);
        }
    }
}
