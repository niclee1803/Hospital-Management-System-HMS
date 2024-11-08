package managers;

import entities.User;
import entities.Pharmacist;
import entities.Gender;
import filehandlers.PharmacistFileHandler;

public class PharmacistManager extends UserManager{

     public PharmacistManager() {
        super(new PharmacistFileHandler());
     
    }

     public User createUser(String id){

        String[] record = fileHandler.readLine(id);
        if (record == null || record.length < 4) {
            System.err.println("Pharmacist with ID " + id + " not found or has incomplete data.");
            return null;
        }
    
        // Extract data from the record
        String pharmacistId = record[0];
        String name = record[1];
        Gender gender = Gender.valueOf(record[2].toUpperCase()); // Assuming gender is stored as a string like "MALE" or "FEMALE"
        int age = Integer.parseInt(record[3]);
    
        // Create and return a new Pharmacist instance
        return new Pharmacist(pharmacistId, name, gender, age);
    
     }

    public String[] createRecordFromUser(User user){
       
            if (!(user instanceof Pharmacist pharmacist)) {
                throw new IllegalArgumentException("Provided user is not a Pharmacist.");
            }
        
            // Create a record with four fields for pharmacist data
            String[] record = new String[4];
            record[0] = pharmacist.getId();
            record[1] = pharmacist.getName();
            record[2] = pharmacist.getgender().toString(); // Assuming gender is an enum
            record[3] = String.valueOf(pharmacist.getAge());
        
            return record;
        }
        
    


    
}
