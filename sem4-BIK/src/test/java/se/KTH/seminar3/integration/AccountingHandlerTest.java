package se.KTH.seminar3.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountingHandlerTest {

    private AccountingHandler instanceToTest;
    private final double TOTALPAYMENT = 100.0;

    @BeforeEach
    public void setUp() {
        this.instanceToTest = new AccountingHandler();
    }

    @Test
    public void testUpdateAccountHandler() {
        try {
            double compare = instanceToTest.getBalance();
            instanceToTest.updateAccountHandler(TOTALPAYMENT);
            assertTrue(instanceToTest.getBalance() >= compare, "Accounting system data base was not correctly updated.");

        } catch (ConnectionToDatabaseFailed e) {
            System.out.println(e);
            fail(e);
        }
    }
}
