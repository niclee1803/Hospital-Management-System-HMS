package login;

import java.security.MessageDigest;

/**
 * The {@code LoginManager} class handles user authentication and password management
 * for patients and staff. It interacts with the {@code LoginFileHandler} to verify
 * the existence of users, authenticate them using hashed passwords, and allow users
 * to change their passwords securely.
 *
 * <p>It includes methods to:
 * <ul>
 *   <li>Check if a patient exists by their ID.</li>
 *   <li>Check if a staff member exists by their ID.</li>
 *   <li>Authenticate a patient using their ID and password.</li>
 *   <li>Authenticate a staff member using their ID and password.</li>
 *   <li>Change the password for a user (patient or staff).</li>
 * </ul>
 */
public class LoginManager {
    private final LoginFileHandler loginFileHandler;

    /**
     * Constructs a {@code LoginManager} instance, initializing the {@code LoginFileHandler} to manage file operations
     * for user records.
     */
    LoginManager() {
        loginFileHandler = new LoginFileHandler();
    }

    /**
     * Checks if a patient exists in the user list by their unique ID
     * @param patientID The ID of the patient to be checked.
     * @return {@code true} if the patient exists, {@code false} otheriwse
     * @throws Exception If an error occurs while reading the file
     */
    boolean checkPatientExists(String patientID) throws Exception {
        return (loginFileHandler.findPatientById(patientID) != null);
    }

    /**
     * Checks if a staff member exists in the user list by their unique ID
     * @param staffID The ID of the staff member to be checked.
     * @return {@code true} if the staff exists, {@code false} otherwise
     * @throws Exception If an error occurs while reading the file
     */
    boolean checkStaffExists(String staffID) throws Exception {
        return (loginFileHandler.findStaffById(staffID) != null);
    }

    /**
     * Authenticates a patient by comparing the provided password with the stored hashed password.
     * @param patientID The ID of the patient to be authenticated.
     * @param password The password provided by the patient
     * @return {@code true} of the authentication is successful, {@code false} otherwise
     * @throws Exception If an error occurs while reading the file
     */
    boolean authenticatePatient(String patientID, String password) throws Exception {
        String[] patient = loginFileHandler.findPatientById(patientID);
        if (patient != null) {
            return patient[2].equals(hashWith256(password));
        }
        return false;
    }

    /**
     * Autheticates a staff member by comparing the provided password with the stored hashed password.
     * @param staffID The ID of the staff member to be autheticated.
     * @param password The password provided by the staff member.
     * @return {@code true} if authentication is successful, {@code false} otherwise
     * @throws Exception If an error occurs while reading the file
     */
    boolean authenticateStaff(String staffID, String password) throws Exception {
        String[] staff = loginFileHandler.findStaffById(staffID);
        if (staff != null) {
            return staff[2].equals(hashWith256(password));
        }
        return false;
    }

    /**
     * Changes the password for a user by updating the password in the user list.
     * @param id The ID of the user whose password is to be changed.
     * @param password Thew new password to set for the user.
     */
    void changePassword(String id, String password) {
        loginFileHandler.updatePassword(id, hashWith256(password));
    }

    /**
     * Hashes a password using the SHA-526 algorithm
     * @param password The plain-text password to be hashed.
     * @return The hashed password as a hexadecimal strong.
     * @throws RuntimeException If an error occurs during the hashing process
     */
    String hashWith256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

