package serverside;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {

    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }
        int port = Integer.parseInt(args[0]);
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server Running on " + port);
            while (true) {
                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                String input = in.readLine();
                System.out.println(input);
                out.println(input);
            }
        } catch (Exception e) {}
    }
    
}
