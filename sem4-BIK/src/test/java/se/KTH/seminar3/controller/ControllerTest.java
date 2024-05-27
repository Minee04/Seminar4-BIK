package se.KTH.seminar3.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import se.KTH.seminar3.integration.*;
import se.KTH.seminar3.integration.InventoryHandler.InvalidItemBarcodeException;
import se.KTH.seminar3.model.*;

public class ControllerTest {

    private Controller contr;
    private final int BARCODE = 111;
    private final int QUANTITY = 1;
    private final double AMOUNT = 110.0f;
    private final double DISCOUNT_PERCENT = 10.0;

    @BeforeEach
    public void setUp() {
        this.contr = new Controller();
    }

    @Test
    public void testStartSale() {
        try {
            contr.startSale();
        } catch (ConnectionToDatabaseFailed e) {
            fail("Exception thrown during startSale: " + e.getMessage());
        }
    }

    @Test
    public void testRegisterItem() {
        try {
            contr.startSale();
            contr.registerItem(BARCODE, QUANTITY);
        } catch (ConnectionToDatabaseFailed | InvalidItemBarcodeException e) {
            fail("Exception thrown during registerItem: " + e.getMessage());
        }
    }

    @Test
    public void testEndSale() {
        try {
            contr.startSale();
            contr.registerItem(BARCODE, QUANTITY);
            contr.endSale();
        } catch (ConnectionToDatabaseFailed | InvalidItemBarcodeException e) {
            fail("Exception thrown during endSale: " + e.getMessage());
        }
    }

    @Test
    public void testRegisterPayment() {
        try {
            contr.startSale();
            contr.registerItem(BARCODE, QUANTITY);
            contr.endSale();
            contr.registerPayment(AMOUNT, new AccountingHandler());
        } catch (ConnectionToDatabaseFailed | InvalidItemBarcodeException e) {
            fail("Exception thrown during registerPayment: " + e.getMessage());
        }
    }

@Test
public void testApplyDiscount() {
    try {
        contr.startSale();
        contr.endSale();
        contr.applyDiscount(contr.endSale().getTotalPrice());
    } catch (ConnectionToDatabaseFailed e) {
        fail("Unexpected exception thrown during applyDiscount: " + e.getMessage());
    }
}



    }

