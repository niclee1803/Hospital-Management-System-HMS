package filehandlers;

/**
 * The {@code PatientFileHandler} class is responsible for handling file operations related to patient records stored in a
 * CSV file. This class extends the {@code FileHandler} class and works specifically with the file located at
 * "Database/Patient_Records.csv"
 *<p>This class inherits the core file handling methods from {@code FileHandler} and uses the file path for patient records
 * in the constructor</p>
 */
public class PatientFileHandler extends UserFileHandler {
    /**
     * Constructs a {@code PatientFileHandler} object and sets the file path to "Database/Patient_Record.csv" for managing
     * patient record files.
     * <p>This constructor calls the parent {@code FileHandler} constructor to initialize the file path for handling
     * patient-related data.</p>
     */
    public PatientFileHandler() {
        super("Database/Patient_Records.csv");
    }
}
