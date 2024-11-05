package login;

import java.io.*;

public class LoginFileHandler {
    private final String filePath = "Database/User_List.csv";

    // Find user by ID, returns user data as a String[] or null if not found
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

