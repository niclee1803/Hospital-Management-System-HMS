package managers;

import filehandlers.AdministratorFileHandler;
import filehandlers.DoctorFileHandler;
import filehandlers.PharmacistFileHandler;
import utility.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Administrator;

/**
 * The {@code AdministratorManager} class manages administrator-related actions,
 * including managing other staff members like doctors and pharmacists.
 */
public class AdministratorManager {
    private final DoctorFileHandler doctorFileHandler = new DoctorFileHandler();
    private final PharmacistFileHandler pharmacistFileHandler = new PharmacistFileHandler();
    private final AdministratorFileHandler administratorFileHandler = new AdministratorFileHandler();

    public Administrator createUser(String id) {
    String[] record = administratorFileHandler.readLine(id);
    if (record == null) {
        return null;
    }
    return new Administrator(record[0], record[1], record[2]); // ID, Name, Role
}


    /**
     * Handles the staff management menu and routes to specific actions.
     *
     * @param sc Scanner object for user input.
     */
    public void manageStaff(Scanner sc) {
        System.out.println("Choose an action:");
        System.out.println("1. Add Staff");
        System.out.println("2. Update Staff");
        System.out.println("3. Remove Staff");
        System.out.println("4. View Staff");
        System.out.println("X. Return to Main Menu");

        String action = sc.nextLine().trim().toUpperCase();
        switch (action) {
            case "1":
                addStaff(sc);
                break;
            case "2":
                updateStaff(sc);
                break;
            case "3":
                removeStaff(sc);
                break;
            case "4":
                viewStaff();
                break;
            case "X":
                System.out.println("Returning to main menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    public void addStaff(Scanner sc) {
        System.out.println("Enter Staff Role - (D)octor/(P)harmacist/(A)dministrator: ");
        char roleChar = sc.nextLine().toUpperCase().trim().charAt(0);
    
        String role;
        if (roleChar == 'D') {
            role = "Doctor";
        } else if (roleChar == 'P') {
            role = "Pharmacist";
        } else if (roleChar == 'A') {
            role = "Administrator";
        } else {
            System.out.println("Invalid role. Please try again.");
            return; // Exit the method early if role is invalid
        }
    
        System.out.println("Enter Staff ID:");
        String id = sc.nextLine().trim();
        System.out.println("Enter Staff Name:");
        String name = sc.nextLine().trim();
        System.out.println("Enter Gender (Male/Female):");
        String gender = sc.nextLine().trim();
        System.out.println("Enter Age:");
        int age;
        try {
            age = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Please enter a valid number.");
            return;
        }
    
        // Create the record with the proper role
        String[] record = {id, name, role, gender, String.valueOf(age)};
    
        // Write the record to the appropriate file
        if (role.equals("Doctor")) {
            doctorFileHandler.writeLine(record);
            System.out.println("Doctor added successfully.");
        } else if (role.equals("Pharmacist")) {
            pharmacistFileHandler.writeLine(record);
            System.out.println("Pharmacist added successfully.");
        } else if (role.equals("Administrator")) {
            administratorFileHandler.writeLine(record);
            System.out.println("Administrator added successfully.");
        }
    
        // Update User_List.csv with default password
        String defaultPassword = "password";
        String hashedPassword = hashPassword(defaultPassword); // Optional: Hash the password
        String[] userRecord = {id, role, hashedPassword};
        administratorFileHandler.writeLine(userRecord);
        System.out.println("User_List.csv updated with default password.");
    }
    
    // Optional: Simulate password hashing (can use a proper library if required)
    private String hashPassword(String password) {
        // Simple hash simulation (replace with real hashing like BCrypt if needed)
        return Integer.toHexString(password.hashCode());
    }
    
    

    public void updateStaff(Scanner sc) {
        System.out.println("Enter Staff Role (Doctor/Pharmacist/Administrator):");
        String role = sc.nextLine().trim().toLowerCase();
        System.out.println("Enter Staff ID to Update:");
        String id = sc.nextLine().trim();

        System.out.println("Enter New Name:");
        String newName = sc.nextLine().trim();

        String[] updatedRecord = {id, newName, role};
        if (role.equals("doctor")) {
            doctorFileHandler.updateLine(updatedRecord);
            System.out.println("Doctor updated successfully.");
        } else if (role.equals("pharmacist")) {
            pharmacistFileHandler.updateLine(updatedRecord);
            System.out.println("Pharmacist updated successfully.");
        } else if (role.equals("administrator")) {
            administratorFileHandler.updateLine(updatedRecord);
            System.out.println("Administrator updated successfully.");
        } else {
            System.out.println("Invalid role. Please try again.");
        }
    }

    public void removeStaff(Scanner sc) {
        System.out.println("Enter Staff Role (Doctor/Pharmacist/Administrator):");
        String role = sc.nextLine().trim().toLowerCase();
        System.out.println("Enter Staff ID to Remove:");
        String id = sc.nextLine().trim();

        if (role.equals("doctor")) {
            doctorFileHandler.deleteLine(id);
            System.out.println("Doctor removed successfully.");
        } else if (role.equals("pharmacist")) {
            pharmacistFileHandler.deleteLine(id);
            System.out.println("Pharmacist removed successfully.");
        } else if (role.equals("administrator")) {
            administratorFileHandler.deleteLine(id);
            System.out.println("Administrator removed successfully.");
        } else {
            System.out.println("Invalid role. Please try again.");
        }
    }

    public void viewStaff() {
        System.out.println("<< View Staff Details >>");

        // Define headers for the table
        String[] headers = {
            "ID", "Name", "Role", "Gender", "Age"
        };

        // Prepare the rows for the table
        List<String[]> rows = new ArrayList<>();
        rows.add(headers); // Add the header row first

        // Add Doctor records to the table
        List<String[]> doctorRecords = doctorFileHandler.readAllLines();
        for (String[] record : doctorRecords) {
            rows.add(new String[]{
                record[0],               // ID
                record[1],               // Name
                "Doctor",                // Role
                record[2],               // Gender
                record[3]                // Age
            });
        }

        // Add Pharmacist records to the table
        List<String[]> pharmacistRecords = pharmacistFileHandler.readAllLines();
        for (String[] record : pharmacistRecords) {
            rows.add(new String[]{
                record[0],               // ID
                record[1],               // Name
                "Pharmacist",            // Role
                record[2],               // Gender
                record[3]                // Age
            });
        }

        // Add Administrator records to the table
        List<String[]> adminRecords = administratorFileHandler.readAllLines();
        for (String[] record : adminRecords) {
            rows.add(new String[]{
                record[0],               // ID
                record[1],               // Name
                "Administrator",         // Role
                record[3],               // Gender
                record[4]                // Age
            });
        }

        // Print the table
        if (rows.size() > 1) { // Check if there are records besides the header
            table.printTable(rows);
        } else {
            System.out.println("\n<< No staff details available >>\n");
        }
    }

    
}
