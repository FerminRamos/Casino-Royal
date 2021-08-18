package main.backend;

import main.backend.player.Player;

/**
 * This class defines helpful static utility functions that
 * help maintain consistency of messages to the user.
 * This class could also potentially hold non IO related
 * functions...
 */
public class Utils {
    public static String invalidChoice(String choice) {
        return "We're sorry but " + choice + " is not a " +
                "valid choice please try again";
    }

    public static String chipStatus(Player player) {
        return "You currently have " + player.getChipValue() + " chips.";
    }
}
