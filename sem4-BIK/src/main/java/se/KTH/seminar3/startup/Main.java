package se.KTH.seminar3.startup;

import se.KTH.seminar3.controller.Controller;
import se.KTH.seminar3.view.View;

/**
 * Starts the entire application, contains the main method used to start all the
 * applications. These applications are Controller, View, InventoryHandler,
 * AccountingHandler, Printer, and SaleLog.
 */
public class Main {

    /**
     * The main method used to start the entire application.
     *
     * @param args The application does not take any command line parameters.
     */
    public static void main(String[] args) {
        Controller contr = new Controller();
        View view = new View(contr);

        view.runFakeExecution();
    }
}
