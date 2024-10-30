package login;

import java.io.BufferedReader;
import java.io.FileReader;

public class StaffRepository {
    private final String filePath = "Database/Staff_List.csv"; // Path to your CSV file

    // Find user by ID, returns user data as a String[] or null if not found
    public String[] findUserById(String userId) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(","); // Split each line by comma
            if (userData[0].equalsIgnoreCase(userId)) {
                reader.close();
                return userData; // Return user data if found
            }
        }
        reader.close();
        return null; // Return null if user not found
    }

    // Validate user credentials by checking ID and password
    public boolean validateCredentials(String userId, String password) throws Exception {
        String[] user = findUserById(userId);
        if (user != null) {
            return user[1].equals(password); // Check if the password matches
        }
        return false; // Return false if user not found or password doesn't match
    }
}
