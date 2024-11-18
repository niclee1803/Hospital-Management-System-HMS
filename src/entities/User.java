package entities;

/**
 * Represents a User with an ID and a name. This class can be extended to create specific type of users.
 */
public class User {
    private String id;
    private String name;

    /**
     * Constructs a User object with the specified ID and name.
     * @param id The unique identifier for the user
     * @param name The name of the user
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the ID of the user
     * @return The user's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the user
     * @return The user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user
     * @param name new name for the user
     */
    public void setName(String name) {
        this.name = name;
    }
}
