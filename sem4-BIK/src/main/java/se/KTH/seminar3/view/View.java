package se.KTH.seminar3.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import se.KTH.seminar3.controller.Controller;
import se.KTH.seminar3.integration.AccountingHandler;
import se.KTH.seminar3.integration.ConnectionToDatabaseFailed;
import se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException;
import se.KTH.seminar3.model.ItemDTO;
import se.KTH.seminar3.model.Receipt;
import se.KTH.seminar3.model.SaleDTO;
import se.KTH.seminar3.integration.FileLogger;

/**
 * This is a placeholder for the real view. It contains a hard-coded execution
 * with calls to all system operations in the controller.
 */
public class View {

    private Controller contr;
    private final int AMOUNT = 250;
    private FileLogger fileLogger;

    /**
     * Creates a new instance, that uses the controller for all calls to other
     * layers.
     *
     * @param contr The controller to use for all calls to other layers.
     */
    public View(Controller contr) {
        this.contr = contr;
        this.fileLogger = new FileLogger();

    }

    /*
    * Performs a fake sale, by calling all system operations in the controller.
     */
    public void runFakeExecution() {
        try {
            contr.startSale();
            System.out.println("\nA new sale has been started");

            int[] barcodes = {555, 111, 999};
            int[] quantities = {10, 1, 1};
            double runningTotal = 0;
            double totalVAT = 0;

            List<Integer> validBarcodes = new ArrayList<>();
            List<Integer> validQuantities = new ArrayList<>();

            for (int i = 0; i < barcodes.length; i++) {
                System.out.println("\nItem's barcode scanning ");

                try {
                    ItemDTO itemDTO = contr.registerItem(barcodes[i], quantities[i]);
                    printScannedItem(itemDTO);

                    double vatAmount = itemDTO.getItemPrice() * itemDTO.getItemVAT() * quantities[i];
                    runningTotal += itemDTO.getItemPrice() * quantities[i];
                    totalVAT += vatAmount;
                    System.out.println("\nRunning Total (incl. VAT): " + runningTotal + " SEK");
                    System.out.println("Total VAT: " + totalVAT + " SEK");

                    validBarcodes.add(barcodes[i]);
                    validQuantities.add(quantities[i]);
                } catch (InvalidItemBarcodeException e) {
                    System.out.println(e.toString());
                    fileLogger.log(e.toString());
                } catch (ConnectionToDatabaseFailed e) {
                    System.out.println("Error: Item does not exist, " + e);
                    fileLogger.log("Error: Item does not exist, " + e);
                }
            }

            System.out.println("\nSale ends, Total cost displayed");
            SaleDTO finalizedSale = contr.endSale();

            contr.applyDiscount(runningTotal);
            double totalPriceAfterDiscount = contr.getTotalPriceAfterDiscount();
            String discountMessage = contr.applyDiscount(runningTotal);
            System.out.println(discountMessage);
            System.out.println("Total Price after Discount: " + totalPriceAfterDiscount + " SEK");

            System.out.println("\nCustomer gives " + AMOUNT + " SEK");

            System.out.println("\nSent sale info to external accounting system.");
            for (int i = 0; i < validBarcodes.size(); i++) {
                System.out.println("Told external inventory system to decrease inventory quantity of item " + validBarcodes.get(i) + " by " + validQuantities.get(i) + " units.");
            }
            double paymentAmount = AMOUNT;
            AccountingHandler accHandler = new AccountingHandler();
            Receipt receiptFromSale = contr.registerPayment(paymentAmount, accHandler);

            System.out.println("\n------------------------Receipt------------------------");
            printReceipt(receiptFromSale, finalizedSale, totalPriceAfterDiscount);
        } catch (ConnectionToDatabaseFailed e) {
            System.out.println("Error: " + e.getMessage());
            fileLogger.log("Error: " + e.getMessage());
        }
    }

    private void printScannedItem(ItemDTO itemDTO) {
        System.out.println("Item(s): " + itemDTO.getItemName() + "\n"
                + "Price: " + itemDTO.getItemPrice() + " SEK" + "\n"
                + "Tax[%]: " + (itemDTO.getItemVAT() * 100) + "%" + "\n"
                + "Quantity: " + itemDTO.getItemQuantity());
    }

    private void printFinalizedSale(double totalPriceAfterDiscount) {
        System.out.println("Items cost (incl.VAT): " + totalPriceAfterDiscount + " SEK" + "\n");
    }

    private void printReceipt(Receipt receiptFromSale, SaleDTO finalizedSale, double totalPriceAfterDiscount) {
        printSaleDate(receiptFromSale);
        printFinalizedSale(totalPriceAfterDiscount);
        for (ItemDTO item : finalizedSale.getItemList()) {
            System.out.printf(Locale.US, "%-20s %d x %.2f          %.2f SEK%n",
                    item.getItemName(),
                    item.getItemQuantity(),
                    item.getItemPrice(),
                    item.getItemQuantity() * item.getItemPrice());
        }

        System.out.printf(Locale.US, "\n%-38s %.2f SEK%n", "Payment: ", (double) AMOUNT);
        System.out.printf(Locale.US, "%-38s %.2f SEK%n", "Change: ", (double) AMOUNT - totalPriceAfterDiscount);

        printStoreInformation(receiptFromSale);
    }

    private void printSaleDate(Receipt receipt) {
        System.out.println("\nTime of Sale: " + receipt.getSaleDate());
    }

    private void printStoreInformation(Receipt receiptFromSale) {
        System.out.println("\nGoodbye! \n" + receiptFromSale.getStoreName() + ", " + receiptFromSale.getStoreAddress());

    }
}
