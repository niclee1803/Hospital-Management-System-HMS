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

    /**
     * Writes a staff record to the respective records file.
     *
     * @param record The record to be added.
     * @param role The role of the staff (Doctor, Pharmacist, or Administrator).
     */
    public void writeStaffRecord(String[] record, String role) {
        String filePath;
        if (role.equalsIgnoreCase("Doctor")) {
            filePath = "Database/Doctor_Records.csv";
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            filePath = "Database/Pharmacist_List.csv";
        } else if (role.equalsIgnoreCase("Administrator")) {
            filePath = "Database/Administrator_Records.csv";
        } else {
            System.out.println("Invalid role. Cannot write staff record.");
            return;
        }

        File file = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            // Check if the last line ends with a newline
            if (file.exists() && file.length() > 0) {
                try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                    raf.seek(file.length() - 1); // Move to the last byte
                    if (raf.readByte() != '\n') { // Check if the last byte is a newline
                        bw.newLine(); // Add a newline if missing
                    }
                }
            }

            // Write the new record
        // Append a comma for Doctor records specifically
        if (role.equalsIgnoreCase("Doctor")) {
            bw.write(String.join(",", record) + ","); // Add a trailing comma for Doctor records
        } else {
            bw.write(String.join(",", record)); // Write the new record without extra comma
        }
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to records file: " + e.getMessage());
        }
    }

    /**
     * Removes a staff record from the respective records file.
     *
     * @param id The ID of the staff to remove.
     * @param role The role of the staff (Doctor, Pharmacist, or Administrator).
     * @return True if the record was found and removed, false otherwise.
     */
    public boolean removeStaffRecord(String id, String role) {
        String filePath;
        if (role.equalsIgnoreCase("Doctor")) {
            filePath = "Database/Doctor_Records.csv";
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            filePath = "Database/Pharmacist_List.csv";
        } else if (role.equalsIgnoreCase("Administrator")) {
            filePath = "Database/Administrator_Records.csv";
        } else {
            System.out.println("Invalid role. Cannot remove staff record.");
            return false;
        }

        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                if (record[0].equals(id)) { // Match found
                    found = true;
                    continue; // Skip writing this line
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error while removing staff record: " + e.getMessage());
            return false;
        }

        // Replace the original file with the updated file
        if (found && inputFile.delete() && tempFile.renameTo(inputFile)) {
            return true;
        } else {
            System.err.println("Error replacing the original file with the updated file.");
            return false;
        }
    }

    /**
     * Checks if a record exists in the respective file.
     *
     * @param id The ID of the staff.
     * @param role The role of the staff.
     * @return True if the record exists, false otherwise.
     */
    public boolean recordExists(String id, String role) {
        String filePath;
        if (role.equalsIgnoreCase("Doctor")) {
            filePath = "Database/Doctor_Records.csv";
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            filePath = "Database/Pharmacist_List.csv";
        } else if (role.equalsIgnoreCase("Administrator")) {
            filePath = "Database/Administrator_Records.csv";
        } else {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                if (record[0].equals(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking record existence: " + e.getMessage());
        }
        return false;
    }

    /**
     * Updates a staff record in the respective records file.
     *
     * @param updatedRecord The updated record details.
     * @param role The role of the staff (Doctor, Pharmacist, or Administrator).
     */
    public void updateStaffRecord(String[] updatedRecord, String role) {
        String filePath;
        if (role.equalsIgnoreCase("Doctor")) {
            filePath = "Database/Doctor_Records.csv";
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            filePath = "Database/Pharmacist_List.csv";
        } else if (role.equalsIgnoreCase("Administrator")) {
            filePath = "Database/Administrator_Records.csv";
        } else {
            System.out.println("Invalid role. Cannot update staff record.");
            return;
        }

        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean updated = false;

            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                if (record[0].equals(updatedRecord[0])) { // Match found
                    bw.write(String.join(",", updatedRecord)); // Write the updated record
                    updated = true;
                } else {
                    bw.write(line); // Write the original record
                }
                bw.newLine();
            }

            if (!updated) {
                System.out.println("Record with ID " + updatedRecord[0] + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error updating staff record: " + e.getMessage());
        }

        // Replace the original file with the updated file
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.err.println("Error replacing the original records file.");
        }
    }


    /**
     * Writes a new user to the user_list.csv file.
     *
     * @param id The ID of the staff.
     * @param role The role of the staff.
     */
    public void writeToUserList(String id, String role) {
        String defaultPassword = "password";
        String hashedPassword = Integer.toHexString(defaultPassword.hashCode()); // Hash simulation
        String[] userRecord = {id, role, hashedPassword};

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Database/User_List.csv", true))) {
            bw.write(String.join(",", userRecord));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to user_list.csv: " + e.getMessage());
        }
    }

        /**
     * Removes a user record from the User_List.csv file.
     *
     * @param id The ID of the user to remove.
     */
    public void removeFromUserList(String id) {
        String filePath = "Database/User_List.csv";
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1);
                if (record[0].equals(id)) { // Match found
                    continue; // Skip writing this line
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error while removing user from User_List.csv: " + e.getMessage());
        }

        // Replace the original file with the updated file
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.err.println("Error replacing the original User_List.csv file.");
        }
    }

    /**
     * Checks if a given ID exists in the User_List.csv file.
     *
     * @param id The ID to check for duplicates.
     * @return True if the ID exists, false otherwise.
     */
    public boolean checkDuplicateID(String id) {
        String filePath = "Database/User_List.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",", -1); // Split by commas
                if (record[0].equals(id)) { // Check if the first column matches the ID
                    return true; // Duplicate found
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking duplicate ID in User_List.csv: " + e.getMessage());
        }
        return false; // No duplicate found
    }


}
