package usermenus;

// import

import java.util.Scanner;

public class AdministratorMenu implements IHasMenu {
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
            System.out.println("Welcome Administrator " + ",\n");
            
            System.out.println("(1) View and Manage Hospital Staff (Coming Soon)\n" +
                               "(2) View Appointments details (Coming Soon)\n" + 
                               "(3) View and Manage Medication Inventory (Coming Soon)\n" + 
                               "(4) Approve Replenishment Requests (Coming Soon)\n" +
                               "(5) Log Out\n");
            
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
                    viewAndManageHospitalStaff();
                    break;
                case 2:
                    viewAppointmentsDetails();
                    break;
                case 3:
                    viewAndManageMedicationInventory();
                    break;
                case 4:
                    approveReplenishmentRequests();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    System.out.println("Successfully logged out!\n\n");
                    return;
                default:
                    System.out.println("Invalid choice\n");
                    break;
            }
        }
    }

    private void viewAndManageHospitalStaff() throws Exception{

    }


    private void viewAppointmentsDetails() throws Exception{

    }

    private void approveReplenishmentRequests() throws Exception{
        
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
