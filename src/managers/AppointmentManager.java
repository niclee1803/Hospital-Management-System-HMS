package managers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.io.IOException;

import entities.Appointment;
import filehandlers.AppointmentFileHandler;

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

    public static void patientPrintAvailableAppointments() throws Exception {

        List<Appointment> appts = readAppointments();

        for (Appointment appointment : appts) {

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

    public static void patientPrintScheduledAppointments(String patientID) throws Exception {

        List<Appointment> appts = readAppointments();

        for (Appointment appointment : appts) {

            if (appointment.getPatientID() == null) {
                continue;
            }

            if (!appointment.getStatus().equals("Completed")) {

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

    }

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

    public static void patientPrintAppointmentRecords(String patientID) throws Exception {
        
        List<Appointment> appts = readAppointments();

        for (Appointment appointment : appts) {

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

    //Doctor

    public static void doctorAddAppointments(String doctorId) throws Exception {

    }

    public static void doctorAppointmentRequests(String doctorId) throws Exception {
        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);

        for (Appointment appt : appts) {

            if (appt.getDoctorID().equals(doctorId)) {
                if (appt.getStatus().equals("Pending Confirmation")) {
                    System.out.println(
                        appt.getAppointmentID() + " " +
                        appt.getPatientID() + " " +
                        appt.getDate() + " " +
                        appt.getTime()
                    );
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

        writeAppointments(appts);

    }

    public static void doctorViewUpcomingAppointments(String doctorId) throws Exception {
        List<Appointment> appts = readAppointments();

        for (Appointment appointment : appts) {

            if (appointment.getDoctorID().equals(doctorId)) {
                if (appointment.getStatus().equals("Confirmed")) {
                    System.out.println(
                        appointment.getAppointmentID() + " " +
                        appointment.getDoctorID() + " " +
                        appointment.getPatientID() + " " +
                        appointment.getDate() + " " +
                        appointment.getTime()
                    );
                }
            }
        }

    }

    public static void doctorRecordAppointmentOutcome(String doctorId) throws Exception {
        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);

        for (Appointment appt : appts) {

            if (appt.getDoctorID().equals(doctorId)) {
                if (appt.getStatus().equals("Confirmed")) {
                    System.out.println(
                        appt.getAppointmentID() + " " +
                        appt.getPatientID() + " " +
                        appt.getDate() + " " +
                        appt.getTime()
                    );
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

        writeAppointments(appts);
    }

    //Pharmacist

    public static void pharmacistViewAppointmentOutcome() throws Exception {
        List<Appointment> appts = readAppointments();

        for (Appointment appointment : appts) {

            if (appointment.getStatus().equals("Completed")) {
                if (appointment.getMedStatus() != null) {

                    System.out.println(
                        appointment.getAppointmentID() + " " +
                        appointment.getDoctorID() + " " +
                        appointment.getPatientID() + " " +
                        appointment.getMedName() + " " +
                        appointment.getMedStatus()
                    );

                }
            }

        }
    }

    public static void pharmacistUpdatePrescriptionStatus() throws Exception {
        List<Appointment> appts = readAppointments();
        Scanner sc = new Scanner(System.in);

        for (Appointment appointment : appts) {

            if (appointment.getStatus().equals("Completed")) {
                if (appointment.getMedStatus().equals("Pending")) {

                    System.out.println(
                        appointment.getAppointmentID() + " " +
                        appointment.getDoctorID() + " " +
                        appointment.getPatientID() + " " +
                        appointment.getMedName() + " " +
                        appointment.getMedStatus()
                    );

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

        writeAppointments(appts);
    }

    //Admin

    public static void adminPrintAppointmentDetails() throws Exception {

        List<Appointment> appts = readAppointments();

        for (Appointment appointment : appts) {
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

}
