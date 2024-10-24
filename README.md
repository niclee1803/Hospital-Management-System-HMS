# Hospital Management System (HMS)

## What has been implemented
* login System for Doctors and Patients
* View Patient Medical users for Patients and Doctors:
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
1. DatabaseManagers
   * Contains utility classes to load user records to instantiate user objects and to store user records to the database.

2. login
   * Contains a utility login class to handle login functionality.

3. users
   * Contains user record classes that stores attributes of each user class.

4. usermenus
   * Contains the respective user classes that has user records as the attributes, and methods relating to the functionalities of the user. They belong to an interface IHasMenu, as they all have different implementations of the displayMenu() function.

5. utility
   * Helper methods such as those to check validity of phone numbers and email addresses.

## Database (to be updated)
* Patient_List.csv - list of patientIDs and their corresponding passwords
* Staff_List.csv - list of staffIDs and their corresponding passwords
* Medical_Records.csv - list of patient medical record information
* Doctor_Records.csv - list of doctorIDs, their names, and their patients
* 

## Bugs to fix
* Entering empty input leads to crashing
