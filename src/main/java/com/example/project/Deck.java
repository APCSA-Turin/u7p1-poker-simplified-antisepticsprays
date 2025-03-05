package com.example.project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck{
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        // cards initilizated
        initializeDeck();
        // cards are shuffled randomly 
        shuffleDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public void initializeDeck(){ //hint.. use the utility class
        String[] suits = Utility.getSuits();
        String[] ranks = Utility.getRanks();
        //for each rank (being 2, 3, 4, 5 ... Q, K, etc.), it get iterated, it also makes 4 suits of that card for each iteration
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                // new card object made each time
                Card newCard = new Card(ranks[i], suits[j]);
                cards.add(newCard);
            }
        }
    }

    public void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        //https://www.geeksforgeeks.org/collections-shuffle-method-in-java-with-examples/
        Collections.shuffle(cards);
    }

    public Card drawCard(){
        // done according to the instructions
        if (isEmpty() == false) {
            Card drawnCard = cards.remove(0);
            return drawnCard;
        }else{
            return null;
        }
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
}