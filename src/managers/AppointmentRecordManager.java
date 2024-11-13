package managers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AppointmentRecordManager} class is responsible for managing appointment records.
 * It provides functionality for loading appointment data from a CSV file and returning it as a list of arrays.
 *
 * <p>This class includes a method to load all appointments from the CSV file stored in the
 * "Database/Appointment_list.csv" path and returns the data in the form of a {@code List<String[]>},
 * where each string array represents an individual appointment record.
 *
 */
public class AppointmentRecordManager {

    /**
     * Loads all appointments from the CSV file and returns them as a list of string arrays.
     * Each string array represents an individual appointment record, with the fields separated by commas.
     * @return {@code List<String[]>} containing all appointment records from the CSV file.
     * @throws Exception If there is an error reading the CSV file or processing the data.
     */
    // Method to load all appointments from CSV and return List<String[]>
    public static List<String[]> loadAllAppointments() throws Exception {
        List<String[]> appointments = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("Database/Appointment_list.csv"));
        String line;

        //Read each line from the CSV file
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 5) { // Ensure all fields are present
                appointments.add(data); // Add each line as String[] to the list
            }
        }

        reader.close();
        return appointments;
    }
}
