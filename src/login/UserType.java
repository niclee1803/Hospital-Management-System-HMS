package login;

/**
 * Represents different types of users in the system.
 * <p>
 * The {@code UserType} enum defines two user types:
 * - {@link #PATIENT} for patients using the system.
 * - {@link #STAFF} for staff members using the system.
 * </p>
 */
public enum UserType {
    /**
     * Represents a patient user type.
     */
    PATIENT,

    /**
     * Represents a staff user type.
     */
    STAFF;
}
