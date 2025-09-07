package model;
import java.time.LocalDate;

public record FoodItem(
    String name, 
    LocalDate expirationDate, 
    double estimatedContentAmount,
    LocalDate dateOfPurchase
    ) {}

    /*
     * Possible additions:
     * Vare-ID (alle forekomst av 500g jarlsberg har samme vare-id)
     * ID (unik for alle forekomst)
     */
