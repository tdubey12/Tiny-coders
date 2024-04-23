package org.launchcode.library.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends AbstractEntity {

    @NotBlank(message = "First Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String firstname;

    @NotBlank(message = "Last Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email, try again")
    private String contactEmail;

    @OneToMany
    @JoinColumn(name="student_id")
    private List<BookCheckout> bookCheckouts = new ArrayList<>();

    public Student(String firstname, String lastname, String contactEmail) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.contactEmail = contactEmail;
    }

    public Student() {

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}

