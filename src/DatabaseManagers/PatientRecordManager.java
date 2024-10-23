package DatabaseManagers;

import Records.PatientRecord;
import Records.BloodType;
import Records.ContactInfo;
import Records.Gender;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
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

    public static boolean updatePatientPhone(String patientID, int newPhone) throws Exception {
        PatientRecord patientRecord = loadPatientRecord(patientID);
        if (patientRecord == null) {
            return false;
        }
        patientRecord.setPhone(newPhone);
        return storeRecord(patientRecord);
    }

    public static boolean updatePatientEmail(String patientID, String newEmail) throws Exception {
        PatientRecord patientRecord = loadPatientRecord(patientID);
        if (patientRecord == null) {
            return false;
        }
        patientRecord.setEmail(newEmail);
        return storeRecord(patientRecord);
    }

    public static boolean storeRecord(PatientRecord patientRecord) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String patientID = patientRecord.getPatientID();
        List<String> fileContent = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("Database/Medical_Records.csv"));
        String line;
        boolean recordFound = false;

        // Read the file line by line and either update or preserve the existing lines
        while ((line = reader.readLine()) != null) {
            String[] nextLine = line.split(","); // Split by comma
            if (nextLine[0].equals(patientID)) {
                // This is the record to update, replace it with the new patientRecord details
                String updatedRecord = String.join(",", new String[]{
                        patientRecord.getPatientID(),
                        patientRecord.getName(),
                        formatter.format(patientRecord.getDob()),
                        patientRecord.getGender().toString(),
                        String.valueOf(patientRecord.getContactInfo().getPhoneNumber()),
                        patientRecord.getContactInfo().getEmail(),
                        patientRecord.getBloodType().toString(),
                        String.join(";", patientRecord.getDiagnoses()),
                        String.join(";", patientRecord.getTreatments())
                });
                fileContent.add(updatedRecord);
                recordFound = true;
            } else {
                // If the line doesn't match the patientID, keep it as is
                fileContent.add(line);
            }
        }
        reader.close();

        // If the patient ID was not found, append the new record
        if (!recordFound) {
            String newRecord = String.join(",", new String[]{
                    patientRecord.getPatientID(),
                    patientRecord.getName(),
                    formatter.format(patientRecord.getDob()),
                    patientRecord.getGender().toString(),
                    String.valueOf(patientRecord.getContactInfo().getPhoneNumber()),
                    patientRecord.getContactInfo().getEmail(),
                    patientRecord.getBloodType().toString(),
                    String.join(";", patientRecord.getDiagnoses()),
                    String.join(";", patientRecord.getTreatments())
            });
            fileContent.add(newRecord);
        }

        // Write the updated content back to the CSV file
        BufferedWriter writer = new BufferedWriter(new FileWriter("Database/Medical_Records.csv"));
        for (String record : fileContent) {
            writer.write(record);
            writer.newLine(); // Write each record on a new line
        }
        writer.close();
        return true;
    }
}
