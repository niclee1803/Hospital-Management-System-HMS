package entities;

import java.util.List;

/**
 * Represents a Pharmacist, which is a type of User. A pharmacist has a gender and an age.
 */
public class Pharmacist extends User {
    private Gender gender;
    private int age;

    /**
     * Constructs a Pharamacist object with the speciffied pharmacist ID, name, gender and age.
     * @param pharmacistID The unique ID of the pharmacist
     * @param name The name of the pharmacist
     * @param gender The gender of the pharmacist
     * @param age The age of the pharmacist
     */
    public Pharmacist(String pharmacistID, String name, Gender gender, int age){
        super(pharmacistID, name); 
        this.gender = gender;
        this.age = age;

    }

    /**
     * Gets the gender of the pharmacist.
     * @return The gender of the pharmacist
     */
    public Gender getgender(){
        return gender;
    }

    /**
     * Gets the age of the pharmacist
     * @return The age of the pharmacist
     */
    public int getAge(){
        return age; 
    }
        
    

    
}
