package usermenus;

import entities.Doctor;
import entities.Patient;
import managers.DoctorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DoctorMenu implements IUserMenu {
    private Doctor doctor;
    private final DoctorManager doctorManager;

    public DoctorMenu(String doctorID) throws Exception {
        doctorManager = new DoctorManager();
        doctor = (Doctor) doctorManager.createUser(doctorID);
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
                choice = 0;
            }
            System.out.println();
            switch (choice) {
                case 1:
                    viewPatientRecordsMenu();
                    break;
                case 2:
                    updatePatientRecordsMenu();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    System.out.println("Logging out...");
                    System.out.println("Successfully logged out!\n\n");
                    return;
                default:
                    System.out.println("Invalid choice\n");
                    break;
            }
        }
    }

    private void printChoices() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.printf("║ %28s %-28s ║%n", "Welcome, Doctor", doctor.getName() + "!");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║ (1) View Patient Medical Records                          ║");
        System.out.println("║ (2) Update Patient Medical Records                        ║");
        System.out.println("║ (3) View Personal Schedule                                ║");
        System.out.println("║ (4) Set Availability for Appointments                     ║");
        System.out.println("║ (5) Accept or Decline Appointment Requests                ║");
        System.out.println("║ (6) View Upcoming Appointments                            ║");
        System.out.println("║ (7) Record Appointment Outcome                            ║");
        System.out.println("║ (8) Log Out                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void viewPatientRecordsMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("<<Patient Records View>>");
        System.out.println("Enter (X) to return\n");
        System.out.println("Patient List:");
        for (Patient patient : doctor.getPatients()) {
            System.out.println(patient.getId() + " - " + patient.getName());
        }
        System.out.print("\nEnter Patient ID to view record: ");
        String patientID = sc.nextLine().toUpperCase();
        if (patientID.equals("X")) {
            return;
        }
        System.out.println();
        boolean found = false;
        for (Patient patient : doctor.getPatients()) {
            if (patient.getId().equals(patientID)) {
                System.out.println(patient);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Invalid Patient ID. Please try again.\n");
            viewPatientRecordsMenu();
            return;
        }
        System.out.println("Press enter to continue...\n");
        sc.nextLine();
    }


    private void updatePatientRecordsMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("<<Update Patient Records>>");
        System.out.println("Enter (X) to return\n");
        System.out.println("Patient List:");
        for (Patient patient : doctor.getPatients()) {
            System.out.println(patient.getId() + " - " + patient.getName());
        }
        System.out.print("\nEnter Patient ID to update record: ");
        String patientID = sc.nextLine().toUpperCase();
        if (patientID.equals("X")) {
            System.out.println();
            return;
        }
        System.out.println();
        Patient selectedPatient = null;
        for (Patient patient : doctor.getPatients()) {
            if (patient.getId().equals(patientID)) {
                selectedPatient = patient;
            }
        }
        if (selectedPatient == null) {
            System.out.println("Invalid Patient ID. Please try again.\n");
            updatePatientRecordsMenu();
            return;
        }

        System.out.println(selectedPatient);

        System.out.println("\nSelect which attribute to update: (X to return)\n" +
                "(1) Diagnoses\n" +
                "(2) Treatments\n" +
                "(3) Prescriptions\n");
        String choiceInput = sc.nextLine();
        if (choiceInput.equalsIgnoreCase("X")) {
            updatePatientRecordsMenu();
            return;
        }

        int choice = Integer.parseInt(choiceInput);
        List<String> updatedList;
        switch (choice) {
            case 1 -> {
                updatedList = manageList(sc, selectedPatient.getDiagnoses(), "Diagnoses");
                doctorManager.updatePatientDiagnoses(doctor, patientID, updatedList);
            }
            case 2 -> {
                updatedList = manageList(sc, selectedPatient.getTreatments(), "Treatments");
                doctorManager.updatePatientTreatments(doctor, patientID, updatedList);
            }
            case 3 -> {
                updatedList = manageList(sc, selectedPatient.getPrescriptions(), "Prescriptions");
                doctorManager.updatePatientPrescriptions(doctor, patientID, updatedList);
            }
            default -> System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private List<String> manageList(Scanner sc, List<String> list, String listName) {
        List<String> modifiedList = new ArrayList<>(list);
        while (true) {
            System.out.println("\n" + listName + ": " + modifiedList);
            System.out.println("Select an action:\n" +
                    "(1) Add new " + listName.substring(0, listName.length() - 1) + "\n" +
                    "(2) Edit an existing " + listName.substring(0, listName.length() - 1) + "\n" +
                    "(3) Remove an existing " + listName.substring(0, listName.length() - 1) + "\n" +
                    "(X) Return");
            String actionInput = sc.nextLine();
            if (actionInput.equalsIgnoreCase("X")) {
                updatePatientRecordsMenu();
                break;
            }

            int action;
            try {
                action = Integer.parseInt(actionInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue; // Restart the loop
            }

            switch (action) {
                case 1 -> {
                    System.out.print("Enter new " + listName.substring(0, listName.length() - 1) + ": ");
                    String newItem = sc.nextLine();
                    modifiedList.add(newItem); // Add new item
                    System.out.println(listName + " updated.");
                }
                case 2 -> {
                    while (true) {
                        System.out.print("Enter the index of the " + listName.substring(0, listName.length() - 1) + " to edit: ");
                        int index;
                        try {
                            index = Integer.parseInt(sc.nextLine()); // Try to parse the index
                            if (index >= 0 && index < modifiedList.size()) {
                                System.out.print("Enter the new value: ");
                                modifiedList.set(index, sc.nextLine()); // Edit item
                                System.out.println(listName + " updated.");
                                break;
                            } else {
                                System.out.println("Invalid index.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid index input. Please enter a valid integer.");
                        }
                    }
                }
                case 3 -> {
                    while (true) {
                        System.out.print("Enter the index of the " + listName.substring(0, listName.length() - 1) + " to remove: ");
                        int index;
                        try {
                            index = Integer.parseInt(sc.nextLine()); // Try to parse the index
                            if (index >= 0 && index < modifiedList.size()) {
                                modifiedList.remove(index); // Remove item
                                System.out.println(listName + " updated.");
                                break;
                            } else {
                                System.out.println("Invalid index.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid index input. Please enter a valid integer.");
                        }
                    }
                }
                default -> System.out.println("Invalid action. Try again.");
            }
        }
        return modifiedList; // Return the updated list
    }
}
