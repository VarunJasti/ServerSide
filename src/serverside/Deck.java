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
public class Deck {
    private final static int nCardsInDeck = 52;
    private ArrayList<Card> deckOfCards= new ArrayList<Card>();
    
    public Deck(){
        makeDeck();
    }
//    public ArrayList deal(int nCards){
//        
//    }
    
    public void makeDeck(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deckOfCards.add(new Card(i,j));
            }
        }
    }
    
    
    
        
            
}
