# Hospital Management System (HMS) :hospital:

## Overview
HMS is a command-line-interface application designed in Java to help healthcare providers and patients manage and update medical records, appointments, and medicine inventory. 

## Features
### User Authentication :lock:
* Login System for Doctors and Patients.
* Users can change their default password after the first login for added security.
* Password Hashing (SHA256) for secure authentication. Default password is "password."

### Medical Record Management :ledger:
* Patients can:
  * View their medical records, including personal information such as ID, name, birth date, contact information, and blood 
    type
  * Update non-medical personal information such as email address and phone number.
* Doctors can:
  * View medical records of patients under their care.
  * Update patient medical records of patients under their care (e.g., diagnoses, treatments, prescriptions).

### Appointment Management :calendar:
* Patients can:
  * View available appointment slots with doctors.
  * Schedule new appointments by selecting a doctor, date, and time.
  * Reschedule existing appointments to a new time slot, ensuring no conflicts.
  * Cancel an existing appointment and view its status.
  * View the outcome records of past appointments.
* Doctors can:
  * View and manage their schedule and availability.
  * Accept or decline patient appointment requests.
  * Record the outcome of completed appointments, including service type, prescribed medications, and consultation notes.

### Medication Inventory Management :pill:
* Pharmacists can:
  * View and manage the inventory of medications.
  * Update the status of medication prescriptions (e.g., pending to dispensed).
  * Monitor stock levels and submit replenishment requests to administrators when stock levels are low.
* Administrators can:
  * View and update the inventory, including adding or removing medications.
  * Approve replenishment requests made by pharmacists.

### Staff Management :man_with_gua_pi_mao:
* Administrators can:
  * Manage hospital staff (Doctors and Pharmacists) by adding, updating, or removing staff members.
  * View staff information, filter by role, gender, age, etc.


## Set-up Instructions
### Prerequisites
* JDK 22 or higher

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

The list of usernames are in the [User_List.csv](/Database/User_List.csv) file in the Database folder.
 Default password for all users is "password".

## Documentation
### UML Diagram
* [UML Diagram](/UML Diagram/UML HMS.jpg)

### Javadocs
* The application includes a comprehensive set of [Javadocs](/docs/index.html) to help developers understand the structure and functionality of the code.
