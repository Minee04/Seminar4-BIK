package se.KTH.seminar3.view;

import se.KTH.seminar3.model.RevenueObserver;

/**
 * Observes and displays the total revenue.
 */
public class TotalRevenueView implements RevenueObserver {

    private double totalRevenue = 0;

    /**
     * Updates and prints the total revenue.
     *
     * @param totalPriceAfterDiscount The total price after discount.
     */
    @Override
    public void updateTotalRevenue(double totalPriceAfterDiscount) {
        totalRevenue += totalPriceAfterDiscount;
        System.out.println("\nTotal revenue: " + totalRevenue);
    }  
}
