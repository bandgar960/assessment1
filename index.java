
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class index {
    private static final String METADATA_FILE = "metadata.txt";

    public static void main(String[] args) {
        // Create table
        String createTableQuery = "CREATE TABLE my_table (col1 INTEGER, col2 STRING, col3 INTEGER)";
        parseCreateTableQuery(createTableQuery);

        // Insert into table
        String insertQuery = "INSERT INTO my_table VALUES (1, 'Pravin Bandgar', 21)";
        parseInsertQuery(insertQuery);
    }

    private static void parseCreateTableQuery(String query) {
        String tableName = query.substring(query.indexOf("CREATE TABLE") + 13, query.indexOf("(")).trim();
        String columnsString = query.substring(query.indexOf("(") + 1, query.lastIndexOf(")"));
        List<String> columnList = Arrays.asList(columnsString.split(","));
        List<String> columnNames = new ArrayList<>();
        List<String> columnTypes = new ArrayList<>();

        for (String column : columnList) {
            String[] parts = column.trim().split(" ");
            columnNames.add(parts[0]);
            columnTypes.add(parts[1]);
        }

        // Save metadata to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(METADATA_FILE, true))) {
            writer.write(tableName + "\n");
            for (int i = 0; i < columnNames.size(); i++) {
                writer.write(columnNames.get(i) + " " + columnTypes.get(i) + "\n");
            }
            writer.write("\n");
            System.out.println("Table created successfully.");
        } catch (IOException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    private static void parseInsertQuery(String query) {
        String tableName = query.substring(query.indexOf("INSERT INTO") + 12, query.indexOf("VALUES")).trim();
        String valuesString = query.substring(query.indexOf("VALUES") + 6);
        List<String> valuesList = Arrays.asList(valuesString.split(","));

        // Save values to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tableName + ".txt", true))) {
            for (String value : valuesList) {
                writer.write(value.trim() + "\t");
            }
            writer.write("\n");
            System.out.println("Data inserted successfully.");
        } catch (IOException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }
}