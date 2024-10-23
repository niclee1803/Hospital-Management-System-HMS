# Hospital Management System (HMS)

## What has been implemented
* Login System for Doctors and Patients
* View Patient Medical Records for Patients and Doctors:
  * Patient can view their own medical record
  * Doctor can ONLY view medical records of patients under their care

## To be implemented
* Pharmacist and Administrator Roles
* Patient can update contact info
* Doctor can update patient diagnoses and treatments
* Appointments
* Medications
* Password Hashing
*

## Packages Used
1. DatabaseManagers
   * Contains utility classes to load user records to instantiate user objects

2. Login
   * Contains a utility login class to handle login functionality.

3. Records
   * Contains user record classes that stores attributes of each user class.

4. User
   * Contains the respective user classes that has user records as the attributes, and methods relating to the functionalities of the user. They belong to an interface IHasMenu, as they all have different implementations of the displayMenu() function.

## Database (to be updated)
