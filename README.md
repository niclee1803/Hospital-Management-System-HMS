# Hospital Management System (HMS)

## What has been implemented
* Login System for Doctors and Patients
* View Patient Medical User for Patients and Doctors:
  * Patient can view their own medical record
  * Doctor can ONLY view medical records of patientMenus under their care
  * 
* Patient can update their contact info (Phone number and email)
* 

## To be implemented
* Pharmacist and Administrator Roles
* Doctor can update patient diagnoses and treatments
* Appointments
* Medications
* Error Handling for inputs
* Password Hashing
* Make password invisible when entering
* 

## Packages
1. Managers
   * Load and store user records to the database. Create instances of user objects and update their attributes.

2. Login
   * Contains LoginManager class for login interface, and PatientRepository and StaffRepository class to handle authentication.

3. User
   * Contains user record classes that stores attributes of each user class.

4. UserMenus
   * Contains the respective user classes that has user records as the attributes, and methods relating to the functionalities of the user. They belong to an interface IHasMenu, as they all have different implementations of the displayMenu() function.

5. Utility
   * Helper methods such as those to check validity of phone numbers and email addresses.

## Database (to be updated)
* Patient_List.csv - list of patientIDs and their corresponding passwords
* Staff_List.csv - list of staffIDs and their corresponding passwords
* Medical_Records.csv - list of patient medical record information
* Doctor_Records.csv - list of doctorIDs, their names, and their patients
* 

## Bugs to fix
* Entering empty input leads to crashing
