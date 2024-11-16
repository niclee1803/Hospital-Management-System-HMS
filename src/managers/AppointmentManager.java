package managers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.io.IOException;

import utility.table;

import managers.DoctorManager;
import managers.PatientManager;

import entities.Appointment;
import entities.Doctor;
import entities.Patient;
import filehandlers.AppointmentFileHandler;

/**
 * The (@code AppointmentManager} class provides methods to manage the scheduling, rescheduling
 * and cancellation of appointments for patients. It interacts with the {@code AppointmentFileHandler}
 * to read and write appointments from a CSV file and provides functionalities such as finding appointments,
 * printing available appointments, and displaying patient-specific appointment records.
 */
public class AppointmentManager {

    //File methods
    
    public static List<Appointment> readAppointments() throws IOException {

        AppointmentFileHandler file = new AppointmentFileHandler();
        List<Appointment> appointments = new ArrayList<>();

        try {
            List<String[]> rows = file.readFile();
            
            // Convert each row into an Appointment object
            for (String[] row : rows) {
                // Make sure the row has the expected number of elements
                if (row.length >= 10) {
                    String appointmentID = row[0];
                    String doctorID = row[1];
                    String patientID = row[2].isEmpty() ? null : row[2];
                    LocalDate date = LocalDate.parse(row[3]);
                    LocalTime time = LocalTime.parse(row[4]);
                    String status = row[5];
                    String service = row[6].isEmpty() ? null : row[6];
                    String medName = row[7].isEmpty() ? null : row[7];
                    String medStatus = row[8].isEmpty() ? null : row[8];
                    String notes = row[9].isEmpty() ? null : row[9];
                    
                    // Create an Appointment object and add it to the list
                    appointments.add(new Appointment(appointmentID, doctorID, patientID, date, time, status, service, medName, medStatus, notes));
                }
            }

        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

    return appointments;
 
    }

    public static void writeAppointments(List<Appointment> appointments) throws IOException {
        AppointmentFileHandler file = new AppointmentFileHandler();
        List<String[]> rows = new ArrayList<>();

        String[] header = {"appointmentID","doctorID","patientID","date","time","status","service","medName","medStatus","notes"};
        rows.add(header);

        for (Appointment item : appointments) {
            String[] temp = {
                item.getAppointmentID(),
                item.getDoctorID(),
                item.getPatientID() == null ? "" : item.getPatientID(),
                item.getDate().toString(),
                item.getTime().toString(),
                item.getStatus() == null ? "" : item.getStatus(),
                item.getService() == null ? "" : item.getService(),
                item.getMedName() == null ? "" : item.getMedName(),
                item.getMedStatus() == null ? "" : item.getMedStatus(),
                item.getNotes() == null ? "" : item.getNotes()
            };
            rows.add(temp);
        }

        file.writeFile(rows);

    }

    //Helper methods

    /**
     * Prompts the user for Doctor ID, date and time of an appointment slot.
     *
     * @return Returns a string array containing doctor ID, date and time of the appointment
     */
    public static String[] getSlotInput() {

        String[] appt = new String[3];

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Doctor ID: ");
        String dId = sc.nextLine();
        System.out.println("Enter the date (yyyy-MM-dd): ");
        String date = sc.nextLine();
        System.out.println("Enter the time (hh:mm) :");
        String time = sc.nextLine();

        appt[0] = dId;
        appt[1] = date;
        appt[2] = time;

        return appt;
    }
    
    /**
     * Finds an appointment based on doctor ID, date and time
     *
     * @param appt the lsit of {@code appointment} objects to search
     * @param dId the doctor's ID
     * @param date the date of the appointment
     * @param time the time of the appointment
     * @return Returns 0 if the appointment is found and unbooked, 1 if the appointment is booked and -1 if no appointment is found
     */
    public static int findAppointment(List<Appointment> appt, String dId, LocalDate date, LocalTime time) {

        for (Appointment appointment : appt) {
            if (dId.equals(appointment.getDoctorID()) && date.equals(appointment.getDate()) && time.equals(appointment.getTime())) {
                if (appointment.getStatus().equals("Unbooked")) {
                    return 0;
                } else {
                    return 1;
                } 
            }
        }

        return -1;

    }

    /**
     * Finds an appointment by pateitn ID, doctor ID, date and time
     *
     * @param appt the list of {@code appointment} objects to search
     * @param patientID the patient's ID
     * @param dId the doctor's ID
     * @param date the date of the appointment
     * @param time the time of the appointment
     * @return Returns 0 if the appointment is scheduled but not completed or canceled, 1 if the appointment is completed or canceled, and -1 if no appointment is found
     */
    public static int findAppointmentByPatient(List<Appointment> appt, String patientID, String dId, LocalDate date, LocalTime time) {

        for (Appointment appointment : appt) {
            if (patientID.equals(appointment.getPatientID()) && dId.equals(appointment.getDoctorID()) && date.equals(appointment.getDate()) && time.equals(appointment.getTime())) {
                if (appointment.getStatus().equals("Completed") || appointment.getStatus().equals("Cancelled")) {
                    return 1;
                } else {
                    return 0;
                } 
            }
        }

        return -1;

    }

    //Main functionality methods

    //Patient
    
    /**
     * Prints available (unbooked) appointment slots
     *
     * @throws Exception if there is an error reading from the file
     */
    public static void patientPrintAvailableAppointments() throws Exception {

        List<Appointment> appts = readAppointments();
        boolean isEmpty = true;

        String[] headers = {
            "Doctor ID", "Doctor Name", "Date", "Time"
        };
    
        List<String[]> rows = new ArrayList<>();
        rows.add(headers);

        for (Appointment appointment : appts) {

            if (appointment.getStatus().equals("Unbooked")) {
                isEmpty = false;
                DoctorManager manager = new DoctorManager();
                Doctor doc = (Doctor) manager.createUser(appointment.getDoctorID());

                rows.add(new String[]{
                    appointment.getDoctorID(),
                    doc.getName(),
                    appointment.getDate().toString(),
                    appointment.getTime().toString()
                });
            }

        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< There are no available appointments >>");
            System.out.println();
        } else {
            table.printTable(rows);
        }

    }

    /**
     * Prints all scheduled appointments for a given patient
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading from the file
     */
    public static void patientPrintScheduledAppointments(String patientID) throws Exception {

        List<Appointment> appts = readAppointments();
        boolean isEmpty = true;

        String[] headers = {
            "Doctor ID", "Doctor Name", "Date", "Time", "Status"
        };
    
        List<String[]> rows = new ArrayList<>();
        rows.add(headers);

        for (Appointment appointment : appts) {

            if (appointment.getPatientID() == null) {
                continue;
            }

            if (appointment.getStatus().equals("Completed")) {
                continue;
            }

            if (appointment.getPatientID().equals(patientID)) {
                isEmpty = false;
                DoctorManager manager = new DoctorManager();
                Doctor doc = (Doctor) manager.createUser(appointment.getDoctorID());

                rows.add(new String[]{
                    appointment.getDoctorID(),
                    doc.getName(),
                    appointment.getDate().toString(),
                    appointment.getTime().toString(),
                    appointment.getStatus()
                });
            }
            
        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< You have no scheduled appointments >>");
            System.out.println();
        } else {
            table.printTable(rows);
        }

    }

    /**
     * Allows a patient to schedule an appointment. The method promps the patient for doctor ID, dae and time, and then
     * schedules an available appointment
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading or writing to the file
     */
    public static void patientScheduleAppointment(String patientID) throws Exception {
        
        List<Appointment> appts = readAppointments();
        System.out.println("Scheduling appointment..."); 

        String doctorID;
        LocalDate date;
        LocalTime time;

        while(true) {
            
            String[] slotDetails = AppointmentManager.getSlotInput();
            
            doctorID = slotDetails[0];
            date = LocalDate.parse(slotDetails[1]);
            time = LocalTime.parse(slotDetails[2]);

            int c = findAppointment(appts, doctorID, date, time);

            if (c == -1) {
                System.out.println("No such slot exists! Please enter again");
            } else if (c > 0) {
                System.out.println("Slot already taken! Please enter again");
            } else {
                break;
            }
        }

        for (Appointment appointment : appts) {
            if (appointment.getDoctorID().equals(doctorID) && appointment.getDate().equals(date) && appointment.getTime().equals(time)) {
                appointment.setPatientID(patientID);
                appointment.setStatus("Pending Confirmation");
            }
        }

        writeAppointments(appts);
        System.out.println("Appointment request sent!"); 

    }

    /**
     * Allows a patient to reschedule an appointment. The method first removes the existing appointment and then prompts
     * the patient to schedule a new one.
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading or writing to the file
     */
    public static void patientRescheduleAppointment(String patientID) throws Exception {

        List<Appointment> appts = readAppointments();
        System.out.println("Rescheduling appointment..."); 

        String doctorID;
        LocalDate date;
        LocalTime time;

        while(true) {
            String[] slotDetails = AppointmentManager.getSlotInput();
            
            doctorID = slotDetails[0];
            date = LocalDate.parse(slotDetails[1]);
            time = LocalTime.parse(slotDetails[2]);

            int c = findAppointmentByPatient(appts, patientID, doctorID, date, time);

            if (c == -1) {
                System.out.println("You have no such appointment! Please enter again");
            } else if (c > 0) {
                System.out.println("Appointment has been cancelled or completed! Please enter again");
            } else {
                break;
            }
        }

        for (Appointment appointment : appts) {

            if (appointment.getPatientID() == null) {
                continue;
            }

            if (appointment.getPatientID().equals(patientID) && appointment.getDoctorID().equals(doctorID) && appointment.getDate().equals(date) && appointment.getTime().equals(time)) {
                appointment.setPatientID(null);
                appointment.setStatus("Unbooked");
            }
        }

        writeAppointments(appts);

        AppointmentManager.patientScheduleAppointment(patientID);


    }

    /**
     * Cancels a scheduled appointment for a patient
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading or writing to the file
     */
    public static void patientCancelAppointment(String patientID) throws Exception {
        List<Appointment> appts = readAppointments();
        System.out.println("Cancelling appointment..."); 

        String doctorID;
        LocalDate date;
        LocalTime time;

        while(true) {
            String[] slotDetails = AppointmentManager.getSlotInput();
            
            doctorID = slotDetails[0];
            date = LocalDate.parse(slotDetails[1]);
            time = LocalTime.parse(slotDetails[2]);

            int c = findAppointmentByPatient(appts, patientID, doctorID, date, time);

            if (c == -1) {
                System.out.println("You have no such appointment! Please enter again");
            } else if (c > 0) {
                System.out.println("Appointment has been cancelled or completed! Please enter again");
            } else {
                break;
            }
        }

        for (Appointment appointment : appts) {

            if (appointment.getPatientID() == null) {
                continue;
            }

            if (appointment.getPatientID().equals(patientID) && appointment.getDoctorID().equals(doctorID) && appointment.getDate().equals(date) && appointment.getTime().equals(time)) {
                appointment.setStatus("Cancelled");
            }
        }

        writeAppointments(appts);
        System.out.println("Appointment cancelled!");

    }

    /**
     * Prints all completed appointment records for a given patient
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading from the file
     */
    public static void patientPrintAppointmentRecords(String patientID) throws Exception {
        
        List<Appointment> appts = readAppointments();
        boolean isEmpty = true;
        String[] headers = {
            "Doctor ID", "Doctor Name", "Date", "Time", "Status", "Service", "Med Name", "Notes"
        };
    
        List<String[]> rows = new ArrayList<>();
        rows.add(headers);

        for (Appointment appointment : appts) {

            if (appointment.getPatientID() == null) {
                continue;
            }

            if (!(appointment.getStatus().equals("Completed"))) {
                continue;
            }

            if (appointment.getPatientID().equals(patientID)) {
                isEmpty = false;
                DoctorManager manager = new DoctorManager();
                Doctor doc = (Doctor) manager.createUser(appointment.getDoctorID());

                rows.add(new String[]{
                    appointment.getDoctorID(),
                    doc.getName(),
                    appointment.getDate().toString(),
                    appointment.getTime().toString(),
                    appointment.getStatus(),
                    appointment.getService(),
                    appointment.getMedName(),
                    appointment.getNotes()
                });
            }

        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< You have no completed appointments >>");
            System.out.println();
        } else {
            table.printTable(rows);
        }

    }

    //Doctor

    public static void doctorViewPersonalSchedule(String doctorId) throws Exception {
        List<Appointment> appts = readAppointments();
        boolean isEmpty = true;
        String[] headers = {
            "Patient ID", "Patient Name", "Date", "Time", "Status"
        };
    
        List<String[]> rows = new ArrayList<>();
        rows.add(headers);

        for (Appointment appointment : appts) {

            if (appointment.getDoctorID().equals(doctorId)) {

                if (!appointment.getStatus().equals("Completed") && !appointment.getStatus().equals("Cancelled")) {
                    isEmpty = false;

                    if (appointment.getPatientID() != null) {
                        PatientManager manager = new PatientManager();
                        Patient patient = (Patient) manager.createUser(appointment.getPatientID());

                        rows.add(new String[] {
                            appointment.getPatientID(),
                            patient.getName(),
                            appointment.getDate().toString(),
                            appointment.getTime().toString(),
                            appointment.getStatus()
                        });
        
                    } else {

                        rows.add(new String[] {
                            "None",
                            "None",
                            appointment.getDate().toString(),
                            appointment.getTime().toString(),
                            appointment.getStatus()
                        });

                    }
                }
            }
        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< You have no appointments on your schedule >>");
            System.out.println();
        } else {
            table.printTable(rows);
        }
    }

    public static void doctorAddAppointments(String doctorId) throws Exception {

        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);
        LocalDate date;
        LocalTime time;

        System.out.println();
        System.out.println("<< Enter the appointment date and time >>");
        System.out.println();
        
        while(true) {
            System.out.println("Enter the date (yyyy-MM-dd): ");
            date = LocalDate.parse(sc.nextLine());
            System.out.println("Enter the time (hh:mm) :");
            time = LocalTime.parse(sc.nextLine());

            if (findAppointment(appts, doctorId, date, time) >= 0) {
                System.out.println("You have already indicated availability for this slot! Enter a new slot");
            } else {
                break;
            }
        }

        int length = appts.size() + 1;
        String number = String.format("%03d", length);
        String apptId = "A" + number;

        Appointment newSlot = new Appointment(apptId, doctorId, null, date, time, "Unbooked", null, null, null, null);
        appts.add(newSlot);
        writeAppointments(appts);
    }

