import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        MainPile deck = new MainPile();
        Pile discardPile = new Pile();
        Pile drawPile = new Pile();

        List<Player> players = new ArrayList<>();

        System.out.println("\n ----UNO----");
        System.out.print("Enter how many players (max 8): ");
        String inputPlayerCount = input.nextLine();
        while (!isValidNumber(inputPlayerCount)) {
            System.out.println("Invalid player count");
            System.out.print("Please enter a valid player count: ");
            inputPlayerCount = input.nextLine();
        }
        int playerCount = Integer.parseInt(inputPlayerCount);
        clearScreen();

        if (playerCount == 1) {
            System.out.println("Your opponent will be an AI");
            players.add(new AI());
            System.out.print("Enter your name: ");
            players.add(new Player(input.nextLine()));
            clearScreen();
        } else {
            for (int i = 0; i < playerCount; i++) {
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
            if (discardPile.getCurr().getValue().equals("reverse") && players.size() > 2) {
                forward = false;
                currentPlayer = currentPlayer.prev;
                currentPlayer = currentPlayer.prev;
            } else if (discardPile.getCurr().getValue().equals("skip")) {
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
                    skipped = false;
                } else {
                    if (forward) {
                        currentPlayer = currentPlayer.next;
                    } else {
                        currentPlayer = currentPlayer.prev;
                    }
                    skipped = true;
                }
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
                System.out.println(player.getName() + " won!");
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

    public static boolean isValidNumber(String s) {
        try {
            int count = Integer.parseInt(s);
            if (count > 8 || count < 1) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
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