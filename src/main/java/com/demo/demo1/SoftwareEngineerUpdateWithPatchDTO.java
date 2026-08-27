package com.demo.demo1;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SoftwareEngineerUpdateWithPatchDTO {
    private String firstName;
    private String lastName;

    @Email(message="Enter a valid email")
    private String email;

    @Pattern(
    regexp = "^[0-9]{10}$",
    message = "Phone number must contain exactly 10 digits")
    private String phoneNumber;
    
    @Past(message="Date of birth should be in the past.")
    private LocalDate dateOfBirth;

    @Size(max = 100, message = "Tech stack cannot contain more than 100 items")
    private List<String> techstack;

        public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getTechstack() {
        return techstack;
    }

    public void setTechstack(List<String> techstack) {
        this.techstack = techstack;
    }
   
}

