package managers;

import entities.*;
import filehandlers.PatientFileHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The {@code PatientManager} class manages the creation, updating and handling of patient records. It uses the
 * {@code PatientFileHandler} to read, update and store patient data in a CSV file. The class handles the conversion
 * between raw data (from CSV) and {@code Patient} objects as well as updating various patient details such as contact
 * information, diagnoses, treatments and prescriptions.
 */
public class PatientManager extends UserManager {
    /**
     * Constructs a new {@code PatientManager} and initializes thw {@code PatientFileHandler} to handle storing patient
     * records in a CSV file.
     */
    public PatientManager() {
        super(new PatientFileHandler());
    }

    /**
     * Creates a {code Patient} object from the provided pateint ID by reading the associated record from the CSV file.
     * The method parses the relevant details such as name, date of birth, gender, contact information, blood type,
     * diagnoses, treatments and prescriptions
     * @param id The ID of the patient
     * @return A {@code Patient} object with the corresponding data, or {@code null} if any error occurs
     */
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

    /**
     * Converts a {@code Patient} object into a record array format suitable for storage in the CSV file.
     * The method converts all attributes to their corresponding string formats, inclduing formatting the date of birth
     * and converting enum values into strings
     * @param user the {@code User} object (must be an instance of {@code Patient}).
     * @return An array of strings representing the patient's record.
     * @throws IllegalArgumentException If the provided user is npt a {@code Patient}.
     */
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

    /**
     * Updates the phone number for a petient with the specified ID. The patient's record is updated with the new phone
     * number in the CSV file.
     * @param patientID The ID of the patient
     * @param newPhone The new phone number to update
     * @return The updated {@code Patient} object, or {@code null} if the patient cannot be found.
     */
    public Patient updatePatientPhone(String patientID, int newPhone) {
        Patient patient = (Patient) createUser(patientID);
        if (patient != null) {
            patient.setPhone(newPhone);
            String[] record = createRecordFromUser(patient);
            fileHandler.updateLine(record);
        }
        return patient;
    }

    /**
     * Updates the email address for a patient with the specified ID. The patient's record is updated with the new email
     * in the CSV file.
     * @param patientID The ID of the patient.
     * @param newEmail The new email address to update.
     * @return The updaated {@code Patient} object, or {@code null} if the patient is not found.
     */
    public Patient updatePatientEmail(String patientID, String newEmail) {
        Patient patient = (Patient) createUser(patientID);
        if (patient != null) {
            patient.setEmail(newEmail);
            String[] record = createRecordFromUser(patient);
            fileHandler.updateLine(record);
        }
        return patient;
    }

    /**
     * Updates the diagnoses for a patient with the specified ID. The patient's record is updated with the new diagnoses
     * list in the CSV file
     * @param patientID The ID of the patient
     * @param newDiagnoses The list of new diagnoses to update
     * @return The updated {@code Patient} object, or {@code null} if the patient cannot be found
     */
    public Patient updateDiagnoses(String patientID, List<String> newDiagnoses) {
        Patient patient = (Patient) createUser(patientID);
        if (patient != null) {
            patient.setDiagnoses(newDiagnoses);
            String[] record = createRecordFromUser(patient);
            fileHandler.updateLine(record);
        }
        return patient;
    }

    /**
     * Updates the treatments for a patient with the specified ID. The patient's record is updated with the new treatments list
     * in the CSV file.
     * @param patientID The ID of the patient.
     * @param newTreatments The list of new treatments to update.
     * @return The updated {@code Patient} object, or {@code null} if the patient cannot be found.
     */
    public Patient updateTreatments(String patientID, List<String> newTreatments) {
        Patient patient = (Patient) createUser(patientID);
        if (patient != null) {
            patient.setTreatments(newTreatments);
            String[] record = createRecordFromUser(patient);
            fileHandler.updateLine(record);
        }
        return patient;
    }

    /**
     * Updates the prescriptions for a patient with the specified ID.
     * The patient's record is updated with the new prescriptions list in the CSV file.
     * @param patientID The ID of the patient.
     * @param newPrescriptions The list of new prescriptions to update.
     * @return The updated {@code Patient} object, or {@code null} if the patient cannot be found.
     */
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
