import java.util.*;

public class MainPile {
    private List<Card> deck = new ArrayList<>();
    
    MainPile() {
        initDeck();
        shuffle();
    }

    private void shuffle() {
        for (int i = 0; i < deck.size(); i++) {
            int randomIndex = (int)(Math.random() * deck.size());
            Card temp = deck.get(randomIndex);
            deck.set(randomIndex, deck.get(i));
            deck.set(i, temp);
        }
    }

    public void dealCards(List<Player> playerList, Pile drawPile, Pile discardPile) {
        for (int i = 0; i < playerList.size(); i++) {
            for (int j = 0; j < 7; j++) {
                playerList.get(i).add(deck.remove(0));
            }
        }
        for (Card c : deck) {
            drawPile.setCurr(c);
        }
        discardPile.setCurr(drawPile.removeTop());
    }

    private void initDeck() {
        for (int i = 0; i < 4; i++) {
            String color = "";
            switch (i) {
                case 0: color = "red";
                        break;
                case 1: color = "green";
                        break;
                case 2: color = "yellow";
                        break;
                case 3: color = "blue";
                        break;
            }

            deck.add(new Card("0", color));

            for (int j = 0; j < 2; j++) {
                deck.add(new Card("+2", color));
            }

            for (int j = 0; j < 2; j++) {
                deck.add(new Card("reverse", color));
            }

            for (int j =0; j < 2; j++) {
                deck.add(new Card("skip", color));
            }

            for (int j = 0; j < 2; j++) {
                for (int x = 1; x <= 9; x++) {
                    deck.add(new Card(String.valueOf(x), color));
                }
            }
        } 

        for (int i = 0; i < 4; i++) {
            deck.add(new Card("wild", "all"));
        }

        for (int i = 0; i < 4; i++) {
            deck.add(new Card("+4", "all"));
        }
    }

    public Card getCurr() {
        return deck.get(0);
    }

    public void setCurr(Card newCurr) {
        deck.add(0, newCurr);
    }

    public Card removeTop() {
        return deck.remove(0);
    }

    public int getDeckSize() {
        return deck.size();
    }

    public String toString() {
        String data = "";

        for (Card c: deck) {
            data += c + "\n";
        }
        
        return data;
    }
}
