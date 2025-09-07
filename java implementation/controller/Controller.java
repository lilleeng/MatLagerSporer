package controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import view.TerminalView;

public class Controller {
    
    public boolean PROGRAM_IS_RUNNING = true;
    private TerminalView view;
    private String tempNameRegistration; 
    private LocalDate tempDateRegistration;
    private int tempNumberKey;

    public Controller(TerminalView view) {
        this.view = view;
    }

    public void takeCommand() {   //rename to takeCommand
        String input = userInput();
        String[] command = input.split(" ");
        evaluateInput(command);
    }

    private String userInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input.trim();
    }

    private void evaluateInput(String[] command) {
        /* 
            1. Check current screen state
            2. Check if command makes sense in context
            3. Draw screen
            4. Change screen state
            5. Return to input (unless quit)
        */
        switch (view.getMenuState()) {
            case NO_ITEMS:
                evaluateInput_NO_ITEMS(command);
                break;
            case ITEM_OVERVIEW:
                evaluateInput_ITEM_OVERVIEW(command);
                break;
            case REGISTRATION_MODE_INPUT_NAME:
                evaluateInput_REGISTRATION_MODE_INPUT_NAME(command);
                break;
            case REGISTRATION_MODE_INPUT_EXPIRATION_DATE:
                evaluateInput_REGISTRATION_MODE_INPUT_EXPIRATION_DATE(command);
                break;
            case REGISTRATION_MODE_CONFIRMATION:
                evaluateInput_REGISTRATION_MODE_CONFIRMATION(command);
                break;
            case REGISTRATION_MODE_COMPLETE:
                evaluateInput_REGISTRATION_MODE_COMPLETE(command);
                break;
            case DELETE_MODE_OVERVIEW:
                evaluateInput_DELETE_MODE(command);
                break;
            case DELETE_MODE_CONFIRMATION:
                evaluateInput_DELETE_MODE_CONFIRMATION(command);
                break;
            case DELETE_MODE_COMPLETE:
                evaluateInput_DELETE_MODE_COMPLETE(command);
                break;
            default:
                System.out.println("INVALID MENU STATE");
                break;
        }
    }

    private void evaluateInput_NO_ITEMS(String[] command) {
        String commandName = command[0];
        switch (commandName) {
            case "register":
                view.registerName();
                break;
            case "quit":
                this.PROGRAM_IS_RUNNING = false;
                view.quit();
                break;
            default:
                break;
        }
    }

    private void evaluateInput_ITEM_OVERVIEW(String[] command) {
        String commandName = command[0];
        switch (commandName) {
            case "":
                break;
            case "sortby":
                if (command.length != 2) {
                    break;
                }
                String argument1 = command[1];
                if (!Arrays.asList("lce", "expired", "abc", "rb").contains(argument1)) {
                    break;
                }
                view.itemOverview(argument1);
                break;
            case "register":
                view.registerName();
                break;
            case "delete":
                view.deleteOverview();
                break;
            case "next":
                view.nextOverviewPage();
                break;
            case "prev":
                view.previousOverviewPage();
                break;
            case "quit":
                this.PROGRAM_IS_RUNNING = false;
                view.quit();
                break;
            default:
                break;
        }
    }

    private void evaluateInput_REGISTRATION_MODE_INPUT_NAME(String[] command) {
        //TODO Make sure to not have ',' in input
        String input = command[0];
        if (!input.isEmpty()) {
            this.tempNameRegistration = input;
            view.registerExpDate(input);
        }
        else {
            view.itemOverview();
        }
    }

    private void evaluateInput_REGISTRATION_MODE_INPUT_EXPIRATION_DATE(String[] command) {
        String input = command[0];
        if (input.isEmpty()) {
            view.registerName();
        }
        else {
            String[] dayMonthYear = input.split("\\.");
            LocalDate expDate;

            try {
                expDate = LocalDate.of(
                    Integer.valueOf(dayMonthYear[2]),
                    Integer.valueOf(dayMonthYear[1]),
                    Integer.valueOf(dayMonthYear[0])
                );
                this.tempDateRegistration = expDate;
                view.registerConfirmation(this.tempNameRegistration, expDate);
            } 
            catch (Exception e) {
                view.registerExpDate(this.tempNameRegistration);
            }
        }
    }

    private void evaluateInput_REGISTRATION_MODE_CONFIRMATION(String[] command) {
        String input = command[0];
        if (input.equals("y")) {
            view.registerComplete(this.tempNameRegistration, this.tempDateRegistration);
        }
        else if (input.equals("n")) {
            view.registerExpDate(this.tempNameRegistration);
        }
        else {
            view.registerConfirmation(this.tempNameRegistration, this.tempDateRegistration);
        }
    }

    private void evaluateInput_REGISTRATION_MODE_COMPLETE(String[] command) {
        view.itemOverview(0);
    }

    private void evaluateInput_DELETE_MODE(String[] command) {
        String input = command[0];
        switch (input) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
                this.tempNumberKey = Integer.valueOf(input);
                view.deleteConfirmation(Integer.valueOf(input));
                break;
            case "next":
                view.nextDeleteOverviewPage();
                break;
            case "prev":
                view.previousDeleteOverviewPage();
                break;
            default:
                view.itemOverview();
                break;
        }
    }

    private void evaluateInput_DELETE_MODE_CONFIRMATION(String[] command) {
        String input = command[0];
        switch (input) {
            case "y":
                view.deleteComplete();
                break;
            case "n":
                view.deleteOverview();
                break;
            default:
                view.deleteConfirmation(this.tempNumberKey);
                break;
        }
    }

    private void evaluateInput_DELETE_MODE_COMPLETE(String[] command) {
        view.itemOverview(0);
    }

}
