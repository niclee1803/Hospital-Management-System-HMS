package filehandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
            br.readLine();
            String line;
            
            // Read each line in the CSV file
            while ((line = br.readLine()) != null) {
                // Split the line by commas to handle CSV format
                String[] row = line.split(",", -1);
                rows.add(row);
            }
        }
        
        return rows;
    }

    public void writeFile(List<String[]> rows) throws IOException {

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            
            for (String[] item : rows) {
                // Use a StringBuilder to construct each line
                StringBuilder line = new StringBuilder();
    
                // Loop over each field in the current row
                for (int i = 0; i < item.length; i++) {
                    // Append the current field, or an empty string if it's null
                    line.append(item[i] == null ? "" : item[i]);
    
                    // Append a comma if it's not the last field
                    if (i < item.length - 1) {
                        line.append(",");
                    }
                }
    
                // Write the constructed line to the file
                bw.write(line.toString());
                bw.newLine(); // Add a new line after each row
            }

        }

    }

}
