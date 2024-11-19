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
 * including managing other staff members like doctors and pharmacists. It provides
 * functionality for handling files and inventory related to medical staff and medication.
 */
public class AdministratorManager {
    private final MedInventoryManager medInventoryManager;
    private final DoctorFileHandler doctorFileHandler;
    private final PharmacistFileHandler pharmacistFileHandler;
    private final AdministratorFileHandler administratorFileHandler;
    private final MedicationFileHandler medicationFileHandler;
    private final MedRequestFileHandler medRequestFileHandler;

    /**
     * Constructs an {@code AdministratorManager} object, initializing the necessary
     * file handlers and managers required for administrator-related actions.
     */
    public AdministratorManager() {
        this.medInventoryManager = new MedInventoryManager();
        this.doctorFileHandler = new DoctorFileHandler();
        this.pharmacistFileHandler = new PharmacistFileHandler();
        this.administratorFileHandler = new AdministratorFileHandler();
        this.medicationFileHandler = new MedicationFileHandler();
        this.medRequestFileHandler = new MedRequestFileHandler();
    }

    /**
     * Creates an {@code Administrator} object based on the information retrieved from
     * the administrator file associated with the provided {@code id}.
     * <p>
     * The method reads the record for the given {@code id} from the administrator file
     * using the {@link AdministratorFileHandler}. If the record is not found, the method
     * returns {@code null}.
     * </p>
     * <p>
     * The retrieved record contains the following information:
     * <ul>
     *   <li>Name (String)</li>
     *   <li>Gender (String, converted to {@link Gender} enum)</li>
     *   <li>Age (int, parsed from the string)</li>
     * </ul>
     * </p>
     *
     * @param id The unique identifier for the administrator to be created.
     * @return An {@code Administrator} object if the record is found, {@code null} if
     *         no record is found for the provided {@code id}.
     */
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

    /**
     * Adds a new staff member (Doctor, Pharmacist, or Administrator) by collecting their
     * details such as role, ID, name, gender, and age through user input. The method ensures
     * that the staff ID is unique and valid before adding the new staff member to the
     * appropriate file and updating the user list.
     * <p>
     * The user is prompted to enter a staff role, and the corresponding staff information is
     * gathered in a loop to handle invalid inputs. If the user chooses to exit at any point by
     * entering "x", the method returns without making any changes. The new staff's details are
     * then written to the relevant file based on their role.
     * </p>
     *
     * @param sc The {@link Scanner} object used to capture user input from the console.
     */
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

        System.out.print("Enter Staff Name: ");
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

    /**
     * Updates the details of an existing staff member (Doctor, Pharmacist, or Administrator)
     * by prompting the user to provide a new name, gender, and age. The method validates
     * that the staff ID exists before allowing the user to update the record. If the user enters
     * "x" at any point, the operation is cancelled and no changes are made.
     * <p>
     * The user is first prompted to enter the role of the staff member to update. After selecting
     * a role, the user is asked to provide the staff ID. If the ID is valid and exists in the corresponding
     * records file, the user can update the staff details. The updated information is then saved to the
     * appropriate file.
     * </p>
     *
     * @param sc The {@link Scanner} object used to capture user input from the console.
     */
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

    /**
     * Removes a staff member (Doctor, Pharmacist, or Administrator) from the system
     * by deleting their record from the corresponding file and from the user list.
     * The user is prompted to enter the role and ID of the staff member to be removed.
     * If the ID is valid and the record exists, it is removed from the respective records file.
     * The operation is cancelled if the user enters "x" at any point.
     * <p>
     * The method first prompts the user to select a staff role (Doctor, Pharmacist, or Administrator).
     * After selecting the role, the user is asked to input the staff ID. If the ID exists, the record
     * is deleted from the corresponding file, and the user list is updated. If no matching record is found,
     * an error message is displayed, and the user is prompted to re-enter the ID.
     * </p>
     *
     * @param sc The {@link Scanner} object used to capture user input from the console.
     */
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

