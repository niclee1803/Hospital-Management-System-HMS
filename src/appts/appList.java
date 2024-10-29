package appts;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import managers.DoctorRecordManager;
import managers.PatientRecordManager;
import users.Doctor;
import users.Patient;

public class appList extends appSlots {

    protected String appointmentId;
    protected String patientId;
    protected String status;

    public appList(String aptId, String dId, String pId, String status, LocalDate date, LocalTime time) {
        
        super(dId, date, time);
        appointmentId = aptId;
        patientId = pId;
        this.status = status;

    }

    public String getPatientId() {
        return patientId;
    }

    public String getStatus() {
        return status;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public static String generateAppointmentId() {

        String fileName = "Database/Appointment_List.csv";

        long lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        String appointmentId = "A" + String.format("%03d", lines);

        return appointmentId;
    }

    public void viewApptList() throws Exception {

        Doctor doctor = DoctorRecordManager.loadDoctorRecord(doctorId); 
        Patient patient = PatientRecordManager.loadRecord(patientId);     

        System.out.println("AppointmentID: " + appointmentId+" Doctor: "+doctor.getName()+" Patient: "+ patientId+" Status: "+ status +" Date: "+date+" Time: "+time);

    }

    public static void writeApptList(appList a) {
        String dId = a.doctorId;
        String pId = a.patientId;
        String stat = a.status;
        LocalDate wDate = a.date;
        LocalTime wTime = a.time;
        String aptId = a.appointmentId;
        
        String fileName = "Database/Appointment_List.csv";

        String newLine = aptId + "," + pId + "," + dId + "," + stat + "," + wDate + "," + wTime + "\n";

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(newLine);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

    }

    public static void printApptList() throws Exception {
        List<appList> lists = new ArrayList<>();    
        
        String fileName = "Database/Appointment_List.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();  // Skip header row if present
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String aptId = values[0];
                String doctorId = values[1];
                String patientId = values[2];
                String status = values[3];
                
                // Parse date using dd-MM-yyyy format
                LocalDate date = LocalDate.parse(values[4]);
                
                // Parse time as HH:mm
                LocalTime time = LocalTime.parse(values[5]);

                // Create an AppointmentSlot object
                appList list = new appList(aptId, patientId, doctorId, status, date, time);

                // Add to the list
                lists.add(list);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        for (appList list : lists) {

            list.viewApptList();
        }
    }

    public static void printApptRequests() throws Exception {
        List<appList> lists = new ArrayList<>();    
        
        String fileName = "Database/Appointment_List.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();  // Skip header row if present
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String aptId = values[0];
                String doctorId = values[1];
                String patientId = values[2];
                String status = values[3];
                
                // Parse date using dd-MM-yyyy format
                LocalDate date = LocalDate.parse(values[4]);
                
                // Parse time as HH:mm
                LocalTime time = LocalTime.parse(values[5]);

                // Create an AppointmentSlot object
                appList list = new appList(aptId, patientId, doctorId, status, date, time);

                // Add to the list
                lists.add(list);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        for (appList list : lists) {
            if(list.getStatus().equals("pending confirmation")) {
                list.viewApptList();
            }
        }
    }

    public static void printApptList(String pId) throws Exception {
        List<appList> lists = new ArrayList<>();    
        
        String fileName = "Database/Appointment_List.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();  // Skip header row if present
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String aptId = values[0];
                String doctorId = values[1];
                String patientId = values[2];
                String status = values[3];
                
                // Parse date using dd-MM-yyyy format
                LocalDate date = LocalDate.parse(values[4]);
                
                // Parse time as HH:mm
                LocalTime time = LocalTime.parse(values[5]);

                // Create an AppointmentSlot object
                appList list = new appList(aptId, patientId, doctorId, status, date, time);

                // Add to the list
                lists.add(list);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        for (appList list : lists) {
            if(list.getPatientId().equals(pId)) {
                list.viewApptList();
            }
        }
    }

}
