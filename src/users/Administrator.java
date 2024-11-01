package users;

import java.util.List;

public class Administrator {
    private String adminID;
    private String name;

    public Administrator(String adminID, String name, List<Patient> patients) {
        this.adminID = adminID;
        this.name = name;
    }

    public String getAdminID() {
        return adminID;
    }

    public String getName() {
        return name;
    }
}
