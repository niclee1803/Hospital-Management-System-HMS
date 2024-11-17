package managers;

import entities.Doctor;
import entities.Gender;
import entities.Patient;
import entities.User;
import filehandlers.DoctorFileHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DoctorManager} class is responsible for managing doctor records and their associated patients.
 * It extends the {@link UserManager} class to provide specific functionality for creating and managing doctors and their patient records.
 * This class provides methods for creating doctor users, generating records from doctor objects, and updating patient-related information.
 *
 */
public class DoctorManager extends UserManager {
    private final PatientManager patientManager;

    /**
     * Constructs a new {@code DoctorManager} with an associated {@code DoctorFileHandler} and a {@code PatientManager}.
     */
    public DoctorManager() {
        super(new DoctorFileHandler());
        patientManager = new PatientManager();
    }

    /**
     * Creates a {@code Doctor} user from the given ID. The method retrieves the doctor's data, including associated patients,
     * and returns a populated {@code Doctor} object.
     *
     * @param id The unique identifier for the doctor.
     * @return The {@code Doctor} object with populated data, including a list of associated patients.
     * @throws IllegalArgumentException if the doctor ID does not exist.
     */
    public User createUser(String id) {
        String[] record = fileHandler.readLine(id);
        String userId = record[0];
        String name = record[1];
        Gender gender = Gender.valueOf(record[2].toUpperCase());
        int age = Integer.parseInt(record[3]);
        String[] patientIds = record[4].split(";");
        List<Patient> patients = new ArrayList<Patient>();
        for (String patientId : patientIds) {
            Patient patient = (Patient) patientManager.createUser(patientId);
            if (patient != null) {
                patients.add(patient);
            } else {
                System.err.println("Patient with ID " + patientId + " not found.");
            }
        }
        return new Doctor(userId, name, gender, age, patients);
    }

    /**
     * Generates a record array from the provided {@code Doctor} object.
     * The array represents the doctor's ID, name, and a list of associated patient IDs.
     * @param user The {@code User} object to convert into a record.
     * @return A string array representing the doctor's record
     * @throws IllegalArgumentException if the provided user is not a {@code Doctor}.
     */
    public String[] createRecordFromUser(User user) {
        if (!(user instanceof Doctor doctor)) {
            throw new IllegalArgumentException("Provided user is not a Doctor.");
        }
        String[] record = new String[3];
        record[0] = doctor.getId();
        record[1] = doctor.getName();
        record[2] = String.join(";", doctor.getPatientIds());

        return record;
    }

    /**
     * Updates the diagnoses for a specific patient associated with the given doctor.
     * @param doctor The doctor who is updating the patient's diagnoses.
     * @param patientID The unique identifier of the patient.
     * @param newDiagnoses A list of new diagnoses for the patient.
     * @throws IllegalArgumentException If the patient ID is not associated with the doctor
     */
    public void updatePatientDiagnoses(Doctor doctor, String patientID, List<String> newDiagnoses) {
        doctor.setPatientByID(patientID, patientManager.updateDiagnoses(patientID, newDiagnoses));
    }

    /**
     * Updates the treatments for a specific patient associated with the given doctor.
     * @param doctor The doctor who is updating the patient's treatments.
     * @param patientID The unique identifier of the patient.
     * @param newTreatments A list of new treatments for the patient.
     * @throws IllegalArgumentException If the pateint ID is not associated with the doctor.
     */
    public void updatePatientTreatments(Doctor doctor, String patientID, List<String> newTreatments) {
        doctor.setPatientByID(patientID, patientManager.updateTreatments(patientID, newTreatments));
    }

    /**
     * Updates the prescriptions for a specific patient associated with the given doctor.
     * @param doctor The doctor who is updating the patient's prescriptions.
     * @param patientID The unique identifier of the patient.
     * @param newPrescriptions A list of new prescriptions for the patient.
     * @throws IllegalArgumentException If the patient ID is not associated with the doctor
     */
    public void updatePatientPrescriptions(Doctor doctor, String patientID, List<String> newPrescriptions) {
        doctor.setPatientByID(patientID, patientManager.updatePrescriptions(patientID, newPrescriptions));
    }


    // javadoc
    public void addPatientByID(Doctor doctor, String patientId) {
        Patient patient = (Patient) patientManager.createUser(patientId);
        doctor.addPatient(patient);
        String[] record = createRecordFromUser(doctor);
        fileHandler.updateLine(record);
    }
}
