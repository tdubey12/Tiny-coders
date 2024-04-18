package org.launchcode.library.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
public class StudentBook {

    //EmbeddedId create a new id with studentId and bookId
    @EmbeddedId
    private StudentBookId id;

    //lazy fetch data whenever its needed
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "Student_Id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "Book_Id")
    private Book book;

    private boolean isHold;

    private boolean isCheckOut;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date heldUntilDate;

    public StudentBookId getId() {
        return id;
    }
    public void setId(StudentBookId id) {
        this.id = id;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public boolean isHold() {
        return isHold;
    }
    public void setHold(boolean hold) {
        isHold = hold;
    }
    public boolean isCheckOut() {
        return isCheckOut;
    }
    public void setCheckOut(boolean checkOut) {
        isCheckOut = checkOut;
    }
    public Date getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getHeldUntilDate() {
        return heldUntilDate;
    }

    public void setHeldUntilDate(Date heldUntilDate) {
        this.heldUntilDate = heldUntilDate;
    }
}
