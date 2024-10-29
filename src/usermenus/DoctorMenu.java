package usermenus;

import managers.DoctorRecordManager;
import managers.PatientRecordManager;
import users.Doctor;
import users.Patient;

import java.util.Scanner;

public class DoctorMenu implements IHasMenu {
    private Doctor doctor;

    public DoctorMenu(String doctorID) throws Exception {
        doctor = DoctorRecordManager.loadDoctorRecord(doctorID);
    }

    String getDoctorID() {
        return doctor.getDoctorID();
    }

    String getDoctorName() {
        return doctor.getName();
    }

    @Override
    public void displayMenu() throws Exception {
        while (true) {
            System.out.println("Welcome Doctor " + doctor.getName() + ",\n");
            
            System.out.println("(1) View Patient Medical Records\n" + 
                               "(2) Update Patient Medical Records (Coming Soon)\n" + 
                               "(3) Appointments (Coming Soon)\n" + 
                               "(4) Log Out\n");

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
        for (Patient p : doctor.getPatients()) {
            System.out.println(p.getPatientID() + " - " + p.getName());
        }
        System.out.print("\nEnter Patient ID to view record: ");
        String patientID = sc.nextLine().toUpperCase();
        if (patientID.equals("X")) {
            return;
        }
        System.out.println();
        Patient patient = PatientRecordManager.loadRecord(patientID);
        if (patient == null) {
            System.out.println("Invalid Patient ID. Please try again.\n");
            viewPatientRecords();
            return;
        }
        boolean authorized = false;
        for (Patient p : doctor.getPatients()) {
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
        System.out.println(patient + "\n");
        System.out.println("Press enter to continue...\n");
        sc.nextLine();
    }
}
