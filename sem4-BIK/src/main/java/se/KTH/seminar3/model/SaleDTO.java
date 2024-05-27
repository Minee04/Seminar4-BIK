package se.KTH.seminar3.model;

import java.util.ArrayList;

/**
 * The SaleDTO class is a data transfer object that encapsulates the
 * SaleInformation related to a sale. It includes a method to set the
 * SaleInformation.
 */
public class SaleDTO {

    private double totalPrice;
    private double totalVAT;
    private ArrayList<ItemDTO> itemList = new ArrayList<ItemDTO>();

    /**
     * Constructor which initializes information for the sale to be transferred.
     *
     * @param itemList The list of items
     * @param totalPrice The total price of the sale.
     * @param totalVAT The total VAT of the sale
     */
    public SaleDTO(ArrayList<ItemDTO> itemList, double totalPrice, double totalVAT) {
        this.totalPrice = totalPrice;
        this.totalVAT = totalVAT;
        this.itemList = itemList;

    }

    /**
     * Gets the list of items
     *
     * @return The list of items
     */
    public ArrayList<ItemDTO> getItemList() {
        return itemList;
    }

    /**
     * Gets the total price of sale
     *
     * @return The total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Gets the total VAT of the sale
     *
     * @return The total VAT
     */
    public double getTotalVAT() {
        return totalVAT;
    }

}
