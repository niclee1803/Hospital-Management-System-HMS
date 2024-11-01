package users;


import java.util.List;

public class Doctor extends User {
    private List<Patient> patients;
    //private List<Appointment> appointments;

    public Doctor(String doctorID, String name, List<Patient> patients) {
        super(doctorID, name);
        this.patients = patients;
    }

    public String getID() {
        return super.getId();
    }

    public String getName() {
        return super.getName();
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
