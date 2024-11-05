package entities;


import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private List<Patient> patients;
    //private List<Appointment> appointments;

    public Doctor(String doctorID, String name, List<Patient> patients) {
        super(doctorID, name);
        this.patients = patients;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<String> getPatientIds() {
        List<String> patientIds = new ArrayList<String>();
        for (Patient patient : patients) {
            patientIds.add(patient.getId());
        }
        return patientIds;
    }

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
