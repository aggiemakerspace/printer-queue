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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import printerqueue.PrintJob;
import printerqueue.PrintStatus;
import printerqueue.PrintType;

/**
 *
 * @author CCannon
 */
public class PrintJobViewController extends Stage {

    private PrintJob printJob;
    
    private TextField stlPathTextField;
    private ComboBox statusComboBox;
    private TextField typeTextField;
    private TextField dueDateTextField;
    private TextArea requestCommentsTextArea;
    private TextArea printCommentsTextArea;
    
    private TextField requestingStudentNameTextField;
    private TextField requestingStudentEmailTextField;
    private TextField requestingStudentPhoneTextField;
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    public PrintJobViewController(){
        this.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                close();
            }
            
        });
        GridPane root = new GridPane();
        
        Label stlPathLabel = new Label(".stl File Path");
        stlPathTextField = new TextField();
        stlPathTextField.setEditable(false);
        Label statusLabel = new Label("Print Status");
        statusComboBox = new ComboBox();
        ArrayList<String> values = new ArrayList();
        for(PrintStatus type : PrintStatus.values()){
            values.add(type.name());
        }
        statusComboBox.setItems(FXCollections.observableList(values));
        Label typeLabel = new Label("Print Type");
        typeTextField = new TextField();
        typeTextField.setEditable(false);
        Label dueDateLabel = new Label("Due Date");
        dueDateTextField = new TextField();
        dueDateTextField.setEditable(false);
        Label requestCommentsLabel = new Label("Request Comments");
        requestCommentsTextArea = new TextArea();
        
        Label requestingStudentNameLabel = new Label("Requesting Student");
        requestingStudentNameTextField = new TextField();
        requestingStudentNameTextField.setEditable(false);
        Label requestingStudentEmailLabel = new Label("Email");
        requestingStudentEmailTextField = new TextField();
        requestingStudentEmailTextField.setEditable(false);
        Label requestingStudentPhoneLabel = new Label("Phone Number");
        requestingStudentPhoneTextField = new TextField();
        requestingStudentPhoneTextField.setEditable(false);
        
        Label printCommentsLabel = new Label("Print Comments");
        printCommentsTextArea = new TextArea();
        
        Button savePrintCommentsButton = new Button("Save comments");
        savePrintCommentsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printJob.setPrintComments(printCommentsTextArea.getText());
                printJob.setComments(requestCommentsTextArea.getText());
            }
            
        });
        
        Button savePrintStatusButton = new Button("Save Print Status");
        savePrintStatusButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printJob.setStatus(PrintStatus.valueOf((String)statusComboBox.getSelectionModel().getSelectedItem()));
            }
            
        });
        Button closeButton = new Button("Close");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.fireEvent(new WindowEvent(null, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
            
        });
        
        root.add(stlPathLabel, 0, 0);
        root.add(stlPathTextField, 1, 0);
        root.add(statusLabel, 0, 1);
        root.add(statusComboBox, 1, 1);
        root.add(typeLabel, 0, 2);
        root.add(typeTextField, 1, 2);
        root.add(dueDateLabel, 0, 3);
        root.add(dueDateTextField, 1, 3);
        root.add(requestCommentsLabel, 0, 4);
        root.add(requestCommentsTextArea, 1, 4);
        
        root.add(requestingStudentNameLabel, 3, 0);
        root.add(requestingStudentNameTextField, 4, 0);
        root.add(requestingStudentPhoneLabel, 3, 1);
        root.add(requestingStudentPhoneTextField, 4, 1);
        root.add(requestingStudentEmailLabel, 3, 2);
        root.add(requestingStudentEmailTextField, 4, 2);
        
        root.add(printCommentsLabel, 0, 8);
        root.add(printCommentsTextArea, 1, 8);
        
        HBox saveButtons = new HBox();
        saveButtons.getChildren().addAll(savePrintCommentsButton, savePrintStatusButton);
        root.add(saveButtons, 1, 9);
        root.add(closeButton, 3, 9);
        
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

    /**
     * @return the printJob
     */
    public PrintJob getPrintJob() {
        return printJob;
    }

    /**
     * @param printJob the printJob to set
     */
    public void setPrintJob(PrintJob printJob) {
        this.printJob = printJob;
        this.stlPathTextField.setText(printJob.getStlPath());
        this.typeTextField.setText(printJob.getType().name());
        this.dueDateTextField.setText(dateFormat.format(printJob.getDueDate()));
        this.requestCommentsTextArea.setText(printJob.getComments());
        this.printCommentsTextArea.setText(printJob.getPrintComments());
        this.statusComboBox.getSelectionModel().select(printJob.getStatus());
        
        this.requestingStudentNameTextField.setText(printJob.getStudent().getFirstName() + " " + printJob.getStudent().getLastName());
        this.requestingStudentPhoneTextField.setText(printJob.getStudent().getPhoneNumber());
        this.requestingStudentEmailTextField.setText(printJob.getStudent().getEmailAddress());
    }
    
}
