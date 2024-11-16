package filehandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file operations related to doctor records.
 * This class extends the {@link FileHandler} class to handle doctor-specific file management.
 */
public class DoctorFileHandler extends FileHandler {

    /**
     * Constructs a DoctorFileHandler object that manages the doctor records file located at "Database/Doctor_Records.csv".
     */
    public DoctorFileHandler() {
        super("Database/Doctor_Records.csv");
    }

    /**
     * Reads all lines from the doctor_records.csv file.
     * 
     * @return A list of doctor records as String arrays.
     */
    public List<String[]> readAllLines() {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("Database/Doctor_Records.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                records.add(record);
            }
        } catch (IOException e) {
            System.err.println("Error reading doctor records: " + e.getMessage());
        }
        return records;
    }
}
