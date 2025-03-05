package com.example.project;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static String determineWinner(Player p1, Player p2, String p1HandRanking, String p2HandRanking, ArrayList<Card> communityCards) {
        int p1Rank = Utility.getHandRanking(p1HandRanking);
        int p2Rank = Utility.getHandRanking(p2HandRanking);

        if (p1Rank > p2Rank) {
            return "Player 1 wins!";
        } else if (p2Rank > p1Rank) {
            return "Player 2 wins!";
        } else {
            return tiebreaker(p1, p2);
        }
    }

    // for ties, it compares individual card values in descending order
    private static String tiebreaker(Player p1, Player p2) {
        ArrayList<Card> p1Cards = p1.getAllCards();
        ArrayList<Card> p2Cards = p2.getAllCards();

        // sort both hands from highest to lowest
        sortCardsDescending(p1Cards);
        sortCardsDescending(p2Cards);

        // compare each card in sorted order
        for (int i = 0; i < Math.min(p1Cards.size(), p2Cards.size()); i++) {
            int p1CardValue = Utility.getRankValue(p1Cards.get(i).getRank());
            int p2CardValue = Utility.getRankValue(p2Cards.get(i).getRank());

            if (p1CardValue > p2CardValue) {return "Player 1 wins!";}
            if (p2CardValue > p1CardValue) {return "Player 2 wins!";}
        }

        return "Tie!";
    }

    // sorts cards in descending order using insertion sort
    private static void sortCardsDescending(ArrayList<Card> cards) {
        for (int i = 1; i < cards.size(); i++) {
            Card swapped = cards.get(i);
            int j = i - 1;
            while (j >= 0 && Utility.getRankValue(swapped.getRank()) > Utility.getRankValue(cards.get(j).getRank())) {
                cards.set(j + 1, cards.get(j));
                j--;
            }
            cards.set(j + 1, swapped);
        }
    }

    public static void play() {
        Scanner input = new Scanner(System.in);
        System.out.println("-------- chu's gambling hut --------");
        System.out.println(" ----- press any key to start ----- ");
        input.nextLine();
    
        boolean playAgain = true;
        while (playAgain) {
            Player playerOne = new Player();
            Player playerTwo = new Player();
            ArrayList<Card> communityCards = new ArrayList<>();
            Deck deck = new Deck();
    
            playerOne.addCard(deck.drawCard());
            playerOne.addCard(deck.drawCard());
            playerTwo.addCard(deck.drawCard());
            playerTwo.addCard(deck.drawCard());
    
            System.out.print("player 1 hand : " + playerOne.getHand());    
            System.out.println();
            System.out.println("-------------------------------------");
    
            System.out.println("press any key to view community cards");
            input.nextLine();
            for (int i = 0; i < 4; i++) {
                if (i == 0) {
                    deck.drawCard();
                } else {
                    communityCards.add(deck.drawCard()); // draw three
                }
            }
            System.out.println("Community Cards: " + communityCards);
            System.out.println("-------------------------------------");
            System.out.println("player 2 hand : " + playerTwo.getHand());    
    
            System.out.println("who wins??");
            input.nextLine();
            String winner = determineWinner(playerOne, playerTwo, playerOne.playHand(communityCards), playerTwo.playHand(communityCards), communityCards);
    
            System.out.println(winner + "\n");
    
            System.out.println("Play again? (yes/no)");
            String response = input.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                playAgain = false;
            }
        }
    }
}