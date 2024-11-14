package entities;

/**
 * Represents an Administrator user in the system, inheriting the basic properties of a User.
 * This class can be extended further to add specific properties or methods related to the administrator role.
 */
public class Administrator extends User {

    /**
     * Constructs an Administrator object with the specified ID and name.
     *
     * @param id   The unique identifier for the administrator
     * @param name The name of the administrator
     */
    public Administrator(String id, String name) {
        super(id, name);
    }

    // Additional administrator-specific fields and methods can be added here
}
