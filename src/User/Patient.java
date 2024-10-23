package User;

import DatabaseManagers.PatientRecordManager;
import Records.PatientRecord;
import java.util.Scanner;

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

    public void displayMenu() {
        while (true) {
            System.out.println("Welcome " + patientRecord.getName() + ",\n");
            System.out.println("(1) View Medical Record\n" + "(2) Update Contact Details (Coming Soon)\n" + "(3) Appointments (Coming Soon)\n" + "(4) Log Out\n");
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

    @Override
    public String toString() {
        return getPatientID() + " - " + getPatientName();
    }
}
