package filehandlers;

import login.LoginManager;

import java.io.*;

/**
 * Handles file operations for administrator records.
 * Extends the {@link UserFileHandler} class to provide specific functionality for administrators.
 */
public class AdministratorFileHandler extends UserFileHandler {

    /**
     * Constructs an {@code AdministratorFileHandler} object to manage "Administrator_Records.csv".
     */
    public AdministratorFileHandler() {
        super("Database/Administrator_Records.csv");
    }

    /**
     * Writes a new user to the user_list.csv file.
     *
     * @param id The ID of the staff.
     * @param role The role of the staff.
     */
    public void writeToUserList(String id, String role) {
        String defaultPassword = "password";
        String hashedPassword = LoginManager.hashWith256(defaultPassword); // Hash simulation
        String[] userRecord = {id, role, hashedPassword};

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Database/User_List.csv", true))) {
            bw.write(String.join(",", userRecord));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to user_list.csv: " + e.getMessage());
        }
    }

    /**
     * * Removes a user record from the User_List.csv file.
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
