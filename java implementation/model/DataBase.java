package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DataBase {
    
    private boolean dataBaseHasUpdated = false;
    private ArrayList<FoodItem> dataBase;
    private ArrayList<FoodItem> lceSort;
    private ArrayList<FoodItem> expiredSort;
    private ArrayList<FoodItem> abcSort;
    private ArrayList<FoodItem> rbSort;

    public DataBase() {
        this.dataBase = new ArrayList<>();
    }

    public void loadDataBase() {
        loadDataBaseFromCSV();
    }

    private void loadDataBaseFromCSV() {
        // TODO 
        updateECAValues();
    }

    private void updateECAValues() {
        // TODO Auto-generated method stub
    }

    public void saveChanges() {
        //TODO
    }

    public void register(String name, LocalDate expDate) {
        double eca = 1.0;
        LocalDate boughtDate = LocalDate.now();
        this.dataBase.add(new FoodItem(
            name,
            expDate,
            eca,
            boughtDate
        ));
        this.dataBaseHasUpdated = true;
    }

    public void delete(FoodItem fi) {
        this.dataBase.remove(fi);
        this.dataBaseHasUpdated = true;
    }

    public FoodItem getItem(int index, String sort) {
        //TODO check for if db has changed
        FoodItem returnItem = null;
        switch (sort) {
            case "lce":
                returnItem = this.lceSort.get(index);
                break;
            case "expired":
                returnItem = this.expiredSort.get(index);
                break;
            case "abc":
                returnItem = this.abcSort.get(index);
                break;
            case "rb":
                returnItem = this.rbSort.get(index);
                break;
        }
        return returnItem;
    }

    public ArrayList<FoodItem> getItems(int indexStart, int IndexEnd, String sort) {
        //TODO check for if db has changed and as written below
        //test in Test.java:
            //if you have a clone of an array
            //the clone is sorted
            //an item is removed from the original
            //effect on original and clone?
        sortDB(sort);
        if (indexStart > this.dataBase.size()) {
            indexStart = 0; 
            IndexEnd = Math.max(10, this.dataBase.size());
        }
        else if (indexStart < 0) {
            indexStart = (this.dataBase.size() / 10) * 10;
            IndexEnd = this.dataBase.size();
        }
        else if (IndexEnd > this.dataBase.size()) {
            IndexEnd = this.dataBase.size();
        }
        ArrayList<FoodItem> returnList = new ArrayList<>(IndexEnd-indexStart);
        ArrayList<FoodItem> tempList = null;
        switch (sort) {
            case "lce":
                tempList = this.lceSort;
                break;
            case "expired":
                tempList = this.expiredSort;
                break;
            case "abc":
                tempList = this.abcSort;
                break;
            case "rb":
                tempList = this.rbSort;
                break;
        }
        for (;indexStart < IndexEnd; indexStart++) {
            returnList.add(tempList.get(indexStart));
        }
        return returnList;
    }

    private void sortDB(String sort) {
        switch (sort) {
            case "lce":
                this.lceSort = new ArrayList<>(this.dataBase);
                Collections.sort(this.lceSort, Comparator.comparing(FoodItem::estimatedContentAmount));
                break;
            case "expired":
                this.expiredSort = new ArrayList<>(this.dataBase);
                Collections.sort(this.expiredSort, Comparator.comparing(FoodItem::expirationDate));
                break;
            case "abc":
                this.abcSort = new ArrayList<>(this.dataBase);
                Collections.sort(this.abcSort, Comparator.comparing(FoodItem::name));
                break;
            case "rb":
                this.rbSort = new ArrayList<>(this.dataBase);
                Collections.sort(this.rbSort, Comparator.comparing(FoodItem::dateOfPurchase));
                break;
        }
    }
}
