package login;

public class LoginManager {
    public static boolean checkPatientExists(String patientID) throws Exception {
        return (LoginFileHandler.findPatientById(patientID) != null);
    }

    public static boolean checkStaffExists(String staffID) throws Exception {
        return (LoginFileHandler.findStaffById(staffID) != null);
    }

    public static boolean authenticatePatient(String patientID, String password) throws Exception {
        String[] patient = LoginFileHandler.findPatientById(patientID);
        if (patient != null) {
            return patient[2].equals(password);
        }
        return false;
    }

    public static boolean authenticateStaff(String staffID, String password) throws Exception {
        String[] staff = LoginFileHandler.findStaffById(staffID);
        if (staff != null) {
            return staff[2].equals(password);
        }
        return false;
    }
}