    public static void doctorAppointmentRequests(String doctorId) throws Exception {
        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);
        boolean isEmpty = true;

        for (Appointment appt : appts) {

            if (appt.getDoctorID().equals(doctorId)) {
                if (appt.getStatus().equals("Pending Confirmation")) {
                    
                    isEmpty = false;

                    PatientManager manager = new PatientManager();
                    Patient patient = (Patient) manager.createUser(appt.getPatientID());

                    String[] headers = {
                        "Patient ID", "Patient Name", "Date", "Time"
                    };
                
                    List<String[]> rows = new ArrayList<>();
                    rows.add(headers);

                    rows.add(new String[] {
                        appt.getPatientID(),
                        patient.getName(),
                        appt.getDate().toString(),
                        appt.getTime().toString()
                    });

                    table.printTable(rows);

                    while(true) {
                        System.out.println("Do you wish to accept this appointment request? (Y/N): ");
                        char choice = sc.next().charAt(0);
                        sc.nextLine();
                        if (choice == 'Y' || choice == 'y') {
                            appt.setStatus("Confirmed");
                            System.out.println("Appointment confirmed!");
                            break;
                        } else if (choice == 'N' || choice == 'n') {
                            appt.setStatus("Cancelled");
                            System.out.println("Appointment cancelled!");
                            break;
                        } else {
                            System.out.println("Invalid choice! Please enter Y or N: ");
                        }

                    }

                    

                }
            }
        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< You have no appointment requests >>");
            System.out.println();
        }

