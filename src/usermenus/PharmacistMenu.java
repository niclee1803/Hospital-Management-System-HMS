package usermenus;
import java.util.Scanner;

import managers.MedInventoryManager;
import managers.MedRequestManager;
import managers.PatientManager;
import entities.*;

import java.io.IOException;
import managers.PharmacistManager;

public class PharmacistMenu implements IUserMenu {

    private Scanner scanner;
    private MedInventoryManager inventoryManager;
    private MedRequestManager requestManager;
    private PharmacistManager pharmacistmanager;
    private Pharmacist pharmacist; 

    // Constructor to initialize the menu and managers
    public PharmacistMenu(String pharmacistID) {
        this.scanner = new Scanner(System.in);
        this.inventoryManager = new MedInventoryManager();
        this.requestManager = new MedRequestManager();
       this.pharmacistmanager = new PharmacistManager(); // Assuming this is a valid instance
    this.pharmacist = (Pharmacist) pharmacistmanager.createUser(pharmacistID);
    }

    @Override
    public void mainMenu() {
        while (true) {
            // Displaying the pharmacist menu
            System.out.println("Pharmacist Menu:");
            System.out.println("1. View Medication Inventory");
            System.out.println("2. Submit Replenishment Request");
            System.out.println("3. Logout");
            System.out.print("Choose an option (1-3): ");
            
            // Read user choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // View medication inventory
                    viewMedicationInventory();
                    break;
                case 2:
                    // Submit a replenishment request
                    submitReplenishmentRequest();
                    break;
                case 3:
                    // Logout (exit the loop)
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Method to view medication inventory
    private void viewMedicationInventory() {
        System.out.println("Viewing medication inventory...");
        inventoryManager.printFullInventory();
    }

    // Method to submit a replenishment request
    private void submitReplenishmentRequest() {
        System.out.print("Enter Medicine Name: ");
        String medicine = scanner.nextLine();

        System.out.print("Enter Amount Requested: ");
        int amount = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter Unit (e.g., tablets, bottles): ");
        String unit = scanner.nextLine();

        System.out.print("Enter Status (e.g., Pending, Approved): ");
        String status = scanner.nextLine();

        try {
            requestManager.createNewRequest(medicine, amount, unit, status);
        } catch (IOException e) {
            System.err.println("Error submitting replenishment request: " + e.getMessage());
        }
    }
}
