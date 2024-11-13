package filehandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MedicationFileHandler {
    // Hardcoded file path for the medication stock CSV
    /**
     * The file path for the medication stock CSV.
     */
    private static final String FILE_PATH = "Database/Medicine_List.csv";

    /**
     * Reads the medication stock CSV file and returns the rows, including the header row,
     * as a list of string arrays. Each array represents a row with columns:
     * Medicine Name, Current Stock, Unit.
     *
     * <p>Each line in the CSV file is split by commas to form the string arrays.</p>
     * 
     * @return List of string arrays where each array is a row from the CSV. The first row is typically the header (if
     * present in the file)
     * @throws IOException if the file cannot be read. (For eg, if the file does not exist or cannot be accessed)
     */
    public List<String[]> readMedicationStock() throws IOException {
        List<String[]> rows = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            
            // Read each line in the CSV file
            while ((line = br.readLine()) != null) {
                // Split the line by commas to handle CSV format
                String[] row = line.split(",");
                rows.add(row);
            }
        }
        
        return rows;
    }

    /**
     * Updates the stock quantity for a specified medication in the CSV file/
     *
     * <p>The method searches for the medication by name (case insensitive), updates its stock the given {@code newStock},
     * and writes the changes back to the file. If the medication is not found, an error message is printed to the console.
     * </p>
     * @param medicineName The name of the medication to update (case insensitive)
     * @param newStock The new stock quantity to set for the medication
     * @throws IOException If the file cannot be read or written to
     */
     public void updateMedicationStock(String medicineName, int newStock) throws IOException {
        List<String[]> rows = readMedicationStock();
        boolean updated = false;

        // Update the stock for the specified medication
        for (String[] row : rows) {
            if (row[0].equalsIgnoreCase(medicineName)) { // Assuming Medicine Name is in the first column
                row[1] = String.valueOf(newStock); // Update the Current Stock
                updated = true;
                break;
            }
        }

        if (!updated) {
            System.err.println("Medicine with name " + medicineName + " not found.");
            return;
        }

        // Write updated data back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] row : rows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }

    // ADD the rows inputting or medication updating functions here 
}
