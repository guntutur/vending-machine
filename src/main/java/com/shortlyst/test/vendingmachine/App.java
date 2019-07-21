package com.shortlyst.test.vendingmachine;

import com.shortlyst.test.vendingmachine.domain.ShelveBox;
import com.shortlyst.test.vendingmachine.service.CoinCalculatorService;
import com.shortlyst.test.vendingmachine.service.ShelveBoxService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by zer0, the Maverick Hunter
 * on 20/07/19.
 * Class: App.java
 */
public class App {

    private ShelveBoxService shelveBox;
    private Hinter hinter;
    public Integer insertedCoin = 0;

    private static final Collection<Integer> DEFAULT_COIN_STOCK = Arrays.asList(
            10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
            100, 100, 100, 100, 100, 100, 100, 100, 100, 100
    );

    public static void main(String[] args) {

        App app = new App();
        app.interactiveShell();
    }

    private void interactiveShell() {

        hinter = new App().new Hinter();
        shelveBox = new ShelveBoxService().init();

        System.out.println("Welcome to Vending Machine ver 1.0-SNAPSHOT");
        System.out.println("Type help to see available command");
        System.out.println("Type exit to terminate the program");
        System.out.println("Status : ");
        status();

        String theAbsoluteRealInput;
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("> ");
            theAbsoluteRealInput = scanner.nextLine();

            switch (theAbsoluteRealInput) {
                case "help" :
                    hinter.help();
                    break;
                case "exit" :
                case "quit" :
                    System.exit(1);
                    break;
                default:
                    if (hinter.validateCommand(theAbsoluteRealInput)) processCommand(theAbsoluteRealInput);
            }

            status();
        }
    }

    public void processCommand(String input) {
        String[] fullCommand = input.split(" ");
        switch (fullCommand[0]) {
            case "1":
                insertedCoin += Integer.valueOf(fullCommand[1]);
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
        }
    }

    private void status() {

        CoinCalculatorService calculatorService = new CoinCalculatorService(DEFAULT_COIN_STOCK);

        System.out.println("----------------------------------");
        StringBuilder inputAmount = new StringBuilder();
        inputAmount.append("[Input Amount]\n").append("\t"+insertedCoin+" JPY\n");
        System.out.println(inputAmount);

        StringBuilder change = new StringBuilder("[Change]\n");
        change.append("\t100 JPY ").append(calculatorService.check100limit()+"\n");
        change.append("\t10 JPY ").append(calculatorService.check10limit()+"\n");
        System.out.println(change);

        System.out.println("[Return Gate]\n\tEmpty\n");

        System.out.println("[Items for sale]");

        for (int i = 0; i < shelveBox.getAvailableGoods().size(); i++) {
            ShelveBox box = shelveBox.getShelveBoxFromIndex(i);
            hinter.setOutput(box.getStatus(insertedCoin), "\t" + (i + 1) + ". " + box.getGoods().getName() + " " + box.getGoods().getPrice() + " JPY");
        }

        System.out.print("\n");

        System.out.println("[Outlet]\n\tEmpty");
        System.out.println("----------------------------------");
    }

    class Hinter {
        static final String ANSI_RESET = "\u001B[0m";
        static final String ANSI_RED = "\u001B[31m";
        static final String ANSI_GREEN = "\u001B[32m";
        private final String[] RECOGNIZED_COMMAND = {"1", "2", "3", "4", "5"};

        void setOutput(Integer status, String output) {
            switch (status) {
                case 1:
                    System.out.println(ANSI_RED + output + " Out of stock" + ANSI_RESET);
                    break;
                case 2:
                    System.out.println(ANSI_GREEN + output + " Available for purchase" + ANSI_RESET);
                    break;
                default:
                    System.out.println(output);
                    break;
            }
        }

        void help() {
            String help = "Vending Machine, available command : \n";

            help += "\n";
            help += "Command (1)\n" +
                    "\tCommand name     : Insert coins\n" +
                    "\tCommand number   : 1\n" +
                    "\tArgument           : int, coin types (any of 10, 50, 100, 500)\n" +
                    "For example: “1 500” (Insert 500 JPY coin)\n" +
                    "\n" +
                    "Command (2)\n" +
                    "\tCommand name     : Choose item to purchase\n" +
                    "\tCommand number   : 2\n" +
                    "\tArgument           : int, item types (any of item numbers)\n" +
                    "For example: “2 1” (1: Choose Canned coffee)\n" +
                    "\n" +
                    "Command (3)\n" +
                    "\tCommand name     : Get items\n" +
                    "\tCommand number   : 3\n" +
                    "\tArgument           : None\n" +
                    "For example: “3” (Get items)\n" +
                    "\n" +
                    "Command (4)\n" +
                    "\tCommand name     : Return coins\n" +
                    "\tCommand number   : 4\n" +
                    "\tArgument           : None\n" +
                    "For example: “4” (Pull Return lever)\n" +
                    "\n" +
                    "Command (5)\n" +
                    "\tCommand name     : Get returned coins\n" +
                    "\tCommand number   : 5\n" +
                    "\tArgument           : None\n" +
                    "For example: “5” (Get returned coins)";

            System.out.println(help);
        }

        boolean validateCommand(String input) {
            boolean valid = true;

            String ERROR_HINT = "Command {cause}, type help for available command with usage";
            String MISSING_ARGUMENT = "missing one or more argument";
            String NOT_ACCEPT_ARG = "command does not accept any argument";
            String NOT_RECOGNIZED = "not recognized";

            String[] fullCommand = input.split(" ");

            switch (fullCommand[0]) {
                case "1" :
                case "2" :
                case "3" :
                    if (fullCommand.length == 1) {
                        setOutput(1, ERROR_HINT.replace("{cause}", MISSING_ARGUMENT));
                        valid = false;
                    }
                    break;
                case "4" :
                case "5" :
                    if (fullCommand.length > 1) {
                        setOutput(1, ERROR_HINT.replace("{cause}", NOT_ACCEPT_ARG));
                        valid = false;
                    }
                    break;
            }

            if (Arrays.stream(RECOGNIZED_COMMAND).noneMatch(fullCommand[0]::equals)) {
                setOutput(1, ERROR_HINT.replace("{cause}", NOT_RECOGNIZED));
                valid = false;
            }

            return valid;
        }
    }
}
