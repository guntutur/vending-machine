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

    private ShelveBoxController shelveBoxController;
    private CoinCalculatorService calculatorService;
    private Hinter hinter = new Hinter();

    App() {
        this.shelveBoxController = new ShelveBoxController().init();
        this.calculatorService = new CoinCalculatorService(COIN_STOCK_CONTAINER);
    }

    App(ShelveBoxController shelveBoxController, CoinCalculatorService coinCalculatorService) {
        this.shelveBoxController = shelveBoxController;
        this.calculatorService = coinCalculatorService;
    }

    private List<Integer> returnGate = new ArrayList<>();

    private static final Collection<Integer> DEFAULT_COIN_STOCK = Arrays.asList(
            10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
            100, 100, 100, 100, 100, 100, 100, 100, 100, 100
    );

    private static List<Integer> COIN_STOCK_CONTAINER = new ArrayList<>(DEFAULT_COIN_STOCK);

    private static final Collection<Integer> ACCEPTED_DENOMINATION_COIN = Arrays.asList(10, 50, 100, 500);

    public static void main(String[] args) {

        App app = new App();
        app.interactiveShell();
    }

    private void interactiveShell() {

        hinter.clear();
        System.out.println("Welcome to Vending Machine ver 1.0-SNAPSHOT");
        System.out.println("Type help to see available command");
        System.out.println("Type exit to terminate the program");
        System.out.println("Status : ");
        System.out.println(status());

        String theAbsoluteRealInput;
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("[input]> ");
            theAbsoluteRealInput = scanner.nextLine();

            switch (theAbsoluteRealInput) {
                case "help" :
                    hinter.clear();
                    hinter.help();
                    break;
                case "exit" :
                    System.exit(1);
                    break;
                case "" :
                    // do nothing on enter key
                    break;
                default:
                    if (hinter.validateCommand(theAbsoluteRealInput)) {
                        hinter.clear();
                        System.out.println(processCommand(theAbsoluteRealInput));
                    }
            }
        }
    }

    public String processCommand(String input) {

        StringBuilder systemResponse = new StringBuilder();

        int availableGoodsLen = shelveBoxController.getAvailableGoods().size();
        String[] fullCommand = input.split(" ");
        switch (fullCommand[0]) {
            case "1":
                int amount = Integer.valueOf(fullCommand[1]);
                if (ACCEPTED_DENOMINATION_COIN.stream().noneMatch(s -> s.equals(amount))) {
                    systemResponse.append(hinter.setOutput(1, "Denomination is not acceptable, valid denomination are : " + String.join(", ", ACCEPTED_DENOMINATION_COIN.toString()))).append("\n");
                } else {

                    if ((calculatorService.check10limit() == 0) && (calculatorService.check100limit() == 0)) {
                        shelveBoxController.insertCoin(amount);
                    } else if ((calculatorService.check10limit() == 1) && (amount == 10)) {
                        shelveBoxController.insertCoin(amount);
                    } else if ((calculatorService.check100limit() == 1) && ((amount == 10) || (amount == 50) || (amount == 100))) {
                        shelveBoxController.insertCoin(amount);
                    } else {
                        systemResponse.append(hinter.setOutput(1, "Denomination is not acceptable, coin stock limit reached")).append("\n");
                    }
                }
                systemResponse.append(statusWithArgs(
                        shelveBoxController.getTotalHoldAmount(),
                        availableGoodsLen,
                        shelveBoxController.getSelectedGoods()
                ));
                break;
            case "2":
                int selectedIndex = Integer.valueOf(fullCommand[1]) - 1;
                if (shelveBoxController.getShelveBoxFromIndex(selectedIndex).getQuantity() == 0) {
                    systemResponse.append(hinter.setOutput(1, "Cannot select item, out of stock")).append("\n");
                } else {

                    shelveBoxController.selectShelf(selectedIndex);
                    if (!shelveBoxController.canProceed()) {
                        shelveBoxController.removeFromContainer();
                        systemResponse.append(hinter.setOutput(1, "Coin insufficient, try insert more")).append("\n");
                    } else {
                        shelveBoxController.releaseIfProceed(selectedIndex);
                    }
                }
                systemResponse.append(statusWithArgs(
                        shelveBoxController.getTotalHoldAmount(),
                        availableGoodsLen,
                        shelveBoxController.getSelectedGoods()
                ));
                break;
            case "3":
                calculatorService = new CoinCalculatorService(
                        COIN_STOCK_CONTAINER,
                        shelveBoxController.getTotalHoldAmount()
                );
                returnGate = calculatorService.getChange();
                systemResponse.append(hinter.setOutput(2, "Your change is being prepared (if any), you may now empty the Outlet")).append("\n");
                systemResponse.append(statusWithArgs(
                        shelveBoxController.getTotalHoldAmount(),
                        availableGoodsLen,
                        shelveBoxController.getSelectedGoods()
                ));
                break;
            case "4":

                if (shelveBoxController.getInsertedCoin().size() > 0) {
                    calculatorService.getRemainingCoins().addAll(shelveBoxController.getInsertedCoin());
                    COIN_STOCK_CONTAINER = calculatorService.getRemainingCoins();
                    systemResponse.append(hinter.setOutput(2, "Please collect your change in Return Gate")).append("\n");
                    calculatorService = new CoinCalculatorService(calculatorService.getRemainingCoins());

                    systemResponse.append(statusWithArgs(
                            0,
                            availableGoodsLen,
                            shelveBoxController.getSelectedGoods()
                    ));
                    reset();
                } else {
                    systemResponse.append(status());
                }
                break;
            case "5":
                reset();
                systemResponse.append(hinter.setOutput(2, "Thank you for using our service")).append("\n");
                systemResponse.append(status());
                break;
            case "6": // easter egg
                COIN_STOCK_CONTAINER.forEach(System.out::println);
        }

        return systemResponse.toString();
    }

    private void reset() {
        shelveBoxController.reset();
        returnGate = new ArrayList<>();
    }

    private String status() {

        StringBuilder response = new StringBuilder();

        response.append("----------------------------------").append("\n");
        String inputAmount = "[Input Amount]\n" + "\t" +
                "" + shelveBoxController.getTotalHoldAmount() + "" +
                " JPY\n";
        response.append(inputAmount);

        String change = "[Change]\n" + "\t100 JPY " + (calculatorService.check100limit() == 1 ? "No Change" : "Change") + "\n" +
                "\t10 JPY " + (calculatorService.check10limit() == 1 ? "No Change" : "Change") + "\n";
        response.append(change);

        response.append("[Return Gate]").append("\n");
        response.append(returnGate.size() > 0 ? "" : "\tEmpty\n");
        for (Integer pieceOfChange : returnGate) {
            response.append(hinter.setOutput(0, "\t" + pieceOfChange + " JPY")).append("\n");
        }

        response.append("[Items for sale]").append("\n");

        for (int i = 0; i < shelveBoxController.getAvailableGoods().size(); i++) {
            ShelveBox box = shelveBoxController.getShelveBoxFromIndex(i);
            response.append(hinter.setOutput(
                    box.getStatus(shelveBoxController.getTotalHoldAmount()),
                    "\t" + (i + 1) + ". " +
                            "" + box.getGoods().getName() + " " +
                            "" + box.getGoods().getPrice() + " JPY" + " " +
                            "" + box.getStatusText(shelveBoxController.getTotalHoldAmount()))).append("\n");
        }

        response.append("[Outlet]").append("\n");
        response.append(shelveBoxController.getSelectedGoods().size() > 0 ? "" : "\tEmpty\n");
        for (Goods selectedGoods : shelveBoxController.getSelectedGoods()) {
            response.append(hinter.setOutput(0, "\t" + selectedGoods.getName())).append("\n");
        }
        response.append("----------------------------------");

        return response.toString();
    }

    private String statusWithArgs(int totalHoldAmount, int availableGoodsSize, List<Goods> selectedGoods) {

        StringBuilder response = new StringBuilder();

        response.append("----------------------------------").append("\n");
        String inputAmount = "[Input Amount]\n" + "\t" +
                "" + totalHoldAmount + "" +
                " JPY\n";
        response.append(inputAmount);

        String change = "[Change]\n" + "\t100 JPY " + (calculatorService.check100limit() == 1 ? "No Change" : "Change") + "\n" +
                "\t10 JPY " + (calculatorService.check10limit() == 1 ? "No Change" : "Change") + "\n";
        response.append(change);

        response.append("[Return Gate]").append("\n");
        response.append(returnGate.size() > 0 ? "" : "\tEmpty\n");
        for (Integer pieceOfChange : returnGate) {
            response.append(hinter.setOutput(0, "\t" + pieceOfChange + " JPY")).append("\n");
        }

        response.append("[Items for sale]").append("\n");

        for (int i = 0; i < availableGoodsSize; i++) {
            ShelveBox box = shelveBoxController.getShelveBoxFromIndex(i);
            response.append(hinter.setOutput(
                    box.getStatus(totalHoldAmount),
                    "\t" + (i + 1) + ". " +
                            "" + box.getGoods().getName() + " " +
                            "" + box.getGoods().getPrice() + " JPY" + " " +
                            "" + box.getStatusText(totalHoldAmount))).append("\n");
        }

        response.append("[Outlet]").append("\n");
        response.append(selectedGoods.size() > 0 ? "" : "\tEmpty\n");
        for (Goods goods : selectedGoods) {
            response.append(hinter.setOutput(0, "\t" + goods.getName())).append("\n");
        }
        response.append("----------------------------------");

        return response.toString();
    }

    class Hinter {
        static final String ANSI_RESET = "\u001B[0m";
        static final String ANSI_RED = "\u001B[31m";
        static final String ANSI_GREEN = "\u001B[32m";
        private final String[] RECOGNIZED_COMMAND = {"1", "2", "3", "4", "5", "6"};

        String setOutput(Integer status, String output) {
            String parsedOutput;
            switch (status) {
                case 1:
                    parsedOutput = ANSI_RED + output + ANSI_RESET;
                    break;
                case 2:
                    parsedOutput = ANSI_GREEN + output + ANSI_RESET;
                    break;
                default:
                    parsedOutput = output;
                    break;
            }
            return parsedOutput;
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
                        System.out.println(setOutput(1, ERROR_HINT.replace("{cause}", MISSING_ARGUMENT)));
                        valid = false;
                    }
                    break;
                case "3" :
                case "4" :
                case "5" :
                case "6" :
                    if (fullCommand.length > 1) {
                        System.out.println(setOutput(1, ERROR_HINT.replace("{cause}", NOT_ACCEPT_ARG)));
                        valid = false;
                    }
                    break;
            }

            if (Arrays.stream(RECOGNIZED_COMMAND).noneMatch(fullCommand[0]::equals)) {
                System.out.println(setOutput(1, ERROR_HINT.replace("{cause}", NOT_RECOGNIZED)));
                valid = false;
            }

            return valid;
        }

        void clear() {
            System.out.print("\033\143");
        }
    }
}
