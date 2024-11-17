package filehandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MedRequestFileHandler} class is responsible for handling the writing of replenishment requests to CSV file.
 * The CSV file contains the following columns: Medicine, Amount, Unit, and Status. Each request is appended as a new row
 * in the file.
 */
public class MedRequestFileHandler {

      private static final String FILE_PATH = "Database/Replenishment_Requests.csv";

    /**
     * Appends a new row to the Replenishment_Requests.csv file.
     * The row is provided as an array with columns: Medicine, Amount, Unit, and Status.
     * 
     * @param requestData The data to be added as a row. The array should contain the values:
     *                   [Medicine, Amount, Unit, Status].
     * @throws IOException if the file cannot be written to.
     */
    public void addRequest(String[] requestData) throws IOException {
        if (requestData == null || requestData.length != 4) {
            throw new IllegalArgumentException("Request data must have exactly 4 elements: Medicine, Amount, Unit, Status.");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Convert the array to a CSV row format
            String row = String.join(",", requestData);

            // Write the row to the file, followed by a new line
            bw.write(row);
            bw.newLine();
        }
    }

    public List<String[]> readRequests() throws IOException {
    List<String[]> requests = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
        String line;
        while ((line = br.readLine()) != null) {
            requests.add(line.split(","));
        }
    }
    return requests;
}

public void updateRequests(List<String[]> requests) throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
        for (String[] request : requests) {
            bw.write(String.join(",", request));
            bw.newLine();
        }
    }
}

    
}
