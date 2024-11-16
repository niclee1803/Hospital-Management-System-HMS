package filehandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code PharmacistFileHandler} class is responsible for handling file operations related to pharmacist records
 * stored in a CSV file. This class extends the {@code FileHandler} class and specifically works with the file located at
 * "Database/Pharmacist_list.csv".
 * <p>This class inherits methods from {@code FileHandler} for reading and writing data and can be extended to add pharmacist-
 * specific file handling functionality if needed.</p>
 */
public class PharmacistFileHandler extends FileHandler {

    /**
     * Constructs a {@code PharmacistFileHandler} object and sets the file path to "Database/Pharmacist_list.csv" for managing
     * pharmacist record files.
     */
    public PharmacistFileHandler() {
        super("Database/Pharmacist_list.csv");
    }

    /**
     * Reads all lines from the pharmacist_list.csv file.
     * 
     * @return A list of pharmacist records as String arrays.
     */
    public List<String[]> readAllLines() {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("Database/Pharmacist_list.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                records.add(record);
            }
        } catch (IOException e) {
            System.err.println("Error reading pharmacist records: " + e.getMessage());
        }
        return records;
    }
}
