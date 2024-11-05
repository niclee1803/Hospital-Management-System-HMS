package filehandlers;

import java.io.*;

public class FileHandler {
    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    // Read a specific user line from the CSV by ID
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

    // Write a new record to the CSV file
    public void writeLine(String[] record) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(String.join(",", record));  // Join array elements with commas
            bw.newLine();  // Move to the next line after writing the record
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    // Delete a line in csv by id
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

    // update a line in csv by id
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
