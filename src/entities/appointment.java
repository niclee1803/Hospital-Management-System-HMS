package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class appointment {
    
    private String appointmentID;
    private String doctorID;
    private String patientID;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private String service;
    private String medName;
    private String medStatus;
    private String notes;

    public appointment(String appointmentID, String doctorID, String patientID, LocalDate date, LocalTime time, String status, String service, String medName, String medStatus, String notes) {
        this.appointmentID = appointmentID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.date = date;
        this.time = time;
        this.status = status;
        this.service = service;
        this.medName = medName;
        this.medStatus = medStatus;
        this.notes = notes;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    // Getter and Setter for doctorID
    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    // Getter and Setter for patientID
    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    // Getter and Setter for date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter and Setter for time
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter and Setter for service
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    // Getter and Setter for medName
    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    // Getter and Setter for medStatus
    public String getMedStatus() {
        return medStatus;
    }

    public void setMedStatus(String medStatus) {
        this.medStatus = medStatus;
    }

    // Getter and Setter for notes
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
