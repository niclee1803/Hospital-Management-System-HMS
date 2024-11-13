package appointments;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * The {@code appointmentFileHandler} class handles the reading and writing of appointment data to and from
 * a CSV file. It provides utility methods to read from the file and return a list of appointments, and to
 * write a list of appointments back to the file
 */
public class appointmentFileHandler {

    private static String fileName = "src/appointments/Appointments.csv";

    /**
     * Reads the appointment data from the CSV file and returns a list of {@code appointment} objects.
     * This method parses the CSV file line by line, converting each record into an {@code appointment} object.
     *
     * @return Returns a list of {@code appointment} objects read from the CSV file
     * @throws Exception if an error occurs while reading the file
     */
    public static List<appointment> readFile() throws Exception {

        List<appointment> appointments = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            br.readLine(); // skip header if there is one
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1); // Use -1 to retain empty fields

                // Check each field for empty values and set to null if needed
                String appointmentID = fields[0];
                String doctorID = fields[1];
                String patientID = fields[2].isEmpty() ? null : fields[2];
                LocalDate date = LocalDate.parse(fields[3]);
                LocalTime time = LocalTime.parse(fields[4]);
                String status = fields[5].isEmpty() ? null : fields[5];
                String service = fields[6].isEmpty() ? null : fields[6];
                String medName = fields[7].isEmpty() ? null : fields[7];
                String medStatus = fields[8].isEmpty() ? null : fields[8];
                String notes = fields[9].isEmpty() ? null : fields[9];

                appointments.add(new appointment(appointmentID, doctorID, patientID, date, time, status, service, medName, medStatus, notes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Writes the list of {@code appointment} objects to the CSV file
     * The existing file content will be overwritten and the method writes a header followed by each appointment
     * as a new line in the CSV format
     *
     * @param appointments The list of {@code appointment} objects to be written to the file
     */
    public static void writeFile(List<appointment> appointments) {

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileName))) {
            //Write the header

            bw.write("appointmentID,doctorID,patientID,date,time,status,service,medName,medStatus,notes\n");

            //Write each appointment in CSV format
            for (appointment appointment : appointments) {
                    bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                    appointment.getAppointmentID(),
                    appointment.getDoctorID(),
                    appointment.getPatientID() == null ? "" : appointment.getPatientID(),
                    appointment.getDate().toString(),
                    appointment.getTime().toString(),
                    appointment.getStatus() == null ? "" : appointment.getStatus(),
                    appointment.getService() == null ? "" : appointment.getService(),
                    appointment.getMedName() == null ? "" : appointment.getMedName(),
                    appointment.getMedStatus() == null ? "" : appointment.getMedStatus(),
                    appointment.getNotes() == null ? "" : appointment.getNotes()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}