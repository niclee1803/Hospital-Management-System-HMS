package utility;

import java.util.List;

/**
 * The Table class provides utility methods for printing tables to the console.
 * It is designed to format and display data in a tabular form with aligned columns.
 * The table can be printed from a list of string arrays, where each array represents a row of data.
 *
 * <p>This class handles calculating the necessary column widths, creating table borders, and formatting the data
 * within the cells for a clean and readable table output.</p>
 */
public class Table {
    
        public static void printTable(List<String[]> rows) {
        if (rows.isEmpty()) return;
    
        // Calculate the maximum width for each column
        int numColumns = rows.get(0).length;
        int[] widths = new int[numColumns];
    
        // Find the maximum width for each column
        for (String[] row : rows) {
            for (int i = 0; i < numColumns; i++) {
                // Check if the current row has enough elements
                if (i < row.length && row[i] != null) {
                    widths[i] = Math.max(widths[i], row[i].length());
                }
            }
        }
    
        // Build the top and bottom border
        StringBuilder border = new StringBuilder();
        border.append("+"); // Start with a corner line
        for (int width : widths) {
            border.append("-".repeat(width + 2)).append("+"); // Adjust width for padding
        }
    
        // Print the table
        System.out.println(); // Empty line before the table
        System.out.println(border.toString()); // Top border
    
        // Print the header row
        StringBuilder header = new StringBuilder();
        header.append("|");
        for (int i = 0; i < numColumns; i++) {
            header.append(" ").append(padRight(rows.get(0)[i], widths[i])).append(" |");
        }
        System.out.println(header.toString());
        System.out.println(border.toString()); // Divider after the header
    
        // Print each data row
        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            StringBuilder line = new StringBuilder();
            line.append("|");
            for (int j = 0; j < numColumns; j++) {
                // Check if the current row has enough elements
                if (j < row.length) {
                    line.append(" ").append(padRight(row[j], widths[j])).append(" |");
                } else {
                    line.append(" ").append(padRight("", widths[j])).append(" |"); // Fill with empty space if the element is missing
                }
            }
            System.out.println(line.toString());
        }
    
        System.out.println(border.toString()); // Bottom border
        System.out.println(); // Empty line after the table
    }

    /**
     *
     * Helper method to pad a string to the right with spaces.
     * This ensures that the string fits within the specified length.
     *
     * @param text The text to be padded.
     * @param length The desired length for the padded string.
     * @return A new string with the original text right-padded to the specified length.
     */
    public static String padRight(String text, int length) {
        return String.format("%-" + length + "s", text);
    }
}
