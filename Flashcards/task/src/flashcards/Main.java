package flashcards;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        CardsHolder holder = new CardsHolder();
        holder.start();
    }

    static class CardsHolder {
        private final Scanner scanner = new Scanner(System.in);
        private final Cards cards = new Cards();

        void start() {
            while (true) {
                System.out.println("Input the action (add, remove, import, export, ask, exit):");
                String action = scanner.nextLine();
                if ("add".equals(action)) {
                    addCard();
                } else if ("remove".equals(action)) {
                    removeCard();
                } else if ("import".equals(action)) {
                    importCards();
                } else if ("export".equals(action)) {
                    exportCards();
                } else if ("ask".equals(action)) {
                    ask();
                } else if ("exit".equals(action)) {
                    exit();
                    break;
                }
                System.out.println();
            }
        }

        void addCard() {
            System.out.println("The card:");
            String term = scanner.nextLine();
            if (cards.containsTerm(term)) {
                System.out.println("The card \"" + term + "\" already exists.");
                return;
            }
            System.out.println("The definition of the card:");
            String definition = scanner.nextLine();
            if (cards.containsDefinition(definition)) {
                System.out.println("The definition \"" + definition + "\" already exists.");
                return;
            }
            Card card = Card.of(term, definition);
            cards.add(card);
            System.out.println("The pair (" + card + ") has been added.");
        }

        void removeCard() {
            System.out.println("The card:");
            String term = scanner.nextLine();
            if (cards.containsTerm(term)) {
                System.out.println("Can't remove \"" + term + "\": there is no such card.");
                return;
            }
            cards.removeByTerm(term);
            System.out.println("The card has been removed.");
        }

        void importCards() {
            System.out.println("File name:");
            String filename = scanner.nextLine();
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("File not found.");
                return;
            }
            List<Card> importCards = new ArrayList<>();
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));) {
                Card card;
                while ((card = (Card) input.readObject()) != null) {
                    importCards.add(card);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
            for (Card card : importCards) {
                cards.add(card);
            }
            System.out.println(importCards.size() + " cards have been loaded.");
        }

        void exportCards() {
            System.out.println("File name:");
            String filename = scanner.nextLine();
            File file = new File(filename);
            Collection<Card> exportCards = cards.values();
            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));) {
                for (Card card : exportCards) {
                    output.writeObject(card);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            System.out.println(exportCards.size() + " cards have been saved.");
        }

        void ask() {
            System.out.println("How many times to ask?");
            int count = scanner.nextInt();
            List<Card> allCards = this.cards.values();
            int length = Math.min(count, allCards.size());
            for (int i = 0; i < length; i++) {
                Card card = allCards.get(i);
                System.out.println("Print the definition of \"" + card.term + "\":");
                String answer = scanner.nextLine();
                if (card.definition.equals(answer)) {
                    System.out.println("Correct answer");
                } else if (cards.containsDefinition(answer)) {
                    Card other = cards.getByDefinition(answer);
                    System.out.println("Wrong answer. The correct one is \"" + card.definition + "\"" +
                            ", you've just written the definition of \"" + other.term + "\".\n");
                } else {
                    System.out.println("Wrong answer. The correct one is \"" + card.definition + "\".");
                }
            }
        }

        void exit() {
            System.out.println("Bye bye!");
        }
    }

    static class Cards {
        private final Map<String, Card> terms;
        private final Map<String, Card> definitions;

        Cards() {
            terms = new LinkedHashMap<>();
            definitions = new LinkedHashMap<>();
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

        List<Card> values() {
            return new ArrayList<>(terms.values());
        }

        public void removeByTerm(String term) {
            Card removedCard = terms.remove(term);
            definitions.remove(removedCard.definition);
        }
    }

    static class Card implements Serializable {
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

        @Override
        public String toString() {
            return term +
                    ":" +
                    definition;
        }
    }
}
