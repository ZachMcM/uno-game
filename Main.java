import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        MainPile deck = new MainPile();
        Pile discardPile = new Pile();
        Pile drawPile = new Pile();

        List<Player> players = new ArrayList<>();

        System.out.println("\n ----UNO----");
        System.out.print("Enter how many players (max 20): ");
        int inputPlayerCount = input.nextInt();
        input.nextLine();
        while (inputPlayerCount <= 0 || inputPlayerCount > 20) {
            System.out.println("Invalid player count");
            System.out.print("Please enter a valid player count: ");
            inputPlayerCount = input.nextInt();
            input.nextLine();
        }
        clearScreen();

        if (inputPlayerCount == 1) {
            System.out.println("Your opponent will be an AI");
            players.add(new AI());
            System.out.print("Enter your name: ");
            players.add(new Player(input.nextLine()));
            clearScreen();
        } else {
            for (int i = 0; i < inputPlayerCount; i++) {
                System.out.print("Enter player " + (i + 1) + " name: ");
                players.add(new Player(input.nextLine()));
                clearScreen();
            }
        }

        System.out.println("The game will now begin");
        System.out.println("Dealing cards...");
        deck.dealCards(players, drawPile, discardPile);

        if (players.size() > 2) {
            for (int i = 0; i < players.size(); i++) {
                if (!(players.get(i) instanceof AI)) {
                    System.out.println(players.get(i).getName() + "'s current deck");
                    System.out.println(players.get(i));
                    if (players.size() != 2 && i != players.size() - 1) {
                        System.out.print("Type next to see the next players deck: ");
                        String next = input.nextLine();
                        while (!next.equals("next")) {
                            System.out.print("Please type next: ");
                            next = input.nextLine();
                        }
                        clearScreen();
                    }
                }
            }
        }

        Node<Player> head = convert(players);
        Node<Player> currentPlayer = head;

        boolean skipped = false;
        boolean forward = true;

        while (!isADeckEmpty(players)) {
            if (deck.getCurr().getValue().equals("reverse") && players.size() > 2) {
                forward = false;
                currentPlayer = currentPlayer.prev;
                currentPlayer = currentPlayer.prev;
            } else if (deck.getCurr().getValue().equals("skip")) {
                if (skipped) {
                    currentPlayer.data.move(drawPile, discardPile, input);
                    if (currentPlayer.data.isDeckEmpty()) {
                        break;
                    }
                    if (forward) {
                        currentPlayer = currentPlayer.next;
                    } else {
                        currentPlayer = currentPlayer.prev;
                    }
                } else {
                    if (forward) {
                        currentPlayer = currentPlayer.next;
                    } else {
                        currentPlayer = currentPlayer.prev;
                    }
                    skipped = true;
                }
                skipped = false;
            } else {
                currentPlayer.data.move(drawPile, discardPile, input);
                if (currentPlayer.data.isDeckEmpty()) {
                    break;
                }
                if (forward) {
                    currentPlayer = currentPlayer.next;
                } else {
                    currentPlayer = currentPlayer.prev;
                }
            }
        }

        for (Player player : players) {
            if (player.isDeckEmpty()) {
                System.out.println(player + " won!");
                break;
            }
        }

        input.close();
    }

    public static boolean isADeckEmpty(List<Player> players) {
        for (Player p : players) {
            if (p.isDeckEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static <T> Node<Player> convert(List<Player> plaeryList) {
        Node<Player> head = null;
        Node<Player> tail = null;
        for (Player element : plaeryList) {
            Node<Player> newNode = new Node<Player>(element);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;   
                tail = newNode;
            }
        }

        if (head != null) {
            head.prev = tail;
            tail.next = head;
        }

        return head;
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}