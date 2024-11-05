//package usermenus;
//
//import gitjava.io.FileWriter;
//import java.util.Scanner;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class PharmacistMenu implements IHasMenu {
//
//    public void displayMenu() throws Exception {
//
//
//        while (true) {
//            // Display options
//            System.out.println("\nPlease select an option:");
//            System.out.println("1. View Appointment Outcome Record");
//            System.out.println("2. Update Prescription Status");
//            System.out.println("3. View Medication Inventory");
//            System.out.println("4. Submit Replenishment Request");
//            System.out.println("5. Logout");
//
//            System.out.print("Enter your choice: ");
//            Scanner input = new Scanner(System.in);
//            int choice = input.nextInt();
//
//            if (choice == 1) {
//                System.out.println("Please enter the Appointment ID number: ");
//                int apptNum = input.nextInt();
//                viewAppointment(apptNum);
//
//            } else if (choice == 2) {
//                System.out.println("Please enter the Appointment ID number:");
//                int apptNum = input.nextInt();
//                System.out.println("Please enter the updated Prescription status (Pending/Completed) ");
//                String status = input.next();
//                updatePrescription(apptNum, status);
//
//            } else if (choice == 3) {
//                printMedicineList();
//            } else if (choice == 4) {
//
//                System.out.println("Please enter the Medication to be replenished");
//                String med = input.next();
//                System.out.println("Please enter the Number of" + med + "to be replenished");
//                int number = input.nextInt();
//                addReplenishmentRequest(med, number);
//
//            } else if (choice == 5) {
//                break;
//            } else {
//                System.out.println("Invalid Input! \n");
//            }
//
//
//        }
//
//    }
//
//    private void viewAppointment(int appointmentID) {
//        // Function to view appointment details by appointment ID
//
//        String filePath = "Appointment_Outcomes.csv";
//        String line;
//        boolean appointmentFound = false;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            // Read the header line (first line of CSV)
//            String header = br.readLine();
//            if (header != null) {
//                System.out.println("Header: " + header); // Prints the header if needed
//
//                // Loop through each line in the CSV file
//                while ((line = br.readLine()) != null) {
//                    String[] columns = line.split(","); // Assumes CSV is comma-separated
//
//                    // Check if the first column matches the appointment ID
//                    int currentID = Integer.parseInt(columns[0].trim());
//                    if (currentID == appointmentID) {
//                        // Print the required columns if appointmentID matches
//                        System.out.println("Appointment ID: " + columns[0]);
//                        System.out.println("Date: " + columns[1]);
//                        System.out.println("Service: " + columns[2]);
//                        System.out.println("Medication Name: " + columns[3]);
//                        System.out.println("Status: " + columns[4]);
//                        System.out.println("Notes: " + columns[5]);
//                        appointmentFound = true;
//                        break; // Stop searching once we find the appointment
//                    }
//                }
//            }
//
//            if (!appointmentFound) {
//                System.out.println("Appointment with ID " + appointmentID + " not found.");
//            }
//
//        } catch (IOException e) {
//            System.out.println("Error reading file: " + e.getMessage());
//        } catch (NumberFormatException e) {
//            System.out.println("Error: Appointment ID is not a valid integer.");
//        }
//    }
//
//
//    private void addReplenishmentRequest(String medicine, int amount) {
//        String filePath = "Replenishment_Requests.csv";
//
//        try (FileWriter writer = new FileWriter(filePath, true)) {  // 'true' enables appending to the file
//            // Append the new row with medicine and amount to the CSV file
//            writer.append(medicine);
//            writer.append(",");
//            writer.append(String.valueOf(amount));
//            writer.append("\n");
//
//            System.out.println("Replenishment request added successfully: Medicine = " + medicine + ", Amount = " + amount);
//        } catch (IOException e) {
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
//    }
//
//    private void printMedicineList() {
//        String filePath = "Medicine_List.csv";
//        String line;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            // Read the header line (first line of CSV)
//            String header = br.readLine();
//            if (header != null) {
//                System.out.println("Medicine Name | Initial Stock");
//                System.out.println("----------------------------");
//
//                // Loop through each row in the CSV file
//                while ((line = br.readLine()) != null) {
//                    String[] columns = line.split(","); // Assumes CSV is comma-separated
//
//                    // Assuming "Medicine Name" is in the first column and "Initial Stock" is in the second
//                    String medicineName = columns[0].trim();
//                    String initialStock = columns[1].trim();
//
//                    // Print the values under "Medicine Name" and "Initial Stock"
//                    System.out.println(medicineName + " | " + initialStock);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading file: " + e.getMessage());
//        }
//    }
//
//    private void updatePrescription(int appointmentNumber, String newStatus) {
//        String filePath = "Appointment_Outcomes.csv";
//        String line;
//        boolean appointmentFound = false;
//
//        List<String> fileContent = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            // Read the header line and add it to the file content
//            String header = br.readLine();
//            if (header != null) {
//                fileContent.add(header);
//
//                // Find the AppointmentID column index (assumes AppointmentID is first column)
//                int statusColumnIndex = -1;
//                String[] headers = header.split(",");
//                for (int i = 0; i < headers.length; i++) {
//                    if (headers[i].trim().equals("Status")) {
//                        statusColumnIndex = i;
//                        break;
//                    }
//                }
//
//                // Check if Status column was found
//                if (statusColumnIndex == -1) {
//                    System.out.println("Status column not found in the CSV file.");
//                    return;
//                }
//
//                // Loop through each row in the CSV file
//                while ((line = br.readLine()) != null) {
//                    String[] columns = line.split(",");
//
//                    // Check if the AppointmentID matches the appointmentNumber
//                    int currentAppointmentID = Integer.parseInt(columns[0].trim());
//                    if (currentAppointmentID == appointmentNumber) {
//                        // Update the Status column in this row
//                        columns[statusColumnIndex] = newStatus;
//                        appointmentFound = true;
//                    }
//
//                    // Reconstruct the line with the updated status if needed
//                    fileContent.add(String.join(",", columns));
//                }
//            }
//
//            if (!appointmentFound) {
//                System.out.println("Appointment with ID " + appointmentNumber + " not found.");
//            } else {
//                // Write the updated content back to the original file
//                try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
//                    for (String lineContent : fileContent) {
//                        bw.write(lineContent);
//                        bw.newLine();
//                    }
//                    System.out.println("Status updated successfully for Appointment ID: " + appointmentNumber);
//                }
//            }
//
//        } catch (IOException | NumberFormatException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//
//    }
//}