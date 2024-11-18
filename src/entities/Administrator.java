package entities;

public class Administrator extends User {
    private Gender gender;
    private int age;

    public Administrator(String id, String name, Gender gender, int age) {
        super(id, name);
        this.gender = gender;
        this.age = age;
    }


    public void setName(String name) {
        super.setName(name); // Use the User's setName method
    }
}
