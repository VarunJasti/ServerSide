package serverside;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    
    final static int PORT = 4444;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server Running on " + PORT);
            while (true) {
                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                String input = in.readLine();
                out.println(input);
            }
        } catch (Exception e) {}
    }
    
}
