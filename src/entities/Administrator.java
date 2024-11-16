package entities;

public class Administrator extends User {
    private String role;

    public Administrator(String id, String name, String role) {
        super(id, name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.setName(name); // Use the User's setName method
    }
}
