package filehandlers;

/**
 * Handles file operations related to doctor records.
 * This class extends the {@link FileHandler} class to handle doctor-specific file management.
 */
public class DoctorFileHandler extends FileHandler {
    /**
     * Constructs a DoctorFileHandler object that manages the doctor records file located at "Database/Doctor_Records.csv"
     */
    public DoctorFileHandler() {
        super("Database/Doctor_Records.csv");
    }
}
