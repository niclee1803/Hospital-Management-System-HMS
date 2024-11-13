package entities;
public class ContactInfo {
    private int phoneNumber;
    private String email;

    /**
     * Constructor to initialize the ContactInfo object with a phone number and email
     *
     * @param phoneNumber the phone number of the contact
     * @param email the email address of the contact
     */
    public ContactInfo(int phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Gets the phone number of the contact
     *
     * @return the phone number
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the email address of the contact
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the phone number of the contact
     * @param phoneNumber the phone number to be set
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the email address of the contact
     * @param email the email address to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the contact information
     * @return A formatted string with the phone number and email
     */
    @Override
    public String toString() {
        return "Phone: " + phoneNumber + "\nEmail: " + email;
    }
}
