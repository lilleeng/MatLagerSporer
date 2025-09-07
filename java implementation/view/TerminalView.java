package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.DataBase;
import model.MenuState;
import model.FoodItem;

public class TerminalView implements View {

    private DataBase db;
    private MenuState menuState = MenuState.ITEM_OVERVIEW;
    private int currentOverviewPage = 0;
    private String currentOverviewSort = "lce";
    private FoodItem tempFoodItem;

    public TerminalView(DataBase db) {
        this.db = db;
    }

    @Override
    public void flushTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void printLoadingScreen() {
        System.out.println(
            "-".repeat(80) + "\n" +
            "Laster...\n" +
            "-".repeat(80)
        );
    }

    @Override
    public void openDefaultScreen() {
        itemOverview(0, "lce");
    }

    public MenuState getMenuState() {
        return this.menuState;
    }

    public void quit() {
        flushTerminal();
        System.out.print(
            "-".repeat(80) + "\n\n" +
            "Lagrer endringer og avslutter...\n\n" +
            "-".repeat(80)
        );
        db.saveChanges();
        flushTerminal();
        System.out.print("Avsluttet.");
    }

    private void registerGeneral() {
        flushTerminal();
        System.out.print(
            "-".repeat(80) + "\n" +
            "Registeringsmodus\n" +
            "-".repeat(80) + "\n\n" +
            "Navn: "
        );
    }

    public void registerName() {
        registerGeneral();
        System.out.print(
            "_" + " ".repeat(9) + "(empty = back)"
        );
        this.menuState = MenuState.REGISTRATION_MODE_INPUT_NAME;
    }

    public void registerExpDate(String name) {
        registerGeneral();
        System.out.print(
            name + "\n" +
            "Utløpsdato: _" + " ".repeat(9) + "(empty = back)"
        );
        this.menuState = MenuState.REGISTRATION_MODE_INPUT_EXPIRATION_DATE;
    }

    public void registerConfirmation(String name, LocalDate date) {
        registerGeneral();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.print(
            name + "\n" +
            "Utløpsdato: " + date.format(formatter) + "\n\n" +
            "Registrer? (y/n)"
        );
        this.menuState = MenuState.REGISTRATION_MODE_CONFIRMATION;
    }

    public void registerComplete(String name, LocalDate date) {
        flushTerminal();
        this.db.register(name, date);
        System.out.print(
            "-".repeat(80) + "\n\n" +
            "Registrering fullført.\n\n" +
             "-".repeat(80)
        );
        this.menuState = MenuState.REGISTRATION_MODE_COMPLETE;
    }

    public void nextDeleteOverviewPage() {
        deleteOverview(currentOverviewPage + 1, currentOverviewSort);
    }

    public void previousDeleteOverviewPage() {
        deleteOverview(currentOverviewPage - 1, currentOverviewSort);
    }

    public void deleteOverview() {
        deleteOverview(currentOverviewPage, currentOverviewSort);
    }

    public void deleteOverview(int page, String sort) {
        flushTerminal();
        ArrayList<FoodItem> foodItems = db.getItems(10*page, 10*(page+1), sort);
        System.out.print(
            "-".repeat(80) + "\n" +
            "Slettemodus\n" +
            String.format("%-5s", "Tast") +
            String.format("%-25s", "|Navn") +
            String.format("%-25s", "|Est. mengde innnhold") +
            String.format("%-25s", "|Utløpsdato") + "\n" +
            "-".repeat(80)
        );
        int j = 0;
        for (int i = 0; i < foodItems.size(); i++) {
            if (i == 9) {j = 10;}
            FoodItem fi = foodItems.get(i);
            double eca = fi.estimatedContentAmount();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            System.out.print(
                String.format("%-6s", i+1-j) +
                String.format("%-25s", fi.name()) +
                String.format("%-25s", Math.round(100*eca) + "%") +
                String.format("%-25s", fi.expirationDate().format(formatter)) + "\n"
            );
        }
        this.currentOverviewPage = page;
        this.currentOverviewSort = sort;
        this.menuState = MenuState.DELETE_MODE_OVERVIEW;
    }

    public void deleteConfirmation(int input) {
        flushTerminal();
        int index = (input != 0) ? 10*this.currentOverviewPage + input : 10*(this.currentOverviewPage+1); 
        FoodItem fi = db.getItem(index, this.currentOverviewSort);
        double eca = fi.estimatedContentAmount();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.print(
            "-".repeat(80) + "\n" +
            String.format("%-5s", "") +
            String.format("%-25s", "|Navn") +
            String.format("%-25s", "|Est. mengde innnhold") +
            String.format("%-25s", "|Utløpsdato") + "\n" +
            String.format("%-6s", "") +
            String.format("%-25s", fi.name()) +
            String.format("%-25s", Math.round(100*eca) + "%") +
            String.format("%-25s", fi.expirationDate().format(formatter)) + "\n" +
            "-".repeat(80) + "\n\n" +
            "Slett denne varen? (y/n)"
        );
        this.tempFoodItem = fi;
        this.menuState = MenuState.DELETE_MODE_CONFIRMATION;
    }

    public void deleteComplete() {
        flushTerminal();
        this.db.delete(tempFoodItem);
        System.out.print(
            "-".repeat(80) + "\n\n" +
            "Sletting fullført.\n\n" +
             "-".repeat(80)
        );
        this.menuState = MenuState.DELETE_MODE_COMPLETE;
    }

    public void nextOverviewPage() {
        itemOverview(currentOverviewPage+1, currentOverviewSort);
    }

    public void previousOverviewPage() {
        itemOverview(currentOverviewPage-1, currentOverviewSort);
    }

    public void itemOverview() {
        itemOverview(currentOverviewPage, currentOverviewSort);
    }

    public void itemOverview(int page) {
        itemOverview(page, currentOverviewSort);
    }

    public void itemOverview(String sort) {
        itemOverview(currentOverviewPage, sort);
    }

    public void itemOverview(int page, String sort) {
        flushTerminal();
        ArrayList<FoodItem> foodItems = db.getItems(10*page, 10*(page+1), sort);
        System.out.print(
            "-".repeat(80) + "\n" +
            "Vareoversikt\n" +
            String.format("%-5s", "Nr.") +
            String.format("%-25s", "|Navn") +
            String.format("%-25s", "|Est. mengde innnhold") +
            String.format("%-25s", "|Utløpsdato") + "\n" +
            "-".repeat(80)
        );
        for (int i = 0; i < foodItems.size(); i++) {

            FoodItem fi = foodItems.get(i);
            double eca = fi.estimatedContentAmount();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            System.out.print(
                String.format("%-6s", 10*page + i + 1) +
                String.format("%-25s", fi.name()) +
                String.format("%-25s", Math.round(100*eca) + "%") +
                String.format("%-25s", fi.expirationDate().format(formatter)) + "\n"
            );
        }
        this.currentOverviewPage = page;
        this.currentOverviewSort = sort;
        this.menuState = MenuState.ITEM_OVERVIEW;
    }

    
}
