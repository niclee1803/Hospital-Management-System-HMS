package usermenus;
import java.util.List;
import java.util.Scanner;

import filehandlers.MedicationFileHandler;
import managers.AppointmentManager;
import managers.MedInventoryManager;
import managers.MedRequestManager;
import entities.*;


import java.io.IOException;
import managers.PharmacistManager;

/**
 * {@code PharmacistMenu} class provides the user interface for a pharmacist.
 * It allows the pharmacist to view appointment outcome records, update prescription statuses,
 * view the medication inventory, and submit replenishment requests.
 * This class implements the {@link IUserMenu} interface to manage the pharmacist's main menu and functionalities.
 */
public class PharmacistMenu implements IUserMenu {
    private MedInventoryManager inventoryManager;
    private MedRequestManager requestManager;
    private PharmacistManager pharmacistmanager;
    private Pharmacist pharmacist;
    private Scanner sc;

    /**
     * Constructs a {@code PharmacistMenu} object for the specified pharmacist.
     * Initialises the menu with required managers.
     * @param pharmacistID the ID of the pharmacist to load and manage.
     */
    public PharmacistMenu(String pharmacistID) {
        this.inventoryManager = new MedInventoryManager();
        this.requestManager = new MedRequestManager();
        this.pharmacistmanager = new PharmacistManager(); // Assuming this is a valid instance
        this.pharmacist = (Pharmacist) pharmacistmanager.createUser(pharmacistID);
        this.sc = new Scanner(System.in);
    }

    /**
     * Displays the main menu for the pharmacist and handles user input for navigating through the options.
     * Depending on the user's selection, it invokes methods for viewing appointment records, updating prescription
     * status, managing the inventory, and submitting replenishment requests.
     */
    @Override
    public void mainMenu() throws Exception{

        AppointmentManager apptManager = new AppointmentManager();

        while (true) {
            // Displaying the pharmacist menu in the specified format
            printChoices();
            System.out.print("Enter your selection: ");
            //Read user choice
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }
            System.out.println();
            switch (choice) {
                case 1:
                    // Placeholder for View Appointment Outcome Record
                    apptManager.pharmacistViewAppointmentOutcome();
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 2:
                    // Placeholder for Update Prescription Status
                    apptManager.pharmacistUpdatePrescriptionStatus();
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 3:
                    // View medication inventory
                    viewMedicationInventory();
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 4:
                    // Submit a replenishment request
                    submitReplenishmentRequest();
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 5:
                    // Logout (exit the loop)
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Prints the choices for the main menu, showing options available to the Pharmacist.
     */
    private void printChoices() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.printf("║ %28s %-28s ║%n", "Welcome, Pharmacist", pharmacist.getName() + "!");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ (1) View Appointment Outcome Record                       ║");
        System.out.println("║ (2) Update Prescription Status                            ║");
        System.out.println("║ (3) View Medication Inventory                             ║");
        System.out.println("║ (4) Submit Replenishment Request                          ║");
        System.out.println("║ (5) Logout                                                ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Displays the full medication inventory
     */
    private void viewMedicationInventory() {
        System.out.println("Viewing medication inventory...");
        inventoryManager.printFullInventory();
    }

    /**
     * Allows the pharmacist to submit a replenishment request for a medication.
     * Takes inputs for the medicine name, amount, unit and status and passes this data to the {@link MedRequestManager}
     * to create a new request
     */
    private void submitReplenishmentRequest() throws Exception {

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
            MedicationFileHandler medFileHandler = new MedicationFileHandler();
            List<String[]> medications = medFileHandler.readMedicationStock();
            boolean exists = false;
    
            for (String[] medication : medications) {
                if (medication[0].equalsIgnoreCase(medicineName)) { // Case-insensitive check
                    exists = true;
                    break;
                }
            }
    
            if (!exists) {
                System.out.println("Medicine with name " + medicineName + " does not exist.");
            } else {
                break;
            }
        }

        int amount;

        while(true){
            System.out.print("Enter Amount Requested: ");
            String amountInput = sc.nextLine().trim();
            if (amountInput.equalsIgnoreCase("x")) {
                return;
            }
            try {
                amount = Integer.parseInt(amountInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock value. Please enter a valid number.");
            }
        }
  

        System.out.print("Enter Unit (e.g., tablets, bottles): ");
        String unit = sc.next();
        if (unit.equalsIgnoreCase("x")) {
            return;
        }

        System.out.println();

        // System.out.print("Enter Status (e.g., Pending, Approved): ");
        // String status = scanner.next();
        // System.out.println();


        try {
            requestManager.createNewRequest(medicineName, amount, unit, "Pending");
        } catch (IOException e) {
            System.err.println("Error submitting replenishment request: " + e.getMessage());
        }
    }

    /**
     * Displays the outcome records for completed appointments of a specific patient.
     * Prompts the pharmacist for a patient ID and then calls {@link appointmentController#patientPrintAppointmentRecords(String)}
     * to display the records.
     */
    // private void viewAppointmentOutcomeRecord() {
    //     try {
    //         System.out.print("Enter Patient ID to view completed appointments: ");
    //         String patientID = scanner.nextLine();

    //         // Calling the static method from appointmentController
    //         appointmentController.patientPrintAppointmentRecords(patientID);
    //     } catch (Exception e) {
    //         System.err.println("An error occurred while retrieving appointment records: " + e.getMessage());
    //     }
    // }

}
