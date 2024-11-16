package usermenus;

import entities.Administrator;
import managers.AdministratorManager;
import managers.MedInventoryManager;
import appointments.appointmentController;

import java.util.Scanner;

public class AdministratorMenu implements IUserMenu {
    private final AdministratorManager administratorManager;
    private final MedInventoryManager inventoryManager;
    private final Administrator administrator;

    public AdministratorMenu(String adminID) throws Exception {
        administratorManager = new AdministratorManager();
        inventoryManager = new MedInventoryManager();
        administrator = (Administrator) administratorManager.createUser(adminID);
    }

    @Override
    public void mainMenu() {
        while (true) {
            printChoices();
            System.out.print("Enter your selection: ");
            Scanner sc = new Scanner(System.in);
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: // View and Manage Hospital Staff
                    manageStaffMenu(sc);
                    break;
                case 2: // View Appointments Details
                    viewAppointmentsMenu();
                    break;
                // case 3: // View and Manage Medication Inventory
                //     manageMedInventoryMenu(sc);
                //     break;
                // case 4: // Approve Replenishment Requests
                //     approveReplenishmentRequests(sc);
                    // break;
                case 5: // Log Out
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void printChoices() {
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
        System.out.println("<< Manage Hospital Staff >>");
        System.out.println("(1) Add Staff");
        System.out.println("(2) Update Staff");
        System.out.println("(3) Remove Staff");
        System.out.println("(4) View Staff List");
        System.out.println("(X) Return to Main Menu");
        System.out.print("Enter your selection: ");

        String action = sc.nextLine().toUpperCase();
        switch (action) {
            case "1": // Add Staff
                administratorManager.addStaff(sc);
                break;
            case "2": // Update Staff
                administratorManager.updateStaff(sc);
                break;
            case "3": // Remove Staff
                administratorManager.removeStaff(sc);
                break;
            case "4": // View Staff List
                administratorManager.viewStaff();
                break;
            case "X": // Return to Main Menu
                System.out.println("Returning to main menu...");
                break;
            default: // Invalid Choice
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void viewAppointmentsMenu() {
        System.out.println("<< View Appointments >>");
        try {
            appointmentController.printAppointments();
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving appointments: " + e.getMessage());
        }
        System.out.println("Press Enter to return to the main menu.");
        new Scanner(System.in).nextLine();
    }

//     private void manageMedInventoryMenu(Scanner sc) {
//         System.out.println("<< Manage Medication Inventory >>");
//         System.out.println("(1) Add Medication");
//         System.out.println("(2) Update Medication Stock");
//         System.out.println("(3) Remove Medication");
//         System.out.println("(X) Return to Main Menu");
//         System.out.print("Enter your selection: ");

//         String action = sc.nextLine().toUpperCase();
//         switch (action) {
//             case "1":
//                 inventoryManager.addMedication(sc);
//                 break;
//             case "2":
//                 inventoryManager.updateStock(sc);
//                 break;
//             case "3":
//                 inventoryManager.removeMedication(sc);
//                 break;
//             case "X":
//                 System.out.println("Returning to main menu...");
//                 break;
//             default:
//                 System.out.println("Invalid choice. Please try again.");
//                 break;
//         }
//     }

//     private void approveReplenishmentRequests(Scanner sc) {
//         System.out.println("<< Approve Replenishment Requests >>");
//         inventoryManager.approveReplenishment(sc);
//         System.out.println("Replenishment requests processed. Returning to the main menu...");
//     }
}
