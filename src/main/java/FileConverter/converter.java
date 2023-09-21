package FileConverter;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class converter {
    String route;
    String Students;


    public void OpenAndConverter() {
        try {
            String file = JOptionPane.showInputDialog("Enter the file");

            // Check if the extension is ".csv"
            if (!file.endsWith(".csv")) {
                System.out.println("The file extension is NOT .csv");
                return;
            }

            // The original file extension is removed
            route = file.substring(0, file.length() - 4);

            // Open the file using FileReader and BufferedReader
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            Students = "["; // The character is added to manually create the structure of an array

            // Read the file line by line
            while ((line = bufferedReader.readLine()) != null) {
                createJson(line);
            }

            // Close the BufferedReader and FileReader
            bufferedReader.close();
            fileReader.close();

            // The last character, which is a comma, is removed and the closure of the array is added
            Students = Students.substring(0, Students.length() - 1);
            Students = Students + "]";
            saveJSON();

        } catch (Exception e) {
            System.err.println("Invalid file");
        }

    }

    public void createJson(String line) {
        String[] separated = line.split(",");
        if (separated.length == 3) {
            int id = Integer.parseInt(separated[0]);
            String name = separated[1];
            String surname = separated[2];

            String student = String.format("{\"id\": \"%d\", \"name\": \"%s\", \"surname\": \"%s\"},", id, name, surname);
            Students = Students + student;

        } else {
            // If the line does not contain the 3 attributes, it generates an error message
            System.out.println("The file contains errors");
        }
    }

    public void saveJSON() {
        String routeJSON = route + ".json"; // The new extension is added
        try (FileWriter fileWriter = new FileWriter(routeJSON)) {
            // Write the JSON string to the file
            fileWriter.write(Students);
            System.out.println("JSON file successfully exported to " + routeJSON);
        } catch (IOException e) {
            // Exception handling if any error occurs while writing the file
            System.out.println(e.getMessage());
        }
    }
}
