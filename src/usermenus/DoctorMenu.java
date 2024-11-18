package usermenus;

import entities.Doctor;
import entities.Patient;
import managers.AppointmentManager;
import managers.DoctorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code DoctorMenu} class represents the menu interface for a doctor user.
 * It allow doctors to interact with the system and manage patient records, appointments and personal schedules.
 * <p>This class provides various options for doctors, including viewing and updating patient records, managing appointments,
 * and looging out of the system. It implements the {@code IUserMenu} interface and provides the actual menu logic for doctors
 * to interact with their data and the system.</p>
 */
public class DoctorMenu implements IUserMenu {
    private Doctor doctor;
    private final DoctorManager doctorManager;
    private final AppointmentManager apptManager;
    private final Scanner sc;

    /**
     * Constructs a new {@code DoctorMenu} for a specific doctor by their ID.
     * This constructor initializes the {@code DoctorMenu} and loads the doctor data from the system
     * @param doctorID The ID of the doctor for whom the menu is created.
     */
    public DoctorMenu(String doctorID) {
        doctorManager = new DoctorManager();
        doctor = (Doctor) doctorManager.createUser(doctorID);
        apptManager = new AppointmentManager();
        sc = new Scanner(System.in);
    }

    /**
     * Displays the main menu for the doctor, allowing them to select various actions.
     * The menu includes options like viewing and updating patient records, managing appointments and logging out.
     */
    @Override
    public void mainMenu() throws Exception{
        while (true) {
            printChoices();
            System.out.print("Enter your selection: ");
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
                case 3: //View personal schedule
                    apptManager.doctorViewPersonalSchedule(doctor.getId());
                    System.out.println("\nPress enter to continue...\n");
                    sc.nextLine();
                    break;
                case 4: //Set availability for appointments
                    apptManager.doctorAddAppointments(doctor.getId());
                    System.out.println("\nPress enter to continue...\n");
                    sc.nextLine();
                    break;
                case 5: //Accept or decline appointment requests
                    apptManager.doctorAppointmentRequests(doctor.getId());
                    break;
                case 6: //View upcoming appointments
                    apptManager.doctorViewUpcomingAppointments(doctor.getId());
                    System.out.println("Press enter to continue...\n");
                    sc.nextLine();
                    break;
                case 7: //Record Appointment Outcome 
                    this.doctor = apptManager.doctorRecordAppointmentOutcome(doctor.getId());
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

    /**
     * Prints the available choices in the main menu.
     */
    private void printChoices() {
        System.out.println();
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

    /**
     * Displays the menu for viewing patient medical records.
     * The doctor can select a patient to view their record.
     */
    private void viewPatientRecordsMenu() {
        System.out.println("<<Patient Records View>>");
        System.out.println("Enter (X) to return\n");
        System.out.println("Patient List:");
        if (doctor.getPatients().isEmpty()) {
            System.out.println("You currently have no patients under your care.\n");
            return;
        }
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


    /**
     * Displays the menu for updating patient medical records.
     * The doctor can update various aspects of a patient's records, such as diagnoses, treatments or prescriptions
     */
    private void updatePatientRecordsMenu() {
        System.out.println("<<Update Patient Records>>");
        System.out.println("Enter (X) to return\n");
        System.out.println("Patient List:");
        if (doctor.getPatients().isEmpty()) {
            System.out.println("You currently have no patients under your care.\n");
            return;
        }
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
                updatedList = manageList(selectedPatient.getDiagnoses(), "Diagnoses", patientID);
                doctorManager.updatePatientDiagnoses(doctor, patientID, updatedList);
            }
            case 2 -> {
                updatedList = manageList(selectedPatient.getTreatments(), "Treatments", patientID);
                doctorManager.updatePatientTreatments(doctor, patientID, updatedList);
            }
            case 3 -> {
                updatedList = manageList(selectedPatient.getPrescriptions(), "Prescriptions", patientID);
                doctorManager.updatePatientPrescriptions(doctor, patientID, updatedList);
            }
            default -> System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    /**
     * Manages the list of attributes such as diagnoses, treatments or prescrptions.
     * Allows the doctor to add, edit, or remove items from the list.
     * @param list The list to be modified (eg diagnoses, treatments or prescriptions)
     * @param listName The name of the list (eg "Diagnoses")
     * @return The updated list
     */
    private List<String> manageList(List<String> list, String listName, String patientID) {
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
            if (listName.equals("Diagnoses")) {
                doctorManager.updatePatientDiagnoses(doctor, patientID, modifiedList);
            } else if (listName.equals("Treatments")) {
                doctorManager.updatePatientTreatments(doctor, patientID, modifiedList);
            } else if (listName.equals("Prescriptions")) {
                doctorManager.updatePatientPrescriptions(doctor, patientID, modifiedList);
            }
        }
        return modifiedList; // Return the updated list
    }
}
