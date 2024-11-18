package filehandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Handles file operations for medication stock in the CSV file.
 */
public class MedicationFileHandler {
    // Hardcoded file path for the medication stock CSV
    private static final String FILE_PATH = "Database/Medicine_List.csv";

    /**
     * Reads the medication stock CSV file and returns the rows as a list of string arrays.
     *
     * @return List of string arrays where each array represents a row from the CSV.
     * @throws IOException if the file cannot be read.
     */
    public List<String[]> readMedicationStock() throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line.split(",")); // Split by comma
            }
        }
        return rows;
    }

    /**
     * Updates the stock of a specified medication in the CSV file.
     *
     * @param medicineName The name of the medication to update (case insensitive).
     * @param newStock     The new stock value to set.
     * @return true if the medication was found and updated; false otherwise.
     * @throws IOException If the file cannot be read or written to.
     */
    public boolean updateMedicationStock(String medicineName, int newStock) throws IOException {
        List<String[]> rows = readMedicationStock();
        boolean updated = false;

        // Update the stock for the specified medication
        for (String[] row : rows) {
            if (row[0].equalsIgnoreCase(medicineName)) { // Medicine Name is in the first column
                row[2] = String.valueOf(newStock); // Update the Current Stock column
                updated = true;
                break;
            }
        }

        // Write updated rows back to the file
        if (updated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String[] row : rows) {
                    bw.write(String.join(",", row));
                    bw.newLine();
                }
            }
        }
        return updated;
    }

    /**
     * Adds a new medication entry to the CSV file.
     *
     * @param newMedication The new medication entry as a string array.
     * @throws IOException If the file cannot be written to.
     */
    public void addMedication(String[] newMedication) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(String.join(",", newMedication));
            bw.newLine();
        }
    }

    /**
     * Updates the low stock alert level for a specific medication in the CSV file.
     *
     * @param medicineName   The name of the medication to update (case insensitive).
     * @param lowStockAlert  The new low stock alert level.
     * @return true if the medication was found and updated; false otherwise.
     * @throws IOException If the file cannot be read or written to.
     */
     public boolean updateLowStockAlert(String medicineName, int lowStockAlert) throws IOException {
        List<String[]> rows = readMedicationStock();
        boolean updated = false;
    
        // Update the Low Stock Alert level for the specified medication
        for (String[] row : rows) {
            if (row[0].equalsIgnoreCase(medicineName)) { // Medicine Name is in the first column
                // Ensure the row has enough columns to update the Low Stock Alert
                if (row.length < 4) { // Add missing columns if necessary
                    String[] extendedRow = new String[4];
                    System.arraycopy(row, 0, extendedRow, 0, row.length);
                    for (int i = row.length; i < extendedRow.length; i++) {
                        extendedRow[i] = ""; // Fill missing columns with empty values
                    }
                    row = extendedRow;
                }
                row[1] = String.valueOf(lowStockAlert); // Update Low Stock Alert (now column 2)
                updated = true;
                break;
            }
        }
    
        // Write the updated rows back to the file
        if (updated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String[] row : rows) {
                    bw.write(String.join(",", row));
                    bw.newLine();
                }
            }
        }
    
        return updated;
    }

    public boolean updateOrAddStock(String medicineName, int replenishAmount, String unit) throws IOException {
        List<String[]> rows = readMedicationStock();
        boolean updated = false;
    
        // Check if the medicine already exists in the inventory
        for (String[] row : rows) {
            if (row[0].equalsIgnoreCase(medicineName)) { // Medicine Name matches
                int currentStock = Integer.parseInt(row[2].trim()); // Current Stock column
                row[2] = String.valueOf(currentStock + replenishAmount); // Update stock
                updated = true;
                break;
            }
        }
    
        // If the medicine doesn't exist, add it to the inventory
        if (!updated) {
            rows.add(new String[]{medicineName, "10", String.valueOf(replenishAmount), unit}); // Default low stock alert: 10
            updated = true;
        }
    
        // Write updated rows back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] row : rows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    
        return updated;
    }

}
