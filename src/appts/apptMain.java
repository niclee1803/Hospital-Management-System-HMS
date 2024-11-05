package appts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class apptMain {

    public static void viewAvailableAppointments() throws Exception {
        appSlots.printAppSlots();
    }

    public static void viewAppointmentRequests() throws Exception {
        appList.printApptRequests();
    }

    public static void viewAppointmentList() throws Exception {
        appList.printApptList();
    }

    public static void viewAppointmentOutcome() throws Exception{

    }

    public static void approveAppointment() throws Exception{
        
    }

    public static void addAppointmentSlots(String doctorId) throws Exception{

    }

    public static void scheduleAppointment(String patientId) throws Exception{

        Scanner sc = new Scanner(System.in);
        appSlots slot;
        while(true) {
            System.out.println("Enter the Doctor ID: ");
            String doctorId = sc.nextLine();
            System.out.println("Enter the date (yyyy-MM-dd): ");
            String date = sc.nextLine();
            System.out.println("Enter the time (hh:mm): ");
            String time = sc.nextLine();

            slot = new appSlots(doctorId, LocalDate.parse(date), LocalTime.parse(time));

            if (appSlots.findSlot(slot) == 1) {
                break;
            } else if(appSlots.findSlot(slot) == 0) {
                System.out.println("Slot already taken! Choose another slot");
            } else {
                System.out.println("No such slot exists! Please choose a valid slot");
            }

        }

        appSlots.changeAval(slot, false);
        appList newAppt = new appList(appList.generateAppointmentId(), slot.getDoctorId(), patientId, "Pending Confirmation", slot.getDate(), slot.getTime());
        appList.writeApptList(newAppt);
        System.out.println("Successfully created appointment request!");

    }

    public static void rescheduleAppointment(String patientId) throws Exception{

        Scanner sc = new Scanner(System.in);
        appSlots slot;
        while(true) {
            System.out.println("Enter the Doctor ID: ");
            String doctorId = sc.nextLine();
            System.out.println("Enter the date (yyyy-MM-dd): ");
            String date = sc.nextLine();
            System.out.println("Enter the time (hh:mm): ");
            String time = sc.nextLine();

            slot = new appSlots(doctorId, LocalDate.parse(date), LocalTime.parse(time));
            int val = appList.findAppointment(slot, patientId);

            if (val == 1) {
                break;
            } else if(val == 0) {
                System.out.println("Appointment has already been cancelled or completed! Please choose a valid appointment");
            } else {
                System.out.println("No such appointment exists under your name! Please choose a valid appointment");
            }

        }

        appList.changeStatus(slot, patientId, "Cancelled");
        appSlots.changeAval(slot, true);
        System.out.println("Previous appointment cancelled!\nSchedule new appointment:");
        scheduleAppointment(patientId);

    }

    public static void cancelAppointment() throws Exception{

    }

    public static void viewScheduledAppointments(String patientId) throws Exception{
        appList.printApptList(patientId);
    }



}
