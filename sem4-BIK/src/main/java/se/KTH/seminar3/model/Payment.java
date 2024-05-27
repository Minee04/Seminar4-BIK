package se.KTH.seminar3.model;

import se.KTH.seminar3.integration.AccountingHandler;

/**
 * Payment represents a payment, which is made by a customer.
 */
public class Payment {

    private double paymentAmount;
    private AccountingHandler accHandler;

    /**
     * The payment method constructs an object with the specified payment amount
     * and accounting handler.
     *
     * @param paymentAmount The amount of payment made by the customer
     * @param accHandler The handler for updating the accounting system
     */
    public Payment(double paymentAmount, AccountingHandler accHandler) {
        this.paymentAmount = paymentAmount;
        this.accHandler = accHandler;
    }

    /**
     * Calculates the change to be returned to the customer and updates the
     * accounting handler.
     *
     * @param totalPayment The total payment required for the sale
     * @return <code>0</code> The change to be returned to the customer, or 0 if
     * the payment amount is less than the total payment
     */
    public double getChange(double totalPayment) {
        if (this.paymentAmount >= totalPayment) {
            return this.paymentAmount - totalPayment;
        }
        return 0;
    }

}
