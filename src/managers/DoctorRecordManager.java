package managers;

import users.Doctor;
import users.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DoctorRecordManager {

    public static Doctor loadDoctorRecord(String doctorID) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("Database/Doctor_Records.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] nextLine = line.split(","); // Split by comma
            if (nextLine[0].equals(doctorID)) {
                String name = nextLine[1];
                List<Patient> patients = new ArrayList<>();
                String[] patientIDs = nextLine[2].split(";");
                for (String patientID : patientIDs) {
                    patients.add(PatientRecordManager.loadRecord(patientID));
                }
                reader.close();
                return new Doctor(doctorID, name, patients);
            }
        }
        reader.close();
        return null;
    }
}
