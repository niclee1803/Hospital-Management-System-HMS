package managers;

import entities.User;
import filehandlers.UserFileHandler;

/**
 * The {@code UserManager} class serves as an abstract base class for managing user records.
 * It defines the core methods for creating user objects and converting them into a file-compatible format.
 * This class is extended by specific user managers (e.g., {@code PharmacistManager}, {@code PatientManager}, etc.)
 * that provide implementations for managing different types of users.
 *
 * <p>The {@code UserManager} uses a {@code FileHandler} to interact with the file system and perform
 * read and write operations on user data.</p>
 */
public abstract class UserManager {
    /**
     * The file handler used to manage data storage and retrieval.
     */
    protected UserFileHandler userFileHandler;

    /**
     * Consructs a new {@code UserManager} with the specified {@code FileHandler}. This constructor is called by subclasses
     * to intialize the {@code fileHandler} that will be used for reading and writing user data.
     * @param userFileHandler
     */
    protected UserManager(UserFileHandler userFileHandler) {
        this.userFileHandler = userFileHandler;
    }

    /**
     * Creates a {@code User} object based on the specified user ID by reading the
     * corresponding record from the file. The actual user object returned depends on
     * the specific subclass implementing this method.
     *
     * @param id The ID of the user to be created.
     * @return The {@code User} object associated with the given ID, or {@code null}
     *         if the user cannot be found or created.
     */
    public abstract User createUser(String id);

    /**
     * Converts a {@code User} object into a record array format suitable for storage
     * in a file. The actual format of the record depends on the specific subclass
     * implementing this method.
     *
     * @param user The {@code User} object to be converted to a record.
     * @return A string array representing the user record.
     * @throws IllegalArgumentException If the provided user cannot be converted
     *                                  to a record (e.g., if the user is of the wrong type).
     */
    public abstract String[] createRecordFromUser(User user);
}
