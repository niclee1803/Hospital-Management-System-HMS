package Records;

import User.Patient;

import java.util.List;

public class DoctorRecord {
    private String doctorID;
    private String name;
    private List<Patient> patients;
    //private List<Appointment> appointments;

    public DoctorRecord(String doctorID, String name, List<Patient> patients) {
        this.doctorID = doctorID;
        this.name = name;
        this.patients = patients;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getName() {
        return name;
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
