package filehandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ItemFileHandler {

    private String FILE_PATH;

    public ItemFileHandler(String s) {
        FILE_PATH = s;
    }
    
    public List<String[]> readFile() throws IOException {
        List<String[]> rows = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            
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
    
   public void writeFile(List<String[]> rows) throws IOException {
    if (rows == null || rows.isEmpty()) {
        return; // If no rows exist, just return
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
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
