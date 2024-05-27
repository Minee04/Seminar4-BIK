package se.KTH.seminar3.integration;

/**
 * Handles the stores accounting data, balance, etc.
 */
public class AccountingHandler {

    private double balance;

    /**
     * Constructor method
     */
    public AccountingHandler() {
    }

    /**
     * The stores balance is updated
     *
     * @param totalPayment The total payment from a customer
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If
     * connection fails
     */
    public void updateAccountHandler(double totalPayment) throws ConnectionToDatabaseFailed {
        this.balance += totalPayment;

    }

    /**
     * Getter method, gets the stores current account balance.
     *
     * @return The balance of the stores account
     */
    public double getBalance() {
        return this.balance;
    }

}
