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
package printerqueueviewcontroller;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import printerqueue.Student;

/**
 *
 * @author CCannon
 */
public class AddStudentTextInputDialog extends Dialog<Student> {
    private ButtonType add = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    public AddStudentTextInputDialog() {
        setTitle("Add Student");
        setHeaderText("Complete the following form to register with the 3D Printer Queue");
        
        GridPane studentInfoPane = new GridPane();
        Label firstNameLabel = new Label("First Name");
        Label lastNameLabel = new Label("Last Name");
        Label studentIDLabel = new Label("Student ID");
        Label emailAddressLabel = new Label("Email Address");
        Label phoneNumberLabel = new Label("Phone Number");
      
        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();
        TextField studentIDTextField = new TextField();
        TextField emailAddressTextField = new TextField();
        TextField phoneNumberTextField = new TextField();
        
        studentInfoPane.add(firstNameLabel, 0, 0);
        studentInfoPane.add(firstNameTextField, 1, 0);
        studentInfoPane.add(lastNameLabel, 0, 1);
        studentInfoPane.add(lastNameTextField, 1, 1);
        studentInfoPane.add(studentIDLabel, 0, 2);
        studentInfoPane.add(studentIDTextField, 1, 2);
        studentInfoPane.add(emailAddressLabel, 0, 3);
        studentInfoPane.add(emailAddressTextField, 1, 3);
        studentInfoPane.add(phoneNumberLabel, 0, 4);
        studentInfoPane.add(phoneNumberTextField, 1, 4);
        
        getDialogPane().getButtonTypes().addAll(add, cancel);
        getDialogPane().setContent(studentInfoPane);
        
        setResultConverter((ButtonType button) -> {
            return new Student(firstNameTextField.getText(), lastNameTextField.getText(), studentIDTextField.getText(), emailAddressTextField.getText(), phoneNumberTextField.getText());
        });
    }
}