        writeAppointments(appts);

    }

    public static void doctorViewUpcomingAppointments(String doctorId) throws Exception {
        List<Appointment> appts = readAppointments();
        boolean isEmpty = true;
        String[] headers = {
            "Patient ID", "Patient Name", "Date", "Time"
        };
    
        List<String[]> rows = new ArrayList<>();
        rows.add(headers);

        for (Appointment appointment : appts) {

            if (appointment.getDoctorID().equals(doctorId)) {

                if (appointment.getStatus().equals("Confirmed")) {
                    isEmpty = false;

                    PatientManager manager = new PatientManager();
                    Patient patient = (Patient) manager.createUser(appointment.getPatientID());

                    rows.add(new String[] {
                        appointment.getPatientID(),
                        patient.getName(),
                        appointment.getDate().toString(),
                        appointment.getTime().toString()
                    });
                }
            }
        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< You have no upcoming confirmed appointments >>");
            System.out.println();
        } else {
            table.printTable(rows);
        }

    }

    public static void doctorRecordAppointmentOutcome(String doctorId) throws Exception {
        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);
        boolean isEmpty = true;

        for (Appointment appt : appts) {

            if (appt.getDoctorID().equals(doctorId)) {
                if (appt.getStatus().equals("Confirmed")) {
                    isEmpty = false;

                    PatientManager manager = new PatientManager();
                    Patient patient = (Patient) manager.createUser(appt.getPatientID());

                    String[] headers = {
                        "Patient ID", "Patient Name", "Date", "Time"
                    };
                
                    List<String[]> rows = new ArrayList<>();
                    rows.add(headers);

                    rows.add(new String[] {
                        appt.getPatientID(),
                        patient.getName(),
                        appt.getDate().toString(),
                        appt.getTime().toString()
                    });

                    table.printTable(rows);

                    while(true) {
                        System.out.println("Have you completed this appointment? (Y/N): ");
                        char choice = sc.next().charAt(0);
                        sc.nextLine();
                        
                        if (choice == 'Y' || choice == 'y') {
                            appt.setStatus("Completed");
                            System.out.println("Service provided?: ");
                            String service = sc.nextLine();

                            while(true) {
                                System.out.println("Medicine prescribed? (Y/N): ");
                                char a = sc.next().charAt(0);
                                sc.nextLine();
                                if (a == 'Y' || a == 'y') {
                                    System.out.println("Name of medicine prescribed?: ");
                                    String medName = sc.nextLine();
                                    appt.setMedName(medName);
                                    appt.setMedStatus("Pending");
                                    break;
                                } else if (a == 'N' || a == 'n') {
                                    break;
                                } else {
                                    System.out.println("Invalid choice! Please enter Y or N: ");
                                }
                            }

                            System.out.println("Any notes?: ");
                            String notes = sc.nextLine();

                            appt.setService(service);
                            appt.setNotes(notes);
                            
                            System.out.println("Appointment outcome recorded!");

                            break;
                        } else if (choice == 'N' || choice == 'n') {
                            break;
                        } else {
                            System.out.println("Invalid choice! Please enter Y or N: ");
                        }

                    }

                }
            }
        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< You have no confirmed appointments to complete >>");
            System.out.println();
        }

        writeAppointments(appts);
    }

    //Pharmacist

    public static void pharmacistViewAppointmentOutcome() throws Exception {
        List<Appointment> appts = readAppointments();
        boolean isEmpty = true;

        String[] headers = {
            "Appointment ID", "Doctor ID", "Patient ID", "Med Name", "Med Status"
        };
    
        List<String[]> rows = new ArrayList<>();
        rows.add(headers);

        for (Appointment appointment : appts) {

            if (appointment.getStatus().equals("Completed")) {
                if (appointment.getMedStatus() != null) {
                    isEmpty = false;
                    rows.add(new String[] {
                        appointment.getAppointmentID(),
                        appointment.getDoctorID(),
                        appointment.getPatientID(),
                        appointment.getMedName(),
                        appointment.getMedStatus()
                    });

                }
            }
        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< There are no completed appointments >>");
            System.out.println();
        } else {
            table.printTable(rows);
        }

    }

    public static void pharmacistUpdatePrescriptionStatus() throws Exception {
        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);
        boolean isEmpty = true;

        for (Appointment appointment : appts) {

            if (appointment.getStatus().equals("Completed")) {
                if (appointment.getMedStatus().equals("Pending")) {

                    isEmpty = false;

                    String[] headers = {
                        "Appointment ID", "Doctor ID", "Patient ID", "Med Name", "Med Status"
                    };
                
                    List<String[]> rows = new ArrayList<>();
                    rows.add(headers);

                    rows.add(new String[] {
                        appointment.getAppointmentID(),
                        appointment.getDoctorID(),
                        appointment.getPatientID(),
                        appointment.getMedName(),
                        appointment.getMedStatus()
                    });

                    table.printTable(rows);

                    while(true) {
                        System.out.println("Medicine dispensed? (Y/N): ");
                        char choice = sc.next().charAt(0);
                        
                        if (choice == 'Y' || choice == 'y') {
                            appointment.setMedStatus("Dispensed");
                            System.out.println("Medicine status updated!");
                            break;
                        } else if (choice == 'N' || choice == 'n') {
                            break;
                        } else {
                            System.out.println("Invalid choice! Please enter Y or N: ");
                        }
                    }

                }
            }
        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< There are no completed appointments to dispense medicines for >>");
            System.out.println();
        }

        writeAppointments(appts);
    }

    //Admin

    public static void adminPrintAppointmentDetails() throws Exception {

        List<Appointment> appts = readAppointments();

        if (appts.isEmpty()) {
            System.out.println();
            System.out.println("<< No appointment details available >>");
            System.out.println();
            return; 
        }
    
        String[] headers = {
            "ApptID", "DoctorID", "PatientID", "Date", "Time", "Status", "Med Name", "Med Status", "Notes"
        };
    
        List<String[]> rows = new ArrayList<>();
        rows.add(headers);
    
        // Iterate through the appointments and add each as a row in the table
        for (Appointment appointment : appts) {
            rows.add(new String[]{
                appointment.getAppointmentID(),
                appointment.getDoctorID(),
                appointment.getPatientID() == null ? "None" : appointment.getPatientID(),
                appointment.getDate().toString(),
                appointment.getTime().toString(),
                appointment.getStatus(),
                appointment.getMedName() == null ? "None" : appointment.getMedName(),
                appointment.getMedStatus() == null ? "None" : appointment.getMedStatus(),
                appointment.getNotes() == null ? "None" : appointment.getNotes()
            });
        }
    
        // Print the table
        table.printTable(rows);
    }

}
