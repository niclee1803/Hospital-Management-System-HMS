package managers;

import entities.Gender;
import filehandlers.AdministratorFileHandler;
import filehandlers.DoctorFileHandler;
import filehandlers.PharmacistFileHandler;
import filehandlers.MedicationFileHandler;
import filehandlers.MedRequestFileHandler;
import utility.Table;

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
    private final MedInventoryManager medInventoryManager;
    private final DoctorFileHandler doctorFileHandler;
    private final PharmacistFileHandler pharmacistFileHandler;
    private final AdministratorFileHandler administratorFileHandler;
    private final MedicationFileHandler medicationFileHandler;
    private final MedRequestFileHandler medRequestFileHandler;

    public AdministratorManager() {
        this.medInventoryManager = new MedInventoryManager();
        this.doctorFileHandler = new DoctorFileHandler();
        this.pharmacistFileHandler = new PharmacistFileHandler();
        this.administratorFileHandler = new AdministratorFileHandler();
        this.medicationFileHandler = new MedicationFileHandler();
        this.medRequestFileHandler = new MedRequestFileHandler();
    }

    public Administrator createUser(String id) {
        String[] record = administratorFileHandler.readLine(id);
        if (record == null) {
            return null;
        }
        String name = record[1];
        Gender gender = Gender.valueOf(record[2].toUpperCase());
        int age = Integer.parseInt(record[3]);

        return new Administrator(id, name, gender, age); // ID, Name, Role
    }

    // Staff functions

    public void addStaff(Scanner sc) {

        String role;
        System.out.println();
        System.out.println("<< Enter x to go back to the menu >> ");

        while(true) {
            System.out.print("Enter Staff Role - (D)octor/(P)harmacist/(A)dministrator: ");
            char roleChar = sc.nextLine().toUpperCase().trim().charAt(0);
        
            if (roleChar == 'D') {
                role = "Doctor";
                break;
            } else if (roleChar == 'P') {
                role = "Pharmacist";
                break;
            } else if (roleChar == 'A') {
                role = "Administrator";
                break;
            } else if (roleChar == 'X') {
                return;
            } else {
                System.out.println("Invalid role. Please try again.");
            }
        }

        String id;
        while (true) { // Loop until a unique ID is provided
            System.out.print("Enter Staff ID: ");
            id = sc.nextLine().trim().toUpperCase();
            if (id.equalsIgnoreCase("x")){
                return;
            }
    
            // Check for duplicate ID in User_List.csv
            if (administratorFileHandler.checkDuplicateID(id)) {
                System.out.println("ID already exists. Please enter a different ID.");
            } else {
                break; // Exit the loop if ID is unique
            }
        }
    
        System.out.print("Enter Staff Name ");
        String name = sc.nextLine().trim();
        if (name.equalsIgnoreCase("x")){
            return;
        }

        String gender;
        while(true) {
            System.out.print("Enter Gender (Male/Female): ");
            gender = sc.nextLine().trim().toUpperCase();
            if (gender.equalsIgnoreCase("x")){
                return;
            }

            if (!(gender.equalsIgnoreCase("MALE") || gender.equalsIgnoreCase("FEMALE"))) {
                System.out.println("Invalid gender. Please enter either Male/Female");
            } else {
                break;
            }
        }
        int age;
        while(true) {
            System.out.print("Enter Age: ");
            String ageInput = sc.nextLine().trim();
            if (ageInput.equalsIgnoreCase("x")){
                return;
            }
            try {
                age = Integer.parseInt(ageInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter a valid number.");
            }
        }

        String[] record;

        if (role.equals("Doctor")) {
            record = new String[]{id, name, gender, String.valueOf(age), ""};
        } else {
            record = new String[]{id, name, gender, String.valueOf(age)};
        }
        // Write to respective records file
        switch(role) {
            case "Doctor"->doctorFileHandler.writeLine(record);
            case "Pharmacist"->pharmacistFileHandler.writeLine(record);
            case "Administrator"->administratorFileHandler.writeLine(record);
        }
    
        // Write to User_List.csv
        administratorFileHandler.writeToUserList(id, role);
    
        System.out.println(role + " added successfully.");
    }
    
    public void updateStaff(Scanner sc) {
        String role;
        System.out.println();
        System.out.println("<< Enter x to go back to the menu >> ");

        while(true) {
            System.out.print("Enter Staff Role - (D)octor/(P)harmacist/(A)dministrator: ");
            char roleChar = sc.nextLine().toUpperCase().trim().charAt(0);
        
            if (roleChar == 'D') {
                role = "Doctor";
                break;
            } else if (roleChar == 'P') {
                role = "Pharmacist";
                break;
            } else if (roleChar == 'A') {
                role = "Administrator";
                break;
            } else if (roleChar == 'X') {
                return;
            } else {
                System.out.println("Invalid role. Please try again.");
            }
        }

        String id;
        while(true) {
            System.out.print("Enter Staff ID to Update: ");
            id = sc.nextLine().trim().toUpperCase();
            if (id.equalsIgnoreCase("x")){
                return;
            }

            boolean exists = false;

            // Fetch existing record for validation
            switch(role) {
                case "Doctor" -> {
                    exists = doctorFileHandler.recordExists(id);
                }
                case "Pharmacist" -> {
                    exists = pharmacistFileHandler.recordExists(id);
                }
                case "Administrator" -> {
                    exists = administratorFileHandler.recordExists(id);
                }
            }

            if (!exists) {
                System.out.println("No matching record found for ID: " + id);
            } else {
                break;
            }
        }
    
        // Gather updated details
        System.out.print("Enter Updated Name: ");
        String name = sc.nextLine().trim();
        if (name.equalsIgnoreCase("x")){
            return;
        }
        
        String gender;
        while(true) {
            System.out.print("Enter Gender (Male/Female): ");
            gender = sc.nextLine().trim().toUpperCase();
            if (gender.equalsIgnoreCase("x")){
                return;
            }
            if (!(gender.equalsIgnoreCase("MALE") || gender.equalsIgnoreCase("FEMALE"))) {
                System.out.println("Invalid gender. Please enter either Male/Female");
            } else {
                break;
            }
        }

        int age;
        while(true) {
            System.out.print("Enter Updated Age: ");
            String ageInput = sc.nextLine().trim();
            if (ageInput.equalsIgnoreCase("x")){
                return;
            }
            try {
                age = Integer.parseInt(ageInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter a valid number.");
            }
        }

        String[] updatedRecord = new String[]{id, name, gender, String.valueOf(age)};
    
        // Update records file
        switch(role) {
            case "Doctor" -> {
                doctorFileHandler.updateLine(updatedRecord);
            }
            case "Pharmacist" -> {
                pharmacistFileHandler.updateLine(updatedRecord);
            }
            case "Administrator" -> {
                administratorFileHandler.updateLine(updatedRecord);
            }
        }
    
        System.out.println(role + " with ID " + id + " updated successfully.");
    }
    
    public void removeStaff(Scanner sc) {
        String role;
        System.out.println();
        System.out.println("<< Enter x to go back to the menu >> ");

        while(true) {
            System.out.print("Enter Staff Role - (D)octor/(P)harmacist/(A)dministrator: ");
            char roleChar = sc.nextLine().toUpperCase().trim().charAt(0);
        
            if (roleChar == 'D') {
                role = "Doctor";
                break;
            } else if (roleChar == 'P') {
                role = "Pharmacist";
                break;
            } else if (roleChar == 'A') {
                role = "Administrator";
                break;
            } else if (roleChar == 'X') {
                return;
            } else {
                System.out.println("Invalid role. Please try again.");
            }
        }
        String id;
        while(true) {
            System.out.print("Enter Staff ID to Remove: ");
            id = sc.nextLine().trim().toUpperCase();
            if (id.equalsIgnoreCase("x")) {
                return;
            }
        
            // Remove from the respective records file
            boolean recordRemoved = false;

            switch (role) {
                case "Doctor"->{
                    recordRemoved = doctorFileHandler.deleteLine(id);
                }
                case "Pharmacist"->{
                    recordRemoved = pharmacistFileHandler.deleteLine(id);
                }
                case "Administrator"->{
                    recordRemoved = administratorFileHandler.deleteLine(id);
                }
            }
        
            if (recordRemoved) {
                // Remove from User_List.csv
                administratorFileHandler.removeFromUserList(id);
                System.out.println(role + " with ID " + id + " removed successfully.");
                break;
            } else {
                System.out.println("No matching record found for ID: " + id);
                System.out.println("Please enter a valid ID.");
                System.out.println();
            }
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
                record[2],               // Gender
                record[3]                // Age
            });
        }

        // Print the table
        if (rows.size() > 1) { // Check if there are records besides the header
            Table.printTable(rows);
        } else {
            System.out.println("\n<< No staff details available >>\n");
        }
    }

    //Medication functions

    public void viewMedicationInventory() {
        System.out.println("Viewing medication inventory...");
        medInventoryManager.printFullInventory();
        System.out.print("Press enter to continue...\n");
        new Scanner(System.in).nextLine();
    }

    public void addMedication(Scanner sc) {
        try {

            String medicineName;
            System.out.println();
            System.out.println("<< Enter x to go back to the menu >> ");

            while(true) {
                System.out.print("Enter Medicine Name: ");
                medicineName = sc.nextLine().trim();
                if (medicineName.equalsIgnoreCase("x")) {
                    return;
                }
        
                // Read medication stock and check if the medicine exists
                List<String[]> medications = medicationFileHandler.readMedicationStock();
                boolean exists = false;
        
                for (String[] medication : medications) {
                    if (medication[0].equalsIgnoreCase(medicineName)) { // Case-insensitive check
                        exists = true;
                        break;
                    }
                }
        
                if (exists) {
                    System.out.println("Medicine with name " + medicineName + " already exists.");
                } else {
                    break;
                }
            }
            


            int lowStock;
            int initialStock;

            while(true){
                System.out.print("Enter Low Stock Level: ");
                String lowStockInput = sc.nextLine().trim();
                if (lowStockInput.equalsIgnoreCase("x")) {
                    return;
                }
                
                try {
                    lowStock = Integer.parseInt(lowStockInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid stock value. Please enter a valid number.");
                }
            }

            while(true) {
                System.out.print("Enter Initial Stock: ");
                String stockInput = sc.nextLine().trim();
                if (stockInput.equalsIgnoreCase("x")) {
                    return;
                }
                try {
                    initialStock = Integer.parseInt(stockInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid stock value. Please enter a valid number.");
                }
            }
            
            System.out.println("Enter Unit (e.g., packs, bottles):");
            String unit = sc.nextLine().trim();
    
            // Create a new medication record
            String[] newMedication = {medicineName, String.valueOf(lowStock), String.valueOf(initialStock), unit};
    
            // Write the new medication to the file
            medicationFileHandler.addMedication(newMedication);
    
            System.out.println("Medication added successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while adding medication: " + e.getMessage());
        }
    }
    
    public void updateMedicationStock(Scanner sc) {

        medInventoryManager.printFullInventory();

        try {

            String medicineName;
            System.out.println("<< Enter x to go back to the menu >> ");

            while(true) {
                System.out.print("Enter Medicine Name to Update: ");
                medicineName = sc.nextLine().trim();

                if (medicineName.equalsIgnoreCase("x")) {
                    return;
                }
        
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
                } else {
                    break;
                }
            }

            // If the medicine exists, prompt for stock value
            int newStock;

            while(true){
                System.out.print("Enter New Stock Value: ");
                String stockInput = sc.nextLine().trim();
                if (stockInput.equalsIgnoreCase("x")) {
                    return;
                }
                try {
                    newStock = Integer.parseInt(stockInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid stock value. Please enter a valid number.");
                }
            }
    
            // Update the stock in the file
            medicationFileHandler.updateMedicationStock(medicineName, newStock);
            System.out.println("Medication stock updated successfully.");
    
        } catch (IOException e) {
            System.out.println("An error occurred while updating medication stock: " + e.getMessage());
        }
    }

    public void updateLowStockAlert(Scanner sc) {

        medInventoryManager.printFullInventory();

        try {
            String medicineName;

            System.out.println("<< Enter x to go back to the menu >> ");

            while(true) {
                System.out.print("Enter Medicine Name for Low Stock Alert Update: ");
                medicineName = sc.nextLine().trim();
                if (medicineName.equalsIgnoreCase("x")) {
                    return;
                }
        
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
                } else {
                    break;
                }
            }
            
            int lowStockAlert;

            while(true) {
                System.out.print("Enter New Low Stock Alert Level: ");
                String alertInput = sc.nextLine().trim();
                if (alertInput.equalsIgnoreCase("x")) {
                    return;
                }

                try {
                    lowStockAlert = Integer.parseInt(alertInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid alert value. Please enter a valid number.");
                }
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
                    System.out.println("Approve or Reject this request? (A/R/any key to skip): ");
    
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
