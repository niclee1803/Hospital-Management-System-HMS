package entities;

/**
 * Represents an Administrator, which is a subclass of User.
 * An Administrator has additional attributes such as gender and age.
 */
public class Administrator extends User {
    private Gender gender;
    private int age;

    /**
     * Constructs a new Administrator with the specified ID, name, gender, and age.
     *
     * @param id     the unique identifier for the Administrator.
     * @param name   the name of the Administrator.
     * @param gender the gender of the Administrator.
     * @param age    the age of the Administrator.
     */
    public Administrator(String id, String name, Gender gender, int age) {
        super(id, name);
        this.gender = gender;
        this.age = age;
    }

    /**
     * Sets the name of the Administrator.
     * Overrides the setName method from the User class.
     *
     * @param name the new name for the Administrator.
     */
    @Override
    public void setName(String name) {
        super.setName(name); // Use the User's setName method
    }
}
