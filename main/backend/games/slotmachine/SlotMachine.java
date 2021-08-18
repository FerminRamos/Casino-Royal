package main.backend.games.slotmachine;

import main.backend.Utils;
import main.backend.games.Game;
import main.backend.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class SlotMachine extends Game {
    //Make a list of Icons.
    private final String[] slotIcons;

    /**
     * Slot Machine Constructor. Initializes Symbols used for game.
     * @param scanner user input
     * @param player player object.
     */
    public SlotMachine(Scanner scanner, Player player){
        super(scanner, player);
        // diamond, heart, spade, horseshoe, star, liberty bell

        slotIcons = new String[6];
        slotIcons[0] = "diamond";
        slotIcons[1] = "heart";
        slotIcons[2] = "spade";
        slotIcons[3] = "horseshoe";
        slotIcons[4] = "star";
        slotIcons[5] = "liberty bell";
    }

    /**
     * Sorts through winningIcons and checks how many times each icon appeared
     * in the slotMachine. After this, we check to see if that combination is
     * part of a payout rate. If it is, we pay the user.
     */
    private void PayOut(String[] winningIcons){
        //list of icons (how many times they appear)
        Map<String, Integer> iconFreq = new HashMap<>();
        iconFreq.put("diamond", 0);
        iconFreq.put("heart", 0);
        iconFreq.put("spade", 0);
        iconFreq.put("horseshoe", 0);
        iconFreq.put("star", 0);
        iconFreq.put("liberty bell", 0);

        //Checks repetition of winning icons
        for(String winIcon: winningIcons){
            iconFreq.put(winIcon, iconFreq.get(winIcon) + 1);
        }

        //Pays
        if(iconFreq.get("horseshoe") == 2){
            if(iconFreq.get("star") == 1){
                player.gainChips(15);

            }else{
                player.gainChips(10);
            }
        }else if(iconFreq.get("spade") == 3){
            player.gainChips(25);
        }else if(iconFreq.get("diamond") == 3){
            player.gainChips(35);
        }else if(iconFreq.get("heart") == 3){
            player.gainChips(45);
        }else if(iconFreq.get("liberty bell") == 3){
            player.gainChips(55);
        }else{
            //if no combo matched
            System.out.println("You lose.");
            System.out.println("Better luck next time!\n");
        }
    }


    /**
     * The "meat"
     */
    @Override
    protected void play() {
        //This is going to be the "meat" of the program. That interacts with
        // the user and extends to other classes // methods.
        System.out.println("What would you like to do?");
        System.out.println("[p] pull slot lever");
        System.out.println("[q] walk away");

        String consent = scanner.next().toLowerCase();
        switch (consent){
            case "p":
                //Plays game
                if(player.loseChips(5)){

                    //Suspense
                    System.out.println("\nSpinning...\n");
                    try{
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Failed to spin the slot machine");
                    }

                    //MAYBE MAKE THIS INTO A METHOD?
                    //Selects Winning Icons per Slot.
                    String[] winningIcons = new String[3];
                    winningIcons[0] = slotIcons[(int) (Math.random() * 6)];
                    winningIcons[1] = slotIcons[(int) (Math.random() * 6)];
                    winningIcons[2] = slotIcons[(int) (Math.random() * 6)];

                    System.out.println("\nYour Slots: ");
                    System.out.print("[" + winningIcons[0] + "] ");
                    System.out.print("[" + winningIcons[1] + "] ");
                    System.out.print("[" + winningIcons[2] + "] \n\n");

                    //Pay
                    PayOut(winningIcons);
                }
                startGame();
                break;

            case "q":
                System.out.println("Thanks for playing!");
                break;

            default:
                System.out.println(Utils.invalidChoice(consent));
                startGame();
                break;
        }

    }


}
