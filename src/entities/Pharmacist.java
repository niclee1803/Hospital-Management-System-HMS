package entities;

import java.util.List;

public class Pharmacist extends User {

    private Gender gender;

    private int age; 

    public Pharmacist(String pharmacistID, String name, Gender gender, int age){
        super(pharmacistID, name); 
        this.gender = gender;
        this.age = age;

    }

    public Gender getgender(){
        return gender;
    }
       
    public int getAge(){
        return age; 
    }
        
    

    
}
