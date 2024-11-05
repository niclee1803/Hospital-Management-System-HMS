# Hospital Management System (HMS)

## What has been implemented
* login System for Doctors and Patients, plus change their password on their first login
* View Patient Medical users for Patients and Doctors:
  * Patient can view their own medical record
  * Doctor can view medical records of patients under their care
  * Doctor can update medical records (diagnoses, treatments, and prescriptions) of patients under their care
  * 
* Patient can view their medical record
* Patient can update their contact info (Phone number and email)
* 

## To be implemented
* Pharmacist and Administrator Roles
* Appointments
* Medications and Inventory
* Error Handling for inputs
* Password Hashing
* 

## Packages
1. managers
   * Load and store user records to the database. Create instances of user objects and update their attributes.

2. login
   * Contains LoginManager class for login interface, and PatientRepository and StaffRepository class to handle authentication.

3. users
   * Contains user record classes that stores attributes of each user class.

4. usermenus
   * Contains the respective user classes that has user records as the attributes, and methods relating to the functionalities of the user. They belong to an interface IHasMenu, as they all have different implementations of the displayMenu() function.

5. utility
   * Helper methods such as those to check validity of phone numbers and email addresses, and if password is long enough.

## Database (to be updated)
* Patient_Records.csv - list of patient medical record information
* Doctor_Records.csv - list of doctorIDs, their names, and their patients
* User_List.csv - list of userIDs and their corresponding passwords
* 

## Bugs to fix
* 
