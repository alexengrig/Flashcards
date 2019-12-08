package flashcards;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Input the number of cards:");
        int count = Integer.parseInt(SCANNER.nextLine());
        Cards cards = new Cards(count);
        for (int i = 1; i <= count; i++) {
            System.out.println("The card #" + i + ":");
            String term;
            while (cards.containsTerm(term = SCANNER.nextLine())) {
                System.out.println("The card \"" + term + "\" already exists. Try again:");
            }
            System.out.println("The definition of the card #" + i + ":");
            String definition;
            while ((cards.containsDefinition(definition = SCANNER.nextLine()))) {
                System.out.println("The definition \"" + definition + "\" already exists. Try again:");
            }
            Card card = Card.of(term, definition);
            cards.add(card);
        }
        for (Card card : cards.values()) {
            System.out.println("Print the definition of \"" + card.term + "\":");
            String answer = SCANNER.nextLine();
            boolean right = card.right(answer);
            if (right) {
                System.out.println("Correct answer.");
            } else if (cards.containsDefinition(answer)) {
                Card other = cards.getByDefinition(answer);
                System.out.println("Wrong answer. The correct one is \"" + card.definition +
                        "\", you've just written the definition of \"" +
                        other.term + "\".");
            } else {
                System.out.println("Wrong answer. The correct one is \"" + card.definition + "\".");
            }
        }
    }

    static class Cards {
        private final Map<String, Card> terms;
        private final Map<String, Card> definitions;

        Cards(int count) {
            terms = new LinkedHashMap<>(count);
            definitions = new LinkedHashMap<>(count);
        }

        void add(Card card) {
            terms.put(card.term, card);
            definitions.put(card.definition, card);
        }

        boolean containsTerm(String term) {
            return terms.containsKey(term);
        }

        boolean containsDefinition(String definition) {
            return definitions.containsKey(definition);
        }

        Card getByDefinition(String definition) {
            return definitions.get(definition);
        }

        Collection<Card> values() {
            return terms.values();
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
