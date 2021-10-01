package main.backend.games.roulette;

import main.backend.Utils;
import main.backend.games.Game;
import main.backend.player.Player;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * GENERAL ALGORITHM ~ Start up game default settings. Pick a winning slot
 *  number. Then, go to each user and ask for name, betting amount, and betting
 *  number. Then, check to see if their bet matches our pre-established winning
 *  number. If yes, then we append that boolean value to their profile object,
 *  which we will later use to determine payouts. Then, after every has chosen
 *  their betting amount // slot number, we fake spin the wheel --> payout
 *  according to whether their profile object says that won = true.
 *
 *  profile(String name, int betAmount, int slotNum, boolean won);
 *
 */
public class Roulette extends Game {
    private final int[][] rouletteSlots;
    private final int[] Red = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    private int winningSlotNum;

    public Roulette(Scanner scanner, Player player) {
        super(scanner, player);

        rouletteSlots = new int[12][3];
        //Writes the number to each Slot
        int number = 1;
        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 3; col++) {
                rouletteSlots[row][col] = number;
                number++;
            }
        }

    }

    /**
     * Keeps track of the bets made by all users.
     */
    private static class Bet {
        private int betAmount;
        private String betType;
        private boolean Won;

        /**
         * Constructor for making bets. Sets betAmount, betType, and
         */
        public Bet(int betAmount, String betType, boolean won) {
            this.betAmount = betAmount;
            this.betType = betType;
            this.Won = won;
        }

        /**
         * Getters
         * @return returns their respective values.
         */
        private int getBetAmount() {
            return this.betAmount;
        }

        private boolean checkWinStatus() {
            return this.Won;
        }

        private String getBetType(){
            return this.betType;
        }
    }


    /**
     * Shows the playing area where users can make bets.
     */
    private void showBoard() {
        int[] startingNum = {3, 2, 1};
        final int rows = 11;

        System.out.println("Roulette Board ~");
        for (int num : startingNum) {
            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

            //Prints out the first number first
            int position = 0;

            if(num == 2){
                System.out.print("| " + num + " |");
            }else {
                System.out.print("| " + num + "*|");
            }

            //Prints out the remaining numbers in the row.
            while (position < rows) {
                boolean isRed = false;
                num += 3;
                for (int red : Red) {
                    if (num == red) {
                        isRed = true;
                        break;
                    }
                }
                if (!isRed) {
                    System.out.print(" " + num + " |");
                } else {
                    // * = red
                    System.out.print(" " + num + "*|");
                }
                position++;
            }
            System.out.println();
        }
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    /**
     * Takes in all possible betTypes from user
     *
     * @return returns true if user won.
     */
    private boolean takeBets(String betType) {

        //Keeps track of user variables.
        boolean won = false;

        int choseNum;

        switch (betType) {
            case "stra":
                System.out.println("Pick a number to bet your straight on.");
                choseNum = scanner.nextInt();

                won = straight(choseNum);
                break;

            case "six":
                System.out.println("Pick two rows for your six line.");

                //Prints out the diff. pairs that the user can choose from.
                int rows = 0;
                int numOn = 1;
                System.out.println("                            " +
                        "      :: Rows to Choose From ::");
                while (rows < 11) {
                    System.out.print(" [" + numOn + ", " + (numOn + 3) + "]");
                    numOn += 3;
                    rows++;
                }
                System.out.println();
                System.out.print("1st Row (left):");
                choseNum = scanner.nextInt();
                System.out.println("Your rows: " + choseNum + " & " +
                        (choseNum + 3));

                if(choseNum > 0 && choseNum < 37 && choseNum + 3 < 37){
                    //subtractNum is the number that we have to subtract in
                    // order to convert row number into a usable index.
                    int subtractNum = 1;
                    for(int index = 0; index < 12; index++){
                        if((choseNum - subtractNum) == index){
                            won = sixLine(choseNum - subtractNum);
                            break;
                        }
                        subtractNum += 2;
                    }
                    break;
                }else{
                    System.out.println("Invalid rows. Voiding win.");
                    won = false;
                }
                break;

            case "hol":
                System.out.println("Which one do you want: High or Low");
                String position = scanner.next().toLowerCase();

                //Checks to see if user entered a valid answer.
                boolean validAnswer = false;
                while (!validAnswer) {
                    if (position.equals("high") || position.equals("low")) {
                        validAnswer = true;
                        break;
                    } else {
                        System.out.println("Please insert a valid rowNum");
                        position = scanner.next();
                    }
                }

                won = highOrLow(position);
                break;

            case "split":
                System.out.println("Which numbers would you like to split?");
                System.out.print("1st Number: ");
                int num1 = scanner.nextInt();

                System.out.print("2nd Number: ");
                int num2 = scanner.nextInt();
                
                boolean together = false;
                if (num1 != num2 && (num1 > 0 && num1 < 37) && (num2 > 0 && num2 < 37)) {
                    //Check to see if next to each other.
                    for (int row = 0; row < 12; row++) {
                        for (int col = 0; col < 3; col++) {
                            if (rouletteSlots[row][col] == num1) {
                                if (row == 0) {
                                    if (col == 2) {
                                        if (rouletteSlots[row][col - 1] == num2 || rouletteSlots[row + 1][col] == num2) {
                                            together = true;
                                        }
                                    } else if (col == 0) {
                                        if (rouletteSlots[row][col + 1] == num2 || rouletteSlots[row + 1][col] == num2) {
                                            together = true;
                                        }
                                    } else {
                                        if (rouletteSlots[row][col + 1] == num2 || rouletteSlots[row][col - 1] == num2 || rouletteSlots[row + 1][col] == num2) {
                                            together = true;
                                        }
                                    }

                                } else if (row == 11) {
                                    if (col == 2) {
                                        if (rouletteSlots[row][col - 1] == num2 || rouletteSlots[row - 1][col] == num2) {
                                            together = true;
                                        }
                                    } else if (col == 0) {
                                        if (rouletteSlots[row][col + 1] == num2 || rouletteSlots[row - 1][col] == num2) {
                                            together = true;
                                        }
                                    } else {
                                        if (rouletteSlots[row][col + 1] == num2 || rouletteSlots[row][col - 1] == num2 || rouletteSlots[row - 1][col] == num2) {
                                            together = true;
                                        }
                                    }
                                } else {
                                    if (col == 2) {
                                        if (rouletteSlots[row][col - 1] == num2 || rouletteSlots[row + 1][col] == num2 || rouletteSlots[row - 1][col] == num2) {
                                            together = true;
                                        }
                                    } else if (col == 0) {
                                        if (rouletteSlots[row][col + 1] == num2 || rouletteSlots[row + 1][col] == num2 || rouletteSlots[row - 1][col] == num2) {
                                            together = true;
                                        }
                                    } else {
                                        if (rouletteSlots[row][col + 1] == num2 || rouletteSlots[row][col - 1] == num2 || rouletteSlots[row + 1][col] == num2 || rouletteSlots[row - 1][col] == num2) {
                                            together = true;
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                if (together) {
                    won = split(num1, num2);
                }else{
                    System.out.println("Numbers aren't together. Continuing " +
                            "without number placement.");
                    System.out.println();
                }
                break;

            case "rob":
                System.out.println("Which one do you want: red or black?");
                String choice = scanner.next().toLowerCase();

                won = redOrBlack(choice);
                break;

            case "doz":
                System.out.println("Which dozen do you want?");
                System.out.println(" [1] (1-12)");
                System.out.println(" [2] (13-24)");
                System.out.println(" [3] (25-36)");

                int rowNum = scanner.nextInt();
                if (rowNum == 1) {
                    won = dozen(1);
                } else if (rowNum == 2) {
                    won = dozen(13);
                } else {
                    won = dozen(25);
                }
                break;

            case "stre":
                System.out.println("What street would you like to bet on? ");
                System.out.print("~ Options:");
                for(int i = 1; i < 35; i+=3){
                    System.out.print(" [" + i + "]");
                }
                System.out.println();
                rowNum = scanner.nextInt();

                boolean numValid = false;
                for (int i = 1; i < 35; i += 3) {
                    //If rowNum = valid row
                    if (i == rowNum) {
                        numValid = true;
                        //subtractNum is the number that we have to subtract in
                        // order to convert row number into a usable index.
                        int subtractNum = 1;
                        for(int index = 0; index < 12; index++){
                            if((rowNum - subtractNum) == index){
                                won = street(rowNum - subtractNum);
                                break;
                            }
                            subtractNum += 2;
                        }
                    }
                }
                if(!numValid){
                    System.out.println("You entered an invalid row. " +
                            "Continuing without row placement.");
                }
                break;

            case "ooe":
                System.out.println("Which one do you want: Odd or Even?");
                choice = scanner.next().toLowerCase();

                won = oddOrEven(choice);
                break;

            case "col":
                System.out.println("Which column do you want?");
                System.out.println("[1] (3-36)");
                System.out.println("[2] (2-35)");
                System.out.println("[3] (1-34)");
                rowNum = scanner.nextInt();

                won = column(rowNum);
                break;

            default:
                System.out.println(Utils.invalidChoice(betType));
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Restarting Bet");
                    TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    System.out.println("Unable to restart profile.");
                }
                startGame();
                break;
        }
        //Returns the status of won. If user won = true or vice versa.
        return won;
    }










    //*START* OF PLAY METHODS FOR CHECKING IF BET = Win.

    /**
     * Method for checking if straight bet gave user a win. Checks if
     *  winningSlotNum matches user picked slotNum.
     *
     * @return returns true if user won.
     */
    private boolean straight(int slotNum) {
        return winningSlotNum == slotNum;
    }

    /**
     * Method for checking if sixLine bet gave user a win. The input will always
     * be the left-most number of the pair. Therefore, to find second column
     * you will do: rowNum + 1 to find the row on the right of the board.
     *
     * @param rowNum Left-Most pair number.
     * @return returns true if user won.
     */
    private boolean sixLine(int rowNum) {
        for(int col = 0; col < 3; col++) {
            if(rouletteSlots[rowNum][col] == winningSlotNum ||
                    rouletteSlots[rowNum + 1][col] == winningSlotNum) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for checking if high or low bet gave user a win. Checks if
     * winningSlotNum is 19 or above (high). Else, winningSlotNum is low.
     *
     * @param position Represents either high or low.
     * @return returns true if won.
     */
    private boolean highOrLow(String position) {
        //If winningSlotNum is high, and guessed high
        if(winningSlotNum >= 19){
            if(position.equals("high")){
                return true;
            }
            return false;
        }else{
            //If winningSlotNum is low, and guessed low
            if(position.equals("low")){
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     * Checks to see if at least 1 of the slot numbers that the user picked =
     * winning slot number.
     *
     * @param slotNum1 gives you the one of the slot numbers
     * @param slotNum2 tells you the position of the second slot number in the
     *                 split. A move of +/- 3 = horizontal movement. A move of
     *                 +/- 1 = vertical movement.
     * @return return true if won.
     */
    private boolean split(int slotNum1, int slotNum2) {
        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 3; col++) {
                if(rouletteSlots[row][col] == slotNum1 ||
                        rouletteSlots[row][col] == slotNum2){
                    if(rouletteSlots[row][col] == winningSlotNum){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method for checking if red or black bet gave user a win. If user picked
     * red, then slotNum will default to 1. Else, it will default to 2. Then,
     * it will check if winning number is in Red Array.
     *
     * @param color If user picked red, slotNum = 1. Else, slotNum = 2.
     * @return return true if won.
     */
    private boolean redOrBlack(String color) {

        for(int isRed: Red){
            if(isRed == winningSlotNum){
                if(color.equals("red")){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method for checking if dozen bet gave user a win. Checks if
     * winningSlotNum is is either between 1-12 or 13-24 or 25-36.
     *
     * @param slotNum The first number of the dozen bunch. Tells you which dozen
     *                the user picked.
     * @return return true if user won.
     */
    private boolean dozen(int slotNum) {
        while(slotNum < 12){
            if(slotNum == winningSlotNum){
                return true;
            }
            slotNum++;
        }
        return false;
    }

    /**
     * Method for checking if street bet gave user a win. Iterates through
     * table and finds the row that matches slotNum. Checks to see if
     * winningSlotNum is located in that row.
     *
     * @param row Represents the row that the user picked.
     * @return returns true if user won.
     */
    private boolean street(int row){
        for(int col = 0; col < 3; col++){
            if(rouletteSlots[row][col] == winningSlotNum){
                return true;
            }
        }
        return false;
    }

    /**
     * Method for checking if odd or even bet gave user a win. Checks to see if
     * winningSlotNum is even or odd.
     *
     * @param numType If user picked odd, slotNum defaults to 1. Else, slotNum
     *                equals 2.
     * @return returns true if user won.
     */
    private boolean oddOrEven(String numType){
        if(winningSlotNum % 2 == 0){
            if(numType.equals("even")){
                return true;
            }
        }else{
            if(numType.equals("odd")){
                return true;
            }
        }
        return false;
    }

    /**
     * Method for checking if column bet gave user a win.
     *
     * @param column Will either be 0, 1, or 2. Represents column index. Not
     *                the slot number.
     * @return returns true if user won.
     */
    private boolean column(int column) {
        for(int row = 0; row < 3; row++){
            if(rouletteSlots[row][column] == winningSlotNum){
                return true;
            }
        }
        return false;
    }
    //*END* OF PLAY METHODS FOR CHECKING IF BET = Win.














    /**
     * Grabs a winning slot number.
     *
     * @return returns the winning slot number.
     */
    private void randSlotNum() {
        int randRow = (int) (Math.random() * 12);
        int randCol = (int) (Math.random() * 3);

        winningSlotNum = rouletteSlots[randRow][randCol];
    }

    /**
     * This function mimics the action of spinning the wheel. Does not pick out
     * a winning slot number.
     */
    private void spinRoulette() {
        //Suspense
        System.out.println();
        System.out.print("Spinning roulette");
        suspense();
    }

    /**
     * Does TimeUnit.SECONDS.sleep(). It appends the print statements "..." to
     *  the console that was printed before this method was called.
     */
    private void suspense(){
        try{
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            System.out.println();
        } catch (InterruptedException e) {
            System.out.println("Error occurred.\nUnable to continue.");
        }
    }

    /**
     * Shows all betting options to user if requested.
     */
    private void showBettingOptions() {
        System.out.println("                         :: Possible Bets ::");
        System.out.print("| [stra] straight      ");
        System.out.print("| [six] six line      |");
        System.out.print("  [hol] high or low   |");
        System.out.println();
        System.out.print("| [split] split        ");
        System.out.print("| [rob] red or black  |");
        System.out.print("  [doz] dozens        |");
        System.out.println();
        System.out.print("| [stre] street        ");
        System.out.print("| [ooe] odd or even   |");
        System.out.print("  [col] columns       |");
        System.out.println();
        System.out.println();
    }


    @Override
    protected void play() {
        System.out.println("What would you like to do?");
        System.out.println("[p] make a bet");
        System.out.println("[q] walk away");
        String choice = scanner.next();

        switch (choice) {
            case "p":
                //Keeps track of users inputs
                Bet[] bets = new Bet[1];

                //Generates a new random winning slot number.
                randSlotNum();

                //Player Betting Amount
                //TODO: Make sure input = int. (Loop)
                System.out.println("How much would you like to bet?");
                int betAmount = scanner.nextInt();
                if(player.getChipValue() < betAmount){
                    System.out.println("Insufficient chips.\n");
                    break;
                }

                //Show Table to make bets
                showBoard();

                //Player Betting Type, and on what number.
                //Bet Type
                showBettingOptions();
                System.out.println("What would you like to bet on? ");
                String betType = scanner.next();
                //TODO: Make sure choice = valid before entering.

                boolean won = takeBets(betType);

                //Makes a new betting user w/ given information.
                boolean sufficientFunds = player.loseChips(betAmount);
                if (sufficientFunds) {
                    Bet bet = new Bet(betAmount, betType, won);
                    bets[0] = bet;

                    //Spin roulette
                    spinRoulette();
                    System.out.println("- - - - - - - - - - - - -");
                    System.out.println("| Roulette landed on: " + winningSlotNum + " |");
                    System.out.println("- - - - - - - - - - - - -");

                    //if profile boolean won = true -> PayOut
                    // else, say something.
                    System.out.println();

                    if(bets[0].checkWinStatus()){
                        //suspense
                        System.out.print("Paying Out");
                        suspense();

                        //PayOut Rates
                        switch (bets[0].getBetType()){
                            case "stra":
                                player.gainChips(bets[0].getBetAmount() * 36);
                                System.out.println();
                                break;

                            case "six":
                                player.gainChips(bets[0].getBetAmount() * 6);
                                System.out.println();
                                break;

                            case "hol":
                                player.gainChips(bets[0].getBetAmount() * 2);
                                System.out.println();
                                break;

                            case "split":
                                player.gainChips(bets[0].getBetAmount() * 18);
                                System.out.println();
                                break;

                            case "rob":
                                player.gainChips(bets[0].getBetAmount() * 2);
                                System.out.println();
                                break;

                            case "doz":
                                player.gainChips(bets[0].getBetAmount() * 3);
                                System.out.println();
                                break;

                            case "stre":
                                player.gainChips(bets[0].getBetAmount() * 18);
                                System.out.println();
                                break;

                            case "ooe":
                                player.gainChips(bets[0].getBetAmount() * 2);
                                System.out.println();
                                break;

                            case "col":
                                player.gainChips(bets[0].getBetAmount() * 3);
                                System.out.println();
                                break;

                            default:
                                System.out.println("Unable to Payout.");
                                System.out.println();
                                break;
                        }
                    }else{
                        try{
                            System.out.println(" Sorry, you lost!");
                            System.out.println();
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            System.out.println("System timeout.\nQuiting Game");
                        }
                    }

                }else{
                    System.out.println("Insufficient funds.");
                    System.out.println("Skipping your turn.");
                    startGame();
                }
                break;

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
