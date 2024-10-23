package User;

import DatabaseManagers.PatientRecordManager;
import Records.ContactInfo;
import Records.PatientRecord;
import java.util.Scanner;
import Utility.CheckValidity;

public class Patient implements IHasMenu {
    private PatientRecord patientRecord;

    public Patient(String patientID) throws Exception {
        this.patientRecord = PatientRecordManager.loadPatientRecord(patientID);
    }

    PatientRecord getPatientRecord() {
        return patientRecord;
    }

    String getPatientID() {
        return patientRecord.getPatientID();
    }

    String getPatientName() {
        return patientRecord.getName();
    }

    public void displayMenu() throws Exception {
        while (true) {
            System.out.println("Welcome " + patientRecord.getName() + ",\n");
            System.out.println("(1) View Medical Record\n" + "(2) Update Contact Details\n" + "(3) Appointments (Coming Soon)\n" + "(4) Log Out\n");
            Scanner sc = new Scanner(System.in);
            int choice = Integer.parseInt(sc.nextLine());
            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("<<Patient Record View>>");
                    System.out.println(patientRecord + "\n");
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 2:
                    updateContact();
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Logging out...");
                    System.out.println("Successfully logged out!\n\n");
                    return;
                default:
                    System.out.println("Invalid choice\n");
                    break;
            }
        }
    }

    private void updateContact() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("<<Update Contact Details>>\n");
        System.out.println("Your current contact details: ");
        System.out.println(patientRecord.getContactInfo());

        System.out.println("\n(1) Update Phone Number\n(2) Update Email\n(X) Exit\n");
        String choice = sc.nextLine();
        switch(choice) {
            case "1":
                updatePhoneNumber();
                break;
            case "2":
                updateEmail();
                break;
            case "x":
            case "X":
                return;
            default:
                System.out.println("Invalid choice");
                updateContact();
                break;
        }
    }

    private void updatePhoneNumber() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new phone number (x to go back): ");
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("x")) {
            updateContact();
            return;
        }
        try {
            int newPhone = Integer.parseInt(input); // Try parsing the input
            if (CheckValidity.isValidPhoneNumber(newPhone)) {
                if (PatientRecordManager.updatePatientPhone(getPatientID(), newPhone)) {
                    patientRecord.setPhone(newPhone);
                    System.out.println("Successfully updated phone number\n");
                    return;
                }
            } else {
                System.out.println("Please enter a valid phone number (6/8/9xxxxxxx)\n");
                updatePhoneNumber();
                return;
            }
        } catch (NumberFormatException e) {
            // Catch the case where input is not a valid integer
            System.out.println("Please enter a valid phone number (6/8/9xxxxxxx)\n");
            updatePhoneNumber();
            return;
        }
        System.out.println("Unknown error! Phone number not updated");
    }

    private void updateEmail() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new email (x to go back): ");
        String newEmail = sc.nextLine();
        if (newEmail.equalsIgnoreCase("x")) {
            updateContact();
            return;
        }
        //valid email
        if (CheckValidity.isValidEmail(newEmail)) {
            if (PatientRecordManager.updatePatientEmail(getPatientID(), newEmail)) {
                patientRecord.setEmail(newEmail);
                System.out.println("Successfully updated email address\n");
                return;
            }
        } else {
            System.out.println("Please enter a valid email\n");
            updateEmail();
            return;
        }
        System.out.println("Unknown error! Email not updated");
    }

    @Override
    public String toString() {
        return getPatientID() + " - " + getPatientName();
    }
}
