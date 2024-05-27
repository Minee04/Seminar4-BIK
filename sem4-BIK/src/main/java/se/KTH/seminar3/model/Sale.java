package se.KTH.seminar3.model;

import java.util.ArrayList;
import java.util.List;
import se.KTH.seminar3.integration.AccountingHandler;
import se.KTH.seminar3.integration.ConnectionToDatabaseFailed;
import se.KTH.seminar3.integration.InventoryHandler;
import se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException;

/**
 * the central Sale method in the class. Represents a point of sale between a
 * customer and a cashier.
 */
public class Sale {

    private Receipt receipt;
    private double totalPrice;
    private double totalPriceAfterDiscount;
    private double totalVAT;
    private ArrayList<ItemDTO> itemList = new ArrayList<ItemDTO>();
    private InventoryHandler invHandler;
    private StoreDTO storeInformation;
    private StrategyDiscount discount;
    private List<RevenueObserver> revenueObservers = new ArrayList<>();

    /**
     * Receipt is created at start of sale. System is ready to log the
     * saleInformation, and inventory information.
     *
     * @param storeInformation The object holding the stores information
     * @param invHandler The item management in the inventory
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If the
     * connection to the database fails
     */
    public Sale(StoreDTO storeInformation, InventoryHandler invHandler) throws ConnectionToDatabaseFailed {
        this.storeInformation = storeInformation;
        this.receipt = new Receipt(storeInformation);
        this.invHandler = invHandler;
    }

    /**
     * This method will return the list of items which have been registered in
     * the sale.
     *
     * @return The item list in the sale
     */
    public ArrayList<ItemDTO> getListOfItems() {
        return this.itemList;
    }

    /**
     * This method calls for externelInventorySystem to get information about a
     * specific item. and adds it to the sale but if an item already has been
     * registered it just increases the quantity.
     *
     * @param barCode: A bar code that represents a specific item.
     * @param itemQuantity: the quantity of the item
     * @param invHandler The Management of items in the inventory
     * @return : returns the information about the item. But if it is already
     * registered it returns the information with the updated quantity.
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If the
     * connection to the database fails
     * @throws
     * se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException
     */
    public ItemDTO addItemToSale(int barCode, int itemQuantity, InventoryHandler invHandler) throws ConnectionToDatabaseFailed, InvalidItemBarcodeException {
        if (checkIfItemAlreadyRegistered(barCode)) {
            return increaseQuantity(barCode, itemQuantity);
        } else {
            ItemDTO existingItem = invHandler.getItemInformation(barCode);
            ItemDTO newItem = new ItemDTO(existingItem.getItemName(), existingItem.getItemPrice(), existingItem.getItemVAT(), existingItem.getBarCode(), itemQuantity);
            itemList.add(newItem);
            return newItem;
        }
    }

    /**
     * This method checks if an item has already been registered and if its in
     * the itemList.
     *
     * @param barCode the bar code of the item being scanned
     * @return <code>true</code> if item status is changed so that the system
     * will know that this item has already been scanned before and should
     * increase the quantity, <code>false</code> otherwise.
     */
    public boolean checkIfItemAlreadyRegistered(int barCode) {
        for (ItemDTO item : itemList) {
            if (barCode == item.getBarCode()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * If an item already been registered then it will add up the quantity of
     * that item.
     *
     * @param barCode The item bar code
     * @param itemQuantity the quantity of item to increase with
     * @return <code>null</code> The object item which quantity will increase.
     */
    public ItemDTO increaseQuantity(int barCode, int itemQuantity) {
        for (int i = 0; i < itemList.size(); i++) {
            ItemDTO item = itemList.get(i);
            if (barCode == item.getBarCode()) {
                int newQuantity = item.getItemQuantity() + itemQuantity;
                ItemDTO newItem = new ItemDTO(item.getItemName(), item.getItemPrice(), item.getItemVAT(), item.getBarCode(), newQuantity);
                itemList.set(i, newItem);
                return newItem;
            }
        }
        return null;
    }

    /**
     * This method will update the products in the sale as they are being
     * scanned, it will calculate the running total and item list will be
     * updated accordingly
     *
     * @return The object holding the updated sales information.
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If
     * Connection fails
     */
    public SaleDTO updateProducts() throws ConnectionToDatabaseFailed {
        runningTotal();
        SaleDTO finalizedSale = new SaleDTO(this.itemList, this.totalPrice, this.totalVAT);
        return finalizedSale;
    }

    private void runningTotal() {
        for (ItemDTO item : itemList) {
            this.totalPrice += (item.getItemPrice()) * (item.getItemQuantity());
            this.totalVAT += (item.getItemPrice() * item.getItemVAT()) * (item.getItemQuantity());
        }
    }

    /**
     * Updates the item inventory with the sale information, this will be the
     * final valued update.
     *
     * @param payment The payment registered for the Sale.
     * @param invHandler The management of items inventory.
     * @return The object holding the final sales information.
     */
    public SaleDTO SaleInformation(Payment payment, InventoryHandler invHandler) {
        SaleDTO finalizedSale = new SaleDTO(this.itemList, this.totalPrice, this.totalVAT);
        invHandler.updateInventory(finalizedSale);
        notifyObservers();
        return finalizedSale;
    }

    /**
     * Updates the handlers with information from the sale. Payment will update
     * the balance, item quantity will be updated in the inventory.
     *
     * @param paymentAmount The amount paid for the sale.
     * @param accHandler The management of the accounting balance
     * @param invHandler The management of the inventory items.
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If
     * connection fails
     */
    public void updateHandlers(double paymentAmount, AccountingHandler accHandler,
            InventoryHandler invHandler) throws ConnectionToDatabaseFailed {
        for (ItemDTO item : itemList) {
            invHandler.decreaseItemQuantityInInventoryHandler(item.getBarCode(), item.getItemQuantity());
        }
        accHandler.updateAccountHandler(paymentAmount);
    }

    /**
     * Adds a revenue observer.
     *
     * @param obs The observer to be added
     */
    public void addRevenueObserver(RevenueObserver obs) {
        revenueObservers.add(obs);
    }

    private void notifyObservers() {
        for (RevenueObserver obs : revenueObservers) {
            obs.updateTotalRevenue(getTotalPriceAfterDiscount());
        }
    }

    /**
     *
     * Sets the discount based on the strategy
     *
     * @param discount the Discount
     */
    public void setStrategyDiscount(StrategyDiscount discount) {
        this.discount = discount;
    }

    /**
     *
     * Gets the discount
     *
     * @throws ConnectionToDatabaseFailed If Connection fails
     */
    public void getDiscount() throws ConnectionToDatabaseFailed {
        if (discount != null) {
            this.totalPriceAfterDiscount = discount.calculateDiscount(this.totalPrice, this.itemList);
        }
    }

    /**
     *
     * Gets the total value of the sale after discount has been applied.
     *
     * @return the total price of sale after discount applied
     */
    public double getTotalPriceAfterDiscount() {
        return this.totalPriceAfterDiscount;
    }

    /**
     * Gets the stores name
     *
     * @return The stores name
     */
    public String getStoreName() {
        return this.storeInformation.getStoreName();
    }

    /**
     * Gets the stores location
     *
     * @return The stores address
     */
    public String getStoreAddress() {
        return this.storeInformation.getStoreAddress();
    }
}
