package managers;

import entities.Doctor;
import entities.Patient;
import entities.User;
import filehandlers.DoctorFileHandler;

import java.util.ArrayList;
import java.util.List;

public class DoctorManager extends UserManager {
    private final PatientManager patientManager;

    public DoctorManager() {
        super(new DoctorFileHandler());
        patientManager = new PatientManager();
    }

    public User createUser(String id) {
        String[] record = fileHandler.readLine(id);
        String userId = record[0];
        String name = record[1];
        String[] patientIds = record[2].split(";");
        List<Patient> patients = new ArrayList<Patient>();
        for (String patientId : patientIds) {
            Patient patient = (Patient) patientManager.createUser(patientId);
            if (patient != null) {
                patients.add(patient);
            } else {
                System.err.println("Patient with ID " + patientId + " not found.");
            }
        }
        return new Doctor(userId, name, patients);
    }

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

    public void updatePatientDiagnoses(Doctor doctor, String patientID, List<String> newDiagnoses) {
        doctor.setPatientByID(patientID, patientManager.updateDiagnoses(patientID, newDiagnoses));
    }

    public void updatePatientTreatments(Doctor doctor, String patientID, List<String> newTreatments) {
        doctor.setPatientByID(patientID, patientManager.updateTreatments(patientID, newTreatments));
    }

    public void updatePatientPrescriptions(Doctor doctor, String patientID, List<String> newPrescriptions) {
        doctor.setPatientByID(patientID, patientManager.updatePrescriptions(patientID, newPrescriptions));
    }
}
