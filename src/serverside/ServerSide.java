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
                System.out.println(name);
                clients.add(new User(client, name));
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
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static ArrayList<User> getClients() {
        return clients;
    }
    
}
