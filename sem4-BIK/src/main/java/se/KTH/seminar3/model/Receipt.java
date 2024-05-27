package se.KTH.seminar3.model;

import java.time.LocalDate;
import se.KTH.seminar3.integration.ConnectionToDatabaseFailed;
import se.KTH.seminar3.integration.InventoryHandler;
import se.KTH.seminar3.integration.SaleLog;

/**
 * The Receipt class represents a receipt, which is created from a SaleDTO
 * object.
 *
 */
public class Receipt {

    private String storeName;
    private String storeAddress;
    private SaleDTO SaleInformation;
    private LocalDate saleDate = LocalDate.now();

    /**
     * The stores information is initialized into the receipt.
     *
     * @param store the StoreDTO object holds store information.
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If the
     * connection to the database fails.
     */
    public Receipt(StoreDTO store) throws ConnectionToDatabaseFailed {
        this.storeName = store.getStoreName();
        this.storeAddress = store.getStoreAddress();

    }

    /**
     *
     * The method that generates a receipt when a sale is finalized. Sale log is
     * updated with the sale information.
     *
     * @param payment The payment transaction
     * @param finalizedSale the completed sale with information for the receipt
     * @param saleLog The sale log which is ready to be updated with the sale
     * information
     * @param invHandler Manages the item inventory
     * @return a generated receipt with the completed sale information
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If the
     * connection to the database fails.
     */
    public static Receipt generateReceipt(Payment payment, Sale finalizedSale, SaleLog saleLog, InventoryHandler invHandler) throws ConnectionToDatabaseFailed {
        SaleDTO currentSale = finalizedSale.SaleInformation(payment, invHandler);
        Receipt receiptFromSale = new Receipt(new StoreDTO(finalizedSale.getStoreName(), finalizedSale.getStoreAddress()));
        receiptFromSale.SaleInformation = currentSale;
        saleLog.updateSalelog(currentSale);
        return receiptFromSale;
    }

    // Getter methods
    /**
     * gets the stores name
     *
     * @return the stores name
     */
    public String getStoreName() {
        return this.storeName;
    }

    /**
     * gets the address of the store
     *
     * @return the stores address
     */
    public String getStoreAddress() {
        return this.storeAddress;
    }

    /**
     * Gets the object holding the sales information
     *
     * @return the object with the sale information
     */
    public SaleDTO getSaleInformation() {
        return this.SaleInformation;
    }

    /**
     * Gets the sales date
     *
     * @return the date of sale
     */
    public LocalDate getSaleDate() {
        return this.saleDate;
    }

}

