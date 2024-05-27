package se.KTH.seminar3.model;

import se.KTH.seminar3.integration.ConnectionToDatabaseFailed;
import se.KTH.seminar3.integration.InventoryHandler;
import se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException;

/**
 * Item description of an item in the store.
 */
public class Item {

    private int barCode;
    private String itemName;
    private double itemPrice;
    private int itemQuantity;
    private double itemVAT;

    /**
     *
     * Constructor for item. Creates the instance for item to be represented.
     *
     * @param barCode - the items bar code
     * @param itemQuantity - the quantity of the item.
     * @param invHandler - the item description comes from the inventory
     * handler.
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If the
     * connection to the database fails.
     * @throws se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException
     *
     */
    public Item(int barCode, int itemQuantity, InventoryHandler invHandler) throws ConnectionToDatabaseFailed, InvalidItemBarcodeException {
        ItemDTO itemDescription = invHandler.getItemInformation(barCode);
        this.barCode = itemDescription.getBarCode();
        this.itemName = itemDescription.getItemName();
        this.itemPrice = itemDescription.getItemPrice();
        this.itemVAT = itemDescription.getItemVAT();
        this.itemQuantity = itemQuantity;

    }

    /**
     * This method gets the Items bar code
     *
     * @return items bar code
     */
    public int getBarCode() {
        return barCode;
    }

    /**
     * This method gets the Items name.
     *
     * @return the items name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method gets the Items price
     *
     * @return the items price
     */
    public double getItemPrice() {
        return itemPrice;
    }

    /**
     * This method gets the quantity amount of the Item.
     *
     * @return the quantity of item
     */
    public int getItemQuantity() {
        return this.itemQuantity;
    }

    /**
     * This method gets the Items VAT
     *
     * @return the items VAT
     */
    public double getItemVAT() {
        return itemVAT;
    }

}
