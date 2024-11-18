package filehandlers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles basic file operations (reading, writing, updating and deleting) for a CSV file.
 * This class provides general methods that can be extended for more specific use cases.
 */
public class UserFileHandler {
    private String filePath;

    /**
     * Constructs a FileHandler object with the specified file path
     * @param filePath The path to the CSV file to be managed by this handler
     */
    public UserFileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads a specific line from the CSV file by matching the given ID
     * @param id The ID to search for in the first column of the CSV file.
     * @return The matching record as an array of Strings, or null if no matching record is found.
     */
    public String[] readLine(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                if (record.length > 0 && record[0].equalsIgnoreCase(id)) {  // Assuming ID is the first field
                    return record;  // Return the matching record
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
        return null;  // Return null if no matching record is found
    }

    /**
     * Writes a new record to the CSV file
     * @param record The record to be added, represented as an array of Strings.
     */
    public void writeLine(String[] record) {
        File file = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (file.exists() && file.length() > 0) {
                try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                    raf.seek(file.length() - 1);  // Move to the last byte
                    int lastByte = raf.read();
                    if (lastByte != '\n') {  // Check if it ends with a newline
                        bw.newLine();  // Add a newline if missing
                    }
                }
            }
            bw.write(String.join(",", record));
            bw.newLine();  // Always add a newline after the record
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }
    }
    

    /**
     * Deletes a line in the CSV file by the given ID.
     * @param id The ID of the record to delete
     * @return returns a boolean true if record was found and deleted and false if record does not exist
     */
    public boolean deleteLine(String id) {
        File inputFile = new File(filePath);
        File tempFile = new File("temp.csv");

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            boolean found = false;

            // Read each line in the original file
            while ((line = br.readLine()) != null) {
                String[] currentRecord = line.split(",");
                if (currentRecord.length > 0 && currentRecord[0].equalsIgnoreCase(id)) {
                    // Skip writing this line to the temp file if the ID matches (deletes the line)
                    found = true;
                    continue;
                }
                // Write all other lines to the temp file
                bw.write(line);
                bw.newLine();
            }

            if (!found) {
                //System.out.println("Record with ID " + id + " not found.");
                return false;
            }
        } catch (IOException e) {
            System.err.println("An error occurred while deleting the line in the CSV file: " + e.getMessage());
        }

        // Replace the original file with the updated temp file
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
            return true;
        } else {
            System.err.println("Could not replace the original file with the updated file.");
            return false;
        }
    }

    /**
     * Updates an existing record in the CSV file by matching the ID in the first column of the CSV fie.
     * If no matching ID is found, the record will be appended to the file.
     * @param record The updated record as an array of Strings. The first element is the ID.
     */
    public void updateLine(String[] record) {
        File inputFile = new File(filePath);
        File tempFile = new File("temp.csv");

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            boolean updated = false;

            while ((line = br.readLine()) != null) {
                String[] currentRecord = line.split(",");

                // Check if the current line has the same ID as the record we want to update
                if (currentRecord.length > 0 && currentRecord[0].equalsIgnoreCase(record[0])) {
                    // Write the new record instead of the old one
                    bw.write(String.join(",", record));
                    updated = true;
                } else {
                    // Write the existing line as is
                    bw.write(line);
                }
                bw.newLine();
            }

            // If ID was not found, append the new record
            if (!updated) {
                bw.write(String.join(",", record));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while updating the CSV file: " + e.getMessage());
        }

        // Replace the original file with the updated temp file
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        } else {
            System.err.println("Could not replace the original file with the updated file.");
        }
    }

    /**
     * Reads all lines from the csv file.
     *
     * @return A list of records as String arrays.
     */
    public List<String[]> readAllLines() {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                records.add(record);
            }
        } catch (IOException e) {
            System.err.println("Error reading records: " + e.getMessage());
        }
        return records;
    }


    public boolean recordExists(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                if (record[0].equalsIgnoreCase(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking record existence: " + e.getMessage());
        }
        return false;
    }

}
