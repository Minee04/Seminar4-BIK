package se.KTH.seminar3.model;

import java.util.ArrayList;

/**
 * This class implements the StrategyDiscount interface and provides a strategy
 * for calculating a discount based on a percentage of the total price.
 */
public class StrategyDiscountPercent implements StrategyDiscount {

    private final double percent = 10;

    /**
     * Constructs a new StrategyDiscountPercent object
     *
     */
    public StrategyDiscountPercent() {}

    /**
     * Calculates the discounted price by subtracting a percentage of the total
     * price.
     *
     * @param totalPrice the total price before discount
     * @param items the list of items (not used in this strategy)
     * @return the discounted price
     */
    @Override
    public double calculateDiscount(double totalPrice, ArrayList<ItemDTO> items) {
        double discount = totalPrice * percent * 0.01;
        return totalPrice - discount;
    }

    /**
     * Determines when the discount is calculated in this classes format
     *
     * @param totalPrice
     * @return the sales total price
     */
    @Override
    public boolean isApplicable(double totalPrice) {
        return totalPrice > 200;
    }
    
    /**
     * A string message for the implemented discount
     * 
     * @return the string message
     */
        @Override
    public String getDiscountMessage() {
        return "Applying " + percent + "% discount to sale";
    }
}
