package main.backend.games.bigsixwheel;

import main.backend.Utils;
import main.backend.games.Game;
import main.backend.player.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Big six wheel is a game of pure chance.
 * The game is played by spinning a wheel of
 * 54 segments. Each segment has a different
 * symbol on it. There are 23 $1 symbols,
 * 15 $2 symbols, 8 $5 symbols, 4 $10 symbols,
 * 2 $20 symbols, and 2 joker symbols.
 * Each symbol has its own payout rate defined
 * in the Symbol enum. To begin the game a player
 * must first place a bet. Then the wheel is spun
 * and if the player guessed the correct symbol then
 * they win their bet * the payout rate.
 */
public class BigSixWheel extends Game {
    private final List<Symbol> symbols;

    public BigSixWheel(Scanner scanner, Player player) {
        super(scanner, player);

        // Create the list of symbols used in the game
        symbols = new ArrayList<>();
        symbols.addAll(Collections.nCopies(23, Symbol.ONE));
        symbols.addAll(Collections.nCopies(15, Symbol.TWO));
        symbols.addAll(Collections.nCopies(8, Symbol.FIVE));
        symbols.addAll(Collections.nCopies(4, Symbol.TEN));
        symbols.addAll(Collections.nCopies(2, Symbol.TWENTY));
        symbols.addAll(Collections.nCopies(2, Symbol.JOKER));
        resetWheel();
    }

    private void resetWheel() {
        Collections.shuffle(symbols);
    }

    /**
     * This class keeps track of how much a player bet
     * and which symbol they bet on.
     */
    private static class Bet {
        private final int amount;
        private final Symbol on;

        public Bet(int amount, Symbol on) {
            this.amount = amount;
            this.on = on;
        }

        public int getAmount() {
            return amount;
        }

        public Symbol getOn() {
            return on;
        }
    }

    /**
     * This function mimics the action of spinning the wheel.
     * It does this by first
     */
    private Symbol spinWheel() {
        Symbol randSymbol = null;
        try {
            int randSpin =
                    ThreadLocalRandom.current()
                            .nextInt(0, symbols.size() - 1);

            System.out.println("Spinning...");
            TimeUnit.SECONDS.sleep(3);

            randSymbol = symbols.get(randSpin);

            System.out.println("Wheel stopped on " + randSymbol);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Failed to spin the wheel");
        }
        return randSymbol;
    }

    /**
     * This function receive input from the player to determine
     * how much they want to bet and on which symbol.
     * @return Bet they player wants to make
     */
    private Bet takeBet() {
        System.out.println("How much would you like to bet?");
        int betAmount = scanner.nextInt();

        if (player.loseChips(betAmount)) {

            System.out.println("Which symbol would you like to bet on?");
            for (Symbol symbol : Symbol.values()) {
                System.out.println(symbol);
            }

            String choice = scanner.next();

            Bet bet = null;
            for (Symbol s : Symbol.values()) {
                if (choice.equalsIgnoreCase(s.getStringValue())) {
                    bet = new Bet(betAmount, s);
                    break;
                }
            }
            if (bet == null) {
                System.out.println(Utils.invalidChoice(choice));
                return takeBet();
            } else {
                return bet;
            }
        }
        else {
            System.out.println("You do not have enough chips to make that" +
                    " bet. Please try again.");
            return takeBet();
        }
    }

    @Override
    public void play() {
        System.out.println("What would you like to do?");
        System.out.println("[p] bet on wheel");
        System.out.println("[q] walk away");

        String choice = scanner.next();
        switch (choice) {
            case "p":
                Bet bet = takeBet();
                Symbol winningSymbol = spinWheel();
                if (bet.getOn() == winningSymbol) {
                    System.out.println("You win!");

                    int payout =
                            bet.getAmount() +
                                    (bet.getAmount() * bet.getOn().getPayoutRate());
                    player.gainChips(payout);
                } else {
                    System.out.println("You lose.");
                    System.out.println("Better luck next time!");
                }
                startGame();
                break;
            case "q":
                System.out.println("Thanks for playing!");
                break;
            default:
                System.out.println(Utils.invalidChoice(choice));
                startGame();
                break;
        }
    }
}
