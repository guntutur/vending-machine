package com.shortlyst.test.vendingmachine;

import java.util.Scanner;

/**
 * Created by zer0, the Maverick Hunter
 * on 20/07/19.
 * Class: App.java
 */
public class App {

    public static void main(String[] args) {

        App app = new App();
        app.interactiveShell();
    }

    private void interactiveShell() {
        System.out.println("Welcome to Vending Machine ver 1.0-SNAPSHOT");
        System.out.println("Type help to see available command");
        System.out.println("Type exit to terminate the program");
        System.out.println("Showing current Money and Goods In-Stock Status : ");

        String theInput;
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("> ");
            theInput = scanner.nextLine();

            switch (theInput) {
                case "help" :
//                    utility.showHelp();
                    break;
                case "exit" :
                case "quit" :
                    System.exit(1);
                    break;
                default:
                    System.out.println(theInput);
            }
        }
    }
}
