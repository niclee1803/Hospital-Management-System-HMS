package Login;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class LoginManager {
    private UserType userType;
    private boolean authenticated = false;
    private String id;

    public UserType getUserType() {
        return userType;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getId() {
        return id;
    }

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
            login();
        }
    }

    private void patientLogin() throws Exception {
        System.out.println("\n<<Patient Login>>");
        System.out.println("Type (X) to return\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        String patientID = sc.nextLine().toUpperCase();
        if (patientID.equals("X")) {
            System.out.println();
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader("Database/Patient_List.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] nextLine = line.split(","); // Split by comma
            if (nextLine[0].equals(patientID)) {
                System.out.print("Enter Password: ");
                String password = sc.nextLine();
                if (nextLine[1].equals(password)) {
                    id = patientID;
                    authenticated = true;
                    System.out.println("Successfully logged in\n");
                    return;
                } else {
                    System.out.println("Incorrect password");
                    patientLogin();
                    return;
                }
            }
        }
        System.out.println("Incorrect patient ID");
        patientLogin();
    }

    private void staffLogin() throws Exception {
        System.out.println("\n<<Staff Login>>");
        System.out.println("Type (X) to return\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Staff ID: ");
        String staffID = sc.nextLine().toUpperCase();
        if (staffID.equals("X")) {
            System.out.println();
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader("Database/Staff_List.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] nextLine = line.split(","); // Split by comma
            if (nextLine[0].equals(staffID)) {
                System.out.print("Enter Password: ");
                String password = sc.nextLine();
                if (nextLine[1].equals(password)) {
                    id = staffID;
                    authenticated = true;
                    System.out.println("Successfully logged in\n");
                    return;
                } else {
                    System.out.println("Incorrect password");
                    staffLogin();
                    return;
                }
            }
        }
        System.out.println("Incorrect staff ID");
        staffLogin();
    }
}
