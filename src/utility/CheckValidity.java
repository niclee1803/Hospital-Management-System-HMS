package utility;

/**
 * {@code CheckValidity} class provides utility methods for validating various inputs
 * such as email addresses, phone numbers, and passwords.
 * It contains static methods that return boolean values to indicate whether the input is valid
 * according to specific criteria such as format and length.
 */
public class CheckValidity {
    /**
     * Validates the email address format using regular expressions.
     * The email must consist of alphanumeric characters, special symbols like '+', '-', '_', and must
     * contain '@' followed by a valid domain.
     *
     * @param email the email address to validate
     * @return {@code true} if the email is valid, otherwise {@code false}
     */
    public static boolean isValidEmail(String email) {
        // Regex to validate email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /**
     * Validates the phone number format.
     * The phone number should start with either 6, 8, or 9 and be exactly 8 digits long.
     *
     * @param phoneNumber the phone number to validate
     * @return {@code true} if the phone number is valid (starts with 6, 8, or 9 and is 8 digits long),
     *         otherwise {@code false}
     */
    public static boolean isValidPhoneNumber(int phoneNumber) {
        // Check if the phone number starts with 6, 8, or 9 and has 8 digits
        return (phoneNumber >= 80000000 && phoneNumber <= 99999999) ||
                (phoneNumber >= 60000000 && phoneNumber <= 69999999);
    }

    /**
     * Validates the password format.
     * The password must contain at least one alphabet (lowercase or uppercase), one digit,
     * and have a minimum length of 8 characters.
     *
     * @param password the password to validate
     * @return {@code true} if the password is valid (contains at least one letter, one digit,
     *         and is at least 8 characters long), otherwise {@code false}
     */
    public static boolean isValidPassword(String password) {
        // Regular expression explanation:
        // (?=.*[a-zA-Z]) : Ensures at least one alphabet (lowercase or uppercase).
        // (?=.*\\d) : Ensures at least one digit.
        // .{8,} : Ensures at least 8 characters.
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$";

        return password != null && password.matches(regex);
    }
}
