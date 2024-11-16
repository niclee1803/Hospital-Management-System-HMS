package filehandlers;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Handles file operations for administrator records.
 * Extends the {@link FileHandler} class to provide specific functionality for administrators.
 */
public class AdministratorFileHandler extends FileHandler {

    /**
     * Constructs an {@code AdministratorFileHandler} object to manage "administrator_records.csv".
     */
    public AdministratorFileHandler() {
        super("Database/administrator_records.csv");
    }

    /**
     * Reads all lines from the administrator_records.csv file.
     * 
     * @return A list of administrator records as String arrays.
     */
    public List<String[]> readAllLines() {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("Database/administrator_records.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                records.add(record);
            }
        } catch (IOException e) {
            System.err.println("Error reading administrator records: " + e.getMessage());
        }
        return records;
    }
}
