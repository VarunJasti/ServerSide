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
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Varun Jasti
 */
public class User {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String user;
    private Listen listen;
    private ArrayList<Card> hand = new ArrayList<>();
    private double bet;
    private boolean fold;

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    public boolean isFold() {
        return fold;
    }
    
    public void sendHandToClient() {
        String s = "hand|";
        for (Card c : hand) {
            s += c.toStringInt() + "|";
        }
        out.println(s);
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public double getBet() {
        return bet;
    }

    public User(Socket socket, BufferedReader in, PrintWriter out, String user) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.user = user;
        listen = new Listen();
        listen.start();
    }

    class Listen extends Thread {

        private final AtomicBoolean running = new AtomicBoolean(false);

        public void stop1() {
            running.set(false);
        }

        @Override
        public void run() {
            running.set(true);
            try {
                while (running.get()) {
                    String input = in.readLine();
                    System.out.println("user " + input);
                    if (input.equals("quit")) {
                        disconnect();
                    } else if (input.equals("start")) {
                        if (ServerSide.getClients().size() <= 1) {
                            out.println("fewplayers");
                        } else {
                            startGame();
                        }
                    } else if (input.equals("startgame")) {
                        stop1();
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                if (e.getMessage().equals("Connection reset")) {
                    disconnect();
                }
            }
        }
    }

    private void startGame() {
        for (User client : ServerSide.getClients()) {
            client.getOut().println("start");
        }
        listen.stop1();
        ServerSide.setPlaying(true);
        System.out.println("Game Started");
        ServerSide.playPoker();
    }

    private void disconnect() {
        if (ServerSide.getClients().remove(this)) {
            System.out.println(user + " disconnected");
            for (User client : ServerSide.getClients()) {
                client.getOut().println("quit," + user);
            }
            listen.stop1();
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
