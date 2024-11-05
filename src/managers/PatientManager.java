package managers;

import entities.*;
import filehandlers.PatientFileHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PatientManager extends UserManager {
    public PatientManager() {
        super(new PatientFileHandler());
    }

    public User createUser(String id) {
        String[] record = fileHandler.readLine(id);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String userId = record[0];
            String name = record[1];
            Date dob = formatter.parse(record[2]);
            Gender gender = Gender.valueOf(record[3]);
            ContactInfo contact = new ContactInfo(Integer.parseInt(record[4]), record[5]);
            BloodType bloodType = BloodType.valueOf(record[6]);
            List<String> diagnoses = Arrays.asList(record[7].split(";"));
            List<String> treatments = Arrays.asList(record[8].split(";"));
            List<String> prescriptions = Arrays.asList(record[9].split(";"));
            return new Patient(userId, name, dob, gender, contact, bloodType, diagnoses, treatments, prescriptions);
        } catch (ParseException e) {
            System.err.println("Error parsing date for record: " + Arrays.toString(record));
        } catch (NumberFormatException e) {
            System.err.println("Error parsing contact info or blood type for record: " + Arrays.toString(record));
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid gender or blood type in record: " + Arrays.toString(record));
        }
        return null;
    }

    public String[] createRecordFromUser(User user) {
        if (!(user instanceof Patient patient)) {
            throw new IllegalArgumentException("Provided user is not a Patient.");
        }

        String[] record = new String[10];
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        record[0] = patient.getId();
        record[1] = patient.getName();
        record[2] = formatter.format(patient.getDob()); // Format date of birth
        record[3] = patient.getGender().name(); // Convert enum to string
        record[4] = String.valueOf(patient.getContactInfo().getPhoneNumber()); // Convert phone number to string
        record[5] = patient.getContactInfo().getEmail();
        record[6] = patient.getBloodType().name(); // Convert enum to string
        record[7] = String.join(";", patient.getDiagnoses()); // Join list with ";" separator
        record[8] = String.join(";", patient.getTreatments()); // Join list with ";" separator
        record[9] = String.join(";", patient.getPrescriptions());

        return record;
    }

    public Patient updatePatientPhone(String patientID, int newPhone) {
        Patient patient = (Patient) createUser(patientID);
        if (patient != null) {
            patient.setPhone(newPhone);
            String[] record = createRecordFromUser(patient);
            fileHandler.updateLine(record);
        }
        return patient;
    }

    public Patient updatePatientEmail(String patientID, String newEmail) {
        Patient patient = (Patient) createUser(patientID);
        if (patient != null) {
            patient.setEmail(newEmail);
            String[] record = createRecordFromUser(patient);
            fileHandler.updateLine(record);
        }
        return patient;
    }

    public Patient updateDiagnoses(String patientID, List<String> newDiagnoses) {
        Patient patient = (Patient) createUser(patientID);
        if (patient != null) {
            patient.setDiagnoses(newDiagnoses);
            String[] record = createRecordFromUser(patient);
            fileHandler.updateLine(record);
        }
        return patient;
    }

    public Patient updateTreatments(String patientID, List<String> newTreatments) {
        Patient patient = (Patient) createUser(patientID);
        if (patient != null) {
            patient.setTreatments(newTreatments);
            String[] record = createRecordFromUser(patient);
            fileHandler.updateLine(record);
        }
        return patient;
    }

    public Patient updatePrescriptions(String patientID, List<String> newPrescriptions) {
        Patient patient = (Patient) createUser(patientID);
        if (patient != null) {
            patient.setPrescriptions(newPrescriptions);
            String[] record = createRecordFromUser(patient);
            fileHandler.updateLine(record);
        }
        return patient;
    }
}
