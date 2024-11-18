package filehandlers;

/**
 * The {@code PharmacistFileHandler} class is responsible for handling file operations related to pharmacist records
 * stored in a CSV file. This class extends the {@code FileHandler} class and specifically works with the file located at
 * "Database/Pharmacist_Records.csv".
 * <p>This class inherits methods from {@code FileHandler} for reading and writing data and can be extended to add pharmacist-
 * specific file handling functionality if needed.</p>
 */
public class PharmacistFileHandler extends UserFileHandler {

    /**
     * Constructs a {@code PharmacistFileHandler} object and sets the file path to "Database/Pharmacist_Records.csv" for managing
     * pharmacist record files.
     */
    public PharmacistFileHandler() {
        super("Database/Pharmacist_Records.csv");
    }

}
