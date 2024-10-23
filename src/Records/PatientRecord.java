package Records;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatientRecord {
    private String patientID;
    private String name;
    private Date dob;
    private Gender gender;
    private ContactInfo contactInfo;
    private BloodType bloodType;
    private List<String> diagnoses;
    private List<String> treatments;

    public PatientRecord(String patientID, String name, Date dob, Gender gender, ContactInfo contactInfo,
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
