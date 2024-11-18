package usermenus;

import entities.Administrator;
import managers.AdministratorManager;
import managers.MedInventoryManager;
import managers.AppointmentManager;

import java.util.Scanner;

public class AdministratorMenu implements IUserMenu {
    private final AdministratorManager administratorManager;
    private final Administrator administrator;
    private final Scanner sc;

    public AdministratorMenu(String adminID) throws Exception {
        administratorManager = new AdministratorManager();
        administrator = (Administrator) administratorManager.createUser(adminID);
        sc = new Scanner(System.in);
    }

    @Override
    public void mainMenu() {
        while (true) {
            printChoices();
            System.out.print("Enter your selection: ");
            String input = sc.nextLine().trim(); // Trim whitespace
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = 0;
            }

            switch (choice) {
                case 1: // View and Manage Hospital Staff
                    manageStaffMenu(sc);
                    break;
                case 2: // View Appointments Details
                    viewAppointmentsMenu();
                    break;
                case 3: // View and Manage Medication Inventory
                    manageMedicationInventory(sc);
                    break;
                case 4: // Approve Replenishment Requests
                    approveReplenishmentRequests();
                    break;
                case 5: // Log Out
                    System.out.println("Logging out...");
                    return; // Exit the loop to log out
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void printChoices() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.printf("║ %28s %-28s ║%n", "Welcome, Administrator", administrator.getName() + "!");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ (1) View and Manage Hospital Staff                        ║");
        System.out.println("║ (2) View Appointments Details                             ║");
        System.out.println("║ (3) View and Manage Medication Inventory                  ║");
        System.out.println("║ (4) Approve Replenishment Requests                        ║");
        System.out.println("║ (5) Log Out                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void manageStaffMenu(Scanner sc) {
        while (true) {
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║                << Manage Hospital Staff >>                ║");
            System.out.println("╠═══════════════════════════════════════════════════════════╣");
            System.out.println("║ (1) Add Staff                                             ║");
            System.out.println("║ (2) Update Staff                                          ║");
            System.out.println("║ (3) Remove Staff                                          ║");
            System.out.println("║ (4) View Staff List                                       ║");
            System.out.println("║ (5) Return to Main Menu                                   ║");
            System.out.println("╚═══════════════════════════════════════════════════════════╝");
            System.out.print("Enter your selection: ");
    
            String input = sc.nextLine().trim(); // Read input and trim whitespace
    
            // Check if input is a valid number
            if (!input.matches("\\d+")) { // Matches only digits
                System.out.println("Invalid input. Please enter a valid number.");
                continue; // Ask again
            }
    
            int action = Integer.parseInt(input); // Convert input to integer
    
            switch (action) {
                case 1: // Add Staff
                    administratorManager.addStaff(sc);
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 2: // Update Staff
                    administratorManager.updateStaff(sc);
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 3: // Remove Staff
                    administratorManager.removeStaff(sc);
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 4: // View Staff List
                    administratorManager.viewStaff();
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 5: // Return to Main Menu
                    System.out.println("Returning to main menu...\n");
                    return; // Exit the method and go back to the main menu
                default: // Invalid Choice
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void viewAppointmentsMenu() {
        AppointmentManager apptManager = new AppointmentManager();
        System.out.println("<< View Appointments >>");
        try {
            apptManager.adminPrintAppointmentDetails();
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving appointments: " + e.getMessage());
        }
        System.out.println("Press Enter to return to the main menu.");
        sc.nextLine(); // Pause and wait for the user to press Enter
    }

    private void manageMedicationInventory(Scanner sc) {
        while (true) {
            System.out.println();
            System.out.println("╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║             << Manage Medication Inventory >>             ║");
            System.out.println("╠═══════════════════════════════════════════════════════════╣");
            System.out.println("║ (1) View Medication Inventory                             ║");
            System.out.println("║ (2) Add New Medication                                    ║");
            System.out.println("║ (3) Update Medication Stock                               ║");
            System.out.println("║ (4) Update Low Stock Alert Line                           ║");
            System.out.println("║ (5) Return to Main Menu                                   ║");
            System.out.println("╚═══════════════════════════════════════════════════════════╝");
            System.out.print("Enter your selection: ");
    
            String input = sc.nextLine().trim();
    
            if (!input.matches("\\d+")) { // Validate numeric input
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
    
            int choice = Integer.parseInt(input);
    
            switch (choice) {
                case 1 -> administratorManager.viewMedicationInventory();
                case 2 -> administratorManager.addMedication(sc);
                case 3 -> administratorManager.updateMedicationStock(sc);
                case 4 -> administratorManager.updateLowStockAlert(sc);
                case 5 -> {
                    System.out.println("Returning to main menu...");
                    return; // Exit the inventory management menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void approveReplenishmentRequests() {
        administratorManager.approveReplenishmentRequests();
    }
    
}
