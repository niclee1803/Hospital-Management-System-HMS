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
            return patient[2].equals(password);
        }
        return false;
    }

    boolean authenticateStaff(String staffID, String password) throws Exception {
        String[] staff = loginFileHandler.findStaffById(staffID);
        if (staff != null) {
            return staff[2].equals(password);
        }
        return false;
    }

    void changePassword(String id, String password) {
        loginFileHandler.updatePassword(id, password);
    }
}

