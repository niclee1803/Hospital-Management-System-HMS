package managers;

import entities.Administrator;
import entities.User;
import filehandlers.AdministratorFileHandler;

import java.util.List;
import java.util.Scanner;

/**
* The {@code AdministratorManager} class is responsible for managing administrator records.
* It extends the {@link UserManager} class to provide specific functionality for creating and managing administrator users.
*/
public class AdministratorManager extends UserManager {

   /**
    * Constructs a new {@code AdministratorManager} with an associated {@code AdministratorFileHandler}.
    */
   public AdministratorManager() {
       super(new AdministratorFileHandler());
   }

   /**
    * Creates an {@code Administrator} user from the given ID. The method retrieves the administrator's data
    * and returns a populated {@code Administrator} object.
    *
    * @param id The unique identifier for the administrator.
    * @return The {@code Administrator} object with populated data.
    */
   @Override
   public User createUser(String id) {
       String[] record = fileHandler.readLine(id);
       if (record == null) {
           System.out.println("Administrator with ID " + id + " not found.");
           return null;
       }
       return new Administrator(record[0], record[1]);
   }

   /**
    * Generates a record array from the provided {@code Administrator} object.
    * The array represents the administrator's ID and name.
    *
    * @param user The {@code User} object to convert into a record.
    * @return A string array representing the administrator's record.
    * @throws IllegalArgumentException if the provided user is not an {@code Administrator}.
    */
   @Override
   public String[] createRecordFromUser(User user) {
       if (!(user instanceof Administrator admin)) {
           throw new IllegalArgumentException("Provided user is not an Administrator.");
       }
       return new String[]{admin.getId(), admin.getName()};
   }

   /**
    * Displays all administrators by reading all records from the file and printing their details.
    */
   public void displayAllAdministrators() {
       List<String[]> records = fileHandler.readAllLines();
       if (records == null || records.isEmpty()) {
           System.out.println("No administrator records found.");
           return;
       }

       System.out.println("Administrator List:");
       for (String[] record : records) {
           if (record.length >= 2) {
               System.out.println("ID: " + record[0] + ", Name: " + record[1]);
           }
       }
   }

   /**
    * Adds a new administrator to the system by prompting the user for details.
    * @param sc The Scanner object for user input.
    */
   public void addAdministrator(Scanner sc) {
       System.out.print("Enter Administrator ID: ");
       String id = sc.nextLine();
       System.out.print("Enter Administrator Name: ");
       String name = sc.nextLine();

       Administrator admin = new Administrator(id, name);
       fileHandler.writeLine(createRecordFromUser(admin));
       System.out.println("Administrator added successfully.");
   }

   /**
    * Updates an existing administrator's details.
    * @param sc The Scanner object for user input.
    */
   public void updateAdministrator(Scanner sc) {
       System.out.print("Enter Administrator ID to update: ");
       String id = sc.nextLine();

       User user = createUser(id);
       if (user == null) {
           System.out.println("Administrator not found.");
           return;
       }

       System.out.print("Enter new name for Administrator (or press Enter to keep current name): ");
       String newName = sc.nextLine();

       Administrator admin = (Administrator) user;
       if (!newName.isEmpty()) {
           admin.setName(newName);
       }

       fileHandler.updateLine(createRecordFromUser(admin));
       System.out.println("Administrator updated successfully.");
   }

   /**
    * Removes an administrator from the system by their ID.
    * @param sc The Scanner object for user input.
    */
   public void removeAdministrator(Scanner sc) {
       System.out.print("Enter Administrator ID to remove: ");
       String id = sc.nextLine();

       fileHandler.deleteLine(id);
       System.out.println("Administrator removed successfully.");
   }
}
