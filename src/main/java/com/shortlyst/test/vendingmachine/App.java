package com.shortlyst.test.vendingmachine;

import com.shortlyst.test.vendingmachine.controller.ShelveBoxController;
import com.shortlyst.test.vendingmachine.domain.Goods;
import com.shortlyst.test.vendingmachine.domain.ShelveBox;
import com.shortlyst.test.vendingmachine.service.CoinCalculatorService;

import java.util.*;

/**
 * Created by zer0, the Maverick Hunter
 * on 20/07/19.
 * Class: App.java
 */
public class App {

    private static ShelveBoxController shelveBoxController;
    private static CoinCalculatorService calculatorService;
    private static Hinter hinter;

    private List<Integer> returnGate = new ArrayList<>();

    private static final Collection<Integer> DEFAULT_COIN_STOCK = Arrays.asList(
            10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
            100, 100, 100, 100, 100, 100, 100, 100, 100, 100
    );

    private static final Collection<Integer> ACCEPTED_DENOMINATION_COIN = Arrays.asList(10, 50, 100, 500);

    public static void main(String[] args) {

        App app = new App();
        shelveBoxController = new ShelveBoxController().init();
        calculatorService = new CoinCalculatorService(DEFAULT_COIN_STOCK);
        hinter = new App().new Hinter();
        app.interactiveShell();
    }

    private void interactiveShell() {

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
                    status();
            }
        }
    }

    void processCommand(String input) {
        String[] fullCommand = input.split(" ");
        switch (fullCommand[0]) {
            case "1":
                int amount = Integer.valueOf(fullCommand[1]);
                if (ACCEPTED_DENOMINATION_COIN.stream().noneMatch(s -> s.equals(amount))) {
                    hinter.setOutput(1, "Denomination is not acceptable, valid denomination are : " + String.join(", ", ACCEPTED_DENOMINATION_COIN.toString()));
                } else {
                    shelveBoxController.insertCoin(amount);
                }
                break;
            case "2":
                int selectedIndex = Integer.valueOf(fullCommand[1]) - 1;
                if (shelveBoxController.getShelveBoxFromIndex(selectedIndex).getQuantity() == 0) {
                    hinter.setOutput(1, "Cannot select item, out of stock");
                } else {

                    shelveBoxController.selectShelf(selectedIndex);
                    if (!shelveBoxController.canProceed()) {
                        shelveBoxController.removeFromContainer();
                        hinter.setOutput(1, "Coin insufficient, try insert more");
                    }
                }
                break;
            case "3":
                calculatorService = new CoinCalculatorService(
                        DEFAULT_COIN_STOCK,
                        shelveBoxController.getTotalHoldAmount()
                );
                returnGate = calculatorService.getChange();
                // empty insertedcoin in controller
                break;
            case "4":

                break;
            case "5":
                break;
        }
    }

    private void status() {

        System.out.println("----------------------------------");
        StringBuilder inputAmount = new StringBuilder();
        inputAmount.append("[Input Amount]\n").append("\t" +
                ""+shelveBoxController.getTotalHoldAmount()+"" +
                " JPY\n");
        System.out.println(inputAmount);

        StringBuilder change = new StringBuilder("[Change]\n");
        change.append("\t100 JPY ").append(calculatorService.check100limit()+"\n");
        change.append("\t10 JPY ").append(calculatorService.check10limit()+"\n");
        System.out.println(change);

        System.out.println("[Return Gate]");
        System.out.print(returnGate.size() > 0 ? "" : "\tEmpty\n");
        for (Integer pieceOfChange : returnGate) {
            hinter.setOutput(0, "\t" + pieceOfChange + " JPY");
        }
        System.out.print("\n");

        System.out.println("[Items for sale]");

        for (int i = 0; i < shelveBoxController.getAvailableGoods().size(); i++) {
            ShelveBox box = shelveBoxController.getShelveBoxFromIndex(i);
            hinter.setOutput(
                    box.getStatus(shelveBoxController.getTotalHoldAmount()),
                    "\t" + (i + 1) + ". " +
                            "" + box.getGoods().getName() + " " +
                            "" + box.getGoods().getPrice() + " JPY" + " " +
                            "" + box.getStatusText(shelveBoxController.getTotalHoldAmount()));
        }

        System.out.print("\n");

        System.out.println("[Outlet]");
        System.out.print(shelveBoxController.getSelectedGoods().size() > 0 ? "" : "\tEmpty\n");
        for (Goods selectedGoods : shelveBoxController.getSelectedGoods()) {
            hinter.setOutput(0, "\t" + selectedGoods.getName());
        }
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
                    System.out.println(ANSI_RED + output + ANSI_RESET);
                    break;
                case 2:
                    System.out.println(ANSI_GREEN + output + ANSI_RESET);
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
                    if (fullCommand.length == 1) {
                        setOutput(1, ERROR_HINT.replace("{cause}", MISSING_ARGUMENT));
                        valid = false;
                    }
                    break;
                case "3" :
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
