/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class Hand {
    private ArrayList<Card> handOfCards= new ArrayList<Card>();
    public Hand(){
        
    }
    
    public void addCard(Card card){
        handOfCards.add(card);
    }
}
