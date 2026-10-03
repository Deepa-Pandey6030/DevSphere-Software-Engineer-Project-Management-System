package com.demo.demo1.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public class SoftwareEngineerCreateDTO {
    @NotBlank(message="First name is required")
    private String firstName;

    @NotBlank(message="Last Name is required")
    private String lastName;

    @NotBlank(message="Email is required")
    @Email(message="Enter a valid email")
    private String email;

    @NotBlank(message="Phone numer is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits")
    private String phoneNumber;
    
    @NotNull(message="Date of birth is required")
    @Past(message="Date of birth should be in the past.")
    private LocalDate dateOfBirth;

    @NotNull(message="Joining date is required")
    private LocalDate joiningDate;

    @NotBlank(message="Designation is required")
    private String designation;

    @NotBlank(message="Employment status is required")
    private String employmentStatus;

    @NotBlank(message="Location is required")
    private String location;

    @NotNull(message="Experience is required")
    private Integer experience;

    private Integer departmentId;
    private Integer managerId;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getExperience() { return experience; }
    public void setExperience(Integer experience) { this.experience = experience; }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
}
