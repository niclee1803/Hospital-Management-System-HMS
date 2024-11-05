package appts;

import java.io.*;
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
    private static String fileName = "Database/Appointment_List.csv";

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

    public static void changeStatus(appSlots a, String patientId, String status){ 
        List<String> lines = new ArrayList<>();

        String dId = a.getDoctorId();
        LocalDate tempDate = a.getDate();
        LocalTime tempTime = a.getTime();

        // Read the file and update the relevant line
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                if (values[1].equals(patientId) &&
                    values[2].equals(dId) &&
                    LocalDate.parse(values[4]).equals(tempDate) &&
                    LocalTime.parse(values[5]).equals(tempTime)) {
                    
                    // Replace 'True' with 'False' in Availability
                    values[3] = status;
                    line = String.join(",", values);
                }
                
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Write the updated lines back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static int findAppointment(appSlots a, String patientId) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();  // Skip header row if present
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String pId = values[1];
                String doctorId = values[2];
                String status = values[3];
                LocalDate date = LocalDate.parse(values[4]);
                LocalTime time = LocalTime.parse(values[5]);

                if (patientId.equals(pId) && a.getDoctorId().equals(doctorId) && date.equals(a.getDate()) && time.equals(a.getTime())) {
                    if (!status.equals("Cancelled") || !status.equals("Completed")) {
                        return 1;
                    } else {
                        return 0;
                    }
                }            

                
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        return -1;
    }

}
