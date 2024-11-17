# Hospital Management System (HMS)

## What has been implemented
* login System for Doctors and Patients, plus change their password on their first login
* View Patient Medical users for Patients and Doctors:
  * Patient can view their own medical record
  * Doctor can view medical records of patients under their care
  * 
* Doctor can update medical records (diagnoses, treatments, and prescriptions) of patients under their care
* Patient can update their contact info (Phone number and email)
* Appointments now has full working functionality for all users - patients, doctors*, pharmacists and admins (* doctors cannot see personal schedule and set availability for appointments just yet - need some clarification on this)

## Extra features
* Password hashing (SHA256) - default pw is password
* Validity checks (using regex)
  * password strength check when resetting password
  * valid SG phone number check
  * valid email check
  * 

## To be implemented
* Pharmacist and Administrator Roles
* Medications and Inventory
* Error Handling for inputs
* UI improvements on the appointment side

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
* Appointments.csv - list of Appointment details, including outcome records

## Bugs to fix
*

## How to run
1. Clone the repository
   
   ```sh
   git clone https://github.com/niclee1803/Hospital-Management-System-HMS.git
   ```
   
2. Navigate to the build directory
   
   ```sh
   cd Hospital-Management-System-HMS/out/production/HMS
   ```
     
3. Run the Main class
   
   ```
   java Main
   ```
