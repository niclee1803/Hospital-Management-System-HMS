package managers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRecordManager {

    // Method to load all appointments from CSV and return List<String[]>
    public static List<String[]> loadAllAppointments() throws Exception {
        List<String[]> appointments = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("Database/Appointment_list.csv"));
        String line;

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
