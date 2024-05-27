package se.KTH.seminar3.controller;

import se.KTH.seminar3.view.TotalRevenueFileOutput;
import se.KTH.seminar3.integration.*;
import se.KTH.seminar3.model.*;
import se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException;
import se.KTH.seminar3.view.TotalRevenueView;

/**
 * This is the applications only controller. All calls to the model pass through
 * this class.
 */
public class Controller {

    private Sale sale;
    private StoreDTO storeInformation;
    private Receipt receipt;
    private InventoryHandler invHandler;
    private SaleLog saleLog;
    private FileLogger fileLogger;

    /**
     * The constructor initializes store information, inventory handler, sale
     * log, and receipt. * Logs an error if database connection fails during
     * receipt creation.
     */
    public Controller() {
        this.storeInformation = new StoreDTO("BIK AB", "BIKgatan 3");
        this.invHandler = InventoryHandler.getInstance();
        this.saleLog = new SaleLog();

        try {
            this.receipt = new Receipt(storeInformation);
        } catch (ConnectionToDatabaseFailed e) {
            fileLogger.log("Error: " + e);
        }
    }

    /**
     * Starts a new sale. This method is the initialization of a sale.
     *
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If
     * connection fails
     */
    public void startSale() throws ConnectionToDatabaseFailed {
        try {
            this.sale = new Sale(storeInformation, invHandler);
        } catch (ConnectionToDatabaseFailed e) {
            this.fileLogger.log("Error starting sale: " + e.getMessage());
        }
    }

    /**
     * Controller creates StoreDTO object with the information on the stores
     * name and location.
     *
     * @param storeAddress The stores address
     * @param storeName The stores name
     * @return The storeDTO object
     */
    public StoreDTO createStoreDTO(String storeAddress, String storeName) {
        StoreDTO storeDTO = new StoreDTO(storeAddress, storeName);
        return storeDTO;
    }

    /**
     *
     * Method to register items via the items bar code, to the sale.
     *
     * @param barCode The items barCode
     * @param itemQuantity The quantity of the item
     * @return Object of the registered item
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed
     * @throws
     * se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException
     */
    public ItemDTO registerItem(int barCode, int itemQuantity) throws ConnectionToDatabaseFailed, InvalidItemBarcodeException {
        return sale.addItemToSale(barCode, itemQuantity, invHandler);
    }

    /**
     * The current sale ends, and then returns the saleInformation
     *
     * @return <code>null</code> The object holding the saleInformation. If
     * there is no sale
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If
     * Connection fails
     */
    public SaleDTO endSale() throws ConnectionToDatabaseFailed {
        if (sale != null) {
            SaleDTO currentSale = sale.updateProducts();
            return currentSale;
        } else {
            return null;
        }

    }

    /**
     * Applies a discount to the sale. The system will calculate the discount
     *
     * @param runningTotal
     * @return the discount type which has been applied to the sale
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If
     * Connection fails
     */
public String applyDiscount(double runningTotal) throws ConnectionToDatabaseFailed {
    StrategyDiscount discount;
    String discountMessage;

    StrategyDiscountPercent discountPercent = new StrategyDiscountPercent();
    StrategyDiscountSum discountSum = new StrategyDiscountSum();

    if (discountPercent.isApplicable(runningTotal)) {
        discount = discountPercent;
    } else {
        discount = discountSum;
    }

    sale.setStrategyDiscount(discount);
    sale.getDiscount();
    discountMessage = discount.getDiscountMessage();

    return discountMessage;
}


    public double getTotalPriceAfterDiscount() {
        return sale.getTotalPriceAfterDiscount();
    }

    /**
     * This method registers a payment for the sale
     *
     * @param paymentAmount The amount paid by the customer.
     * @param accHandler Updates the stores accounting from the sale and
     * payment.
     * @return The Receipt object saved from the sale.
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed
     */
    public Receipt registerPayment(double paymentAmount, AccountingHandler accHandler) throws ConnectionToDatabaseFailed {
        Payment payment = new Payment(paymentAmount, accHandler);

        TotalRevenueView totalRevenueView = new TotalRevenueView();
        TotalRevenueFileOutput totalRevenueFileOutput = new TotalRevenueFileOutput();

        sale.addRevenueObserver(totalRevenueView);
        sale.addRevenueObserver(totalRevenueFileOutput);

        sale.getDiscount();
        Receipt receiptFromSale = Receipt.generateReceipt(payment, sale, saleLog, invHandler);

        return receiptFromSale;
    }

}
