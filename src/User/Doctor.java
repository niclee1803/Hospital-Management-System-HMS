package User;

import DatabaseManagers.DoctorRecordManager;
import Records.DoctorRecord;

import java.util.List;
import java.util.Scanner;

public class Doctor implements IHasMenu {
    private String doctorID;
    private DoctorRecord doctorRecord;

    public Doctor(String doctorID) throws Exception {
        this.doctorID = doctorID;
        doctorRecord = DoctorRecordManager.loadDoctorRecord(doctorID);
    }

    @Override
    public void displayMenu() throws Exception {
        while (true) {
            System.out.println("Welcome Doctor " + doctorRecord.getName() + ",\n");
            System.out.println("(1) View Patient Medical Records\n" +
                    "(2) Update Patient Medical Records (Coming Soon)\n" +
                    "(3) Appointments (Coming Soon)\n" +
                    "(4) Log Out\n");
            Scanner sc = new Scanner(System.in);
            int choice = Integer.parseInt(sc.nextLine());
            System.out.println();
            switch (choice) {
                case 1:
                    viewPatientRecords();
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


    private void viewPatientRecords() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("<<Patient Records View>>");
        System.out.println("Enter (X) to return\n");
        System.out.println("Patient List:");
        for (Patient p : doctorRecord.getPatients()) {
            System.out.println(p);
        }
        System.out.print("Enter Patient ID to view record: ");
        String patientID = sc.nextLine().toUpperCase();
        if (patientID.equals("X")) {
            displayMenu();
        }
        System.out.println();
        Patient patient = new Patient(patientID);
        boolean authorized = false;
        for (Patient p : doctorRecord.getPatients()) {
            if (p.getPatientID().equals(patient.getPatientID())) {
                authorized = true;
                break;
            }
        }

        if (!authorized) {
            System.out.println("You are not authorized to view this patient's record.\n");
            viewPatientRecords();
            return;
        }

        System.out.println(patient.getPatientRecord() + "\n");
    }
}
