package se.KTH.seminar3.model;

/**
 * This interface defines a contract for observing changes in total revenue.
 * Classes implementing this interface should provide their own implementation
 * of the updateTotalRevenue method.
 */
public interface RevenueObserver {

    /**
     * Updates the total revenue based on the total price of the sale and the
     * change given. The exact update behavior is determined by the classes
     * implementing this interface.
     *
     * @param totalPriceAfterDiscount
     */
    void updateTotalRevenue(double totalPriceAfterDiscount);
}
