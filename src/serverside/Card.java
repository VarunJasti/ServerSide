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
    private int suit;//0 = Clubs 1 = Diamonds 2 = Hearts 3 = Spades
    private int cMarking;
    
    public Card(int suit, int cMarking){
        this.suit = suit;
        this.cMarking = cMarking;
    }
    
    public String toString(){
        String s ="";
        if (cMarking ==1) {
            s +="Ace";
        }else if(cMarking ==2){
            s+="Two";
        }else if(cMarking ==3){
            s+="Three";
        }else if(cMarking ==4){
            s+="Four";
        }else if(cMarking ==5){
            s+="Five";
        }else if(cMarking ==6){
            s+="Six";
        }else if(cMarking ==7){
            s+="Seven";
        }else if(cMarking ==8){
            s+="Eight";
        }else if(cMarking ==9){
            s+="Nine";
        }else if(cMarking ==10){
            s+="Ten";
        }else if(cMarking ==11){
            s+="Jack";
        }else if(cMarking ==12){
            s+="Queen";
        }else if(cMarking ==13){
            s+="King";
        }
        
        s +=" of ";
        if (suit == 0) {
            s+="Clubs";
        }else if(suit ==1){
            s+= "Diamonds";
        }else if(suit ==2){
            s+= "Hearts";
        }else if(suit ==3){
            s+= "Spades";
        }
        return s;
    }
    
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
