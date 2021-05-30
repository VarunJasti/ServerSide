package serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerSide {

    private final static int PORT = 4444;
    private static ArrayList<User> clients = new ArrayList<>();
    private static boolean playing = false;
    private static double bet = 0;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server Running on " + PORT);
            while (true) {
                Socket client = server.accept();
                System.out.println("New Connection");
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                String name = in.readLine().split(",")[1];
                System.out.println(name + " connected");
                boolean b = false;
                for (User u : clients) {
                    if (u.getUser().equals(name)) {
                        b = true;
                    }
                }
                if (b) {
                    out.println("nametaken");
                } else if (clients.size() >= 10) {
                    out.println("lobbyfull");
                } else if (playing) {
                    out.println("playing");
                } else {
                    clients.add(new User(client, in, out, name));
                    out.println("connected");
                    String roster = "";
                    for (int i = 0; i < clients.size(); i++) {
                        if (i == 0) {
                            roster += clients.get(i).getUser();
                        } else {
                            roster += "," + clients.get(i).getUser();
                        }
                    }
                    out.println(roster);
                    for (int i = 0; i < clients.size() - 1; i++) {
                        clients.get(i).getOut().println("newuser," + name);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void playPoker() {
        Deck d = new Deck();
        for (User u : clients) {
            u.setHand(d.deal(2));
            u.sendHandToClient();
        }
        round();
        ArrayList<Card> community = d.deal(3);
        String cards = "community|";
        for (Card c : community) {
            cards += c.toStringInt() + "|";
        }
        for (User u : clients) {
            u.getOut().println(cards);
        }
        round();
        community.add(d.deal(1).get(0));
        for (User u : clients) {
            u.getOut().println("1community|" + community.get(3).toStringInt() + "|");
        }
        round();
        community.add(d.deal(1).get(0));
        for (User u : clients) {
            u.getOut().println("2community|" + community.get(4).toStringInt() + "|");
        }
        ArrayList<Hand> hands = combinations(community, clients.get(0).getHand());
        System.out.println("");
        for (Hand hand : hands) {
            System.out.println(Arrays.toString(hand.getCards()));
        }
    }

    public static ArrayList<Hand> combinations(ArrayList<Card> community, ArrayList<Card> hand) {
        community.addAll(hand);
        Card[] card = new Card[7];
        for (int i = 0; i < card.length; i++) {
            card[i] = community.get(i);
        }
        return combinations(card, 5, 0, new Card[5], new ArrayList<Hand>());
    }

    private static ArrayList<Hand> combinations(Card[] cards, int len, int start, Card[] result, ArrayList<Hand> results) {
        if (len == 0) {
            Card[] temp = {result[0], result[1], result[2], result[3], result[4]};
            results.add(new Hand(temp));
        } else {
            for (int i = start; i <= cards.length - len; i++) {
                result[result.length - len] = cards[i];
                combinations(cards, len - 1, i + 1, result, results);
            }
        }
        return results;
    }

    public static void round() {
        for (User u : clients) {
            if (!u.isFold()) {
                u.getOut().printf("bet,%.2f%n", bet);
                try {
                    String input = u.getIn().readLine();
                    System.out.println(input);
                    if (input.startsWith("bet")) {
                        bet = Double.parseDouble(input.split(",")[1]);
                        u.setBet(bet);
                        for (User c : clients) {
                            c.getOut().printf("userbet," + u.getUser() + ",%.2f%n", bet);
                        }
                    } else if (input.equals("fold")) {
                        u.setFold(true);
                        for (int i = 0; i < clients.size(); i++) {
                            clients.get(i).getOut().println("userfold," + u.getUser());
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    //Something for user disconnecting
                }
            }
        }
        for (User u : clients) {
            if (!u.isFold() && u.getBet() < bet) {
                u.getOut().printf("call,%.2f%n", bet);
                try {
                    String input = u.getIn().readLine();
                    System.out.println(input);
                    if (input.startsWith("bet")) {
                        bet = Double.parseDouble(input.split(",")[1]);
                        u.setBet(bet);
                        for (User c : clients) {
                            c.getOut().printf("userbet," + u.getUser() + ",%.2f%n", bet);
                        }
                    } else if (input.equals("fold")) {
                        u.setFold(true);
                        for (int i = 0; i < clients.size(); i++) {
                            clients.get(i).getOut().println("userfold," + u.getUser());
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    //Something for user disconnecting
                }
            }
        }
    }

    public static void setPlaying(boolean play) {
        playing = play;
    }

    public static ArrayList<User> getClients() {
        return clients;
    }

}
