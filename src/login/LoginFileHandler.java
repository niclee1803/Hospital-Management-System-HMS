package login;

import java.io.*;

/**
 * The {@code LoginFileHandler} class is responsible for handling user authentication and file operations related to user
 * data stored in a CSV file. The class allows for finding users (both patients and staff) by their unique IDs and updating
 * user passwords in the "Database/User_List.csv" file.
 * <p>It includes methods to:
 * <ul>
 *   <li>Find a patient by their ID.</li>
 *   <li>Find staff by their ID.</li>
 *   <li>Update the password of a user by their ID.</li>
 * </ul>
 */
public class LoginFileHandler {
    /**
     * The file path for the user list CSV
     */
    private final String filePath = "Database/User_List.csv";

    /**
     * Finds a patient by their unique ID in the user list CSV file.
     *
     * <p>This method searches for a record in the file where the user type is "Patient"
     * and the ID matches the provided {@code patientID}. If found, the user data is returned
     * as a {@code String[]} containing the user information. If the user is not found,
     * {@code null} is returned.
     *
     * @param patientID The unique ID of the patient to be searched.
     * @return A {@code String[]} representing the user data if found, or {@code null} if not.
     * @throws Exception If an error occurs while reading the file.
     */
    String[] findPatientById(String patientID) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData[1].equalsIgnoreCase("Patient") && userData[0].equalsIgnoreCase(patientID)) {
                reader.close();
                return userData; // Return user data if found
            }
        }
        reader.close();
        return null; // Return null if user not found
    }

    /**
     * Finds staff by their unique ID in the user list CSV file.
     *
     * <p>This method searches for a record in the file where the user type is not "Patient"
     * and the ID matches the provided {@code staffID}. If found, the user data is returned
     * as a {@code String[]} containing the user information. If the user is not found,
     * {@code null} is returned.
     *
     * @param staffID The unique ID of the staff member to be searched.
     * @return A {@code String[]} representing the user data if found, or {@code null} if not.
     * @throws Exception If an error occurs while reading the file.
     */
    String[] findStaffById(String staffID) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (!userData[1].equalsIgnoreCase("Patient") && userData[0].equalsIgnoreCase(staffID)) {
                reader.close();
                return userData; // Return user data if found
            }
        }
        reader.close();
        return null; // Return null if user not found
    }

    /**
     * Updates the password for a user with the specified ID in the user list CSV file.
     *
     * <p>This method reads through the file, locates the user with the given ID, and updates
     * their password. If the user is found, the password is updated and the changes are saved
     * to a temporary file. The original file is replaced by the updated temporary file.
     *
     * @param id The ID of the user whose password needs to be updated.
     * @param newPassword The new password to set for the user.
     */
    void updatePassword(String id, String newPassword) {
        File inputFile = new File(filePath);
        File tempFile = new File("Database/temp.csv");

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            boolean updated = false;

            while ((line = br.readLine()) != null) {
                String[] currentRecord = line.split(",");
                // Check if the current line has the same ID as the record we want to update
                if (currentRecord.length > 2 && currentRecord[0].equals(id)) {
                    // Update the password in the record
                    currentRecord[2] = newPassword;
                    updated = true;
                }
                // Write the updated or original line to the temp file
                bw.write(String.join(",", currentRecord));
                bw.newLine();
            }

            // If the ID was not found, print an error message (optional)
            if (!updated) {
                System.out.println("User ID " + id + " not found.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while updating the CSV file: " + e.getMessage());
        }

        // Replace the original file with the updated temp file
        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.err.println("Could not rename temp file to original file.");
            }
        } else {
            System.err.println("Could not delete the original file.");
        }
    }
}

