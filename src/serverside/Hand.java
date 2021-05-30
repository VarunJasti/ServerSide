package serverside;

public class Hand implements Comparable{
    
    private Card[] cards = new Card[2];
    
    public Hand(Card[] cards){
        this.cards = cards;
    }

    public Card[] getCards() {
        return cards;
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
