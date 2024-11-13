package filehandlers;

import java.io.File;

/**
 * The {@code PharmacistFileHandler} class is responsible for handling file operations related to pharmacist records
 * stored in a CSV file. This class extedns the {@code FileHandler} class and specifically works with the file located at
 * "Database/Pharmacist_list.csv"
 * <p>This class inherits methods from {@code FileHandler} for reading and writing data and can be etended to add pharmacist-
 * specific file handling functionality if needed.</p>
 */
public class PharmacistFileHandler extends FileHandler{

    /**
     * Constructs a {@code PharmacistFileHandler} object and sets the file path to "Database/Pharmacist_list.csv" for managing
     * pharmacist record files.
     * <p>This constructor calls the parent {@code FileHandler} constructor to initialize the file path for pharmacist-
     * related data.</p>
     */
    public PharmacistFileHandler() {
        super("Database/Pharmacist_list.csv");
    }




}