// package appts;

// import java.io.BufferedReader;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.List;

// // import managers.*;
// // import users.Patient;
// // import users.Doctor;

// // public class appReqs extends appSlots {
    
// //     protected static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
// //     protected String patientId;
// //     protected String status;

// //     public String getPatientId() {
// //         return patientId;
// //     }

// //     public String getStatus() {
// //         return status;
// //     }

// //     public appReqs(String dId, String pId, String status, LocalDate date, LocalTime time) {
// //         super(dId, date, time);
// //         patientId = pId;
// //         this.status = status;
// //     }

// //     public void viewRequest() throws Exception {

// //         Doctor doctor = DoctorRecordManager.loadDoctorRecord(doctorId); 
// //         Patient patient = PatientRecordManager.loadRecord(patientId);     

// //         System.out.println("Doctor: "+doctor.getName()+"Patient: "+ patientId+" Status: "+ status +" Date: "+date+" Time: "+time);

// //     }

// //     public static void printAppRequests() throws Exception {
// //         List<appReqs> requests = new ArrayList<>();    
        
// //         String fileName = "Database/Appointment_Requests.csv";

// //         try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
// //             String line;
// //             br.readLine();  // Skip header row if present
// //             while ((line = br.readLine()) != null) {
// //                 String[] values = line.split(",");
// //                 String doctorId = values[0];
// //                 String patientId = values[1];
// //                 String status = values[2];
                
// //                 // Parse date using dd-MM-yyyy format
// //                 LocalDate date = LocalDate.parse(values[3]);
                
// //                 // Parse time as HH:mm
// //                 LocalTime time = LocalTime.parse(values[4]);

// //                 // Create an AppointmentSlot object
// //                 appReqs request = new appReqs(patientId, doctorId, status, date, time);

// //                 // Add to the list
// //                 requests.add(request);
// //             }

// //         } catch (FileNotFoundException e) {
// //             System.err.println("File not found: " + e.getMessage());
// //         } catch (IOException e) {
// //             System.err.println("Error reading file: " + e.getMessage());
// //         }

// //         for (appReqs request : requests) {
// //             request.viewRequest();
// //         }
// //     }

// //     public static void writeAppRequest(appReqs a) {
// //         String dId = a.doctorId;
// //         String pId = a.patientId;
// //         String stat = a.status;
// //         LocalDate wDate = a.date;
// //         LocalTime wTime = a.time;
        
// //         String fileName = "Database/Appointment_Requests.csv";

// //         String newLine = pId + "," + dId + "," + stat + "," + wDate + "," + wTime + "\n";

// //         try (FileWriter writer = new FileWriter(fileName, true)) {
// //             writer.append(newLine);
// //         } catch (IOException e) {
// //             System.err.println("Error writing to file: " + e.getMessage());
// //         }

// //     }

// // }