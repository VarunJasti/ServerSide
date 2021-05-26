package serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    
    public static void setPlaying(boolean play) {
        playing = play;
    }

    public static ArrayList<User> getClients() {
        return clients;
    }

}
