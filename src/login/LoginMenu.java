package login;

import java.util.Scanner;

public class LoginMenu {
    private UserType userType;
    private String id;
    private boolean authenticated;

    public LoginMenu() {
        authenticated = false;
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
        } else if (!LoginManager.checkPatientExists(patientID)) {
            System.out.println("Invalid Patient ID. PLease try again.");
            patientLoginMenu();
            return;
        }
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (LoginManager.authenticatePatient(patientID, password)) {
            id = patientID;
            authenticated = true;
            System.out.println("Successfully logged in!\n\n");
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
        } else if (!LoginManager.checkStaffExists(staffID)) {
            System.out.println("Invalid Staff ID. PLease try again.");
            staffLoginMenu();
            return;
        }
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        if (LoginManager.authenticateStaff(staffID, password)) {
            id = staffID;
            authenticated = true;
            System.out.println("Successfully logged in!\n\n");
        } else {
            System.out.println("Incorrect password. Please try again.");
            staffLoginMenu();
        }
    }
}