package entities;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents a doctor, which is a type of User. A doctor has a list of patients and can manage them
 */
public class Doctor extends User {
    private Gender gender;
    private int age;
    private List<Patient> patients;
    //private List<Appointment> appointments;

    /**
     * Constructs a Doctor with the specified ID, name and list of patients
     * @param doctorID the unique ID of the doctor
     * @param name the name of the doctor
     * @param gender the gender of the doctor
     * @param age the age of the doctor
     * @param patients the list of patients assigned to the doctor
     */
    public Doctor(String doctorID, String name, Gender gender, int age, List<Patient> patients) {
        super(doctorID, name);
        this.gender = gender;
        this.age = age;
        this.patients = patients;
    }

    /**
     * Gets the list of patients assigned to this doctor
     * @return A list of Patient objects
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * Gets a list of patient IDs assigned to this doctor
     * @return A list of patient IDs as Strings
     */
    public List<String> getPatientIds() {
        List<String> patientIds = new ArrayList<String>();
        for (Patient patient : patients) {
            patientIds.add(patient.getId());
        }
        return patientIds;
    }

    /**
     * Updates a patient in the list by matching the patient ID.
     * @param patientID the ID of the patient to update
     * @param updatedPatient the updated Patient object
     */
    public void setPatientByID(String patientID, Patient updatedPatient) {
        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            if (p.getId().equals(patientID)) {
                patients.set(i, updatedPatient);
                break;
            }
        }
    }

}
