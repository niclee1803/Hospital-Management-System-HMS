package filehandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides file handling functionalities for reading and writing data to a CSV file.
 * <p>
 * This class manages reading and writing of tabular data from/to a file, where each row
 * is represented as a string array. It supports reading from and writing to the specified
 * file path, making it suitable for handling CSV files.
 * </p>
 */
public class ItemFileHandler {
    private String filePath;

    /**
     * Constructs an ItemFileHandler instance with a specified file path.
     *
     * @param filePath the path to the CSV file for reading and writing data
     */
    public ItemFileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the contents of the file and returns them as a list of rows, where each row is
     * represented as an array of strings.
     * <p>
     * The method reads the file line by line, splits each line into a string array (using commas),
     * and adds it to a list of rows. Empty lines are ignored.
     * </p>
     *
     * @return a list of string arrays representing the rows in the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<String[]> readFile() throws IOException {
        List<String[]> rows = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                // Skip any empty lines
                if (!line.trim().isEmpty()) {
                    String[] row = line.split(",", -1);
                    rows.add(row);
                }
            }
        }
        
        return rows;
    }

    /**
     * Writes the specified rows to the file.
     * <p>
     * The method writes the header (first row) followed by the remaining rows (data) to the file.
     * If no rows are provided, the method returns without writing anything. The file is overwritten
     * if it already exists.
     * </p>
     *
     * @param rows the list of rows to write to the file, where each row is an array of strings
     * @throws IOException if an I/O error occurs while writing to the file
     */

   public void writeFile(List<String[]> rows) throws IOException {
    if (rows == null || rows.isEmpty()) {
        return; // If no rows exist, just return
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
        // Write the header (first row) from the data dynamically
        String[] header = rows.get(0); // The first row is assumed to be the header
        StringBuilder headerLine = new StringBuilder();
        for (int i = 0; i < header.length; i++) {
            headerLine.append(header[i] == null ? "" : header[i]);
            if (i < header.length - 1) {
                headerLine.append(",");
            }
        }
        bw.write(headerLine.toString());
        bw.newLine();  // Move to the next line after header
        
        // Write the remaining rows (data)
        for (int i = 1; i < rows.size(); i++) {  // Start from the second row (skip the header)
            String[] row = rows.get(i);
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < row.length; j++) {
                line.append(row[j] == null ? "" : row[j]);
                if (j < row.length - 1) {
                    line.append(",");
                }
            }
            bw.write(line.toString());
            bw.newLine();
        }
    }
}
}
