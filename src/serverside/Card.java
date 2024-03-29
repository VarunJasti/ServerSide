/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

/**
 *
 * @author andre
 */
public class Card implements Comparable {

    private int cMarking;
    private int suit;//0 = Clubs 1 = Diamonds 2 = Hearts 3 = Spades

    public Card(int cMarking, int suit) {
        this.cMarking = cMarking;
        this.suit = suit;
    }

    public String toStringInt() {
        return cMarking + "," + suit;
    }

    public String toString() {
        String s = "";
        if (cMarking == 1) {
            s += "Ace";
        } else if (cMarking == 2) {
            s += "Two";
        } else if (cMarking == 3) {
            s += "Three";
        } else if (cMarking == 4) {
            s += "Four";
        } else if (cMarking == 5) {
            s += "Five";
        } else if (cMarking == 6) {
            s += "Six";
        } else if (cMarking == 7) {
            s += "Seven";
        } else if (cMarking == 8) {
            s += "Eight";
        } else if (cMarking == 9) {
            s += "Nine";
        } else if (cMarking == 10) {
            s += "Ten";
        } else if (cMarking == 11) {
            s += "Jack";
        } else if (cMarking == 12) {
            s += "Queen";
        } else if (cMarking == 13) {
            s += "King";
        }
        s += " of ";
        if (suit == 0) {
            s += "Clubs";
        } else if (suit == 1) {
            s += "Diamonds";
        } else if (suit == 2) {
            s += "Hearts";
        } else if (suit == 3) {
            s += "Spades";
        }
        return s;
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getCMarking() {
        return cMarking;
    }

    public void setCMarking(int cMarking) {
        this.cMarking = cMarking;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Card) {
            Card c = (Card) o;
            if (this.getSuit() > c.getSuit()) {
                return -1;
            } else if (this.getSuit() < c.getSuit()) {
                return 1;
            } else {
                if (this.getCMarking() == 1 && c.getCMarking() != 1) {
                    return -1;
                } else if (this.getCMarking() != 1 && c.getCMarking() == 1) {
                    return 1;
                } else if (this.getCMarking() > c.getCMarking()) {
                    return -1;
                } else if (this.getCMarking() < c.getCMarking()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
