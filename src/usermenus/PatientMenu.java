package usermenus;

import entities.Patient;
import managers.AppointmentManager;
import managers.PatientManager;
import utility.CheckValidity;

import java.util.Scanner;

/**
 * {@code PatientMenu} class handles the user interface for a patient.
 * It allows the patient to view their medical records, update contact details, and manage appointments.
 * This class implements the {@link IUserMenu} interface, providing the main menu and various functionalities
 * specific to a patient.
 */
public class PatientMenu implements IUserMenu {
    private Patient patient;
    private final PatientManager patientManager;

    /**
     * Constructs a {@code PatientMenu} object for the specified patient.
     * @param patientID The ID of the patient to load and manage.
     * @throws Exception If the patient cannot be found or loaded
     */
    public PatientMenu(String patientID) throws Exception {
        patientManager = new PatientManager();
        this.patient = (Patient) patientManager.createUser(patientID);
    }

    /**
     * Displays the main menu for the patient and handles user input for navigating through the options.
     * Depending on the user's selection, it invokes methods for viewing records, updating contact details, or managing
     * appointments.
     */
    @Override
    public void mainMenu() throws Exception {

        AppointmentManager apptManager = new AppointmentManager();

        while (true) {
            printChoices();
            System.out.print("Enter your selection: ");
            Scanner sc = new Scanner(System.in);
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = 0;
            }
            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("<<Patient Record View>>");
                    System.out.println(patient + "\n");
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 2:
                    updateContactMenu();
                    break;
                case 3: //View appointment slots
                    apptManager.patientPrintAvailableAppointments();
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 4: //Schedule appointment
                    apptManager.patientScheduleAppointment(patient.getId());
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 5: //Reschedule appointment
                    apptManager.patientRescheduleAppointment(patient.getId());
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 6: //Cancel appointment
                    apptManager.patientCancelAppointment(patient.getId());
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 7: //View scheduled appointment
                    apptManager.patientPrintScheduledAppointments(patient.getId());
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 8: //View past appointment records
                    apptManager.patientPrintAppointmentRecords(patient.getId());
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 9: //Logout
                    System.out.println("Logging out...");
                    System.out.println("Successfully logged out!\n\n");
                    return;
                default:
                    System.out.println("Invalid choice\n");
                    break;
            }
        }
    }

    /**
     * Prints the choices for the main menu, showing options available to the patient.
     */
    private void printChoices() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.printf("║ %28s %-28s ║%n", "Welcome,", patient.getName() + "!");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ (1) View Medical Record                                   ║");
        System.out.println("║ (2) Update Contact Details                                ║");
        System.out.println("║ (3) View Available Appointment Slots                      ║");
        System.out.println("║ (4) Schedule an Appointment                               ║");
        System.out.println("║ (5) Reschedule an Appointment                             ║");
        System.out.println("║ (6) Cancel an Appointment                                 ║");
        System.out.println("║ (7) View Scheduled Appointments                           ║");
        System.out.println("║ (8) View Past Appointment Outcome Records                 ║");
        System.out.println("║ (9) Logout                                                ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Provides the menu for updating contact details and prompts the patient to choose between updating their phone number or email.
     */
    private void updateContactMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("<<Update Contact Details>>\n");
        System.out.println("Your current contact details: ");
        System.out.println(patient.getContactInfo());

        System.out.println("\n(1) Update Phone Number\n(2) Update Email\n(X) Exit\n");
        String choice = sc.nextLine();
        switch(choice) {
            case "1":
                updatePhoneNumberMenu();
                break;
            case "2":
                updateEmailMenu();
                break;
            case "x":
            case "X":
                return;
            default:
                System.out.println("Invalid choice");
                updateContactMenu();
                break;
        }
    }

    /**
     * Handles the process of updating the patient's phone number.
     * Validates the new phone number and updates it in the system.
     */
    private void updatePhoneNumberMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new phone number (x to go back): ");
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("x")) {
            updateContactMenu();
            return;
        }
        try {
            int newPhone = Integer.parseInt(input); // Try parsing the input
            if (CheckValidity.isValidPhoneNumber(newPhone)) {
                patient = patientManager.updatePatientPhone(patient.getId(), newPhone);
                System.out.println("Successfully updated phone number\n");
            } else {
                System.out.println("Please enter a valid phone number (6/8/9xxxxxxx)\n");
                updatePhoneNumberMenu();
            }
        } catch (NumberFormatException e) {
            // Catch the case where input is not a valid integer
            System.out.println("Please enter a valid phone number (6/8/9xxxxxxx)\n");
            updatePhoneNumberMenu();
        }
    }

    /**
     * Handles the process of updating the patient's email address.
     * Validates the new email and updates it in the system.
     */
    private void updateEmailMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new email (x to go back): ");
        String newEmail = sc.nextLine();
        if (newEmail.equalsIgnoreCase("x")) {
            updateContactMenu();
            return;
        }
        //valid email
        if (CheckValidity.isValidEmail(newEmail)) {
            patient = patientManager.updatePatientEmail(patient.getId(), newEmail);
            System.out.println("Successfully updated email address\n");
        } else {
            System.out.println("Please enter a valid email\n");
            updateEmailMenu();
        }
    }
}
