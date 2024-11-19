package usermenus;

import entities.Administrator;
import managers.AdministratorManager;
import managers.AppointmentManager;

import java.util.Scanner;

/**
 * The AdministratorMenu class provides a menu interface for administrators to manage hospital staff,
 * view appointment details, manage medication inventory, and approve replenishment requests.
 * It implements the IUserMenu interface and handles the interaction between the administrator and various system functionalities.
 *
 * <p>This class is responsible for presenting choices to the administrator, processing inputs, and invoking the relevant methods
 * in the AdministratorManager, AppointmentManager, and other classes for the different tasks.</p>
 *
 * <p>Each menu method contains its own sub-menu for more specific actions such as managing staff, viewing appointments, and
 * managing medication inventory. It also handles the user input validation and decision-making logic to guide the administrator
 * through the options.</p>
 */
public class AdministratorMenu implements IUserMenu {
    private final AdministratorManager administratorManager;
    private final Administrator administrator;
    private final Scanner sc;

    /**
     * Constructor for the AdministratorMenu. Initializes the menu with the administrator's ID,
     * creates the administrator object, and sets up the scanner for user input.
     *
     * @param adminID The ID of the administrator.
     * @throws Exception If there is an error while creating the administrator object or initializing necessary components.
     */
    public AdministratorMenu(String adminID) throws Exception {
        administratorManager = new AdministratorManager();
        administrator = (Administrator) administratorManager.createUser(adminID);
        sc = new Scanner(System.in);
    }

    /**
     * Displays the main menu to the administrator, allowing them to select various tasks such as
     * managing staff, viewing appointments, and managing medication inventory.
     * It processes the user's choice and navigates to the corresponding sub-menu or performs the selected task.
     */
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
                    manageStaffMenu();
                    break;
                case 2: // View Appointments Details
                    viewAppointmentsMenu();
                    break;
                case 3: // View and Manage Medication Inventory
                    manageMedicationInventory();
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

    /**
     * Prints the available options for the main menu.
     * This method displays the menu with options like managing staff, viewing appointments, etc.
     */
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

    /**
     * Displays a sub-menu for managing hospital staff, including options to add, update, remove, or view staff.
     * It processes the user's choice and performs the corresponding action.
     */
    private void manageStaffMenu() {
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

    /**
     * Displays the appointment details for the administrator by invoking the AppointmentManager's method.
     * It handles any exceptions that may occur while retrieving the appointments.
     */
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

    /**
     * Displays a sub-menu for managing medication inventory, allowing the administrator to view,
     * add, update stock, or manage low stock alerts.
     * It processes the user's choice and performs the corresponding action.
     */
    private void manageMedicationInventory() {
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

    /**
     * Calls the method to approve replenishment requests for medications.
     * This allows the administrator to approve or reject requests for medication replenishment.
     */
    private void approveReplenishmentRequests() {
        administratorManager.approveReplenishmentRequests();
    }
    
}
