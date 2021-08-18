package main.backend.games.blackjack;

import main.backend.Utils;
import main.backend.games.Game;
import main.backend.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BlackJack extends Game {
    private String[] HouseHand;
    private String[] UserHand;
    private int Multiplier;
    private Map<String, Integer> Deck;
    private String[] possibleCards = {"ace", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "jack", "queen", "king"};

    public BlackJack(Scanner scanner, Player player) {
        super(scanner, player);

        //New Usable Deck
        Deck = new HashMap<>();
        Deck.put("ace", 4);
        Deck.put("2", 4);
        Deck.put("3", 4);
        Deck.put("4", 4);
        Deck.put("5", 4);
        Deck.put("6", 4);
        Deck.put("7", 4);
        Deck.put("8", 4);
        Deck.put("9", 4);
        Deck.put("10", 4);
        Deck.put("jack", 4);
        Deck.put("queen", 4);
        Deck.put("king", 4);

        //Initializes Hands
        HouseHand = new String[12];
        UserHand = new String[12];
    }

    

    /**
     * Activates when someone wants to "hit" or grab another card from the
     * deck. It's responsible for subtracting that card from the usable deck.
     * and adding its value to the hand that called it. Note, usable deck is
     * the HashMap. Hand is either the HouseHand or UserHand.
     */
    private void hit(String[] hand){

        //Grabs Rand Card From Deck
        int randIndex = (int) (Math.random() * (12));

        //Checks to see if Usable Deck has that card available. Else, picks
        // another random card.
        while(Deck.get(possibleCards[randIndex]) == 0){
            randIndex = (int) (Math.random() * (12));
        }


        //Removes 1 card from Usable Deck
        Deck.put(possibleCards[randIndex], Deck.get(possibleCards[randIndex]) - 1);

        //Appends card to the player's hand.
        for(int i = 0; i < hand.length; i++){
            if(hand[i] == null){
                hand[i] = possibleCards[randIndex];
                break;
            }
        }
    }

    /**
     * Formats & prints the hand of the user and of the house. House hand will
     *  be on top. Reveal = faced down Card. If true, then we reveal the card.
     *  Else, we keep it faced down.
     */
    private void showTable(boolean reveal){
        //Dirty Work
        int[] hValues = getValues(HouseHand);
        int[] uValues = getValues(UserHand);

        //Formats the Table according to present values
        System.out.println("-------------------------------------------------");
        System.out.print("House Hand: " + hValues[0]);

        //If House hand has two diff. values (appends to print line above)
        if(hValues[1] != 0){
            System.out.print(", " + hValues[1]);
        }

        //Prints out House Hand
        for(int i = 1; i < HouseHand.length; i++){
            if(reveal){
                System.out.println(" [" + HouseHand[i] + "]");
            }else{
                System.out.println(" [FaceDown]");
            }
            if(HouseHand[i] != null){
                System.out.println(" [" + HouseHand[i] + "]");
            }
        }

        //Space Formatting
        for(int i = 0; i < 3; i++){
            System.out.println();
        }

        for(String card:UserHand) {
            System.out.println("Your Hand: " + uValues[0]);
        }

        //If User hand has two diff. values (appends to print line above)
        if(uValues[1] != 0){
            System.out.println(", " + uValues[1]);
        }

        //Prints out User's Hand
        for(String card: UserHand){
            if(card != null){
                System.out.println(" [" + card + "]");
            }
        }

        System.out.println("-------------------------------------------------");
    }

    /**
     * Mimics
     */
    private void showTable2(){
        //Dirty Work
        int[] hValues = getValues(HouseHand);
        int[] uValues = getValues(UserHand);

        //House Value: 0 [card] [card]
        System.out.print("House Values: " + hValues[0] + ", " + hValues[1]);
        for(String card: HouseHand){
            for(String possCards: possibleCards){
                if(card != null){
                    if(card.equals(possCards)){
                        System.out.print(" [" + card + "]");
                    }
                }
            }
        }

        System.out.println();

        //Your Value: 0 [card] [card]
        System.out.print("Your Values: " + uValues[0] + ", " + uValues[1]);
        for(String card: UserHand){
            for(String possCards: possibleCards){
                if(card != null){
                    if(card.equals(possCards)){
                        System.out.print(" [" + card + "]");
                    }
                }
            }
        }
        System.out.println("\n");
    }

    /**
     *
     */
    private void placeMultiple(){
        boolean validValue = false;
        while(!validValue){
            System.out.println("How much would you like to place? Must be a " +
                    "multiple of 10.");
            String input = scanner.next();
            try{
                int value = Integer.parseInt(input);

                if(value % 10 == 0 && value != 0){
                    Multiplier = value;
                    break;
                }else{
                    System.out.println("Number is not valid.");
                }
            }catch (NumberFormatException e){
                System.out.print("Number is not valid. ");
            }

        }
    }

    /**
     * Sets the starting table for a new blackjack game.
     */
    private void setTable(){
        //Makes user bet a valid amount.
        placeMultiple();

        //Suspense
        try{
            System.out.print("Handing out cards");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.println();
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Can't pass out cards.");
        }

        //Basic Table Setup, each player gets two cards.
        hit(HouseHand);
        hit(HouseHand);
        hit(UserHand);
        hit(UserHand);

        //Prints Game Area.
        //false;
        showTable2();
    }

    /**
     * Converts the HashMap String value into a usable int value.
     * @return int value of the string.
     */
    private int toNumber(String card){
        int cardValue = 0;

        switch (card) {
            case "ace":
                cardValue = 1;
                break;
            case "2":
                cardValue = 2;
                break;
            case "3":
                cardValue = 3;
                break;
            case "4":
                cardValue = 4;
                break;
            case "5":
                cardValue = 5;
                break;
            case "6":
                cardValue = 6;
                break;
            case "7":
                cardValue = 7;
                break;
            case "8":
                cardValue = 8;
                break;
            case "9":
                cardValue = 9;
                break;
            case "10":
            case "jack":
            case "queen":
            case "king":
                cardValue = 10;
                break;
        }
        return cardValue;
    }

    /**
     * Sorts through the player's hand and adds up the total value. Also checks
     *  to see how many Ace Cards the player has. Ace is represented as 1 in
     *  the hand. Face Cards are represented as 10. Second index is reserved
     *  for value with ace card.
     */
    private int[] getValues(String[] hand){
        int[] totalValue = new int[2];

        //Finds all cards in player's hand that have values
        for(String card: hand){
            if(card != null){
                int cardValue = toNumber(card);
                //Converts all non ace cards
                if(cardValue != 1){
                    totalValue[0] += cardValue;
                }else{
                    //Checks to see if Ace can be also be converted to 11.
                    if(totalValue[0] + 11 <= 21){
                        totalValue[1] = 11;
                    }
                    //Regardless, ace will be counted as 1
                    totalValue[0] += 1;
                }
            }
        }
        return totalValue;
    }

    /**
     * Checks if User's Total Hand value is under 21
     * @return true if user's hand is less than, but not equal to 21.
     */
    private boolean under21(){
        boolean under21 = true;
        int[] userHandValue = getValues(UserHand);

        //Checks if User got 21 or Bust
        if(userHandValue[0] >= 21){
            under21 = false;
        }
        if(userHandValue[1] >= 21){
            under21 = false;
        }

        return under21;
    }

    @Override
    protected void play(){
        System.out.println("What would you like to do?");
        System.out.println("[p] place bet");
        System.out.println("[q] walk away");

        String choice = scanner.next();
        switch(choice){
            case "p":

                setTable();

                //Check to see if bet != win
                // (includes the starting cards)
                boolean endGame = !under21();


                //Main betting loop for User.
                while(!endGame){
                    //Figure out the "looping code"
                    System.out.println("What would you like to do?");
                    System.out.println("[h] Hit");
                    System.out.println("[s] Stand");
                    System.out.println("[dd] Double Down");
                    System.out.println("[sur] Surrender");

                    choice = scanner.next().toLowerCase();
                    switch (choice){
                        case "h":
                            hit(UserHand);
                            //Check to see if user is over 21
                            endGame = !under21();
                            break;

                        case "s":
                            endGame = true;
                            break;

                        case "dd":
                            hit(UserHand);
                            Multiplier = Multiplier * 2;
                            System.out.println("~ New Betting Amount: " +
                                    Multiplier + " chips.");
                            System.out.println();
                            //Check to see if user is over 21
                            endGame = !under21();
                            break;

                        case "sur":
                            Multiplier = Multiplier / 2;
                            endGame = true;
                            break;

                        default:
                            System.out.println(Utils.invalidChoice(choice));
                    }
                    //False;
                    showTable2();
                }
                //Transition Text
                System.out.println();
                System.out.println();
                System.out.println("- - - - - - - - - - - - - - - ");
                System.out.println("| Now it's the House's Turn. | ");
                System.out.println("- - - - - - - - - - - - - - - ");
                System.out.println();
                System.out.println();
                try{
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Can't switch to house.");
                }

                //Main betting loop for House.
                int[] houseHandValue = getValues(HouseHand);
                boolean over17 = false;
                //true;
                showTable2();
                while(!over17){
                    if(houseHandValue[0] < 17 && houseHandValue[1] < 17){
                        hit(HouseHand);
                        houseHandValue = getValues(HouseHand);

                        //Suspense
                        try{
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println("Can't switch to house.");
                        }

                        System.out.println();

                        //true;
                        showTable2();

                    }else{
                        over17 = true;
                    }
                }

                //MAKE INTO CLASS
                int[] userHandValue = getValues(UserHand);
                //Check if anybody Won.
                if(!choice.equals("sur")){

                    //if user did not bust
                    if((userHandValue[0] <= 21 && userHandValue[0] > 0) || (userHandValue[1] <= 21 && userHandValue[1] > 0)){
                        //if house did not bust
                        if((houseHandValue[0] <= 21 && houseHandValue[0] > 0) || (houseHandValue[1] <= 21 && houseHandValue[1] > 0)){

                            int userHighestOutcome = -1;
                            int houseHighestOutcome = -1;

                            //Checks to see if any hand value is over 21.
                            if(userHandValue[0] <=21){
                                userHighestOutcome = userHandValue[0];
                            }else{
                                userHighestOutcome = userHandValue[1];
                            }
                            if(houseHandValue[0] <=21){
                                houseHighestOutcome = houseHandValue[0];
                            }else{
                                houseHighestOutcome = houseHandValue[1];
                            }

                            //Check which outcome is highest for each hand
                            if(userHandValue[1] > userHighestOutcome &&
                                    userHandValue[1] <= 21){
                                userHighestOutcome = userHandValue[1];
                            }
                            if(houseHandValue[1] > houseHighestOutcome &&
                                    houseHandValue[1] <= 21){
                                houseHighestOutcome = houseHandValue[1];
                            }


                            //Pay whoever wins
                            if(userHighestOutcome > houseHighestOutcome){
                                //You have higher numbers
                                System.out.println();
                                System.out.println("- - - - - - - - - - - - - - -");
                                System.out.println("| Congratulations, You Won! |");
                                System.out.println("- - - - - - - - - - - - - - -");
                                player.gainChips(Multiplier * 3);
                            }else if(userHighestOutcome == houseHighestOutcome){
                                //Tie
                                System.out.println("- - - - - - -");
                                System.out.println("| You Tied! |");
                                System.out.println("- - - - - - - ");
                                System.out.println("   Here's your original bet.");
                                player.gainChips(Multiplier);
                            }else{
                                //House has larger total value
                                System.out.println("- - - - - - - - - - ");
                                System.out.println("| Sorry, you lose. |");
                                System.out.println("- - - - - - - - - - ");
                            }

                        }else{
                            System.out.println("- - - - - - - - - - - - - - -");
                            System.out.println("| The House Busts!          |");
                            System.out.println("| Congratulations, you win! |");
                            System.out.println("- - - - - - - - - - - - - - -");
                            player.gainChips(Multiplier * 2);
                        }
                    }else {
                        //User Bust
                        System.out.println("- - - - - - - - - - -");
                        System.out.println("| Sorry, You bust!   |");
                        System.out.println("- - - - - - - - - - - ");
                        System.out.println("  Come again soon!");
                    }
                }else{
                    //Surrender Option
                    System.out.println("- - - - - - - - - - - - -");
                    System.out.println("| You surrendered.      |");
                    System.out.println("| Here's half your bet. |");
                    System.out.println("- - - - - - - - - - - - -");
                    System.out.println("   Come again soon!");
                    player.gainChips(Multiplier);
                }
                System.out.println();
                System.out.println();

            case "q":
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Finish Process Error.");
                }
                System.out.println("Thanks for playing!");
                break;

            default:
                System.out.println(Utils.invalidChoice(choice));
                startGame();
                break;
        }
    }
}
