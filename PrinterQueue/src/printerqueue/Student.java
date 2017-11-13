/*
 * Copyright (C) 2017 North Carolina A&T State University
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
