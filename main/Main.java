package main;

import main.frontend.ChooseGame;

import java.util.Scanner;

/**
 * This class simply acts as the entry point to the program.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ChooseGame chooseGame = new ChooseGame(scanner);
        chooseGame.chooseGame();
    }
}
