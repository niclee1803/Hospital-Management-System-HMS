package login;

import utility.CheckValidity;

import java.util.Scanner;

/**
 * The {@code LoginMenu} class handles the user interface for logging in as either a
 * patient or a staff member. It provides the functionality for authenticating users,
 * changing passwords upon first login, and handling user input through menus.
 *
 * <p>The class provides the following features:
 * <ul>
 *   <li>Displays the login menu, allowing users to choose between logging in as a patient or a staff member.</li>
 *   <li>Authenticates patients and staff by verifying their ID and password.</li>
 *   <li>Prompts users to change their password if they log in with the default password "password".</li>
 *   <li>Validates new passwords based on predefined criteria.</li>
 * </ul>
 *
 */
public class LoginMenu {
    private UserType userType;
    private String id;
    private boolean authenticated;
    private final LoginManager loginManager;

    /**
     * Constructs a {@code LoginMenu} instance and initializes the authentication status and login manager for handling
     * login and password management.
     */
    public LoginMenu() {
        authenticated = false;
        loginManager = new LoginManager();
    }

    /**
     * Gets the user type (Patient or Staff) for the currently logged-in user.
     * @return The user type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Gets the ID of the currently logged-in user (patient or staff).
     * @return The user ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the authetication status of the user.
     * @return {@code true} if the user is authenticated, {@code false} otherwise.
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Displays the login menu and prompts the user to select a role (Patient or Staff).
     * The user is then directed to the respective login flow.
     * @throws Exception If an error occurs during the login process
     */
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

    /**
     * Handles the login flow for patients. It promps for the patient ID and password,
     * verifies the credentials and allows password change if the user is using the default password.
     * @throws Exception If an error occurs while checking credentials or authenticating.
     */
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
            if (password.equals("password")) {
                changePasswordMenu();
            }
        } else {
            System.out.println("Incorrect password. Please try again.");
            patientLoginMenu();
        }
    }

    /**
     * Handles the login flow for staff. It promps for the staff ID and password, verifies the credentials, and allows
     * password change if the user is using the default password.
     * @throws Exception If an error occurs while checking credentials or authenticating.
     */
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

    /**
     * Prompts the user to change their password if it is their first login or if they are using the default password.
     * The new password is validated based on specific criteria and must match a confirmation password.
     */
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