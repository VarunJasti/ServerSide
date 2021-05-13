/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverside;

import java.util.ArrayList;
import java.util.Random;

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
    public ArrayList<Hand>[] dealHands(int hands, int cards){
        Random rand = new Random();
        if (hands <1 || cards<1) {
            System.out.println("Not enough hands or cards");
            return null;
        }else if(hands*cards<nCardsInDeck){
            System.out.println("Not enough cards in deck");
            return null;
        }else{
            ArrayList<Hand>[] handsList = new ArrayList[hands];
            for (int i = 0; i < hands; i++) {
                Hand currentHand = new Hand();
                for (int j = 0; j < cards; j++) {
                    currentHand.addCard(deckOfCards.remove(rand.nextInt(nCardsInDeck-1-(cards*i+j))));
                    if (cards-1==j) {
                        handsList[hands].add(currentHand);
                    }
                }
            }
            return handsList;
        }
    }
    
    
    public void makeDeck(){
        deckOfCards.removeAll(deckOfCards);//Unsure if this works
        for (int i = 1; i <= 13; i++) {
            for (int j = 0; j < 4; j++) {
                deckOfCards.add(new Card(i,j));
            }
        }
    }
    
    
    
        
            
}
