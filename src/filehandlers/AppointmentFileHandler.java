package filehandlers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import entities.appointment;

import java.io.*;
import java.nio.file.*;


public class AppointmentFileHandler extends ItemFileHandler {


    public AppointmentFileHandler() {
        super("Database/Appointments.csv");
    }

    // public static List<appointment> readFile() throws Exception {

    //     List<appointment> appointments = new ArrayList<>();
    //     try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
    //         String line;
    //         br.readLine(); // skip header if there is one
    //         while ((line = br.readLine()) != null) {
    //             String[] fields = line.split(",", -1); // Use -1 to retain empty fields

    //             // Check each field for empty values and set to null if needed
    //             String appointmentID = fields[0];
    //             String doctorID = fields[1];
    //             String patientID = fields[2].isEmpty() ? null : fields[2];
    //             LocalDate date = LocalDate.parse(fields[3]);
    //             LocalTime time = LocalTime.parse(fields[4]);
    //             String status = fields[5].isEmpty() ? null : fields[5];
    //             String service = fields[6].isEmpty() ? null : fields[6];
    //             String medName = fields[7].isEmpty() ? null : fields[7];
    //             String medStatus = fields[8].isEmpty() ? null : fields[8];
    //             String notes = fields[9].isEmpty() ? null : fields[9];

    //             appointments.add(new appointment(appointmentID, doctorID, patientID, date, time, status, service, medName, medStatus, notes));
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return appointments;
    // }

    // public static void writeFile(List<appointment> appointments) {

    //     try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
    //         bw.write("appointmentID,doctorID,patientID,date,time,status,service,medName,medStatus,notes\n");
    //         for (appointment appointment : appointments) {
    //                 bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
    //                 appointment.getAppointmentID(),
    //                 appointment.getDoctorID(),
    //                 appointment.getPatientID() == null ? "" : appointment.getPatientID(),
    //                 appointment.getDate().toString(),
    //                 appointment.getTime().toString(),
    //                 appointment.getStatus() == null ? "" : appointment.getStatus(),
    //                 appointment.getService() == null ? "" : appointment.getService(),
    //                 appointment.getMedName() == null ? "" : appointment.getMedName(),
    //                 appointment.getMedStatus() == null ? "" : appointment.getMedStatus(),
    //                 appointment.getNotes() == null ? "" : appointment.getNotes()
    //             ));
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

}