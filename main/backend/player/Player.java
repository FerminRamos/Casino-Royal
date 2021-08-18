package main.backend.player;

/**
 * The player class simply abstracts the idea of having
 * a chip account. It also prints out helpful messages
 * when making transactions on the chip account.
 */
public class Player {
    private int chipValue;

    public Player(int chipValue) {
        this.chipValue = chipValue;
    }

    public int getChipValue() {
        return chipValue;
    }

    public void gainChips(int chipValue) {
        if (chipValue > 0) {
            System.out.println("You won " + chipValue + " chips!");
        }
        this.chipValue += chipValue;
    }

    public boolean loseChips(int chipValue) {
        if (chipValue > this.chipValue) {
            System.out.println("You do not have enough chips for that.");
            return false;
        }
        else {
            System.out.println("You lost " + chipValue + " chips.");
            this.chipValue -= chipValue;
            return true;
        }
    }
}
