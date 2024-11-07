package login;

import utility.CheckValidity;

import java.util.Scanner;

public class LoginMenu {
    private UserType userType;
    private String id;
    private boolean authenticated;
    private final LoginManager loginManager;

    public LoginMenu() {
        authenticated = false;
        loginManager = new LoginManager();
    }

    public UserType getUserType() {
        return userType;
    }

    public String getId() {
        return id;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void displayMenu() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your role - (P)atient/(S)taff: ");
        char role = sc.nextLine().toUpperCase().charAt(0);

        if (role == 'P') {
            userType = UserType.PATIENT;
            patientLoginMenu();
        } else if (role == 'S') {
            userType = UserType.STAFF;
            staffLoginMenu();
        } else {
            System.out.println("Invalid role. Please enter 'P' for Patient or 'S' for Staff.");
            displayMenu(); // Retry login for invalid input
        }
    }

    private void patientLoginMenu() throws Exception {
        System.out.println("\n<<Patient Login>>");
        System.out.println("Type (X) to return\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        String patientID = sc.nextLine().toUpperCase();

        if (patientID.equals("X")) {
            System.out.println();
            return; // Return to the previous menu
        } else if (!loginManager.checkPatientExists(patientID)) {
            System.out.println("Invalid Patient ID. PLease try again.");
            patientLoginMenu();
            return;
        }
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (loginManager.authenticatePatient(patientID, password)) {
            id = patientID;
            authenticated = true;
            System.out.println("Successfully logged in!\n");
            if (password.equals("default1234")) {
                changePasswordMenu();
            }
        } else {
            System.out.println("Incorrect password. Please try again.");
            patientLoginMenu();
        }
    }

    private void staffLoginMenu() throws Exception {
        System.out.println("\n<<Staff Login>>");
        System.out.println("Type (X) to return\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Staff ID: ");
        String staffID = sc.nextLine().toUpperCase();

        if (staffID.equals("X")) {
            System.out.println();
            return; // Return to the previous menu
        } else if (!loginManager.checkStaffExists(staffID)) {
            System.out.println("Invalid Staff ID. PLease try again.");
            staffLoginMenu();
            return;
        }
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (loginManager.authenticateStaff(staffID, password)) {
            id = staffID;
            authenticated = true;
            System.out.println("Successfully logged in!\n");
            if (password.equals("password")) {
                changePasswordMenu();
            }
        } else {
            System.out.println("Incorrect password. Please try again.");
            staffLoginMenu();
        }
    }

    private void changePasswordMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Initial Login - Please change password\n");
        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();
        if (!CheckValidity.isValidPassword(newPassword)) {
            System.out.println("Password too weak! (At least 8 characters, 1 letter, 1 number) Please try again.");
            changePasswordMenu();
            return;
        }
        System.out.print("Confirm new password: ");
        String confirmPassword = sc.nextLine();
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("The 2 passwords you entered do not match. Please try again.");
            changePasswordMenu();
            return;
        }
        loginManager.changePassword(id, newPassword);
        System.out.println("Password successfully changed!\n");
    }
}