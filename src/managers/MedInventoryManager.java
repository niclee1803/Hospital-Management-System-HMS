package managers;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import filehandlers.MedicationFileHandler;
import utility.*;

/**
 * The {@code MedInventoryManager} class is responsible for managing the medical inventory, specifically the medications
 * in stock. It handles loading and displaying rhe current inventory of medications and can be extended to include functionality
 * for updatingthe medication stock.
 */
public class MedInventoryManager {

    private MedicationFileHandler fileHandler;

    // Constructor to initialize the MedicationFileHandler
    public MedInventoryManager() {
        this.fileHandler = new MedicationFileHandler();
    }

    /**
     * Prints the full inventory of medical stock.
     * Retrieves all rows from the medication stock file and prints them.
     */
    public void printFullInventory() {

        Table newtable = new Table();

        try {
            List<String[]> inventory = fileHandler.readMedicationStock();

            newtable.printTable(inventory);



        } catch (IOException e) {
            System.err.println("Error reading medication stock: " + e.getMessage());
        }

    }
    // INCLUDE other updating functions here 
}