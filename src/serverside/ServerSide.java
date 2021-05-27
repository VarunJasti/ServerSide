package serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSide {

    private final static int PORT = 4444;
    private static ArrayList<User> clients = new ArrayList<>();
    private static boolean playing = false;

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
                }else {
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
        double bet = 0;
        for (User u : clients) {
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
                    
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                //Something for user disconnecting
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
