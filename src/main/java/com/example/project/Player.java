package com.example.project;
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; // the current community cards + hand
    // handRanking initialized as "Nothing" if no other condition applies
    public String handRanking = "Nothing";
    String[] suits = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    // constructor
    public Player() {
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    // returns the player's hand
    public ArrayList<Card> getHand() { return hand; }

    // returns all available cards
    public ArrayList<Card> getAllCards() { return allCards; }

    // adds a card to the player's hand
    public void addCard(Card c) { 
        hand.add(c);
        allCards.add(c);
    }

    // clear the player's hand
    public void clearHand() { hand.clear(); }

    // determines the best possible hand with community cards
    public String playHand(ArrayList<Card> communityCards) {      
        allCards.clear();
        allCards.addAll(hand);
        allCards.addAll(communityCards);
        sortAllCards(); // sorts all cards from lowest to highest rank
    
        // checks for best possible hand, highest rank takes priority
        if (royalFlush()) { handRanking = "Royal Flush"; }
        else if (straightFlush()) { handRanking = "Straight Flush"; }
        else if (fourOfAKind()) { handRanking = "Four of a Kind"; }
        else if (fullHouse()) { handRanking = "Full House"; }
        else if (flush()) { handRanking = "Flush"; }
        else if (straight()) { handRanking = "Straight"; }
        else if (threeOfAKind()) { handRanking = "Three of a Kind"; }
        else if (twoPair()) { handRanking = "Two Pair"; }
        else if (aPair()) { handRanking = "A Pair"; }
        else if (highCard()) { handRanking = "High Card"; }
        else { handRanking = "Nothing"; }
        return handRanking;
    }

<<<<<<< HEAD
    // checks if player has a Royal Flush 
     public boolean royalFlush() {
        //checks using striaght metho and seeing if the biggest card is an Ace
        if (straight() && allCards.get(allCards.size() - 1).getRank().equals("A")) {
            return true;
        }
            return false;
    }     
=======
    public void sortAllCards(){} 
>>>>>>> upstream/main

    // checks if player has a Straight Flush
    public boolean straightFlush() {
        //checks using striaght and flush method
        return straight() && flush();
    }

    // checks if player has Four of a Kind (four cards of the same rank)
    public boolean fourOfAKind() {
        ArrayList<Integer> rankFreq = findRankingFrequency();
        //looks through the arrayList of frequencies of ranks and sees if any of them is 4
        for (Integer rank : rankFreq) {
            if (rank == 4) return true;
        }
        return false;
    }

    // checks if player has a Full House (three of a kind + a pair)
    public boolean fullHouse() {
        ArrayList<Integer> rankFreq = findRankingFrequency();
        boolean containsThreeOfAKind = false;
        boolean containsAPair = false;
        //looks through the arrayList of frequencies of ranks and sees if any of them is 3 (triple)
        for (Integer rank : rankFreq) {
            if (rank == 3) containsThreeOfAKind = true;
        }
        //looks through the arrayList of frequencies of ranks and sees if any of them is 2 (double)
        for (Integer rank : rankFreq) {
            if (rank == 2) containsAPair = true;
        }
        return containsThreeOfAKind && containsAPair;
    }

    // checks if player has a Flush (five or more cards of the same suit)
    public boolean flush() {
        //looks through the arrayList of frequencies of suits and sees if any of them is 5 or more meaning all cards are the same suit
        for (int count : findSuitFrequency()) {
            if (count >= 5) return true;
        }
        return false;
    }

    // checks if player has a Straight (five consecutive cards)
    public boolean straight() {
        int consecutive = 1; // tracks the number of consecutive cards found
        // iteration
        for (int i = 1; i < allCards.size(); i++) {
            // difference between the current and previous card's rank value
            int diff = Utility.getRankValue(allCards.get(i).getRank()) - Utility.getRankValue(allCards.get(i - 1).getRank());
            if (diff == 1) { // if the cards are consecutive
                consecutive++;
                if (consecutive == 5) return true; // straight found
            } else if (diff > 1) { // reset count if non-consecutive
                consecutive = 1;
            }
        }
        return false; // no straight found
    }

    // checks if player has Three of a Kind (three cards of the same rank)
    public boolean threeOfAKind() {
        ArrayList<Integer> rankFreq = findRankingFrequency();
        //looks through the arrayList of frequencies of ranks and sees if any of them is 3
        for (Integer rank : rankFreq) {
            if (rank == 3) return true;
        }
        return false;
    }

    // checks if player has Two Pair (two different pairs)
    public boolean twoPair() {
        ArrayList<Integer> rankFreq = findRankingFrequency();
        int pairs = 0;
        //looks through the arrayList of frequencies of ranks and sees if 2 of them is 2 
        for (int count : rankFreq) {
            if (count == 2) pairs++;
        }
        return pairs == 2;
    }

    // checks if player has A Pair (two cards of the same rank)
    public boolean aPair() {
        ArrayList<Integer> rankFreq = findRankingFrequency();
        //looks through the arrayList of frequencies of ranks and sees if any of them is 2
        for (Integer rank : rankFreq) {
            if (rank == 2) return true;
        }
        return false;
    }

    // checks if the player's highest card is in their hand
    public boolean highCard() {
        Card highest = allCards.get(0);
        for (Card card : allCards) {
            if (Utility.getRankValue(highest.getRank()) > Utility.getHandRanking(card.getRank())) {
                highest = card;
            }
        }
        return hand.contains(highest);
    }
    
    // sorts all cards in ascending order based on rank value
    public void sortAllCards() {
        for (int i = 1; i < allCards.size(); i++) {
            Card key = allCards.get(i);
            int j = i - 1;
    
            // insertion sort for sorting low-to-high
            while (j >= 0 && Utility.getRankValue(key.getRank()) < Utility.getRankValue(allCards.get(j).getRank())) {
                allCards.set(j + 1, allCards.get(j));
                j--;
            }
            allCards.set(j + 1, key);
        }
    }

    // counts the frequency of each rank in allCards
    public ArrayList<Integer> findRankingFrequency() {
        ArrayList<Integer> rankFreq = new ArrayList<>();
        for (String rank : ranks) {
            int count = 0;
            for (Card card : allCards) {
                if (card.getRank().equals(rank)) {
                    count++;
                }
            }
            rankFreq.add(count);
        }
        return rankFreq;
    }
    
    // counts the frequency of each suit in allCards
    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> suitsFreq = new ArrayList<>();
        for (String suit : suits) {
            int count = 0;
            for (Card card : allCards) {
                if (card.getSuit().equals(suit)) {
                    count++;
                }
            }
            suitsFreq.add(count);
        }
        return suitsFreq;
    }
   
    // returns a string representation of the player's hand
    @Override
    public String toString() {
        return hand.toString();
    }
}