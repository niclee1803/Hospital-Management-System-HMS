package managers;

import java.io.IOException;
import java.util.List;
import filehandlers.MedicationFileHandler;

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
        try {
            List<String[]> inventory = fileHandler.readMedicationStock();

            // Print header
            System.out.printf("%-20s %-15s %-10s%n", "Medicine Name", "Current Stock", "Unit");
            System.out.println("-----------------------------------------------------");

            // Print each row in a formatted way
            for (String[] row : inventory) {
                System.out.printf("%-20s %-15s %-10s%n", row[0], row[1], row[2]);
            }

        } catch (IOException e) {
            System.err.println("Error reading medication stock: " + e.getMessage());
        }
    }
    // INCLUDE other updating functions here 
}
