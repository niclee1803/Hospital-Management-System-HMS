# Hospital Management System (HMS)

## What has been implemented
* login System for Doctors and Patients, plus change their password on their first login
* View Patient Medical users for Patients and Doctors:
  * Patient can view their own medical record
  * Doctor can view medical records of patients under their care
  * 
* Doctor can update medical records (diagnoses, treatments, and prescriptions) of patients under their care
* Patient can update their contact info (Phone number and email)

## Extra features
* Password hashing (SHA256) - default pw is password

## To be implemented
* Pharmacist and Administrator Roles
* Appointments
* Medications and Inventory
* Error Handling for inputs
* 

## Packages
1. managers
   * manager/controller classes

2. login
   * Contains LoginManager, LoginFileHandler, and LoginMenu classes that handle login

3. entities
   * Contains entity classes such as users.

4. usermenus
   * boundary classes for menus
     
5. filehandlers
   * filehandler classes that handle writing/reading to/from respective database csv files

7. utility
   * Helper methods such as those to check validity of phone numbers and email addresses, and if password is long enough.

## Database (to be updated)
* Patient_Records.csv - list of patient medical record information
* Doctor_Records.csv - list of doctorIDs, their names, and their patients
* User_List.csv - list of userIDs and their corresponding passwords
* 

## Bugs to fix
* 
