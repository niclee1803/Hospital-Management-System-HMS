package managers;

import filehandlers.AdministratorFileHandler;
import filehandlers.DoctorFileHandler;
import filehandlers.PharmacistFileHandler;
import filehandlers.MedicationFileHandler;
import filehandlers.MedRequestFileHandler;
import utility.table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private final MedicationFileHandler medicationFileHandler = new MedicationFileHandler();
    private final MedRequestFileHandler medRequestFileHandler = new MedRequestFileHandler();

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
    
        String id;
        while (true) { // Loop until a unique ID is provided
            System.out.println("Enter Staff ID:");
            id = sc.nextLine().trim();
    
            // Check for duplicate ID in User_List.csv
            if (administratorFileHandler.checkDuplicateID(id)) {
                System.out.println("ID already exists. Please enter a different ID.");
            } else {
                break; // Exit the loop if ID is unique
            }
        }
    
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
    
        String[] record;
    
        // If the role is Administrator, prompt for an additional Role field
        if (role.equals("Administrator")) {
            System.out.println("Enter Administrator Role (e.g., CEO, Manager):");
            String adminRole = sc.nextLine().trim();
            record = new String[]{id, name, adminRole, gender, String.valueOf(age)};
        } else {
            record = new String[]{id, name, gender, String.valueOf(age)};
        }
    
        // Write to respective records file
        administratorFileHandler.writeStaffRecord(record, role);
    
        // Write to User_List.csv
        administratorFileHandler.writeToUserList(id, role);
    
        System.out.println(role + " added successfully.");
    }
    

    public void updateStaff(Scanner sc) {
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
    
        System.out.println("Enter Staff ID to Update:");
        String id = sc.nextLine().trim();
    
        // Fetch existing record for validation
        boolean exists = administratorFileHandler.recordExists(id, role);
        if (!exists) {
            System.out.println("No matching record found for ID: " + id);
            return;
        }
    
        // Gather updated details
        System.out.println("Enter Updated Name:");
        String name = sc.nextLine().trim();
    
        System.out.println("Enter Updated Gender (Male/Female):");
        String gender = sc.nextLine().trim();
    
        System.out.println("Enter Updated Age:");
        int age;
        try {
            age = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Please enter a valid number.");
            return;
        }
    
        String[] updatedRecord;
    
        // Additional prompt if the role is Administrator
        if (role.equals("Administrator")) {
            System.out.println("Enter Updated Administrator Role (e.g., CEO, Manager):");
            String adminRole = sc.nextLine().trim();
            updatedRecord = new String[]{id, name, adminRole, gender, String.valueOf(age)};
        } else {
            updatedRecord = new String[]{id, name, gender, String.valueOf(age)};
        }
    
        // Update records file
        administratorFileHandler.updateStaffRecord(updatedRecord, role);
    
        System.out.println(role + " with ID " + id + " updated successfully.");
    }
    
    public void removeStaff(Scanner sc) {
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
    
        System.out.println("Enter Staff ID to Remove:");
        String id = sc.nextLine().trim();
    
        // Remove from the respective records file
        boolean recordRemoved = administratorFileHandler.removeStaffRecord(id, role);
    
        if (recordRemoved) {
            // Remove from User_List.csv
            administratorFileHandler.removeFromUserList(id);
            System.out.println(role + " with ID " + id + " removed successfully.");
        } else {
            System.out.println("No matching record found for ID: " + id);
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

    public void viewMedicationInventory() {
        try {
            List<String[]> medications = medicationFileHandler.readMedicationStock();
            System.out.println("╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║                  Medication Inventory                     ║");
            System.out.println("╠═══════════════════════════════════════════════════════════╣");
            System.out.println("║ Medicine Name   | Current Stock   | Unit                 ║");
            System.out.println("╠═══════════════════════════════════════════════════════════╣");

            for (String[] medication : medications) {
                System.out.printf("║ %-15s | %-15s | %-15s ║%n", medication[0], medication[1], medication[2]);
            }

            System.out.println("╚═══════════════════════════════════════════════════════════╝");
        } catch (IOException e) {
            System.err.println("Error reading medication inventory: " + e.getMessage());
        }
    }

    public void addMedication(Scanner sc) {
        try {
            System.out.println("Enter Medicine Name:");
            String medicineName = sc.nextLine().trim();
    
            System.out.println("Enter Initial Stock:");
            String stockInput = sc.nextLine().trim();
            int initialStock;
            try {
                initialStock = Integer.parseInt(stockInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock value. Please enter a valid number.");
                return;
            }
    
            System.out.println("Enter Unit (e.g., packs, bottles):");
            String unit = sc.nextLine().trim();
    
            // Create a new medication record
            String[] newMedication = {medicineName, String.valueOf(initialStock), unit};
    
            // Write the new medication to the file
            medicationFileHandler.addMedication(newMedication);
    
            System.out.println("Medication added successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while adding medication: " + e.getMessage());
        }
    }
    
    public void updateMedicationStock(Scanner sc) {
        try {
            System.out.println("Enter Medicine Name to Update:");
            String medicineName = sc.nextLine().trim();
    
            // Read medication stock and check if the medicine exists
            List<String[]> medications = medicationFileHandler.readMedicationStock();
            boolean exists = false;
    
            for (String[] medication : medications) {
                if (medication[0].equalsIgnoreCase(medicineName)) { // Case-insensitive check
                    exists = true;
                    break;
                }
            }
    
            if (!exists) {
                System.out.println("Medicine with name " + medicineName + " not found.");
                return; // Exit if medicine does not exist
            }
    
            // If the medicine exists, prompt for stock value
            System.out.println("Enter New Stock Value:");
            String stockInput = sc.nextLine().trim();
    
            int newStock;
            try {
                newStock = Integer.parseInt(stockInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock value. Please enter a valid number.");
                return; // Exit if the stock value is invalid
            }
    
            // Update the stock in the file
            medicationFileHandler.updateMedicationStock(medicineName, newStock);
            System.out.println("Medication stock updated successfully.");
    
        } catch (IOException e) {
            System.out.println("An error occurred while updating medication stock: " + e.getMessage());
        }
    }

    public void updateLowStockAlert(Scanner sc) {
        try {
            System.out.println("Enter Medicine Name for Low Stock Alert Update:");
            String medicineName = sc.nextLine().trim();
    
            // Check if the medicine exists
            List<String[]> medications = medicationFileHandler.readMedicationStock();
            boolean exists = false;
            for (String[] medication : medications) {
                if (medication[0].equalsIgnoreCase(medicineName)) {
                    exists = true;
                    break;
                }
            }
    
            if (!exists) {
                System.out.println("Medicine with name " + medicineName + " not found.");
                return; // Exit if the medicine doesn't exist
            }
    
            System.out.println("Enter New Low Stock Alert Level:");
            String alertInput = sc.nextLine().trim();
    
            int lowStockAlert;
            try {
                lowStockAlert = Integer.parseInt(alertInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid alert value. Please enter a valid number.");
                return;
            }
    
            // Update the Low Stock Alert in the file
            boolean updated = medicationFileHandler.updateLowStockAlert(medicineName, lowStockAlert);
    
            if (updated) {
                System.out.println("Low stock alert updated successfully.");
            } else {
                System.out.println("An unexpected error occurred while updating the alert.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating low stock alert: " + e.getMessage());
        }
    }

    public void approveReplenishmentRequests() {
        try {
            // Read all requests from the file
            List<String[]> requests = medRequestFileHandler.readRequests();
            List<String[]> updatedRequests = new ArrayList<>();
    
            boolean anyPending = false;
    
            for (String[] request : requests) {
                // Check if the status is "Pending"
                if (request[3].trim().equalsIgnoreCase("Pending")) {
                    anyPending = true;
                    System.out.println("Pending Request: " +
                            "Medicine: " + request[0] +
                            ", Amount: " + request[1] +
                            ", Unit: " + request[2]);
                    System.out.println("Approve or Reject this request? (A/R): ");
    
                    Scanner sc = new Scanner(System.in);
                    String decision = sc.nextLine().trim().toUpperCase();
    
                    if (decision.equals("A")) {
                        request[3] = "Approved";
    
                        // Update stock in medication inventory
                        String medicineName = request[0];
                        int replenishAmount;
                        try {
                            replenishAmount = Integer.parseInt(request[1].trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid amount in request. Skipping update.");
                            updatedRequests.add(request);
                            continue;
                        }
    
                        boolean stockUpdated = medicationFileHandler.updateOrAddStock(medicineName, replenishAmount, request[2]);
                        if (stockUpdated) {
                            System.out.println("Request approved. Stock updated.");
                        }
                    } else if (decision.equals("R")) {
                        request[3] = "Rejected";
                        System.out.println("Request rejected.");
                    } else {
                        System.out.println("Invalid input. Request left as Pending.");
                    }
                }
                updatedRequests.add(request);
            }
    
            if (!anyPending) {
                System.out.println("No pending replenishment requests found.");
            } else {
                // Update the file with the modified requests
                medRequestFileHandler.updateRequests(updatedRequests);
                System.out.println("All requests processed.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while processing replenishment requests: " + e.getMessage());
        }
    }
    
    
    
    

}
