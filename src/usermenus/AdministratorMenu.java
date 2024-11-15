package usermenus;

import managers.AppointmentManager;
import managers.AppointmentRecordManager;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.ArrayList;

public class AdministratorMenu implements IHasMenu {
   private String adminID;

   public AdministratorMenu(String adminID) {
       this.adminID = adminID;
   }

   @Override
   public void displayMenu() throws Exception {
       while (true) {
           System.out.println("Welcome Administrator " + adminID + ",\n");

           System.out.println("(1) View and Manage Hospital Staff (Coming Soon)\n" +
                              "(2) View Appointments Details\n" +
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

   private void viewAppointmentsDetails() throws Exception {
       System.out.println("<<Appointment View>>");
       System.out.println("Enter (X) to return\n");

       List<String[]> appointments = AppointmentRecordManager.loadAllAppointments();
       if (appointments.isEmpty()) {
           System.out.println("No appointments found.\n");
           return;
       }

       // Group appointments by date using a TreeMap to maintain sorted order by date
       Map<String, List<String[]>> appointmentsByDate = new TreeMap<>();
       for (String[] appointment : appointments) {
           String date = appointment[4]; // DateTime field

           // Add appointment to the list for the corresponding date
           appointmentsByDate
               .computeIfAbsent(date, k -> new ArrayList<>())
               .add(appointment);
       }

       // Display appointments grouped by date
       for (Map.Entry<String, List<String[]>> entry : appointmentsByDate.entrySet()) {
           String date = entry.getKey();
           List<String[]> appointmentsForDate = entry.getValue();

           System.out.println("\n" + date + " (underline this format)"); // Display date header
           int count = 1;
           for (String[] appointment : appointmentsForDate) {
               String appointmentID = appointment[0];
               String patientID = appointment[1];
               String doctorID = appointment[2];
               String status = appointment[3];

               System.out.println(count + ". " + appointmentID);
               System.out.println("   Patient ID: " + patientID);
               System.out.println("   Doctor ID: " + doctorID);
               System.out.println("   Status: " + status);
               count++;
           }
       }

       System.out.println("\nPress enter to continue...");
       new Scanner(System.in).nextLine();
   }

   private void viewAndManageHospitalStaff() {
       // Placeholder for future implementation
   }

   private void viewAndManageMedicationInventory() {
       // Placeholder for future implementation
   }

   private void approveReplenishmentRequests() {
       // Placeholder for future implementation
   }
}
