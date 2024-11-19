# Hospital Management System (HMS)

## Overview
HMS is a command-line-interface application designed in Java to help healthcare providers and patients manage and update medical records, appointments, and medicine inventory. 

## Features
### User Authentication
* Login System for Doctors and Patients with the ability to reset passwords upon first login.
* Password Hashing (SHA256) for secure authentication. Default password is "password."

### Medical Record Access
* Patients can:
  * View their own medical record.
  * Update their contact information (Phone number, Email).
* Doctors can:
  * View medical records of patients under their care.
  * Update patient medical records (e.g., diagnoses, treatments, prescriptions).

### Appointment Management

### Medication Inventory Management

### Staff Management

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
