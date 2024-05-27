package se.KTH.seminar3.model;

/**
 * The class represents the items description.
 */
public class ItemDTO {

    private int barCode;
    private String itemName;
    private double itemPrice;
    private double itemVAT;
    private int itemQuantity;

    /**
     * A constructor for itemDTO, creates an instance of the item with
     * description.
     *
     * @param itemName The items name
     * @param itemPrice The items price
     * @param itemVAT The items VAT
     * @param barCode The items bar code.
     * @param itemQuantity The quantity of item
     */
    public ItemDTO(String itemName, double itemPrice, double itemVAT, int barCode, int itemQuantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.barCode = barCode;
        this.itemVAT = itemVAT;
        this.itemQuantity = itemQuantity;
    }

    /**
     * This method gets the Items bar code.
     *
     * @return The items bar code
     */
    public int getBarCode() {
        return barCode;
    }

    /**
     * This method gets the Items name.
     *
     * @return The item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method gets the Items price
     *
     * @return The items price
     */
    public double getItemPrice() {
        return itemPrice;
    }

    /**
     * This method gets the quantity amount of the Item.
     *
     * @return The quantity of item
     */
    public int getItemQuantity() {
        return itemQuantity;
    }

    /**
     * This method gets the Items VAT
     *
     * @return The item VAT
     */
    public double getItemVAT() {
        return itemVAT;
    }
}
