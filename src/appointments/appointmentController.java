package appointments;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * The (@code appointmentController} class provides methods to manage the scheduling, rescheduling
 * and cancellation of appointments for patients. It interacts with the {@code appointmentFileHandler}
 * to read and write appointments from a CSV file and provides functionalities such as finding appointments,
 * printing available appointments, and displaying patient-specific appointment records.
 */
public class appointmentController {

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
    public static int findAppointment(List<appointment> appt, String dId, LocalDate date, LocalTime time) {

        for (appointment appointment : appt) {
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
    public static int findAppointmentByPatient(List<appointment> appt, String patientID, String dId, LocalDate date, LocalTime time) {

        for (appointment appointment : appt) {
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

    /**
     * Prints all the appointments read from the CSV file
     *
     * @throws Exception if there ia an error reading from the file
     */
    public static void printAppointments() throws Exception {

        List<appointment> appts = appointmentFileHandler.readFile();

        for (appointment appointment : appts) {
            System.out.println(
                appointment.getAppointmentID() + " " +
                appointment.getDoctorID() + " " +
                appointment.getPatientID() + " " +
                appointment.getDate() + " " +
                appointment.getTime() + " " +
                appointment.getStatus() +  " " +
                appointment.getMedName() + " " +
                appointment.getMedStatus() + " " +
                appointment.getNotes()
            );
        }

    }

    //Main functionality methods

    /**
     * Prints avaialble (unbooked) appointment slots
     *
     * @throws Exception if there is an error reading from the file
     */
    public static void printAvailableAppointments() throws Exception {

        List<appointment> appts = appointmentFileHandler.readFile();

        for (appointment appointment : appts) {

            if (appointment.getStatus().equals("Unbooked")) {
                System.out.println(
                appointment.getAppointmentID() + " " +
                appointment.getDoctorID() + " " +
                appointment.getDate() + " " +
                appointment.getTime()
            );
            }

        }

    }

    /**
     * Prints all scheduled appointments for a given patient
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading from the file
     */
    public static void patientPrintScheduledAppointments(String patientID) throws Exception {

        List<appointment> appts = appointmentFileHandler.readFile();

        for (appointment appointment : appts) {

            if (appointment.getPatientID() == null) {
                continue;
            }

            if (!appointment.getStatus().equals("Completed"))

            if (appointment.getPatientID().equals(patientID)) {
                System.out.println(
                appointment.getAppointmentID() + " " +
                appointment.getDoctorID() + " " +
                appointment.getPatientID() + " " +
                appointment.getDate() + " " +
                appointment.getTime() + " " +
                appointment.getStatus()
            );
            }

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
        
        List<appointment> appts = appointmentFileHandler.readFile();
        System.out.println("Scheduling appointment..."); 

        String doctorID;
        LocalDate date;
        LocalTime time;

        while(true) {
            
            String[] slotDetails = appointmentController.getSlotInput();
            
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

        for (appointment appointment : appts) {
            if (appointment.getDoctorID().equals(doctorID) && appointment.getDate().equals(date) && appointment.getTime().equals(time)) {
                appointment.setPatientID(patientID);
                appointment.setStatus("Pending Confirmation");
            }
        }

        appointmentFileHandler.writeFile(appts);
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

        List<appointment> appts = appointmentFileHandler.readFile();
        System.out.println("Rescheduling appointment..."); 

        String doctorID;
        LocalDate date;
        LocalTime time;

        while(true) {
            String[] slotDetails = appointmentController.getSlotInput();
            
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

        for (appointment appointment : appts) {

            if (appointment.getPatientID() == null) {
                continue;
            }

            if (appointment.getPatientID().equals(patientID) && appointment.getDoctorID().equals(doctorID) && appointment.getDate().equals(date) && appointment.getTime().equals(time)) {
                appointment.setPatientID(null);
                appointment.setStatus("Unbooked");
            }
        }

        appointmentFileHandler.writeFile(appts);

        appointmentController.patientScheduleAppointment(patientID);


    }

    /**
     * Cancels a scheduled appointment for a patient
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading or writing to the file
     */
    public static void patientCancelAppointment(String patientID) throws Exception {
        List<appointment> appts = appointmentFileHandler.readFile();
        System.out.println("Cancelling appointment..."); 

        String doctorID;
        LocalDate date;
        LocalTime time;

        while(true) {
            String[] slotDetails = appointmentController.getSlotInput();
            
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

        for (appointment appointment : appts) {

            if (appointment.getPatientID() == null) {
                continue;
            }

            if (appointment.getPatientID().equals(patientID) && appointment.getDoctorID().equals(doctorID) && appointment.getDate().equals(date) && appointment.getTime().equals(time)) {
                appointment.setStatus("Cancelled");
            }
        }

        appointmentFileHandler.writeFile(appts);
        System.out.println("Appointment cancelled!");

    }

    /**
     * Prints all completed appointment records for a given patient
     *
     * @param patientID the patient's ID
     * @throws Exception if there is an error reading from the file
     */
    public static void patientPrintAppointmentRecords(String patientID) throws Exception {
        
        List<appointment> appts = appointmentFileHandler.readFile();

        for (appointment appointment : appts) {

            if (appointment.getPatientID() == null) {
                continue;
            }

            if (!(appointment.getStatus().equals("Completed"))) {
                continue;
            }

            if (appointment.getPatientID().equals(patientID)) {
                System.out.println(
                appointment.getAppointmentID() + " " +
                appointment.getDoctorID() + " " +
                appointment.getPatientID() + " " +
                appointment.getDate() + " " +
                appointment.getTime() + " " +
                appointment.getStatus() + " " +
                appointment.getService() + " " +
                appointment.getMedName() + " " +
                appointment.getMedStatus() + " " + 
                appointment.getNotes()
            );
            }

        }

    }

}
