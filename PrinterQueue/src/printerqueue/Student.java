/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

/**
 *
 * @author CCannon
 */
public class Student {
    private String firstName;
    private String lastName;
    private String studentID;
    private String emailAddress;
    private String phoneNumber;

    public Student(String fName, String lName, String studentID, String email, String phone) {
        this.firstName = fName;
        this.lastName = lName;
        this.studentID = studentID;
        this.emailAddress = email;
        this.phoneNumber = phone;
    }
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the studentID
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s", firstName, lastName, studentID, phoneNumber, emailAddress);
    }
    
}
