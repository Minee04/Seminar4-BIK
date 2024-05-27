package se.KTH.seminar3.model;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import se.KTH.seminar3.integration.*;

public class ReceiptTest {
    private Receipt instanceToTest;
    private final double TOTALPAYMENT = 20;
    private final double TOTALPRICE = 10;
    private final double TOTALVAT = 10;
    private SaleDTO saleInfo;
    private AccountingHandler accHandler;
    private InventoryHandler invHandler;
    private SaleLog saleLog;

    @BeforeEach
    public void setUp() throws ConnectionToDatabaseFailed {
        StoreDTO storeInformation = new StoreDTO("Test Store", "Test Address");
        this.saleInfo = new SaleDTO(new ArrayList<>(), TOTALPRICE, TOTALVAT);
        this.accHandler = new AccountingHandler();
        this.invHandler = InventoryHandler.getInstance();
        this.saleLog = new SaleLog();
        Payment payment = new Payment(TOTALPAYMENT, accHandler);
        Sale sale = new Sale(storeInformation, invHandler);
        this.instanceToTest = Receipt.generateReceipt(payment, sale, saleLog, invHandler);
    }

    @Test
    public void testGenerateReceipt() {
        assertNotNull(instanceToTest, "Receipt not created");
    }
}
