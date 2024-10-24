package Login;

import Managers.PatientRecordManager;

import java.util.Scanner;

public class LoginManager {
    private UserType userType;
    private boolean authenticated = false;
    private String id;
    private final StaffRepository staffRepository = new StaffRepository();
    private final PatientRepository patientRepository = new PatientRepository();

    public UserType getUserType() {
        return userType;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getId() {
        return id;
    }

    // Handles the login process based on user role
    public void login() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your role - (P)atient/(S)taff: ");
        char role = sc.nextLine().toUpperCase().charAt(0);

        if (role == 'P') {
            userType = UserType.PATIENT;
            patientLogin();
        } else if (role == 'S') {
            userType = UserType.STAFF;
            staffLogin();
        } else {
            System.out.println("Invalid role. Please enter 'P' for Patient or 'S' for Staff.");
            login(); // Retry login for invalid input
        }
    }

    // Handles patient login
    private void patientLogin() throws Exception {
        System.out.println("\n<<Patient Login>>");
        System.out.println("Type (X) to return\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        String patientID = sc.nextLine().toUpperCase();

        if (patientID.equals("X")) {
            System.out.println();
            return; // Return to the previous menu
        }

        String password = promptForPassword(); // Get password input

        if (patientRepository.validateCredentials(patientID, password)) {
            id = patientID;
            authenticated = true;
            userType = UserType.PATIENT;
            System.out.println("Successfully logged in\n");
        } else {
            System.out.println("Incorrect credentials. Please try again.");
            patientLogin(); // Retry login if credentials are incorrect
        }
    }

    // Handles staff login
    private void staffLogin() throws Exception {
        System.out.println("\n<<Staff Login>>");
        System.out.println("Type (X) to return\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Staff ID: ");
        String staffID = sc.nextLine().toUpperCase();

        if (staffID.equals("X")) {
            System.out.println();
            return; // Return to the previous menu
        }

        String password = promptForPassword(); // Get password input

        if (staffRepository.validateCredentials(staffID, password)) {
            id = staffID;
            authenticated = true;
            userType = UserType.STAFF;
            System.out.println("Successfully logged in\n");
        } else {
            System.out.println("Incorrect credentials. Please try again.");
            staffLogin(); // Retry login if credentials are incorrect
        }
    }

    // Helper method to prompt for the password
    private String promptForPassword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Password: ");
        return sc.nextLine();
    }
}
