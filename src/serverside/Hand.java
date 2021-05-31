package serverside;

import java.util.ArrayList;
import java.util.Collections;

public class Hand implements Comparable {

    private ArrayList<Card> cards = new ArrayList<>();

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public int compareTo(Object o) {
//        if (o instanceof Hand) {
//            Hand h = (Hand) o;
//            
//        } else {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isFlush() {
        Collections.sort(cards);
        return cards.get(0).getSuit() == cards.get(cards.size() - 1).getSuit();
    }

    public boolean isStraight() {
        ArrayList<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(new Card(c.getCMarking(),0));
        }
        Collections.sort(temp);
        int n = temp.get(temp.size() - 1).getCMarking();
        for (int i = temp.size() - 2; i >= 0; i--) {
            if (!(temp.get(i).getCMarking() == n + 1)) {
                if (temp.get(i).getCMarking() == 1 && temp.get(i + 1).getCMarking() == 13) {
                    break;
                }
                return false;
            }
            n++;
        }
        return true;
    }
    
    public boolean isStraightFlush() {
        return isFlush() && isStraight();
    }
    
    public boolean isRoyalFlush() {
        return isStraightFlush() && cards.get(0).getCMarking() == 1;
    }

}
