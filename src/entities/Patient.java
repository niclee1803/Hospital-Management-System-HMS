package entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Represents a Patient, which is a type of User. A patient has a date of birth, gender, contact information, blood type,
 * and lists of diagnoses, treatments and prescriptions
 */
public class Patient extends User {
    private Date dob;
    private Gender gender;
    private ContactInfo contactInfo;
    private BloodType bloodType;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<String> prescriptions;

    /**
     * Constructs a Patient object with the specified attributes.
     * @param patientID The unique ID of the patient
     * @param name The name of the patient
     * @param dob The date of birth of the patient
     * @param gender The gender of the patient
     * @param contactInfo The contact information (phone number, email) of the patient
     * @param bloodType The blood type of the patient
     * @param diagnoses A list of diagnoses for the patient
     * @param treatments A list of treatments the patient has received or is receiving
     * @param prescriptions A list of prescriptions issues to the patient
     */
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

    /**
     * Gets the date of birth of the patient
     * @return The patient's date of birth as a Date object
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Gets the gender of the patient
     * @return The gender of the patient
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Gets the blood type of the patient
     * @return The blood type of the patient
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Gets the list of diagnoses of the patient
     * @return A list of diagnoses
     */
    public List<String> getDiagnoses() {
        return diagnoses;
    }

    /**
     * Gets the list of treatments the patient has received or is receiving
     * @return A list of treatments
     */
    public List<String> getTreatments() {
        return treatments;
    }

    /**
     * Gets the list of prescriptions for the patient
     * @return A list of prescriptions
     */
    public List<String> getPrescriptions() { return prescriptions; }

    /**
     * Gets the contact information of the patient
     * @return The contact information of the patient which includes contact number and email address
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the phone number of the patient
     * @param phoneNumber The new phone number to set
     */
    public void setPhone(int phoneNumber) {
        contactInfo.setPhoneNumber(phoneNumber);
    }

    /**
     * Sets the email address of the patient
     * @param email The new email to set
     */
    public void setEmail(String email) {
        contactInfo.setEmail(email);
    }

    /**
     * Sets the list of diagnoses for the patient
     * @param diagnoses The list of diagnoses to set
     */
    public void setDiagnoses(List<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    /**
     * Sets the list of treatments for the patient
     * @param treatments The list of treatments to set
     */
    public void setTreatments(List<String> treatments) {
        this.treatments = treatments;
    }

    /**
     * Sets the list of prescriptions for the patient
     * @param prescriptions The list of prescriptions to set.
     */
    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    /**
     * Provides a string representation of the Patient object
     * @return A formatted string containing the patient's details such as ID, name, date of birth, gender, contat information,
     * bloos type, diagnoses, treatments and prescriptions
     */
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
