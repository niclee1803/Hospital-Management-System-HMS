package User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Patient {
    private String patientID;
    private String name;
    private Date dob;
    private Gender gender;
    private ContactInfo contactInfo;
    private BloodType bloodType;
    private List<String> diagnoses;
    private List<String> treatments;

    public Patient(String patientID, String name, Date dob, Gender gender, ContactInfo contactInfo,
                   BloodType bloodType, List<String> diagnoses, List<String> treatments) {
        this.patientID = patientID;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
    }

    public String getName() {
        return name;
    }

    public String getPatientID() {
        return patientID;
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

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setPhone(int phoneNumber) {
        contactInfo.setPhoneNumber(phoneNumber);
    }

    public void setEmail(String email) {
        contactInfo.setEmail(email);
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dobFormatted = formatter.format(dob);

        return "Patient Record: " + patientID + 
        "\nName: " + name + 
        "\nDate Of Birth: " + dobFormatted +
        "\nGender: " + gender + 
        "\n" + contactInfo + 
        "\nBlood Type: " + bloodType + 
        "\nDiagnoses: " + diagnoses + 
        "\nTreatments: " + treatments;
    }
}