    /**
     * Displays the details of all staff members (Doctors, Pharmacists, and Administrators) in a tabular format.
     * The method retrieves staff records from the corresponding files and presents them in a structured table.
     * If no staff records are found, a message indicating that no staff details are available is displayed.
     * <p>
     * The method first adds the headers for the staff table (ID, Name, Role, Gender, Age). Then, it retrieves
     * records for Doctors, Pharmacists, and Administrators by reading their respective files. Each staff record
     * is formatted and added to the rows of the table. After all records are gathered, the table is printed.
     * If no staff records exist, a message is shown indicating the absence of staff data.
     * </p>
     *
     * @see Table#printTable(List) For the method used to print the table.
     */
    public void viewStaff() {
        System.out.println();
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

    /**
     * Displays the full medication inventory by invoking the {@link MedInventoryManager#printFullInventory()}
     * method to print the inventory details. After displaying the inventory, the method prompts the user
     * to press enter to continue.
     * <p>
     * This method provides a simple interface for viewing the current medication stock managed by the system.
     * It ensures that the user can review the entire inventory and proceed by pressing enter once done.
     * </p>
     */
    public void viewMedicationInventory() {
        System.out.println("Viewing medication inventory...");
        medInventoryManager.printFullInventory();
        System.out.print("Press enter to continue...\n");
        new Scanner(System.in).nextLine();
    }

    /**
     * Prompts the user to add a new medication to the inventory. The method collects the medicine name,
     * low stock level, initial stock, and unit type, ensuring that the data entered is valid. If the medication
     * already exists in the inventory, the user is notified. The new medication details are then added to the
     * medication file.
     * <p>
     * The method follows a step-by-step process:
     * 1. The user is prompted to enter the medication name, with a check for duplicates.
     * 2. The low stock level and initial stock are requested, ensuring valid numeric input.
     * 3. The user specifies the unit (e.g., packs, bottles) for the medication.
     * 4. A new medication record is created and written to the file.
     * If any input is invalid or an error occurs while adding the medication, appropriate messages are displayed.
     * </p>
     *
     * @param sc the {@link Scanner} object used to read user input.
     * @throws IOException if an error occurs while interacting with the medication data files.
     */
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
            
            System.out.print("Enter Unit (e.g., packs, bottles):");
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

    /**
     * Prompts the user to update the stock of an existing medication in the inventory. The method displays
     * the current inventory and allows the user to search for a specific medication by name. If the medication
     * exists, the user is prompted to enter a new stock value. The updated stock value is then saved to the
     * medication file.
     * <p>
     * The method follows a step-by-step process:
     * 1. Displays the current inventory of medications.
     * 2. Prompts the user to enter the name of the medication to update, with a check to ensure it exists.
     * 3. Requests the new stock value from the user and ensures it is a valid integer.
     * 4. Updates the stock of the medication in the inventory file.
     * If any input is invalid or an error occurs while updating the stock, appropriate messages are displayed.
     * </p>
     *
     * @param sc the {@link Scanner} object used to read user input.
     * @throws IOException if an error occurs while interacting with the medication data files.
     */
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

    /**
     * Prompts the user to update the low stock alert level for a specified medication in the inventory.
     * The method displays the current inventory of medications and allows the user to search for a specific
     * medication by name. If the medication exists, the user is prompted to enter a new low stock alert level.
     * The updated low stock alert level is then saved to the medication file.
     * <p>
     * The method performs the following steps:
     * 1. Displays the current inventory of medications.
     * 2. Prompts the user to enter the name of the medication for which the low stock alert is to be updated.
     * 3. Validates that the specified medication exists in the inventory.
     * 4. Requests the user to input a new low stock alert level and ensures it is a valid integer.
     * 5. Updates the low stock alert level for the specified medication in the file.
     * If any input is invalid or an error occurs while updating the low stock alert, appropriate messages are displayed.
     * </p>
     *
     * @param sc the {@link Scanner} object used to read user input.
     * @throws IOException if an error occurs while interacting with the medication data files.
     */
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

    /**
     * Processes replenishment requests by reviewing each pending request, allowing the user to approve or reject the request.
     * If a request is approved, the corresponding medication stock is updated based on the requested amount.
     * The method reads all replenishment requests, and for each pending request, the user is prompted to either approve, reject, or skip the request.
     * After processing all requests, the updated request list is written back to the request file.
     */
    public void approveReplenishmentRequests() {
        try {
            // Read all requests from the file
            List<String[]> requests = medRequestFileHandler.readRequests();
            List<String[]> updatedRequests = new ArrayList<>();
    
            boolean anyPending = false;
            System.out.println("<< Enter x to go back to the menu >> ");
    
            for (String[] request : requests) {
                // Check if the status is "Pending"


                if (request[3].trim().equalsIgnoreCase("Pending")) {
                    anyPending = true;
                    System.out.println();
                    System.out.println("Pending Request");
                    System.out.println("Medicine: " + request[0] +
                            ", Amount: " + request[1] +
                            ", Unit: " + request[2]);
                    System.out.print("Approve or Reject this request? (A/R/any key to skip): ");
    
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
                    } else if (decision.equals("X")) {
                        return;
                    } else {
                        System.out.println("Request left as Pending.");
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
