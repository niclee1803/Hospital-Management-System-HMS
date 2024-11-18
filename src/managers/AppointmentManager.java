package managers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.IOException;

import utility.Table;

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
    
    private List<Appointment> readAppointments() throws IOException {

        AppointmentFileHandler file = new AppointmentFileHandler();
        List<Appointment> appointments = new ArrayList<>();

        try {
            List<String[]> rows = file.readFile();

            boolean isFirstRow = true;
            
            // Convert each row into an Appointment object
            for (String[] row : rows) {

                if (isFirstRow) {
                    isFirstRow = false; // Skip the first iteration
                    continue;           // Move to the next row
                }
                
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

    private void writeAppointments(List<Appointment> appointments) throws IOException {
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
    private String[] getSlotInput() {

        String[] appt = new String[3];

        Scanner sc = new Scanner(System.in);
        System.out.println("<< Enter x to go back to the menu >> ");
        System.out.print("Enter the Doctor ID: ");
        String dId = sc.nextLine();

        if (dId.equalsIgnoreCase("x")) {
            return appt;
        }

        String date = null;
        String time = null;
        
        while (date == null) {
            System.out.print("Enter the date (yyyy-MM-dd): ");
            String dateInput = sc.nextLine();
            if (dateInput.equalsIgnoreCase("x")) {
                return appt;
            }
            try {
                LocalDate.parse(dateInput);
                date = dateInput; 
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter the date in yyyy-MM-dd format.");
            }
        }
        
        while (time == null) {
            System.out.print("Enter the time (hh:mm): ");
            String timeInput = sc.nextLine();
            if (timeInput.equalsIgnoreCase("x")) {
                return appt;
            }
            try {
                LocalTime.parse(timeInput);
                time = timeInput; 
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format! Please enter the time in hh:mm format.");
            }
        }

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
    private int findAppointment(List<Appointment> appt, String dId, LocalDate date, LocalTime time) {

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
    private int findAppointmentByPatient(List<Appointment> appt, String patientID, String dId, LocalDate date, LocalTime time) {

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
    public void patientPrintAvailableAppointments() throws Exception {

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
            System.out.println("<< There are no available appointments >>");
            System.out.println();
        } else {
            System.out.println("<< Available appointment slots >>");
            Table.printTable(rows);
        }

    }

    /**
     * Prints all scheduled appointments for a given patient
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading from the file
     */
    public void patientPrintScheduledAppointments(String patientID) throws Exception {

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
            System.out.println("<< You have no scheduled appointments >>");
            System.out.println();
        } else {
            System.out.println("<< Your scheduled appointments >>");
            Table.printTable(rows);
        }

    }

    public void patientOnlyConfirmedAppointments(String patientID) throws Exception {

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

            if (appointment.getStatus().equals("Cancelled")) {
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
            System.out.println("<< You have no scheduled appointments >>");
            System.out.println();
        } else {
            System.out.println("<< Your scheduled appointments >>");
            Table.printTable(rows);
        }

    }

    /**
     * Allows a patient to schedule an appointment. The method promps the patient for doctor ID, dae and time, and then
     * schedules an available appointment
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading or writing to the file
     */
    public void patientScheduleAppointment(String patientID) throws Exception {
        
        List<Appointment> appts = readAppointments();
        System.out.println();
        System.out.println("<< Scheduling appointment >>"); 
        System.out.println();
        String doctorID;
        LocalDate date;
        LocalTime time;

        while(true) {

            patientPrintAvailableAppointments();
            
            String[] slotDetails = getSlotInput();

            if (slotDetails[2] == null) {
                return;
            }
            
            doctorID = slotDetails[0];
            date = LocalDate.parse(slotDetails[1]);
            time = LocalTime.parse(slotDetails[2]);

            int c = findAppointment(appts, doctorID, date, time);

            if (c == -1) {
                System.out.println("No such slot exists! Please enter again");
                System.out.println();
            } else if (c > 0) {
                System.out.println("Slot already taken! Please enter again");
                System.out.println();
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
        System.out.println();
        System.out.println("<< Appointment request sent! >>"); 
        System.out.println();

    }

    /**
     * Allows a patient to reschedule an appointment. The method first removes the existing appointment and then prompts
     * the patient to schedule a new one.
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading or writing to the file
     */
    public void patientRescheduleAppointment(String patientID) throws Exception {

        List<Appointment> appts = readAppointments();
        System.out.println("<< Rescheduling appointment >>"); 

        boolean hasAppointments = false;
        for (Appointment appointment : appts) {
            if (appointment.getPatientID() != null && appointment.getPatientID().equals(patientID)
                    && (appointment.getStatus().equals("Pending Confirmation") || appointment.getStatus().equals("Confirmed"))) {
                hasAppointments = true;
                break;
            }
        }

        if (!hasAppointments) {
            System.out.println("<< You have no appointments to reschedule >>");
            System.out.println();
            return; 
        }

        String doctorID;
        LocalDate date;
        LocalTime time;

        while(true) {

            patientOnlyConfirmedAppointments(patientID);

            String[] slotDetails = getSlotInput();

            if (slotDetails[2] == null) {
                return;
            }
            
            doctorID = slotDetails[0];
            date = LocalDate.parse(slotDetails[1]);
            time = LocalTime.parse(slotDetails[2]);

            int c = findAppointmentByPatient(appts, patientID, doctorID, date, time);

            if (c == -1) {
                System.out.println("You have no such appointment! Please enter again");
                System.out.println();
            } else if (c > 0) {
                System.out.println("Appointment has been cancelled or completed! Please enter again");
                System.out.println();
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

        AppointmentManager manager = new AppointmentManager();
        manager.patientScheduleAppointment(patientID);


    }

    /**
     * Cancels a scheduled appointment for a patient
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading or writing to the file
     */
    public void patientCancelAppointment(String patientID) throws Exception {
        List<Appointment> appts = readAppointments();
        System.out.println("<< Cancelling appointment >>"); 

        boolean hasAppointments = false;
        for (Appointment appointment : appts) {
            if (appointment.getPatientID() != null && appointment.getPatientID().equals(patientID)
                    && (appointment.getStatus().equals("Pending Confirmation") || appointment.getStatus().equals("Confirmed"))) {
                hasAppointments = true;
                break;
            }
        }

        if (!hasAppointments) {
            System.out.println("<< You have no appointments to cancel >>");
            System.out.println();
            return; 
        }

        String doctorID;
        LocalDate date;
        LocalTime time;

        while(true) {

            patientOnlyConfirmedAppointments(patientID);

            String[] slotDetails = getSlotInput();

            if (slotDetails[2] == null) {
                return;
            }
            
            doctorID = slotDetails[0];
            date = LocalDate.parse(slotDetails[1]);
            time = LocalTime.parse(slotDetails[2]);

            int c = findAppointmentByPatient(appts, patientID, doctorID, date, time);

            if (c == -1) {
                System.out.println("You have no such appointment! Please enter again");
                System.out.println();
            } else if (c > 0) {
                System.out.println("Appointment has been cancelled or completed! Please enter again");
                System.out.println();
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
        System.out.println();
        System.out.println("<< Appointment cancelled! >>");
        System.out.println();

    }

    /**
     * Prints all completed appointment records for a given patient
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading from the file
     */
    public void patientPrintAppointmentRecords(String patientID) throws Exception {
        
        List<Appointment> appts = readAppointments();
        boolean isEmpty = true;
        String[] headers = {
            "Doctor ID", "Doctor Name", "Date", "Time", "Service", "Med Name", "Notes"
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
                    appointment.getService(),
                    appointment.getMedName() == null ? "" : appointment.getMedName(),
                    appointment.getNotes()
                });
            }

        }

        if (isEmpty) {
            System.out.println("<< You have no completed appointments >>");
            System.out.println();
        } else {
            System.out.println("<< Your completed appointment outcome records >>");
            Table.printTable(rows);
        }

    }

    //Doctor

    public void doctorViewPersonalSchedule(String doctorId) throws Exception {
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
                            "",
                            "",
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
            System.out.println("<< Your personal schedule >>");
            Table.printTable(rows);
        }
    }

    public void doctorAddAppointments(String doctorId) throws Exception {

        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);
        LocalDate date = null;
        LocalTime time = null;

        System.out.println();
        System.out.println("<< Enter the appointment date and time >>");
        System.out.println();
        
        while(true) {
            
            System.out.println("<< Enter x to go back to the menu >> ");

            while (date == null) {
                try {
                    System.out.print("Enter the date (yyyy-MM-dd): ");
                    String dateInput = sc.nextLine();
                    if (dateInput.equalsIgnoreCase("x")) {
                        return;
                    }
                    date = LocalDate.parse(dateInput);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format! Please enter the date in yyyy-MM-dd format.");
                }
            }

            while (time == null) {
                try {
                    System.out.print("Enter the time (hh:mm): ");
                    String timeInput = sc.nextLine();
                    if (timeInput.equalsIgnoreCase("x")) {
                        return;
                    }
                    time = LocalTime.parse(timeInput);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid time format! Please enter the time in hh:mm format.");
                }
            }

            if (findAppointment(appts, doctorId, date, time) >= 0) {
                System.out.print("You have already indicated availability for this slot! Enter a new slot");
            } else {
                break;
            }
        }

        int length = appts.size() + 1;
        String number = String.format("%03d", length);
        String apptId = "AP" + number;

        Appointment newSlot = new Appointment(apptId, doctorId, null, date, time, "Unbooked", null, null, null, null);
        appts.add(newSlot);
        writeAppointments(appts);
    }

    public void doctorAppointmentRequests(String doctorId) throws Exception {
        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);
        boolean isEmpty = true;

        System.out.println("<< Enter x to go back to the menu >> ");

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

                    Table.printTable(rows);

                    while(true) {
                        System.out.print("Do you wish to accept this appointment request? (Y/N/Any to skip): ");
                        String choice = sc.nextLine();

                        if (choice.equalsIgnoreCase("Y")) {
                            appt.setStatus("Confirmed");
                            writeAppointments(appts);
                            System.out.println("Appointment confirmed!");
                            break;
                        } else if (choice.equalsIgnoreCase("N")) {
                            appt.setStatus("Cancelled");
                            writeAppointments(appts);
                            System.out.println("Appointment cancelled!");
                            break;
                        } else if (choice.equalsIgnoreCase("X")) {
                            return;
                        } else {
                            System.out.println("Appointment skipped!");
                            break;
                        }

                    }
                }
            }
        }

        if (isEmpty) {
            System.out.println("<< You have no appointment requests >>");
            System.out.println();
        }

    }

    public void doctorViewUpcomingAppointments(String doctorId) throws Exception {
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
            System.out.println("<< Your upcoming appointments >>");
            Table.printTable(rows);
        }

    }

    public Doctor doctorRecordAppointmentOutcome(String doctorId) throws Exception {
        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);
        boolean isEmpty = true;
        DoctorManager docManager = new DoctorManager();
        Doctor doc = (Doctor) docManager.createUser(doctorId);
        

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

                    Table.printTable(rows);

                    while(true) {

                        System.out.println("<< Enter x to go back to the menu >> ");
                        System.out.print("Have you completed this appointment? (Y/Any to skip): ");
                        String choice = sc.nextLine();
                        if (choice.equalsIgnoreCase("X")) {
                            return doc;
                        }
                        
                        if (choice.equalsIgnoreCase("Y")) {
                            appt.setStatus("Completed");
                            System.out.print("Service provided?: ");
                            String service = sc.nextLine();
                            if (service.equalsIgnoreCase("x")) {
                                return doc;
                            }

                            while(true) {
                                System.out.print("Medicine prescribed? (Y/N): ");
                                String a = sc.nextLine();
                                if (a.equalsIgnoreCase("X")) {
                                    return doc;
                                }
                                if (a.equalsIgnoreCase("Y")) {
                                    System.out.print("Name of medicine prescribed?: ");
                                    String medName = sc.nextLine();
                                    if (medName.equalsIgnoreCase("x")) {
                                        return doc;
                                    }
                                    appt.setMedName(medName);
                                    appt.setMedStatus("Pending");
                                    break;
                                } else if (a.equalsIgnoreCase("N")) {
                                    break;
                                } else {
                                    System.out.println("Invalid choice! Please enter Y or N: ");
                                }
                            }

                            System.out.print("Any notes?: ");
                            String notes = sc.nextLine();
                            if (notes.equalsIgnoreCase("x")) {
                                return doc;
                            }

                            appt.setService(service);
                            appt.setNotes(notes);

                            boolean found = false;
                            for (Patient p : doc.getPatients()) {
                                if (p.getId().equals(appt.getPatientID())) {
                                    found = true;
                                }
                            }
                            
                            if (found == false) {
                                docManager.addPatientByID(doc, appt.getPatientID());
                            }
                            
                            System.out.println("Appointment outcome recorded!");
                            System.out.println();
                            break;
                        } else {
                            break;
                        } 

                    }

                }
            }
        }

        if (isEmpty) {
            System.out.println();
            System.out.println("<< You have no confirmed appointments to complete >>");
            System.out.println();
            return doc;
        }

        writeAppointments(appts);
        return doc;
    }

    //Pharmacist

    public void pharmacistViewAppointmentOutcome() throws Exception {
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
            System.out.println("<< There are no completed appointments >>");
            System.out.println();
        } else {
            System.out.println("<< Medication status >>");
            Table.printTable(rows);
        }

    }

    public void pharmacistUpdatePrescriptionStatus() throws Exception {
        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);
        boolean isEmpty = true;
        System.out.println();
        System.out.println("<< Enter x to go back to the menu >> ");

        for (Appointment appointment : appts) {

            if (appointment.getStatus().equals("Completed")) {

                if (appointment.getMedStatus() == null) {
                    continue;
                }

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

                    Table.printTable(rows);

                    while(true) {
                        System.out.print("Medicine dispensed? (Y/Any to skip): ");
                        char choice = sc.next().charAt(0);
                        if (choice == 'X' || choice == 'x') {
                            return;
                        }
                        
                        if (choice == 'Y' || choice == 'y') {
                            appointment.setMedStatus("Dispensed");
                            System.out.print("Medicine status updated!");
                            break;
                        } else {
                            break;
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

    public void adminPrintAppointmentDetails() throws Exception {

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
        System.out.println();
        System.out.println("<< All appointment details >>");
        Table.printTable(rows);
    }

}
