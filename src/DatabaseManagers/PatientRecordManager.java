package DatabaseManagers;

import Records.PatientRecord;
import Records.BloodType;
import Records.ContactInfo;
import Records.Gender;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class PatientRecordManager {

    public static PatientRecord loadPatientRecord(String patientID) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        BufferedReader reader = new BufferedReader(new FileReader("Database/Medical_Records.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] nextLine = line.split(","); // Split by comma
            if (nextLine[0].equals(patientID)) {
                String id = nextLine[0];
                String name = nextLine[1];
                Date dob = formatter.parse(nextLine[2]);
                Gender gender = Gender.valueOf(nextLine[3]);
                ContactInfo contact = new ContactInfo(Integer.parseInt(nextLine[4]), nextLine[5]);
                BloodType bloodType = BloodType.valueOf(nextLine[6]);
                List<String> diagnoses = Arrays.asList(nextLine[7].split(";"));
                List<String> treatments = Arrays.asList(nextLine[8].split(";"));
                reader.close(); // Close the BufferedReader before returning
                return new PatientRecord(id, name, dob, gender, contact, bloodType, diagnoses, treatments);
            }
        }
        reader.close(); // Close the BufferedReader if no record is found
        return null; // Return null if no matching patientID is found
    }
}
