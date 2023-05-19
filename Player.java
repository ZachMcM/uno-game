import java.util.*;

public class Player {
    private String name;
    private List<Card> playerDeck = new ArrayList<>();

    Player(String name) {
        this.name = name;
    }

    public List<Card> getDeck() {
        return playerDeck;
    }

    public void add(Card newCard) {
        playerDeck.add(newCard);
    }

    public boolean isDeckEmpty() {
        return playerDeck.size() == 0;
    }

    public String toString() {
        String data = "| ";
        for (Card c : playerDeck) {
            data += c + "  |  ";
        }
        return data;
    }

    public String getName() {
        return name;
    }

    public void move(Pile drawPile, Pile discardPile, Scanner input) {
        Card currCard = discardPile.getCurr();

        System.out.println(name + "'s turn");
        System.out.println("The current card is: " + currCard);

        if (currCard.getValue().equals("+2")) {
            System.out.println(name + " have to draw two cards");
            for (int i = 0; i < 2; i++) {
                Card removedCard = drawPile.removeTop();
                System.out.println(name + " drew: " + removedCard);
                playerDeck.add(removedCard);
            }    
        } else if (currCard.getValue().equals("+4")) {
            System.out.println(name + " have to draw two cards");
            for (int i = 0; i < 4; i++) {
                Card removedCard = drawPile.removeTop();
                System.out.println(name + " drew: " + removedCard);
                playerDeck.add(removedCard);
            }  
        }

        System.out.println(name + "'s deck...");

        System.out.println(this);

        Card pickedCard = null;

        if (!canPlay(discardPile)) {
            drawCards(discardPile, drawPile);
        }

        System.out.println("Pick a card by typing it's value followed by a space and it's color (all for wild cards)");
        String unformattedPick = input.nextLine();
        String[] pickArr = {
            unformattedPick.substring(0, unformattedPick.indexOf(" ")),
            unformattedPick.substring(unformattedPick.indexOf(" ") + 1)
        };
        
        pickedCard = (isPickValid(discardPile, pickArr));
        while (pickedCard == null) {
            System.out.println("Pick a valid card by typing it's value followed by a space and it's color (all for wild cards)");
            unformattedPick = input.nextLine();
            pickArr[0] = unformattedPick.substring(0, unformattedPick.indexOf(" "));
            pickArr[1] = unformattedPick.substring(unformattedPick.indexOf(" ") + 1);
            pickedCard = isPickValid(discardPile, pickArr);
        }

        playerDeck.remove(pickedCard);
        discardPile.setCurr(pickedCard);
        clearScreen();

        System.out.println(name + " chose " + pickedCard);

        if (pickedCard.getColor().equals("all")) {
            System.out.print("Please set a color (red, green, yellow, or blue): ");
            String nextColor = input.nextLine();
            while (!nextColor.equals("blue") && !nextColor.equals("green") && !nextColor.equals("yellow") && !nextColor.equals("red")) {
                System.out.print("Please chose a valid next color: ");
                nextColor = input.nextLine();
            }
            pickedCard.setColor(nextColor);
            clearScreen();
        }
    }

    private Card isPickValid(Pile discardPile, String[] pickArr) {
        Card currCard = discardPile.getCurr();
        for (Card card : playerDeck) {
            if (currCard.getColor().equals("all")) {
                if (card.getValue().equals(pickArr[0]) && card.getColor().equals(pickArr[1])) {
                    return card;
                }
            } else {
                if (card.getValue().equals(pickArr[0]) && card.getColor().equals(pickArr[1])) {
                    if (card.getColor().equals("all")) {
                        return card;
                    } else if (card.getValue().equals(currCard.getValue()) || card.getColor().equals(currCard.getColor())) {
                        return card;
                    }
                }
            }
        }
        return null;
    }

    protected void drawCards(Pile discardPile, Pile drawPile) {
        Card currCard = discardPile.getCurr();
        Card drawTop = drawPile.removeTop();
        while (!drawTop.getColor().equals(currCard.getColor()) && !drawTop.getValue().equals(currCard.getValue())) {
            drawTop = drawPile.removeTop();
            playerDeck.add(0, drawTop);
            System.out.println(name + " had to draw: " + drawTop);
        }
    }

    private boolean canPlay(Pile discardPile) {
        Card currCard = discardPile.getCurr();
        for (Card c : playerDeck) {
            if (c.getColor().equals(currCard.getColor()) || c.getValue().equals(currCard.getValue())) {
                return true;
            }
        }
        return false;
    }

    public void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}
