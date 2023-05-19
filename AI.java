import java.util.*;

public class AI extends Player {
    private HashMap<Integer, String> colorsMap = new HashMap<>();

    public AI() {
        super("AI");
        colorsMap.put(0, "red");
        colorsMap.put(1, "green");
        colorsMap.put(2, "blue");
        colorsMap.put(3, "yellow");
    }

    public void move(Pile drawPile, Pile discardPile, Scanner input) {
        Card currCard = discardPile.getCurr();

        List<Card> aiDeck = getDeck();
        System.out.println(getName() + "'s turn");

        if (currCard.getValue().equals("+2")) {
            System.out.println("AI has to draw 2 cards");
            for (int i = 0; i < 2; i++) {
                Card removedCard = drawPile.removeTop();
                System.out.println("AI drew: " + removedCard);
                add(removedCard);
            }    
        } else if (currCard.getValue().equals("+4")) {
            System.out.println("AI has to draw 4 cards");
            for (int i = 0; i < 4; i++) {
                Card removedCard = drawPile.removeTop();
                System.out.println("AI drew: " + removedCard);
                add(removedCard);
            }  
        }
        
        Card pickedCard = null;

        for (Card c : aiDeck) {
            if (currCard.getColor().equalsIgnoreCase("all")) {
                pickedCard = c;
            } else {
                if (c.getValue().equals(currCard.getValue()) || c.getColor().equals(currCard.getColor())) {
                    pickedCard = c;
                    break;
                }
            }
        }

        if (pickedCard == null) {
            drawCards(discardPile, drawPile);
            for (Card c : aiDeck) {
                if (currCard.getColor().equals("all")) {
                    pickedCard = c;
                } else {
                    if (c.getValue().equals(currCard.getValue()) || c.getColor().equals(currCard.getColor())) {
                        pickedCard = c;
                        break;
                    }
                }
            }
        }

        System.out.println("AI chose " + pickedCard);

        aiDeck.remove(pickedCard);
        discardPile.setCurr(pickedCard);

        if (pickedCard.getColor().equals("all")) {
            String nextColor = colorsMap.get((int)(Math.random() * 3));
            pickedCard.setColor(nextColor);
        }
    }
}
