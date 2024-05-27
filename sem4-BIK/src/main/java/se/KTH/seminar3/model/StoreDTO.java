package se.KTH.seminar3.model;

/**
 * This is a DTO, used for transferring data from Store related data.
 */
public class StoreDTO {

    private final String storeName;
    private final String storeAddress;

    /**
     * Creates an instance of StoreDTO
     *
     * @param storeName represent the name of the store
     * @param storeAddress represent the address of the store
     */
    public StoreDTO(String storeName, String storeAddress) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }

    /**
     * Gets the stores name.
     *
     * @return The stores name
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Gets the stores address
     *
     * @return The stores address
     */
    public String getStoreAddress() {
        return storeAddress;

    }
}
