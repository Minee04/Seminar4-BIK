package se.KTH.seminar3.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import se.KTH.seminar3.model.SaleDTO;
import java.util.ArrayList;
import java.util.List;

public class SaleLogTest {

    private SaleLog instanceToTest;
    private SaleDTO saleInfo;

    @BeforeEach
    public void setUp() {
        this.instanceToTest = new SaleLog();
        this.saleInfo = new SaleDTO(new ArrayList<>(), 100.0, 10.0);
    }

    @Test
    public void testUpdateSalelog() {
        try {
            instanceToTest.updateSalelog(saleInfo);
            List<SaleDTO> saleLog = instanceToTest.getSaleLog();
            assertTrue(saleLog.contains(saleInfo), "Sale log was not correctly updated.");
        } catch (ConnectionToDatabaseFailed e) {
            fail("Exception thrown during updateSalelog: " + e.getMessage());
        }
    }
}
