package login;

import java.security.MessageDigest;

public class LoginManager {
    private final LoginFileHandler loginFileHandler;

    LoginManager() {
        loginFileHandler = new LoginFileHandler();
    }

    boolean checkPatientExists(String patientID) throws Exception {
        return (loginFileHandler.findPatientById(patientID) != null);
    }

    boolean checkStaffExists(String staffID) throws Exception {
        return (loginFileHandler.findStaffById(staffID) != null);
    }

    boolean authenticatePatient(String patientID, String password) throws Exception {
        String[] patient = loginFileHandler.findPatientById(patientID);
        if (patient != null) {
            return patient[2].equals(hashWith256(password));
        }
        return false;
    }

    boolean authenticateStaff(String staffID, String password) throws Exception {
        String[] staff = loginFileHandler.findStaffById(staffID);
        if (staff != null) {
            return staff[2].equals(hashWith256(password));
        }
        return false;
    }

    void changePassword(String id, String password) {
        loginFileHandler.updatePassword(id, hashWith256(password));
    }

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

