package serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ServerSide {

    private final static int PORT = 4444;
    private static ArrayList<User> clients = new ArrayList<>();
    private static boolean playing = false;
    private static double bet = 0;

    public static void main(String[] args) {
        ArrayList<Card> c = new ArrayList<>();
        c.add(new Card(1,0));
        c.add(new Card(11,0));
        c.add(new Card(13,0));
        c.add(new Card(12,0));
        c.add(new Card(10,0));
        Hand h = new Hand(c);
        System.out.println(h.isRoyalFlush());
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
        for (User u : clients) {
            for (int i = 0; i < clients.size(); i++) {
                clients.get(i).getOut().println("usercard." + u.getUser() + ".|" + u.getHand().get(0).toStringInt() + "|" + u.getHand().get(1).toStringInt() + "|");
            }
        }
        ArrayList<Hand> hands = new ArrayList<>();
        for (User u : clients) {
            combinations(community, u.getHand(), hands);
        }
        for (int i = 0; i < hands.size(); i++) {
            Collections.sort(hands.get(i).getCards());
            System.out.println(hands.get(i).getCards());
        }
    }

    public static void combinations(ArrayList<Card> community, ArrayList<Card> hand, ArrayList<Hand> results) {
        Card[] card = new Card[7];
        for (int i = 0; i < 5; i++) {
            card[i] = community.get(i);
        }
        for (int i = 5; i < 7; i++) {
            card[i] = hand.get(i - 5);
        }
        combinations(card, 5, 0, new Card[5], results);
    }

    private static void combinations(Card[] cards, int len, int start, Card[] result, ArrayList<Hand> results) {
        if (len == 0) {
            results.add(new Hand(new ArrayList<Card>(Arrays.asList(result[0], result[1], result[2], result[3], result[4]))));
        } else {
            for (int i = start; i <= cards.length - len; i++) {
                result[result.length - len] = cards[i];
                combinations(cards, len - 1, i + 1, result, results);
            }
        }
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
