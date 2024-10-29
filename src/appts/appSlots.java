package appts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import users.Doctor;
import managers.DoctorRecordManager;

public class appSlots {

    protected String doctorId;
    protected LocalDate date;
    protected LocalTime time;
    private Boolean isAvailable;

    public appSlots(String dId, LocalDate date, LocalTime time, Boolean isAval) {
        doctorId = dId;
        this.date = date;
        this.time = time;
        isAvailable = isAval;
    }

    public appSlots(String dId, LocalDate date, LocalTime time) {
        doctorId = dId;
        this.date = date;
        this.time = time;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Boolean getAvailability() {
        return isAvailable;
    }

    public void viewSlot() throws Exception {

        Doctor doctor = DoctorRecordManager.loadDoctorRecord(doctorId);     

        System.out.println("Doctor: "+doctor.getName()+" Date: "+date+" Time: "+time);
    }

    public static void printAppSlots() throws Exception {
        List<appSlots> slots = new ArrayList<>();    
        
        String fileName = "Database/Appointment_Slots.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();  // Skip header row if present
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String doctorId = values[0];
                
                // Parse date using dd-MM-yyyy format
                LocalDate date = LocalDate.parse(values[1]);
                
                // Parse time as HH:mm
                LocalTime time = LocalTime.parse(values[2]);

                Boolean aval = Boolean.parseBoolean(values[3]);

                // Create an AppointmentSlot object
                appSlots slot = new appSlots(doctorId, date, time, aval);

                // Add to the list
                slots.add(slot);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        for (appSlots slot : slots) {
            if (slot.getAvailability()) {
                slot.viewSlot();
            }
            
        }
    }

    public static int findSlot(appSlots a) {

        String fileName = "Database/Appointment_Slots.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();  // Skip header row if present
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String doctorId = values[0];
                LocalDate date = LocalDate.parse(values[1]);
                LocalTime time = LocalTime.parse(values[2]);
                Boolean isAval = Boolean.parseBoolean(values[3]);

                if (a.getDoctorId().equals(doctorId) && date.equals(a.getDate()) && time.equals(a.getTime())) {
                    if (isAval) {
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

    // public static void deleteSlot(appSlots s) {

    //     String doctorId = s.getDoctorId();
    //     LocalDate date = s.getDate();
    //     LocalTime time = s.getTime();
        
    //     String fileName = "Database/Appointment_Slots.csv";
    //     List<String> updatedLines = new ArrayList<>();
        
    //     try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
    //         String line;
    //         boolean headerSkipped = false;

    //         while ((line = br.readLine()) != null) {
    //             if (!headerSkipped) {
    //                 // Preserve the header line
    //                 updatedLines.add(line);
    //                 headerSkipped = true;
    //                 continue;
    //             }
                
    //             // Split the line by comma and check for match with target appointment
    //             String[] values = line.split(",");
    //             String currentDoctorId = values[0];
    //             LocalDate currentDate = LocalDate.parse(values[1]);
    //             LocalTime currentTime = LocalTime.parse(values[2]);

    //             // Add line to updated list only if it does NOT match the criteria
    //             if (!(currentDoctorId.equals(doctorId) && currentDate.equals(date) && currentTime.equals(time))) {
    //                 updatedLines.add(line);
    //             }
    //         }

    //         // Write the updated list back to the file, replacing the old content
    //         Files.write(Paths.get(fileName), updatedLines);

    //     } catch (IOException e) {
    //         System.err.println("Error processing file: " + e.getMessage());
    //     }
    // }
}
