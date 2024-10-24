package utility;

public class CheckValidity {
    public static boolean isValidEmail(String email) {
        // Regex to validate email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPhoneNumber(int phoneNumber) {
        // Check if the phone number starts with 6, 8, or 9 and has 8 digits
        return (phoneNumber >= 80000000 && phoneNumber <= 99999999) ||
                (phoneNumber >= 60000000 && phoneNumber <= 69999999);
    }
}
