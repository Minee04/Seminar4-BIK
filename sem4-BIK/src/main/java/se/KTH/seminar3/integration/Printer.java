package se.KTH.seminar3.integration;

import se.KTH.seminar3.model.Receipt;

/**
 * This is the printer, it will print the receipt after the final transaction.
 */
public class Printer {

    public Printer() {

    }

    /**
     * The receipt from the sale is printed once payment is complete.
     *
     * @param receiptFromSale The receipt which is printed
     */
    public void printReceipt(Receipt receiptFromSale) {
        System.out.println(receiptFromSale);
    }
}
