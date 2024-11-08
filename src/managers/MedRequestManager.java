package managers;

import java.io.IOException;

import entities.MedRequests;
import filehandlers.MedRequestFileHandler;

public class MedRequestManager {

    private MedRequestFileHandler fileHandler;

    // Constructor to initialize MedRequestFileHandler
    public MedRequestManager() {
        this.fileHandler = new MedRequestFileHandler();
    }

    /**
     * Creates a new MedRequest object and converts it to an array,
     * then uses the file handler to add it to the CSV file.
     * 
     * @param medicine Name of the medicine
     * @param amount Amount requested
     * @param unit Unit of the medicine (e.g., tablets, bottles)
     * @param status Status of the request (e.g., Pending, Approved)
     * @throws IOException if the request cannot be added to the file
     */
    public void createNewRequest(String medicine, int amount, String unit, String status) throws IOException {
        // Create a new MedRequest object
        MedRequests newRequest = new MedRequests(medicine, amount, unit, status);

        // Convert MedRequest to array format
        String[] requestData = newRequest.toArray();

        // Use the file handler to add the request to the CSV file
        fileHandler.addRequest(requestData);
        System.out.println("Request for " + medicine + " added successfully.");
    }


    
}
