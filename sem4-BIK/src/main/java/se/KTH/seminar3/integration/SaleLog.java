package se.KTH.seminar3.integration;

import java.util.ArrayList;
import java.util.List;
import se.KTH.seminar3.model.SaleDTO;

/**
 * When sale is completed then the sale is logged via the accountingHandler.
 */
public class SaleLog {

    private List<SaleDTO> saleLog = new ArrayList<SaleDTO>();

    /**
     * Default constructor for SaleLog
     */
    public SaleLog() {
    }

    /**
     * Updates the sale log with information of a completed sale.
     *
     * @param finalizedSale A finished sale.
     * @throws se.KTH.seminar3.integration.ConnectionToDatabaseFailed If
     * connection fails
     */
    public void updateSalelog(SaleDTO finalizedSale) throws ConnectionToDatabaseFailed {
        saleLog.add(finalizedSale);
    }

    /**
     * Gets the sale log.
     *
     * @return The sale log.
     */
    public List<SaleDTO> getSaleLog() {
        return this.saleLog;
    }
}
