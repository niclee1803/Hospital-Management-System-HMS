package Records;
public class ContactInfo {
    private int phoneNumber;
    private String email;
    
    public ContactInfo(int phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Phone: " + phoneNumber + "\nEmail: " + email;
    }
}
