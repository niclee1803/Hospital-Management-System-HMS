package filehandlers;

import java.io.*;

/**
 * Handles basic file operations (reading, writing, updating and deleting) for a CSV file.
 * This class provides general methods that can be extended for more specific use cases.
 */
public class FileHandler {
    private String filePath;

    /**
     * Constructs a FileHandler object with the specified file path
     * @param filePath The path to the CSV file to be managed by this handler
     */
    public FileHandler(String filePath) {
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
                if (record.length > 0 && record[0].equals(id)) {  // Assuming ID is the first field
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(String.join(",", record));  // Join array elements with commas
            bw.newLine();  // Move to the next line after writing the record
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    /**
     * Delets a line in the CSV file by the given ID.
     * @param id The ID of the record to delete
     */
    public void deleteLine(String id) {
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
                if (currentRecord.length > 0 && currentRecord[0].equals(id)) {
                    // Skip writing this line to the temp file if the ID matches (deletes the line)
                    found = true;
                    continue;
                }
                // Write all other lines to the temp file
                bw.write(line);
                bw.newLine();
            }

            if (!found) {
                System.out.println("Record with ID " + id + " not found.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while deleting the line in the CSV file: " + e.getMessage());
        }

        // Replace the original file with the updated temp file
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        } else {
            System.err.println("Could not replace the original file with the updated file.");
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
                if (currentRecord.length > 0 && currentRecord[0].equals(record[0])) {
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

}
