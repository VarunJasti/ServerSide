/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Varun Jasti
 */
public class User {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String user;

    public User(Socket socket, BufferedReader in, PrintWriter out, String user) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.user = user;
//        try {
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = new PrintWriter(socket.getOutputStream(), true);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        Listen listen = new Listen();
        listen.start();
    }

    class Listen extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    String input = in.readLine();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                if (e.getMessage().equals("Connection reset")) {
                    disconnect();
                }
            }
        }
    }
    
    private void disconnect() {
        if (ServerSide.getClients().remove(this)){
            System.out.println(user + " disconnected");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public String getUser() {
        return user;
    }

}
