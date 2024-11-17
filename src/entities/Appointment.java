package entities;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The {@code Appointment} class represents an appointment between a patient and a doctor.
 * It includes details such as the appointment ID, doctor ID, date, time, status, service, medication name and status,
 * and additional notes.
 */
public class Appointment {
    
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

    /**
     * Constructs a new {@code Appointment} with the specified details.
     *
     * @param appointmentID the ID of the appointment
     * @param doctorID the ID of the doctor associated with the appointment
     * @param patientID the ID of the patient associated with the appointment
     * @param date the date of the appointment
     * @param time the time of the appointment
     * @param status the status of the appointment (eg "Unbooked", "Pending Confirmation", "Completed")
     * @param service the service provided during the appointment
     * @param medName the name of the prescribed medication
     * @param medStatus the status of the medication (eg "Dispensed", "Pending")
     * @param notes additional notes about the appointment
     */
    public Appointment(String appointmentID, String doctorID, String patientID, LocalDate date, LocalTime time, String status, String service, String medName, String medStatus, String notes) {
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

    /**
     * Gets the appointment ID
     *
     * @return the ID of the appointment
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment ID.
     *
     * @param appointmentID the ID to set for the appointment
     */
    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the doctorID
     *
     * @return the ID of the doctor
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the Doctor ID.
     *
     * @param doctorID the ID to set for the doctor
     */
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Gets the patient ID
     *
     * @return the ID of the patient
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient ID
     *
     * @param patientID the ID to set for the patient
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Gets the date of the appointment.
     *
     * @return the date of the appointment
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the appointment
     *
     * @param date the date to set for the appointment
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the time of the appointment
     *
     * @return the time of the appointment
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the time of the appointment
     *
     * @param time the time to set for the appointment
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * Gets the status of the appointment
     *
     * @return the status of the appointment
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the appointment
     *
     * @param status the status to set for the appointment
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the service provided during the appointment
     *
     * @return the service provided
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the service provided during the appointment
     *
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Gets the name of the prescribed medication
     *
     * @return the name of the medication
     */
    public String getMedName() {
        return medName;
    }

    /**
     * Sets the name of the prescribed medication
     *
     * @param medName the name of the medication to set
     */
    public void setMedName(String medName) {
        this.medName = medName;
    }

    /**
     * Gets the status of the prescribed medication
     *
     * @return the status of the medication
     */
    public String getMedStatus() {
        return medStatus;
    }

    /**
     * Sets the status of the prescribed medication
     *
     * @param medStatus the status of the medication to set
     */
    public void setMedStatus(String medStatus) {
        this.medStatus = medStatus;
    }

    /**
     * Gets the notes associated with the appointment
     *
     * @return additional notes about the appointment
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes associated with the appointment
     *
     * @param notes additional notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
