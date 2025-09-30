package org.company;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// Annotations to control the order of fields in the final JSON output
@JsonPropertyOrder({ "id", "firstName", "lastName", "age", "contactPhone", "email", "mailingAddress", "dateOfBirth" })
public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String contactPhone;
    private String email;
    private String mailingAddress;

    // Store Date of Birth as a LocalDate object for easy age calculation
    @JsonIgnore // Exclude this field from direct JSON serialization
    private LocalDate dateOfBirth;

    // Field to hold the calculated age, which will be included in JSON
    private int age;

    // Formatter for parsing the date string from the data table
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-d");

    // Constructor to initialize all fields, including parsing the Date of Birth string
    public Patient(int id, String firstName, String lastName, String contactPhone, String email, String mailingAddress, String dateOfBirthStr) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactPhone = contactPhone;
        this.email = email;
        this.mailingAddress = mailingAddress;

        // Parse the string into LocalDate
        if (dateOfBirthStr != null && !dateOfBirthStr.trim().isEmpty()) {
            this.dateOfBirth = LocalDate.parse(dateOfBirthStr, DATE_FORMATTER);
            this.age = calculateAge(); // Calculate age upon creation
        }
    }

    // Default constructor for Jackson deserialization (best practice)
    public Patient() {
    }

    // Private method to calculate age
    private int calculateAge() {
        if (this.dateOfBirth == null) {
            return 0; // Or throw an exception
        }
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    /* === Getters (Required for Jackson JSON serialization) === */

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    // Public getter for the age (this will be serialized to JSON)
    public int getAge() {
        return age;
    }

    // Public getter for dateOfBirth string (to show in JSON)
    // NOTE: This uses the LocalDate object to format a string for JSON output
    public String getDateOfBirth() {
        return dateOfBirth != null ? dateOfBirth.format(DATE_FORMATTER) : null;
    }

    /* === Setters (Omitted for brevity, but needed in a full application) === */
}