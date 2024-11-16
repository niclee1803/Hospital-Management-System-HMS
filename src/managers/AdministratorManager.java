package managers;

import filehandlers.AdministratorFileHandler;
import filehandlers.DoctorFileHandler;
import filehandlers.PharmacistFileHandler;
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

        String action = sc.nextLine().toUpperCase();
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
        System.out.println("Enter Staff Role (Doctor/Pharmacist/Administrator):");
        String role = sc.nextLine().toLowerCase();
        System.out.println("Enter Staff ID:");
        String id = sc.nextLine();
        System.out.println("Enter Staff Name:");
        String name = sc.nextLine();

        String[] record = {id, name, role};
        if (role.equals("doctor")) {
            doctorFileHandler.writeLine(record);
            System.out.println("Doctor added successfully.");
        } else if (role.equals("pharmacist")) {
            pharmacistFileHandler.writeLine(record);
            System.out.println("Pharmacist added successfully.");
        } else if (role.equals("administrator")) {
            administratorFileHandler.writeLine(record);
            System.out.println("Administrator added successfully.");
        } else {
            System.out.println("Invalid role. Please try again.");
        }
    }

    public void updateStaff(Scanner sc) {
        System.out.println("Enter Staff Role (Doctor/Pharmacist/Administrator):");
        String role = sc.nextLine().toLowerCase();
        System.out.println("Enter Staff ID to Update:");
        String id = sc.nextLine();

        System.out.println("Enter New Name:");
        String newName = sc.nextLine();

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
        String role = sc.nextLine().toLowerCase();
        System.out.println("Enter Staff ID to Remove:");
        String id = sc.nextLine();

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
        System.out.println("Doctor:");
        List<String[]> doctorRecords = doctorFileHandler.readAllLines();
        for (String[] record : doctorRecords) {
            System.out.println("ID: " + record[0] + ", Name: " + record[1]);
        }

        System.out.println("\nPharmacist:");
        List<String[]> pharmacistRecords = pharmacistFileHandler.readAllLines();
        for (String[] record : pharmacistRecords) {
            System.out.println("ID: " + record[0] + ", Name: " + record[1]);
        }

        System.out.println("\nAdministrator:");
        List<String[]> adminRecords = administratorFileHandler.readAllLines();
        for (String[] record : adminRecords) {
            System.out.println("ID: " + record[0] + ", Name: " + record[1]);
        }
    }
}
