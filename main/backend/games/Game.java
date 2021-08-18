package main.backend.games;

import main.backend.Utils;
import main.backend.player.Player;

import java.util.Scanner;

/**
 * Abstract class designed to be inherited by the various
 * casino games. This class holds a reference to the input scanner
 * as well as to the current player. It has defines useful helper
 * functions showStatus() which shows the players current chip count
 * and startGame() which calls showStatus() and then calls the abstract
 * play() method. The play() method is defined by sub classes and must
 * begin their respective game.
 */
public abstract class Game {
    protected final Scanner scanner;
    protected final Player player;

    public Game(Scanner scanner,
                Player player) {
        this.scanner = scanner;
        this.player = player;
    }

    private void showStatus() {
        System.out.println(Utils.chipStatus(player));
    }

    public void startGame() {
        showStatus();
        play();
    }

    protected abstract void play();
}
