package usermenus;

import managers.PatientRecordManager;
import users.Patient;
import java.util.Scanner;
import utility.CheckValidity;
import appts.apptMain;

public class PatientMenu implements IHasMenu {
    private Patient patient;

    public PatientMenu(String patientID) throws Exception {
        this.patient = PatientRecordManager.loadRecord(patientID);
    }

    Patient getPatient() {
        return patient;
    }

    String getPatientID() {
        return patient.getPatientID();
    }

    String getPatientName() {
        return patient.getName();
    }

    public void displayMenu() throws Exception {
        while (true) {
            System.out.println("Welcome " + patient.getName() + ",\n");
            System.out.println("(1) View Medical Record\n" + "(2) Update Contact Details\n" + "(3) View Available Appointment Slots)\n" + "(4) Schedule an Appointment\n" + "(5) Reschedule an Appointment\n" + "(6) Cancel an Appointment\n" + "(7) View Scheduled Appointments\n" + "(8) View Past Appointment Outcome Records\n" + "(9) Logout\n");
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
                    apptMain.viewAppointments();
                    break;
                case 4: //Schedule appointment
                    break;
                case 5: //Reschedule appointment
                    break;
                case 6: //Cancel appointment
                    break;
                case 7: //View scheduled appointment
                    break;
                case 8: //View past appointment records
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

    private void updateContactMenu() throws Exception {
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

    private void updatePhoneNumberMenu() throws Exception {
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
                patient = PatientRecordManager.updatePatientPhone(getPatientID(), newPhone);
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

    private void updateEmailMenu() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new email (x to go back): ");
        String newEmail = sc.nextLine();
        if (newEmail.equalsIgnoreCase("x")) {
            updateContactMenu();
            return;
        }
        //valid email
        if (CheckValidity.isValidEmail(newEmail)) {
            patient = PatientRecordManager.updatePatientEmail(getPatientID(), newEmail);
            System.out.println("Successfully updated email address\n");
        } else {
            System.out.println("Please enter a valid email\n");
            updateEmailMenu();
        }
    }
}
