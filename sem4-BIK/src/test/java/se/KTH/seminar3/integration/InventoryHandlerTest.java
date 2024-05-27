package se.KTH.seminar3.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException;

public class InventoryHandlerTest {

    private InventoryHandler instanceToTest;
    private final int INVALID_BARCODE = 999;

    @BeforeEach
    public void setUp() {
        this.instanceToTest = InventoryHandler.getInstance();
    }

    @Test
    public void testGetItemInformationInvalidBarcode() {
        try {
            instanceToTest.getItemInformation(INVALID_BARCODE);
            fail("Expected InvalidItemBarcodeException to be thrown");
        } catch (ConnectionToDatabaseFailed e) {
            fail("Exception thrown during getItemInformation: " + e.getMessage());
        } catch (InvalidItemBarcodeException e) {
            assertEquals("Error: Invalid barcode " + INVALID_BARCODE, e.toString());
        }
    }

}
