package filehandlers;

/**
 * Handles file operations related to administrator records.
 * This class extends the {@link FileHandler} class to handle administrator-specific file management.
 */
public class AdministratorFileHandler extends FileHandler {

    /**
     * Constructs an AdministratorFileHandler object that manages the administrator records file located at "Database/Administrator_Records.csv".
     */
    public AdministratorFileHandler() {
        super("Database/User_List.csv"); // Administrator-specific CSV file path
    }
}
