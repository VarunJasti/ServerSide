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
public class Card {
    private int suit;//0 = Club 1 = Diamond 2 = Heart 3 = Spade
    private int cMarking;
    
    public Card(int suit, int cMarking){
        this.suit = suit;
        this.cMarking = cMarking;
    }
    
//    public String toString(){//Will do this later... Just lots of if statements
//        String s ="";
//
//    }
    public int getSuit()
    {
        return suit;
    }
    public void setSuit(int suit)
    {
        this.suit = suit;
    }
    public int getCMarking()
    {
        return suit;
    }
    public void setCMarking(int cMarking)
    {
        this.cMarking = cMarking;
    }
}
