package filehandlers;

import java.io.IOException;
import java.util.List;

/**
 * The {@code MedRequestFileHandler} class is responsible for handling the writing and reading of
 * replenishment requests in a CSV file. It extends {@code ItemFileHandler} to reuse file
 * reading and writing functionalities.
 */
public class MedRequestFileHandler extends ItemFileHandler {

    // Fixed file path for the replenishment requests CSV file
    private static final String FILE_PATH = "Replenishment_Requests.csv";

    /**
     * Default constructor for {@code MedRequestFileHandler}.
     * Initializes the handler with the predefined file path.
     */
    public MedRequestFileHandler() {
        super(FILE_PATH);
    }

    /**
     * Appends a new row to the file.
     * The row is provided as an array with columns: Medicine, Amount, Unit, and Status.
     *
     * @param requestData The data to be added as a row. The array should contain the values:
     *                    [Medicine, Amount, Unit, Status].
     * @throws IOException              if the file cannot be written to.
     * @throws IllegalArgumentException if the input data is invalid.
     */
    public void addRequest(String[] requestData) throws IOException {
        if (requestData == null || requestData.length != 4) {
            throw new IllegalArgumentException("Request data must have exactly 4 elements: Medicine, Amount, Unit, Status.");
        }

        // Read existing rows to preserve existing data
        List<String[]> rows = readFile();
        rows.add(requestData);

        // Write all rows back to the file, including the new request
        writeFile(rows);
    }
}
