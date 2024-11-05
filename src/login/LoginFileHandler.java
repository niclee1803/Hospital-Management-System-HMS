package login;

import java.io.BufferedReader;
import java.io.FileReader;

public class LoginFileHandler {
    private static final String filePath = "Database/User_List.csv";

    // Find user by ID, returns user data as a String[] or null if not found
    public static String[] findPatientById(String patientID) throws Exception {
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

    public static String[] findStaffById(String staffID) throws Exception {
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
}

