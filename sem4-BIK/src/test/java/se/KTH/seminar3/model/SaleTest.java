package se.KTH.seminar3.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import se.KTH.seminar3.integration.*;
import se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException;

public class SaleTest {
    private Sale instanceToTest;
    private final int VALID_BARCODE = 111;
    private final int QUANTITY = 1;
    private final double AMOUNT = 110;
    private final double DISCOUNT_PERCENT = 10;


    @BeforeEach
    public void setUp() throws ConnectionToDatabaseFailed {
        StoreDTO storeInformation = new StoreDTO("Test Store", "Test Address");
        InventoryHandler invHandler = InventoryHandler.getInstance();
        this.instanceToTest = new Sale(storeInformation, invHandler);
    }

@Test
public void testAddItemToSale() {
    try {
        instanceToTest.addItemToSale(VALID_BARCODE, QUANTITY, InventoryHandler.getInstance());
    } catch (ConnectionToDatabaseFailed | InvalidItemBarcodeException e) {
        fail("Exception thrown during addItemToSale: " + e.getMessage());
    }
}


@Test
public void testUpdateProducts() throws InvalidItemBarcodeException {
    try {
        instanceToTest.addItemToSale(VALID_BARCODE, QUANTITY, InventoryHandler.getInstance());
        SaleDTO result = instanceToTest.updateProducts();
        assertNotNull(result, "SaleDTO not created");
    } catch (ConnectionToDatabaseFailed e) {
        fail("Exception thrown during updateProducts: " + e.getMessage());
    }
}


    @Test
    public void testUpdateHandlers() throws InvalidItemBarcodeException {
        try{
            instanceToTest.addItemToSale(VALID_BARCODE, QUANTITY, InventoryHandler.getInstance());
            instanceToTest.updateHandlers(AMOUNT, new AccountingHandler(), InventoryHandler.getInstance());
        } catch(ConnectionToDatabaseFailed e){
            fail("Exception thrown during updateHandlers: " + e.getMessage());
        }
    }

    @Test
    public void testGetDiscount() {
        try{
            instanceToTest.setStrategyDiscount(new StrategyDiscountPercent(DISCOUNT_PERCENT));
            instanceToTest.getDiscount();
        } catch(ConnectionToDatabaseFailed e){
            fail("Exception thrown during getDiscount: " + e.getMessage());
        }
    }
}
