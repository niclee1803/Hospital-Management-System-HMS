package managers;

import entities.User;
import entities.Pharmacist;
import entities.Gender;
import filehandlers.PharmacistFileHandler;

/**
 * The {@code PharmacistManager} class is responsible for managing pharmacist records.
 * It extends the {@code UserManager} class and interacts with the {@code PharmacistFileHandler}
 * to read and write pharmacist data to and from a file. The class is used to create new pharmacist records
 * and convert them to the file-compatible format, as well as handling the retrieval and creation of pharmacist
 * objects from stored data.
 *
 */
public class PharmacistManager extends UserManager{

    /**
     * Constructs a new {@code PharmacistManager} and initializes the {@code PharmacistFileHandler} to handle storing
     * pharmacist records in a CSV file.
     */
     public PharmacistManager() {
        super(new PharmacistFileHandler());
     
    }

    /**
     * Creates a {@code Pharmacist} object by reading the pharmacist's data from a file based on the provided ID.
     * The method reads the CSV record, parses the pharmacist's name, gender, and age,
     * and creates a new {@code Pharmacist} instance.
     *
     * @param id The ID of the pharmacist.
     * @return A {@code Pharmacist} object with the corresponding data, or {@code null} if the pharmacist is not found
     *         or the data is incomplete.

     */
     public User createUser(String id){

        String[] record = userFileHandler.readLine(id);
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

    /**
     * Converts a {@code Pharmacist} object into a record array format suitable for storage in the CSV file.
     * The method converts all attributes to their corresponding string formats, including converting the gender
     * to a string and the age to an integer.
     *
     * @param user The {@code User} object (must be an instance of {@code Pharmacist}).
     * @return An array of strings representing the pharmacist's record.
     * @throws IllegalArgumentException If the provided user is not a {@code Pharmacist}.
     */
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
