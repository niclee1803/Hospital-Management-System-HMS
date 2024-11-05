package entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Patient extends User {
    private Date dob;
    private Gender gender;
    private ContactInfo contactInfo;
    private BloodType bloodType;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<String> prescriptions;

    public Patient(String patientID, String name, Date dob, Gender gender, ContactInfo contactInfo,
                   BloodType bloodType, List<String> diagnoses, List<String> treatments, List<String> prescriptions) {
        super(patientID, name);
        this.dob = dob;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
        this.prescriptions = prescriptions;
    }

    public Date getDob() {
        return dob;
    }

    public Gender getGender() {
        return gender;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public List<String> getDiagnoses() {
        return diagnoses;
    }

    public List<String> getTreatments() {
        return treatments;
    }

    public List<String> getPrescriptions() { return prescriptions; }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setPhone(int phoneNumber) {
        contactInfo.setPhoneNumber(phoneNumber);
    }

    public void setEmail(String email) {
        contactInfo.setEmail(email);
    }

    public void setDiagnoses(List<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public void setTreatments(List<String> treatments) {
        this.treatments = treatments;
    }

    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dobFormatted = formatter.format(dob);

        return "Patient Record: " + super.getId() +
        "\nName: " + super.getName() +
        "\nDate Of Birth: " + dobFormatted +
        "\nGender: " + gender + 
        "\n" + contactInfo + 
        "\nBlood Type: " + bloodType + 
        "\nDiagnoses: " + diagnoses + 
        "\nTreatments: " + treatments +
        "\nPrescriptions: " + prescriptions;
    }
}
