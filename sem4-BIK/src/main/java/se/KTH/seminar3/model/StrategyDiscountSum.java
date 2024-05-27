package se.KTH.seminar3.model;

import java.util.ArrayList;

/**
 * This class implements the StrategyDiscount interface and provides a strategy
 * for calculating a discount based on a fixed amount.
 */
public class StrategyDiscountSum implements StrategyDiscount {

    private final double sum = 20;

    /**
     * Constructs a new StrategyDiscountSum object
     *
     */
    public StrategyDiscountSum() {}

    /**
     * Calculates the discounted price by subtracting the discount amount from
     * the total price.
     *
     * @param totalPrice the total price before discount
     * @param items the list of items (not used in this strategy)
     * @return the discounted price
     */
    @Override
    public double calculateDiscount(double totalPrice, ArrayList<ItemDTO> items) {
        return totalPrice - sum;
    }

    /**
     *Determines when the discount is calculated in this classes format
     *
     * @param totalPrice
     * @return the sales total price
     */
    @Override
    public boolean isApplicable(double totalPrice) {
        return totalPrice <= 200;
    }
    
    /**
     * A message for the discount implementation
     * 
     * @return the string message
     */
        @Override
    public String getDiscountMessage() {
        return "Applying a sum discount of " + sum + " SEK to the sale";
    }

}
