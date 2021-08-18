package main.backend.games.bigsixwheel;

/**
 * This enum represents each of the different symbols
 * on a big six wheel. Each value of the enumerable
 * defines its payout rate. This is the rate that is
 * used to determine how much a player wins if they
 * guess correctly. For example if a player bets 100
 * chips that the wheel will land on FIVE. Then if it
 * does they win 5 times their money back or 500 chips.
 */
public enum Symbol {
    ONE("1", 1),
    TWO("2", 2),
    FIVE("5", 5),
    TEN("10", 10),
    TWENTY("20", 20),
    JOKER("j", 40);

    private final String stringValue;
    private final int payoutRate;

    Symbol(String stringValue,
           int payoutRate) {
        this.stringValue = stringValue;
        this.payoutRate = payoutRate;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getPayoutRate() {
        return payoutRate;
    }

    @Override
    public String toString() {
        if (this != JOKER) {
            return "[" + stringValue + "] $" + payoutRate;
        }
        else {
            return "[" + stringValue + "] Joker";
        }
    }
}
