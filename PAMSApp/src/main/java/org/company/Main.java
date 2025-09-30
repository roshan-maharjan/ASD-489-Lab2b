package org.company;

import org.company.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // --- 1. Initialize Array of Patient Objects ---

        // Using null for missing data fields as per the table
        Patient[] patientsArray = new Patient[]{
                new Patient(1, "Daniel", "Agar", "(641) 123-0009", "dagar@m.as", "1 N Street", "1987-1-19"),
                new Patient(2, "Ana", "Smith", null, "amsith@te.edu", null, "1948-12-5"),
                new Patient(3, "Marcus", "Garvey", "(123) 292-0018", null, "4 East Ave", "2001-9-18"),
                new Patient(4, "Jeff", "Goldbloom", "(999) 165-1192", "jgold@es.co.za", null, "1995-2-28"),
                new Patient(5, "Mary", "Washington", null, null, "30 W Burlington", "1932-5-31")
        };

        // Convert array to List for easier sorting
        List<Patient> patientsList = Arrays.asList(patientsArray);

        // --- 2. Sort Patients by Age in Descending Order ---
        // Oldest Patient (Highest Age) first, Youngest last.
        patientsList.sort(Comparator.comparing(Patient::getAge).reversed());

        System.out.println("Patients sorted by Age (Descending):");
        patientsList.forEach(p -> System.out.printf("  %s %s - Age: %d%n", p.getFirstName(), p.getLastName(), p.getAge()));


        // --- 3. Convert Data to JSON and Write to File ---

        // Jackson ObjectMapper is the core class for JSON serialization/deserialization
        ObjectMapper mapper = new ObjectMapper();

        // Configure the mapper for pretty printing the JSON output
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Define the output file path
        String outputFilePath = "patients_sorted_by_age.json";
        File outputFile = new File(outputFilePath);

        try {
            // Write the list of Patient objects to the file as JSON
            mapper.writeValue(outputFile, patientsList);

            System.out.println("\nSuccessfully wrote " + patientsList.size() + " patients to JSON file:");
            System.out.println("  File Path: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}