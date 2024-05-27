package se.KTH.seminar3.model;

import java.util.ArrayList;

/**
 * This interface defines a strategy for calculating discounts. Classes
 * implementing this interface should provide their own implementation of the
 * calculateDiscount method.
 */
public interface StrategyDiscount {

    /**
     * Calculates the discounted price based on the total price and a list of
     * items. The exact discount calculation is determined by the classes
     * implementing this interface.
     *
     * @param totalPrice the total price before discount
     * @param items the list of items that the discount is applied to
     * @return the discounted price
     */
    double calculateDiscount(double totalPrice, ArrayList<ItemDTO> items);

    boolean isApplicable(double totalPrice);

    String getDiscountMessage();
}
