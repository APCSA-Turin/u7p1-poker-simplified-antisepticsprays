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
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player();
        Player player2 = new Player();
        ArrayList<Card> communityCards = new ArrayList<>();
        boolean gameRunning = true;

        System.out.println("poker at chu's house");

        // Main game loop
        while (gameRunning) {
            System.out.println("\n---------------- options ----------------");
            System.out.println("1. Start a New Game");
            System.out.println("2. View Player 1's Hand");
            System.out.println("3. View Player 2's Hand");
            System.out.println("4. View Community Cards");
            System.out.println("5. Determine Winner");
            System.out.println("6. Quit");
            System.out.println("Select an option: ");
            System.out.println("-----------------------------------------");
            int choice = scanner.nextInt();

            if (choice == 1) {
                // Start a new game by dealing hands and community cards
                startNewGame(player1, player2, communityCards);
            } else if (choice == 2) {
                System.out.println("Player 1's Hand: " + player1.getAllCards());
            } else if (choice == 3) {
                System.out.println("Player 2's Hand: " + player2.getAllCards());
            } else if (choice == 4) {
                System.out.println("Community Cards: " + communityCards);
            } else if (choice == 5) {
                // Determine the winner of the current round
                String p1HandRanking = player1.playHand(communityCards);
                String p2HandRanking = player2.playHand(communityCards);

                // Determine the winner of the current round
                String result = determineWinner(player1, player2, p1HandRanking, p2HandRanking, communityCards);
                System.out.println("Result: " + result);
            } else if (choice == 6) {
                System.out.println("Thank you for playing!");
                gameRunning = false; // Exit the loop and end the game
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    // Starts a new game by dealing hands and community cards
    private static void startNewGame(Player player1, Player player2, ArrayList<Card> communityCards) {
        // Clear the hands of the players and reset the community cards
        player1.clearHand();
        player2.clearHand();
        communityCards.clear();

        // Deal 2 cards to each player
        dealCards(player1);
        dealCards(player2);

        // Add 3 community cards
        for (int i = 0; i < 3; i++) {
            String randomSuit = Utility.getSuits()[(int) (Math.random() * 4)];
            String randomRank = Utility.getRanks()[(int) (Math.random() * 13)];
            Card card = new Card(randomSuit, randomRank);
            communityCards.add(card);
        }

        //for testing
        System.out.println("\nNew game started!");
        System.out.println("Player 1's Hand: " + player1.getAllCards());
        System.out.println("Player 2's Hand: " + player2.getAllCards());
        System.out.println("Community Cards: " + communityCards);
    }

    // dealCards() method - assuming it deals 2 cards to each player
    private static void dealCards(Player player) {
        // Add two random cards to the player's hand
        for (int i = 0; i < 2; i++) {
            String randomSuit = Utility.getSuits()[(int) (Math.random() * 4)];
            String randomRank = Utility.getRanks()[(int) (Math.random() * 13)];
            Card card = new Card(randomSuit, randomRank);
            player.addCard(card);  // This will update both hand and allCards
        }
    }
}