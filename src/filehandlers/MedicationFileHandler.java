package filehandlers;

import java.io.IOException;
import java.util.List;

/**
 * The {@code MedicationFileHandler} class manages the medication stock CSV file.
 * It extends {@code ItemFileHandler} to reuse generic file handling operations.
 */
public class MedicationFileHandler extends ItemFileHandler {

    // Static constant for the file path
    private static final String FILE_PATH = "Database/Medicine_List.csv";

    /**
     * Default constructor for {@code MedicationFileHandler}.
     * Initializes the handler with the predefined file path.
     */
    public MedicationFileHandler() {
        super(FILE_PATH);
    }

    /**
     * Reads the medication stock from the CSV file and returns the rows.
     * Each row is represented as a string array.
     *
     * @return List of string arrays where each array is a row from the CSV.
     * @throws IOException if the file cannot be read.
     */
    public List<String[]> readMedicationStock() throws IOException {
        return readFile(); // Reuse the inherited readFile method
    }

    /**
     * Updates the stock quantity for a specified medication in the CSV file.
     *
     * @param medicineName The name of the medication to update (case insensitive).
     * @param newStock     The new stock quantity to set for the medication.
     * @throws IOException if the file cannot be read or written to.
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
        writeFile(rows); // Reuse the inherited writeFile method
    }
}
