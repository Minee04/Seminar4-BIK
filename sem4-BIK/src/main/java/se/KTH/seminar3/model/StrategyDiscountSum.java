package se.KTH.seminar3.model;

import java.util.ArrayList;

/**
 * This class implements the StrategyDiscount interface and provides a strategy
 * for calculating a discount based on a fixed amount.
 */
public class StrategyDiscountSum implements StrategyDiscount {

    private double discountAmount;

    /**
     * Constructs a new StrategyDiscountSum object with the specified discount
     * amount.
     *
     * @param discountAmount the fixed amount to be discounted from the total
     * price
     */
    public StrategyDiscountSum(double discountAmount) {
        this.discountAmount = discountAmount;
    }

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
        return totalPrice - discountAmount;
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

}
