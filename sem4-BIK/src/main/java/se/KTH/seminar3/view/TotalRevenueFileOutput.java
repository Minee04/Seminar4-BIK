package se.KTH.seminar3.view;

import se.KTH.seminar3.model.RevenueObserver;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class implements the RevenueObserver interface and is responsible for
 * writing the total revenue to a file.
 */
public class TotalRevenueFileOutput implements RevenueObserver {

    private static final String TXTFILE = "Revenue.txt";
    private double totalRevenue = 0;
    private PrintWriter outputStream;

    /**
     * Constructs a new TotalRevenueFileOutput object and initializes the
     * outputStream. If an IOException occurs while initializing the
     * outputStream, an error message is printed to the console.
     */
    public TotalRevenueFileOutput() {
        try {
            outputStream = new PrintWriter(new FileWriter(TXTFILE), true);
        } catch (IOException e) {
            System.out.println("An error occurred while trying to write to the file");
        }
    }

    /**
     * Updates the total revenue based on the payment and change, writes the
     * updated revenue to the file, and notifies all observers of the update.
     */
    @Override
    public void updateTotalRevenue(double totalPriceAfterDiscount) {
        totalRevenue += totalPriceAfterDiscount;
        printRevenue();
    }

    private void printRevenue() {
        System.out.println("Writing to file '" + TXTFILE + "'\n");
        printToFile();
    }

    private void printToFile() {
        outputStream.println("Total revenue: " + totalRevenue + " SEK");
    }

}
